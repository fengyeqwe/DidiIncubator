package com.didiincubator.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.didiincubator.Presenter.DetailPresenter;
import com.didiincubator.R;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.sso.QZoneSsoHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;


public class DetailActivity extends AppCompatActivity implements IdetailVIew  {
    ViewPager mViewPager;
    @Bind(R.id.details_btn_back)
    ImageView mDetailsBtnBack;
    @Bind(R.id.details_btn_collection)
    ImageView mDetailsBtnCollection;
    @Bind(R.id.details_text_address)
    TextView mDetailsTextAddress;
    @Bind(R.id.details_text_price)
    TextView mDetailsTextPrice;
    @Bind(R.id.tv_property_management_level)
    TextView mTvPropertyManagementLevel;
    @Bind(R.id.tv_investor)
    TextView mTvInvestor;
    @Bind(R.id.tv_groundFloor)
    TextView mTvGroundFloor;
    @Bind(R.id.tv_totalCFA)
    TextView mTvTotalCFA;
    @Bind(R.id.tv_floorHeight)
    TextView mTvFloorHeight;
    @Bind(R.id.tv_propertyManagementMoney)
    TextView mTvPropertyManagementMoney;
    @Bind(R.id.tv_room_rate)
    TextView mTvRoomRate;
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
    TextView mDetailsTextTitle;
    @Bind(R.id.detail_sketch)
    TextView mDetailSketch;
    private LayoutInflater inflater;
    private ArrayList<View> mList;
    private PagerAdapter pagerAdapter;
    private List<Integer> imgList;
    private DetailPresenter mPresenter;


    // 整个平台的Controller,负责管理整个SDK的配置、操作等处理
    UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.share");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
       //mPresenter = new DetailPresenter(this);
        initView();
        initData();
        initAdapter();
        setShareContent();
       // mPresenter.loadUI();//网络请求获的数据空指针
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
        mList = new ArrayList<View>();
        inflater = getLayoutInflater();
        mList.add(inflater.inflate(R.layout.viewpager_item1, null));
        mList.add(inflater.inflate(R.layout.viewpager_item2, null));
        mList.add(inflater.inflate(R.layout.viewpager_item3, null));


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
                RongIM.getInstance().startPrivateChat(DetailActivity.this,"362970502","chat");
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


    @Override
    public void setName(String name) {
        mDetailsTextTitle.setText(name);

    }

    @Override
    public void setSketch(String sketch) {
        mDetailSketch.setText(sketch);

    }

    @Override
    public void setDetail_id(int detail_id) {
        //详情id
    }

    @Override
    public void setStation_id(int station_id) {
        //工位情况

    }

    @Override
    public void setCoordinate(String coordinate) {
        //坐标

    }

    @Override
    public void phone(int phoneNumber) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:"+"18238777036"));
        startActivity(intent);

    }


}
