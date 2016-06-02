package com.didiincubator.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.internal.ForegroundLinearLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.didiincubator.Beans.Detail;
import com.didiincubator.Beans.DidiBean;
import com.didiincubator.Beans.GongWei;
import com.didiincubator.Beans.Pictures;
import com.didiincubator.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;


public class DetailActivity extends AppCompatActivity {
    ViewPager mViewPager;//image滑动图
    @Bind(R.id.details_btn_back)
    ImageView mDetailsBtnBack;//返回键
    @Bind(R.id.details_btn_collection)
    ImageView mDetailsBtnCollection;//收藏键
    @Bind(R.id.details_text_address)
    TextView mDetailsTextAddress;//地址
    @Bind(R.id.details_text_price)
    TextView mDetailsTextPrice;//价格


    @Bind(R.id.tv_groundFloor)
    TextView mTvGroundFloor;
    @Bind(R.id.tv_totalCFA)
    TextView mTvTotalCFA;
    @Bind(R.id.tv_floorHeight)
    TextView mTvFloorHeight;

    @Bind(R.id.tv_propertyManagementer)
    TextView mTvPropertyManagementer;
    @Bind(R.id.tv_traffic_metro)
    TextView mTvTrafficMetro;
    @Bind(R.id.tv_traffic_metro_time)
    TextView mTvTrafficMetroTime;
    @Bind(R.id.tv_traffic_airplane)
    TextView mTvTrafficAirplane;
    @Bind(R.id.tv_traffic_airplane_time)
    TextView mTvTrafficAirplaneTime;
    @Bind(R.id.tv_traffic_train)
    TextView mTvTrafficTrain;
    @Bind(R.id.tv_traffic_train_time)
    TextView mTvTrafficTrainTime;
    @Bind(R.id.tv_restaurant_num)
    TextView mTvRestaurantNum;
    @Bind(R.id.tv_hotels_nums)
    TextView mTvHotelsNums;
    @Bind(R.id.tv_gym_nums)
    TextView mTvGymNums;
    @Bind(R.id.tv_bank_nums)
    TextView mTvBankNums;
    @Bind(R.id.details_phone)
    ImageView mDetailsPhone;
    @Bind(R.id.details_message)
    ImageView mDetailsMessage;
    @Bind(R.id.details_share)
    ImageView mDetailsShare;
    @Bind(R.id.details_in)
    ImageView mDetailsIn;
    @Bind(R.id.details_text_title)
    TextView mDetailsTextTitle;//标题
    @Bind(R.id.detail_sketch)
    TextView mDetailSketch;
    @Bind(R.id.tv_countSum)
    TextView mTvCountSum;//工位面积
    @Bind(R.id.tv_countRemainer)
    TextView mTvCountRemainer;//剩余工位
    private LayoutInflater inflater;
    private ArrayList<View> mList;
    private PagerAdapter pagerAdapter;
    private List<Integer> imgList;
    private  Bitmap[] mBitmaps=new Bitmap[3];//加载的图片

