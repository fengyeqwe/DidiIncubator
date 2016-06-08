package com.didiincubator.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.baidu.mapapi.overlayutil.BikingRouteOverlay;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.bumptech.glide.Glide;
import com.didiincubator.Adapter.AllAdapter;
import com.didiincubator.Beans.DidiBean;
import com.didiincubator.R;
import com.didiincubator.View.ApplyListActivity;
import com.didiincubator.View.DetailActivity;
import com.didiincubator.View.HistoryActivity;
import com.didiincubator.utils.HistoryHelper;
import com.didiincubator.utils.HistoryTable;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static final int NOHTTP_MARK = 0x002;
    Context context;
    MapView mapView;
    BaiduMap baiduMap;
    RelativeLayout relativeLayout;

    //定位
    LocationClient locationClient;//LocationClient类是定位SDK的核心类
    MyLocation myLocation;
    boolean isFirstIn = true;//判断是否第一次定位，默认为第一次
    double myLatitude;//我的位置经纬度
    double myLongitude;
    float mCurrentX;//角度值
    MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    private float mCurrentAccracy;//定位范围
    //BitmapDescriptor descriptor;//定位自定义图标
    //MyOrientationListener myOrientationListener ;

    //标注物
    BitmapDescriptor labelDescriptor = null;//标注物图标
    Marker marker;//百度标注物
    List<DidiBean> list;//孵化器集合
    DidiBean incubator;//孵化器实体类
    ImageView imageView;//弹框显示的图片
    TextView tv_name, tv_type, tv_sketch;
    //标志物解析数据
    RequestQueue queue = NoHttp.newRequestQueue();
    ;
    //static  final int NOHTTP_MARKER=0x001;
    //延迟执行initOVerlay()方法
    private Handler handler = new Handler() {
        public void dispatchMessage(Message message) {
            if (1 == message.what) {
                initOverlay(list);
            }
        }
    };

    //左侧滑控件
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //点击蛋显示孵化器列表
    ImageView eggImageView;
    // 点击蛋显示的布局
    LinearLayout linearLayout;
    PullToRefreshListView pullToRefreshListView;//刷新
    AllAdapter adapter;

    //搜索
    EditText searchEditText;//编辑文本搜索框
    Button searchButton;//搜索按钮
    List<String> names = new ArrayList<>();//孵化器名字集合
    double incubatorLatitude;//搜索的孵化器的纬度
    double incubatorLongtitude;//搜索到的孵化器的经度
    String incubatorName;
    //声明数据库操作类
    SQLiteDatabase mDataBase;
    //声明数据库辅助类对象
    HistoryHelper mHistoryHelper;
    private Bundle bundle;
    //个人信息
    View view;//得到 navigationView中的头部控件
    //TODO 交通路线测试
    RouteLine routeLine = null;//路线
    RoutePlanSearch search = null;//路线搜索
    OverlayManager routeOverlay = null;
    Button driveLine;
    private PopupWindow popupWindow;
    Button drive_route, bike_route,walk_route;
    String city;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前e初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        //去除actionbar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();//（孵化器）标志物集合
        initMarkerData();//初始化
        this.context = getApplicationContext();
        mapView = (MapView) findViewById(R.id.didi_mapView);
        initView();//初始化视图
        //初始化左侧滑中的控件
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        navigationView = (NavigationView) findViewById(R.id.nv_left);
        //View view=navigationView.inflateHeaderView(R.layout.navigation_header);
       /* userIcon= (ImageView) view.findViewById(R.id.user_headicon);
        userName= (TextView) view.findViewById(R.id.user_name);*/
        view = navigationView.getHeaderView(0);
        toolbar = (Toolbar) findViewById(R.id.toolbar_left);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        //设置抽屉DrawerLayout;
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();//初始化状态
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //initOverlay(list);
        // 延迟2秒
        handler.sendMessageDelayed(handler.obtainMessage(1), 2000);

        adapter = new AllAdapter(list, context);
        pullToRefreshListView.setAdapter(adapter);
        initNavigation();
        initMyLocation();
        initMarkerListener();//标注物监听事件
        initEggListener();//监听主界面蛋的点击事件，显示附近孵化器列表
        userMessage();
        searchButtonListener();//监听搜索事件
        setSearchEditTextListener();
        routeLineListener();

    }


    private void initView() {
        //初始化标注物中的控件
        imageView = (ImageView) findViewById(R.id.iv_marker);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_sketch = (TextView) findViewById(R.id.tv_sketch);
        baiduMap = mapView.getMap();
        //改变标尺大小
        MapStatusUpdate factory = MapStatusUpdateFactory.zoomTo(15.0f);//500米
        baiduMap.setMapStatus(factory);
        labelDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_marker);
        //初始化蛋
        eggImageView = (ImageView) findViewById(R.id.egg);
        linearLayout = (LinearLayout) findViewById(R.id.egg_linearLayout);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.egg_listView);
        //搜索
        searchEditText = (EditText) findViewById(R.id.suosou);
        searchButton = (Button) findViewById(R.id.search_button);
        search = RoutePlanSearch.newInstance();//初始化路线查询对象
        //driveLine = (Button) findViewById(R.id.routLine);
        View popupView = getLayoutInflater().inflate(R.layout.route_popupwindow, null);
        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        drive_route = (Button) popupView.findViewById(R.id.drive_route);
        walk_route= (Button) popupView.findViewById(R.id.walk_route);
        bike_route= (Button) popupView.findViewById(R.id.bike_route);

        //初始化historyHelper
        mHistoryHelper = new HistoryHelper(MainActivity.this);


    }

    //解析标注物
    private void initMarkerData() {
        String method = "selectall";
        final String name = "";
        String url = "http://115.28.78.82:8080/Didiweb/DidiServlet";
        final Request<JSONArray> request = NoHttp.createJsonArrayRequest(url, RequestMethod.GET);
        request.add("method", method);
        request.add("name", name);
        queue.add(NOHTTP_MARK, request, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                //Toast.makeText(context, "为什么", Toast.LENGTH_SHORT).show();
                JSONArray result = response.get();
                //Toast.makeText(context, "re"+result.toString(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < result.length(); i++) {
                    incubator = new DidiBean();
                    try {
                        JSONObject object = result.getJSONObject(i);
                        incubator.setId(object.getInt("id"));
                        incubator.setName(object.getString("name"));
                        incubator.setType_didi(object.getString("type_didi"));
                        incubator.setSketch(object.getString("sketch"));
                        incubator.setCoordinateX((float) object.getDouble("coordinateX"));
                        incubator.setCoordinateY((float) object.getDouble("coordinateY"));
                        incubator.setHeadPortrait(object.getString("headPortrait"));
                        list.add(incubator);
                        names.add(incubator.getName());
                        adapter.notifyDataSetChanged();
                        //Toast.makeText(context, "为什么"+list.size(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {


            }


            @Override
            public void onFinish(int what) {

            }

        });
    }

    private void initOverlay(List<DidiBean> list) {

        //TODO 从数据库获取数据解析
        //baiduMap.clear();
        LatLng latLng = null;
        //Toast.makeText(MainActivity.this,"xx:"+list.toString(),Toast.LENGTH_LONG).show();
        for (DidiBean info : list) {
            latLng = new LatLng(info.getCoordinateY(), info.getCoordinateX());//位置
            //图标
            OverlayOptions overlayOptions = new MarkerOptions().position(latLng).icon(labelDescriptor).zIndex(5);
            marker = (Marker) baiduMap.addOverlay(overlayOptions);
            Bundle bundle = new Bundle();
            bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);
        }
        // 把地图移动最后一个位置，方便显示所有位置
        MapStatusUpdate msc = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.setMapStatus(msc);
    }


    private void initMyLocation() {
        //配置定位参数
        locationClient = new LocationClient(this);
        int span = 1000;
        //locationClient=new LocationClient(this);
        myLocation = new MyLocation();//定位监听器
        locationClient.registerLocationListener(myLocation);//注册监听函数
        LocationClientOption option = new LocationClientOption();
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
            city=bdLocation.getCity();
            mCurrentAccracy = bdLocation.getRadius();
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
            myLatitude = bdLocation.getLatitude();
            myLongitude = bdLocation.getLongitude();
            //判断是否第一次定位
            if (isFirstIn) {
                //得到经纬度
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(update);//以动画的效果传到地图上
                isFirstIn = false;
            }
        }
    }

    private void initMarkerListener() {
        //final FragmentManager fragmentManager;
        //标注物监听事件
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                bundle = marker.getExtraInfo();
                DidiBean info = (DidiBean) bundle.getSerializable("info");
                Glide.with(context).load(info.getHeadPortrait()).centerCrop().crossFade()
                        .into(imageView);
                // imageView.setImageResource(info.getImageId());

                tv_name.setText("孵化器名称：" + info.getName());
                tv_type.setText("孵化器类型：" + info.getType_didi());
                tv_sketch.setText("简介：" + info.getSketch());
                relativeLayout.setVisibility(View.VISIBLE);
                eggImageView.setVisibility(View.GONE);
                //定义标注物提示窗口
                InfoWindow infoWindow;
                //设置窗口内容
                TextView textView = new TextView(context);
                textView.setBackgroundResource(R.drawable.popup);
                textView.setPadding(30, 20, 30, 50);
                textView.setTextColor(Color.GRAY);
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
                    relativeLayout.setVisibility(View.GONE);//当点击屏幕的时候布局消失
                    eggImageView.setVisibility(View.VISIBLE);//蛋显示
                    searchEditText.setFocusable(false);//点击屏幕其他地方搜索失去焦点
                }
            }
        });
    }

    //监听返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            relativeLayout.setVisibility(View.GONE);//当按下返回键的时候，布局消失
            eggImageView.setVisibility(View.VISIBLE);//蛋显示
            linearLayout.setVisibility(View.GONE);//显示孵化器的布局消失
            exitBy2Click();
            //return super.onKeyDown(keyCode, event);
        }
        return false;
    }

    public static Boolean isExit = false;

    private void exitBy2Click() {
        Timer timer = null;
        if (isExit == false) {
            isExit = true;
            //Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
            ;
            System.exit(0);
        }
    }

    //侧滑事件监听
    public void initNavigation() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_message:
                        startActivity(new Intent(MainActivity.this, MessageActivity.class));
                        break;
                    case R.id.item_apply:
                        startActivity(new Intent(MainActivity.this, ApplyListActivity.class));
                        break;
                    case R.id.item_collection:
                        startActivity(new Intent(MainActivity.this, CollectionActivity.class));
                        break;
                    case R.id.item_hostory:
                        startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                        break;
                    case R.id.item_set:
                        startActivity(new Intent(MainActivity.this, SetActivity.class));
                        break;
                    case R.id.item_about:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
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
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                DidiBean info = (DidiBean) bundle.getSerializable("info");
                int id = info.getId();
                intent.putExtra("id", id + "");
                startActivity(intent);
                addhistory(id);//点击孵化器时，向sqlite添加历史记录
                relativeLayout.setVisibility(View.GONE);
            }

            private void addhistory(int id) {
                //获取当前系统时间
                Calendar c = Calendar.getInstance();
                String time = c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日";
                Integer didi_id = id;//点击的孵化器id，未完成

                //使用getReadableDataBase ，内存不足时，不会抛出异常
                mDataBase = mHistoryHelper.getReadableDatabase();
                String sql = "insert into " + HistoryTable.Field.TABLE_NAME +
                        " (" + HistoryTable.Field.HISTORY_DIDI_ID + "," + HistoryTable.Field.HISTORY_TIME + ")values (" +
                        didi_id + ",'" + time + "')";
                mDataBase.execSQL(sql);
                mDataBase.close();

            }
        });
    }

    //蛋的点击事件
    private void initEggListener() {
        eggImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isClickable()) {
                    Toast.makeText(MainActivity.this, "点击了我", Toast.LENGTH_SHORT).show();
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //TODO 修改个人信息 bundle把值传过来
    public void userMessage() {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ModifyUserMessageActivity.class));
            }
        });
    }

    //TODO 搜索事件
    public void searchButtonListener() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incubatorName = searchEditText.getText().toString().trim();
                getSearchIncubator(incubatorName);
                if(isIncubatorExit(incubatorName)){
                    searchEditText.setFocusable(false);//点击搜索时失去焦点
                    popupWindow.showAtLocation(v, Gravity.CENTER,0, 0);
                    if (popupWindow.isShowing()){
                        searchButtonProcess();
                    }
                }
            }

        });

    }

    //监听文本框
    public void setSearchEditTextListener() {
        //EDITTEXT如果已经失去焦点，使用setOnClickListener()再想获取焦点，并且能够编辑，则需要点击两次才能够进行编辑，
        // 而使用setOnTouchListener进行监听只需点击一次就能够进行编辑
        searchEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                searchEditText.setFocusable(true);
                searchEditText.setFocusableInTouchMode(true);
                searchEditText.requestFocus();
                searchEditText.requestFocusFromTouch();
                return false;
            }
        });
    }

    //判断是否存在这个孵化器
    public boolean isIncubatorExit(String incubatorName) {
        if (names.contains(incubatorName)) {
            return true;
        } else {
            Toast.makeText(context, "没有这个孵化器，请重新输入", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //通过得到的名字来进行查询
    public void getSearchIncubator(final String incubatorName) {
        final DidiBean incubator1 = new DidiBean();
        String method = "select";
        String url = "http://115.28.78.82:8080/Didiweb/DidiServlet";
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(url, RequestMethod.GET);
        request.add("method", method);
        request.add("name", incubatorName);
        //Toast.makeText(context, "mingzi:"+incubatorName, Toast.LENGTH_SHORT).show();
        queue.add(NOHTTP_MARK, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject object = response.get();
                //incubator=new DidiBean();
                try {
                    incubator1.setCoordinateX((float) object.getDouble("coordinateX"));
                    incubator1.setCoordinateY((float) object.getDouble("coordinateY"));
                    incubatorLatitude = incubator1.getCoordinateY();
                    incubatorLongtitude = incubator1.getCoordinateX();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    //TODO 路线查询监听
    //监听驾车路线按钮
    public void searchButtonProcess() {

        if (routeLine != null) {
            routeLine = null;
        }
        if (routeOverlay != null) {
            routeOverlay.removeFromMap();
        }

        drive_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, incubatorLatitude + "---" + incubatorLongtitude, Toast.LENGTH_LONG).show();
                routeLine = null;
                // baiduMap.clear();
                // baiduMap.clear();
                final PlanNode start = PlanNode.withLocation(new LatLng(myLatitude, myLongitude));
                final PlanNode end = PlanNode.withLocation(new LatLng(incubatorLatitude, incubatorLongtitude));
                if (v.getId() == R.id.drive_route) {
                    search.drivingSearch((new DrivingRoutePlanOption()).from(start).to(end));
                }
                popupWindow.dismiss();
                //driveLine.setVisibility(View.GONE);
            }
        });
        bike_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeLine = null;
                // baiduMap.clear();
                // baiduMap.clear();
                Toast.makeText(context, incubatorLatitude + "--->>>" + incubatorLongtitude, Toast.LENGTH_LONG).show();
                final PlanNode start = PlanNode.withLocation(new LatLng(myLatitude, myLongitude));
                final PlanNode end = PlanNode.withLocation(new LatLng(incubatorLatitude, incubatorLongtitude));
                if (v.getId() == R.id.bike_route) {
                    search.bikingSearch((new BikingRoutePlanOption()).from(start).to(end));
                }
                popupWindow.dismiss();
            }
        });

        walk_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeLine = null;
                // baiduMap.clear();
                // baiduMap.clear();
                Toast.makeText(context, incubatorLatitude + "--->>>>>" + incubatorLongtitude, Toast.LENGTH_LONG).show();
                final PlanNode start = PlanNode.withLocation(new LatLng(myLatitude, myLongitude));
                final PlanNode end = PlanNode.withLocation(new LatLng(incubatorLatitude, incubatorLongtitude));
                if (v.getId() == R.id.walk_route) {
                    search.walkingSearch((new WalkingRoutePlanOption()).from(start).to(end));
                }
                popupWindow.dismiss();
            }
        });
    }


    public void routeLineListener() {
        search.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(context, "抱歉未找到结果", Toast.LENGTH_SHORT).show();
                }
                if (walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    return;
                }
                if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    routeLine = walkingRouteResult.getRouteLines().get(0);
                    WalkingRouteOverlay overlay = new MyWalkRouteOverly(baiduMap);
                    routeOverlay = overlay;
                    baiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(walkingRouteResult.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }
            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(context, "抱歉未找到结果", Toast.LENGTH_SHORT).show();
                }
                if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    return;
                }
                if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    routeLine = drivingRouteResult.getRouteLines().get(0);
                    DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(baiduMap);
                    routeOverlay = overlay;
                    baiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(drivingRouteResult.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
                if (bikingRouteResult == null || bikingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(context, "抱歉未找到结果", Toast.LENGTH_SHORT).show();
             }
            if (bikingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            return;
            }
            if (bikingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            routeLine = bikingRouteResult.getRouteLines().get(0);
            MyBikeRouteOverly overlay = new MyBikeRouteOverly(baiduMap);
            routeOverlay = overlay;
            baiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(bikingRouteResult.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
                 }
             }
        });
    }

    class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }
    }
    class  MyBikeRouteOverly extends BikingRouteOverlay{

        public MyBikeRouteOverly(BaiduMap baiduMap) {
            super(baiduMap);
        }
    }
    class  MyWalkRouteOverly extends WalkingRouteOverlay{

        public MyWalkRouteOverly(BaiduMap baiduMap) {
            super(baiduMap);
        }
    }
}
