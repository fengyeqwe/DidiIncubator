package com.didiincubator.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.didiincubator.Adapter.DetailpagerAdapter;
import com.didiincubator.Beans.Detail;
import com.didiincubator.Beans.DidiBean;
import com.didiincubator.Beans.GongWei;
import com.didiincubator.Beans.Pictures;
import com.didiincubator.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.LogRecord;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;


public class DetailActivity extends AppCompatActivity {
    public static final String MYHTTP = "http://10.201.1.46:8080/Didiweb/";
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
    private Bitmap[] mBitmaps = new Bitmap[3];//加载的图片

    // 整个平台的Controller,负责管理整个SDK的配置、操作等处理
    UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.share");
    private DidiBean didi;
    private RequestQueue mQueue;
    public Gson gson = new Gson();
    public ArrayList<View> mViews;
    private DetailpagerAdapter mDetailpagerAdapter;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mQueue = NoHttp.newRequestQueue();
        initView();
        initData();



    }

    private void setViews() {
        mDetailsTextTitle.setText(didi.getName());
        mDetailsTextAddress.setText(didi.getAddress());
        mDetailSketch.setText(didi.getSketch());


    }

    private void setShareContent() {

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(DetailActivity.this
                , "1105330645", "qizrvnP5AuHIs2ks");
        qZoneSsoHandler.addToSocialSDK();
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能");
        //设置Qzone分享内容
        QZoneShareContent qzone = new QZoneShareContent();
        //设置分享文字
        qzone.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能 -- QZone");

        //设置点击消息的跳转URL
        qzone.setTargetUrl("你的URL链接");
        //设置分享内容的标题
        qzone.setTitle("QZone title");
        //设置分享图片
        qzone.setShareImage(new UMImage(this, R.drawable.app));
        mController.setShareMedia(qzone);


        //设置QQ分享内容使用下面的代码：

        QQShareContent qqShareContent = new QQShareContent();
        //设置分享文字
        qqShareContent.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能 -- QQ");
        //设置分享title
        qqShareContent.setTitle("hello, title");
        //设置分享图片
        qqShareContent.setShareImage(new UMImage(this, R.drawable.app));
        qqShareContent.isMultiMedia();
        //设置点击分享内容的跳转链接
        qqShareContent.setTargetUrl("你的URL链接");
        mController.setShareMedia(qqShareContent);

        mController.setShareContent(didi.getSketch());

        //  mController.setShareMedia(new UMImage(this, "http://www.umeng.com/images/pic/banner_module_social.png"));
        // mController.setShareMedia(new UMusic("http://sns.whalecloud.com/test_music.mp3"));
//图片
        // UMImage localimage=new UMImage(this,R.drawable.qq);
        UMImage urlimage = new UMImage(this,"http://o7f489fjp.bkt.clouddn.com/j3.PNG");
        urlimage.setTitle(didi.getName());
        mController.setShareImage(urlimage);

/*// 设置分享音乐
        UMusic uMusic = new UMusic("http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
        uMusic.setAuthor("GuGu");
        uMusic.setTitle(didi.getName());*//*// 设置音乐缩略图
        uMusic.setThumb(urlimage);
        mController.setShareMedia(uMusic);*/



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


    private void initData() {
        mDetailpagerAdapter = new DetailpagerAdapter(mViews);
        mViewPager.setAdapter(mDetailpagerAdapter);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        getDidi(id);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x111) {
                    setViews();
                    getDetail();//详情网络请求
                    getStation();//工位网络请求
                    getImages();//图片
                    setShareContent();//一键分享
                }
            }
        };


    }

    private void getDidi(String id) {
        String url = MYHTTP+"DidiServlet";
        Request<JSONArray> request = NoHttp.createJsonArrayRequest(url);
        request.add("method", "select");
        request.add("id", id);
        mQueue.add(1, request, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {
                Log.e("didi","start");

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                Log.e("didi","success");
                JSONArray result = response.get();
                ArrayList<DidiBean> didis = gson.fromJson(result.toString(), new TypeToken<List<DidiBean>>() {
                }.getType());
                didi = didis.get(0);
                Message msg = new Message();
                msg.what = 0x111;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void getImages() {
        String url = MYHTTP+"picturesServlet";
        Request<JSONArray> imgRequest = NoHttp.createJsonArrayRequest(url);
        imgRequest.add("method", "select");
        imgRequest.add("id", didi.getId());
        mQueue.add(1, imgRequest, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                JSONArray result = response.get();
                ArrayList<Pictures> imageUrls = gson.fromJson(result.toString(), new TypeToken<List<Pictures>>() {
                }.getType());

                for (Pictures pictures : imageUrls) {
                    View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.viewpager_item1, null);
                    ImageView imageView = (ImageView) view.findViewById(R.id.detail_viewPager1);
                    Glide.with(getBaseContext()).load(pictures.getUrl()).into(imageView);
                    mViews.add(view);
                }
                mDetailpagerAdapter.notifyDataSetChanged();


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
        String url = MYHTTP+"gongweiServlet";
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
                mTvCountRemainer.setText(gongWei.getCountRemainer() + " ");
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
        String url = MYHTTP +"detailServlet";
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
                if (list.size() > 0) {
                    Detail detail = list.get(0);
                    mTvGroundFloor.setText(detail.getFloor() + " ");
                    mTvFloorHeight.setText(detail.getHigh() + " ");
                    mTvTotalCFA.setText(detail.getArea());
                    mTvPropertyManagementer.setText(detail.getProperty());
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

    private void initView() {
        mViews = new ArrayList<>();
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
        //传递DdiBean
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("didi",didi);
        intent.putExtras(mBundle);
        startActivity(intent);
    }


    public void phone(int phoneNumber) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + didi.getPhonenumber()));
        startActivity(intent);

    }


}
