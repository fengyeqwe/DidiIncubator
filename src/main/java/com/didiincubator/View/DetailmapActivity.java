package com.didiincubator.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.didiincubator.Beans.DidiBean;
import com.didiincubator.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailmapActivity extends AppCompatActivity {

    @Bind(R.id.btn_baidu_map_subway)
    RadioButton mBtnBaiduMapSubway;
    @Bind(R.id.btn_baidu_map_bus)
    RadioButton mBtnBaiduMapBus;
    @Bind(R.id.btn_baidu_map_restaurant)
    RadioButton mBtnBaiduMapRestaurant;
    @Bind(R.id.btn_baidu_map_bank)
    RadioButton mBtnBaiduMapBank;
    @Bind(R.id.btn_baidu_map_hotel)
    RadioButton mBtnBaiduMapHotel;
    @Bind(R.id.btn_baidu_map_gym)
    RadioButton mBtnBaiduMapGym;
    private PoiSearch mPoiSerach;
    private OnGetPoiSearchResultListener poiListener;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private DidiBean didi;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailmap);
        ButterKnife.bind(this);
        initView();
        inData();
    }

    private void inData() {
        Intent intent = getIntent();
        didi = (DidiBean) intent.getSerializableExtra("didi");
        mBaiduMap = mMapView.getMap();
        mPoiSerach = PoiSearch.newInstance();
        poiListener = new PoiSearchResultListener();
        mPoiSerach.setOnGetPoiSearchResultListener(poiListener);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(didi.getCoordinateY(), didi.getCoordinateX())));//设置地图中心
    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.detail_map);
        mMapView.showZoomControls(true);

    }


    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPoiSerach.destroy();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @OnClick({R.id.btn_baidu_map_subway, R.id.btn_baidu_map_bus, R.id.btn_baidu_map_restaurant, R.id.btn_baidu_map_bank, R.id.btn_baidu_map_hotel, R.id.btn_baidu_map_gym})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_baidu_map_subway:
                num = 1;
                mPoiSerach.searchNearby(new PoiNearbySearchOption().keyword("地铁")
                        .location(new LatLng(didi.getCoordinateY(), didi.getCoordinateX())).radius(5000).pageNum(10));
                break;
            case R.id.btn_baidu_map_bus:
                num = 2;
                mPoiSerach.searchNearby(new PoiNearbySearchOption().keyword("公交")
                        .location(new LatLng(didi.getCoordinateY(), didi.getCoordinateX())).radius(5000).pageNum(10));
                break;
            case R.id.btn_baidu_map_restaurant:
                num = 3;
                mPoiSerach.searchNearby(new PoiNearbySearchOption().keyword("餐厅")
                        .location(new LatLng(didi.getCoordinateY(), didi.getCoordinateX())).radius(5000).pageNum(10));
                break;
            case R.id.btn_baidu_map_bank:
                num = 4;
                mPoiSerach.searchNearby(new PoiNearbySearchOption().keyword("银行")
                        .location(new LatLng(didi.getCoordinateY(), didi.getCoordinateX())).radius(5000).pageNum(10));
                break;
            case R.id.btn_baidu_map_hotel:
                num = 5;
                mPoiSerach.searchNearby(new PoiNearbySearchOption().keyword("酒店")
                        .location(new LatLng(didi.getCoordinateY(), didi.getCoordinateX())).radius(5000).pageNum(10));
                break;
            case R.id.btn_baidu_map_gym:
                num = 6;
                mPoiSerach.searchNearby(new PoiNearbySearchOption().keyword("健身房")
                        .location(new LatLng(didi.getCoordinateY(), didi.getCoordinateX())).radius(5000).pageNum(10));
                break;
        }
    }

    //实现搜索事件监听
    private class PoiSearchResultListener implements OnGetPoiSearchResultListener {

        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if ((poiResult == null) || (poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND)) {
                Toast.makeText(DetailmapActivity.this,"附近无类似建筑",Toast.LENGTH_SHORT).show();
                return;
            }
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                mBaiduMap.clear();
                MyPoiOverlay overlay = new MyPoiOverlay(mBaiduMap, num);
                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(poiResult);
                overlay.addToMap();
                // 缩放地图，使所有Overlay都在合适的视野内
                overlay.zoomToSpan();
                return;

                // mBaiduMap.setOnMarkerClickListener(ov);
            }

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {

            } else {
                Toast.makeText(DetailmapActivity.this, poiDetailResult.getName() + ":" + poiDetailResult.getAddress(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //OverlayManager重写
    private class MyPoiOverlay extends OverlayManager {
        private PoiResult mPoiResult;
        private int num;

        public MyPoiOverlay(BaiduMap baiduMap, int num) {
            super(baiduMap);
            this.num = num;
        }

        public void setData(PoiResult poiResult) {
            this.mPoiResult = poiResult;
        }

        @Override
        public List<OverlayOptions> getOverlayOptions() {
            if ((this.mPoiResult == null)
                    || (this.mPoiResult.getAllPoi() == null)){

                return null;}
            ArrayList<OverlayOptions> arrayList = new ArrayList<OverlayOptions>();

            for (int i = 1; i < this.mPoiResult.getAllPoi().size(); i++) {
                if (this.mPoiResult.getAllPoi().get(i).location == null)
                    continue;
                // 给marker加上标签
                Bundle bundle = new Bundle();
                bundle.putInt("index", i);
                arrayList.add(new MarkerOptions()
                        .icon(BitmapDescriptorFactory
                                .fromBitmap(setNumToIcon(i, num))).extraInfo(bundle)
                        .position(this.mPoiResult.getAllPoi().get(i).location));
            }
            return arrayList;
        }


        @Override
        public boolean onMarkerClick(Marker marker) {
            if (marker.getExtraInfo() != null) {
                int index = marker.getExtraInfo().getInt("index");
                PoiInfo poiInfo = mPoiResult.getAllPoi().get(index);
                mPoiSerach.searchPoiDetail(new PoiDetailSearchOption().poiUid(poiInfo.uid));
                return true;
            }
            return false;
        }

        @Override
        public boolean onPolylineClick(Polyline polyline) {
            return false;
        }

        private Bitmap setNumToIcon(int num, int i) {
            BitmapDrawable bd;
            switch (i) {
                case 1:
                    bd = (BitmapDrawable) getResources().getDrawable(
                            R.drawable.anno_ditie);
                    break;
                case 2:
                     bd = (BitmapDrawable) getResources().getDrawable(
                            R.drawable.anno_gongjiao);
                    break;
                case 3:
                    bd = (BitmapDrawable) getResources().getDrawable(
                            R.drawable.anno_canting);
                    break;
                case 4:
                    bd = (BitmapDrawable) getResources().getDrawable(
                            R.drawable.anno_yinhang);
                    break;

                case 5:
                    bd = (BitmapDrawable) getResources().getDrawable(
                            R.drawable.anno_jiudian);
                    break;
                case 6:
                    bd = (BitmapDrawable) getResources().getDrawable(
                            R.drawable.anno_jianshenfang);
                    break;
                default:
                    bd = (BitmapDrawable) getResources().getDrawable(
                            R.drawable.icon_gcoding);
                    break;


            }

            Bitmap bitmap = bd.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
           /* Canvas canvas = new Canvas(bitmap);

            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
            int widthX;
            int heightY = 0;
            if (num < 10) {
                paint.setTextSize(30);
                widthX = 8;
                heightY = 6;
            } else {
                paint.setTextSize(20);
                widthX = 11;
            }

            canvas.drawText(String.valueOf(num),
                    ((bitmap.getWidth() / 2) - widthX),
                    ((bitmap.getHeight() / 2) + heightY), paint);*/
            return bitmap;
        }

    }
}
