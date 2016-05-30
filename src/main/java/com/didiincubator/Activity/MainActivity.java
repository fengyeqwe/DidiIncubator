package com.didiincubator.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.didiincubator.Beans.InMapInfo;
import com.didiincubator.R;
import com.didiincubator.View.DetailActivity;
import com.didiincubator.utils.HistoryHelper;
import com.didiincubator.utils.HistoryTable;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class MainActivity extends AppCompatActivity {
    Context context;
    MapView mapView;
    BaiduMap baiduMap;
    RelativeLayout relativeLayout;

    //定位
    LocationClient locationClient;//LocationClient类是定位SDK的核心类
    MyLocation myLocation;
    boolean isFirstIn=true;//判断是否第一次定位，默认为第一次
    double myLatitude;//经纬度
    double myLongitude;
    float mCurrentX;//角度值
    MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    private float mCurrentAccracy;//定位范围
    //BitmapDescriptor descriptor;//定位自定义图标
    //MyOrientationListener myOrientationListener ;

    //标注物
    BitmapDescriptor labelDescriptor=null;//标注物图标
    Marker marker;
    List<InMapInfo> list;
    ImageView imageView;
    TextView tv_name,tv_type,tv_sketch,tv_number,tv_distance;

    //左侧滑控件
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //声明数据库操作类
    SQLiteDatabase mDataBase;
    //声明数据库辅助类对象
    HistoryHelper mHistoryHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        //去除actionbar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
       //SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        this.context=this;
        mapView= (MapView) findViewById(R.id.didi_mapView);
        initView();//初始化视图
        //初始化左侧滑中的控件
        drawerLayout= (DrawerLayout) findViewById(R.id.dl_left);
        navigationView= (NavigationView) findViewById(R.id.nv_left);
        toolbar= (Toolbar) findViewById(R.id.toolbar_left);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        //设置抽屉DrawerLayout;
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();//初始化状态
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        initNavigation();
        initMyLocation();
        initData();//初始化
        initOverlay(list);
        initMarkerListener();//监听事件



    }



    private void initView() {
        //初始化标注物中的控件
        imageView= (ImageView) findViewById(R.id.iv_marker);
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_type= (TextView) findViewById(R.id.tv_type);
        tv_sketch= (TextView) findViewById(R.id.tv_sketch);
        tv_number= (TextView) findViewById(R.id.tv_number);
        tv_distance= (TextView) findViewById(R.id.tv_distance);
        baiduMap=mapView.getMap();
        //改变标尺大小
        MapStatusUpdate factory= MapStatusUpdateFactory.zoomTo(15.0f);//500米
        baiduMap.setMapStatus(factory);

        labelDescriptor= BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
        relativeLayout= (RelativeLayout) findViewById(R.id.rl_marker);
        //初始化historyHelper
        mHistoryHelper=new HistoryHelper(MainActivity.this);


    }
    private void initData() {
            list=new ArrayList<>();
        list.add(new InMapInfo(31.265884,120.751372, R.drawable.intor1,"bootcamp创业孵化器",
                "科技","xxxxxxxxxxx",100,
                "1.7公里"));
        list.add(new InMapInfo(31.271005,120.63504,R.drawable.intor2,"苏州吴中国家级科技企业孵化器","科技",
                "xxxxxxxxxxxxxx",200,"10公里"));
        list.add(new InMapInfo(31.280021,120.75086,R.drawable.intor3,"苏州高博","软件",
                "xxxxxxxxxxxxxx",200,"100米"));
    }
    private void initOverlay(List<InMapInfo> list) {

        //TODO 从数据库获取数据解析
        baiduMap.clear();
        LatLng latLng=null;
        for (InMapInfo info:list) {
            latLng=new LatLng(info.getLatitude(),info.getLongtitude());//位置
            //图标
            OverlayOptions overlayOptions=new MarkerOptions().position(latLng).icon(labelDescriptor).zIndex(5);
            marker= (Marker) baiduMap.addOverlay(overlayOptions);
            Bundle bundle=new Bundle();
            bundle.putSerializable("info",info);
            marker.setExtraInfo(bundle);
        }
        //把地图移动最后一个位置，方便显示所有位置
        MapStatusUpdate msc=MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.setMapStatus(msc);
    }


    private void initMyLocation() {
        //配置定位参数
        locationClient=new LocationClient(this);
        int span=1000;
        //locationClient=new LocationClient(this);
        myLocation=new MyLocation();//定位监听器
        locationClient.registerLocationListener(myLocation);//注册监听函数
        LocationClientOption option=new LocationClientOption();
        //设置定位模式，高精度，低功耗，仅设备，
        option.setScanSpan(span);//设置多久刷新一次
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");//设置返回的定位结果坐标系
        option.setIsNeedAddress(true);//设置是否需要地址信息，默认false
        option.setOpenGps(true);//设置是否使用gps，默认false
        option.setLocationNotify(true);//设置=是否当否按照lsl次频率输出Gps结果，默认false
        //可选，默认false，设置是否需要位置语义化结果，
        // 可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setEnableSimulateGps(false);
        locationClient.setLocOption(option);
        //初始化自定义图标
      //descriptor= BitmapDescriptorFactory.fromResource(R.drawable.jiantou);
       /* myOrientationListener=new MyOrientationListener(context);//初始化传感器
        //回调，当方向发生改变时
        myOrientationListener.setOrientataionListener(new MyOrientationListener.OnOrientataionListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX=x;
                // 构造定位数据
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(mCurrentAccracy)
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(mCurrentX)
                        .latitude(myLatitude)
                        .longitude(myLongitude).build();
                // 设置定位数据
                baiduMap.setMyLocationData(locData);
                // 设置自定义图标
                descriptor= BitmapDescriptorFactory
                        .fromResource(R.drawable.jiantou);
                MyLocationConfiguration config = new MyLocationConfiguration(
                        mCurrentMode, true,descriptor);
                baiduMap.setMyLocationConfigeration(config);

            }
        });*/
    }



    //重写生命周期方法

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }
    @Override
    protected void onStart() {
        super.onStart();
        //当开启应用的时候开始定位
        if (!locationClient.isStarted()) {
            locationClient.start();
            //在开始定位时同时显示所有的标注物
            //myOrientationListener.start();//开启传感器
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止定位
        baiduMap.setMyLocationEnabled(false);
        locationClient.stop();
        //myOrientationListener.stop();//停止传感器
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        //mapView.onDestroy();
        labelDescriptor.recycle();
    }

    //定位监听
    public class MyLocation implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            mCurrentAccracy= bdLocation.getRadius();
            // 开启定位图层
            baiduMap.setMyLocationEnabled(true);
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .direction(mCurrentX)
                    .accuracy(mCurrentAccracy)//精度
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100)//
                    .latitude(bdLocation.getLatitude())//经纬度
                    .longitude(bdLocation.getLongitude())//
                    .build();
            //设置定位数据
            baiduMap.setMyLocationData(locData);
            //设置图标
          // MyLocationConfiguration configuration=new MyLocationConfiguration(mCurrentMode,true,descriptor);
           // baiduMap.setMyLocationConfigeration(configuration);
            //更新位置
           myLatitude=bdLocation.getLatitude();
            myLongitude=bdLocation.getLongitude();
            //判断是否第一次定位
            if (isFirstIn){
                //得到经纬度
                LatLng latLng=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                MapStatusUpdate update=MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(update);//以动画的效果传到地图上
                isFirstIn=false;
            }
        }
    }
    private void initMarkerListener() {
        //final FragmentManager fragmentManager;
        //标注物监听事件
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                InMapInfo info = (InMapInfo) bundle.getSerializable("info");
                imageView.setImageResource(info.getImageId());
                tv_name.setText(info.getName());
                tv_type.setText(info.getType());
                tv_sketch.setText(info.getSketch());
                tv_number.setText(info.getNumber()+"");
                tv_distance.setText(info.getDistance());
                relativeLayout.setVisibility(View.VISIBLE);
                //定义标注物提示窗口
                InfoWindow infoWindow;
                //设置窗口内容
                TextView textView = new TextView(context);
                textView.setBackgroundResource(R.drawable.popup);
                textView.setPadding(30, 20, 30, 50);
                textView.setText(info.getName());
                final LatLng latLng = marker.getPosition();
                Point point = baiduMap.getProjection().toScreenLocation(latLng);
                point.y -= 50;
                LatLng lng = baiduMap.getProjection().fromScreenLocation(point);
                infoWindow = new InfoWindow(textView, lng, 0);
                baiduMap.showInfoWindow(infoWindow);
                return true;

            }
        });

        //对屏幕的监听事件
        baiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                int event = motionEvent.getAction();
                if (event == MotionEvent.ACTION_DOWN) {
                    relativeLayout.setVisibility(View.GONE);
                }
            }
        });
    }
    //侧滑事件监听
    public void initNavigation(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_message:
                        startActivity(new Intent(MainActivity.this,MessageActivity.class));
                        //finish();
                        break;
                    case R.id.item_apply:
                        //TODO 菜单选项
                      // startActivity(new Intent(MainActivity.this,ApplyActivity.class));
                        //toolbar.setTitle("我的申请");
                        //finish();
                        break;
                    case R.id.item_collection:
                        startActivity(new Intent(MainActivity.this,CollectionActivity.class));
                        //toolbar.setTitle("收藏");
                        //finish();
                        break;
                    case R.id.item_hostory:
                        startActivity(new Intent(MainActivity.this,HistoryActivity.class));
                        //toolbar.setTitle("历史记录");
                        //finish();
                        break;
                    case R.id.item_set:
                        startActivity(new Intent(MainActivity.this,SetActivity.class));
                        //toolbar.setTitle("历史记录");
                            //finish();
                        break;
                    case R.id.item_about:
                        startActivity(new Intent(MainActivity.this,AboutActivity.class));
                        //toolbar.setTitle("历史记录");
                        //finish();
                        break;
                }
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent);
                addhistory();//点击孵化器时，向sqlite添加历史记录
            }

            private void addhistory() {
                //获取当前系统时间
                Calendar c=Calendar.getInstance();
                String time=c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月"+c.get(Calendar.DAY_OF_MONTH)+"日";
                Integer didi_id=1;//点击的孵化器id，未完成

                //使用getReadableDataBase ，内存不足时，不会抛出异常
                mDataBase=mHistoryHelper.getReadableDatabase();
                String sql="insert into "+ HistoryTable.Field.TABLE_NAME+
                        " ("+HistoryTable.Field.HISTORY_DIDI_ID+","+HistoryTable.Field.HISTORY_TIME+")values ("+
                        didi_id+",'"+time+"')";
                mDataBase.execSQL(sql);
                mDataBase.close();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate()作用就是将xml定义的一个布局找出来,但仅仅是找出来而且隐藏的,没有找到的同时并显示功能
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.map_common:
                Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