    // 整个平台的Controller,负责管理整个SDK的配置、操作等处理
    UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.share");
    private DidiBean didi;
    private RequestQueue mQueue;
    public Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        setViews();
        initAdapter();
        setShareContent();//一键分享

    }

    private void setViews() {
        mDetailsTextTitle.setText(didi.getName());
        mDetailsTextAddress.setText("地址");
        mDetailsTextPrice.setText("价格");
        mDetailSketch.setText(didi.getSketch());


    }

    private void setShareContent() {

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(DetailActivity.this
                , "1105330645", "qizrvnP5AuHIs2ks");
        qZoneSsoHandler.addToSocialSDK();
        //mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能");
        //  mController.setShareMedia(new UMImage(this, "http://www.umeng.com/images/pic/banner_module_social.png"));
        // mController.setShareMedia(new UMusic("http://sns.whalecloud.com/test_music.mp3"));
//图片
        // UMImage localimage=new UMImage(this,R.drawable.qq);
        UMImage urlimage = new UMImage(this, "http://www.umeng.com/images/pic/social/integrated_3.png");

// 设置分享音乐
        UMusic uMusic = new UMusic("http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
        uMusic.setAuthor("GuGu");
        uMusic.setTitle("天籁之音");
// 设置音乐缩略图
        uMusic.setThumb(urlimage);
        mController.setShareMedia(uMusic);


/*
//设置分享视频
        UMVideo umVideo = new UMVideo(
                "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
        //设置视频缩略图
        umVideo.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
        umVideo.setTitle("友盟社会化分享!");
        mController.setShareMedia(umVideo);
*/
    }


    private void initAdapter() {
        pagerAdapter = new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mList.get(position);
              ImageView image= (ImageView) view.findViewById(R.id.detail_viewPager1);
                if (mBitmaps[position]!=null) {
                    image.setImageBitmap(mBitmaps[position]);
                }
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mList.get(position));
            }

            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        mViewPager.setAdapter(pagerAdapter);
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        //didi=(DidiBean)extras.getSerializable("didi");
        didi = new DidiBean();
        didi.setId(4);
        didi.setDetail_id(2);
        didi.setStation_id(3);
        getDetail();//详情网络请求
        getStation();//工位网络请求
        getImages();

        mList = new ArrayList<View>();
        inflater = getLayoutInflater();
        mList.add(inflater.inflate(R.layout.viewpager_item1, null));
        mList.add(inflater.inflate(R.layout.viewpager_item1, null));
        mList.add(inflater.inflate(R.layout.viewpager_item1, null));


    }

    private void getImages() {
        String url ="http://10.201.1.152:8080/Didiweb/picturesServlet";
        Request<JSONArray> imgRequest=NoHttp.createJsonArrayRequest(url);
        imgRequest.add("method","select");
        imgRequest.add("id",didi.getId());
        mQueue.add(1, imgRequest, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                JSONArray result = response.get();
                List<Pictures> list = gson.fromJson(result.toString(), new TypeToken<List<Pictures>>() {
                }.getType());
                for(int i=0;i<list.size();i++){
                    Request<Bitmap> request=NoHttp.createImageRequest(list.get(i).getUrl());
                    mQueue.add(i, request, new OnResponseListener<Bitmap>() {
                        @Override
                        public void onStart(int what) {

                        }

                        @Override
                        public void onSucceed(int what, Response<Bitmap> response) {

                            Bitmap image = response.get();
                            mBitmaps[what]=image;
                            pagerAdapter.notifyDataSetChanged();
                            Toast.makeText(DetailActivity.this,"success",Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

                        }

                        @Override
                        public void onFinish(int what) {

                        }
                    });

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

    private void getStation() {
        String url = "http://10.201.1.152:8080/Didiweb/gongweiServlet";
        Request<JSONArray> stationRequest = NoHttp.createJsonArrayRequest(url);
        stationRequest.add("method", "select");
        stationRequest.add("id", didi.getStation_id());
        mQueue.add(1, stationRequest, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                JSONArray result = response.get();
                List<GongWei> list = gson.fromJson(result.toString(), new TypeToken<List<GongWei>>() {
                }.getType());
                GongWei gongWei = list.get(0);
                mTvCountSum.setText(gongWei.getAreaAvg());
                mTvCountRemainer.setText(gongWei.getCountRemainer()+" ");
                mDetailsTextPrice.setText(gongWei.getPrice() + "元/平方米.月");

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    private void getDetail() {
        String url = "http://10.201.1.152:8080/Didiweb/detailServlet";
        Request<JSONArray> detailrequest = NoHttp.createJsonArrayRequest(url);
        detailrequest.add("method", "select");
        detailrequest.add("id", didi.getDetail_id());
        mQueue.add(1, detailrequest, new OnResponseListener<JSONArray>() {


            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                JSONArray result = response.get();
                List<Detail> list = gson.fromJson(result.toString(), new TypeToken<List<Detail>>() {
                }.getType());
                Detail detail = list.get(0);
                mTvGroundFloor.setText(detail.getFloor() + " ");
                mTvFloorHeight.setText(detail.getHigh() + " ");
                mTvTotalCFA.setText(detail.getArea());
                mTvPropertyManagementer.setText(detail.getProperty());


            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.detail_viewPager);

    }

    @OnClick({R.id.details_btn_back, R.id.details_btn_collection, R.id.tv_traffic_metro, R.id.tv_traffic_airplane, R.id.tv_traffic_train, R.id.tv_restaurant_num, R.id.tv_hotels_nums, R.id.tv_gym_nums, R.id.tv_bank_nums, R.id.details_phone, R.id.details_message, R.id.details_share, R.id.details_in})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.details_btn_back:
                //返回上一个activity
                DetailActivity.this.finish();
                break;
            case R.id.details_btn_collection:

                break;
            case R.id.tv_traffic_metro:
                break;
            case R.id.tv_traffic_airplane:
                break;
            case R.id.tv_traffic_train:
                break;
            case R.id.tv_restaurant_num:
                break;
            case R.id.tv_hotels_nums:
                break;
            case R.id.tv_gym_nums:
                break;
            case R.id.tv_bank_nums:
                break;
            case R.id.details_phone:
                phone(2);
                break;
            case R.id.details_message:
                RongIM.getInstance().startPrivateChat(DetailActivity.this, "362970502", "chat");
                break;
            case R.id.details_share://第三方分享
                share();
                break;
            case R.id.details_in:
                goApply();
                break;
        }
    }

    private void share() {
        mController.openShare(this, false);
    }

    private void goApply() {
        Intent intent = new Intent(DetailActivity.this, ApplyActivity.class);
        startActivity(intent);
    }


    public void phone(int phoneNumber) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + "18238777036"));
        startActivity(intent);

    }


}
