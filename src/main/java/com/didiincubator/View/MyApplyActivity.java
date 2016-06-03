package com.didiincubator.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.didiincubator.Presenter.MyApplyPresenter;
import com.didiincubator.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 具体的申请详情页
 */

public class MyApplyActivity extends AppCompatActivity implements IMyApplyView {

    @Bind(R.id.myapply_back)
    ImageView mMyapplyBack;
    @Bind(R.id.myapply_phone)
    ImageView mMyapplyPhone;
    @Bind(R.id.details_text_title)
    TextView mDetailsTextTitle;
    @Bind(R.id.myapply_img)
    ImageView mMyapplyImg;
    @Bind(R.id.myapply_name)
    TextView mMyapplyName;
    @Bind(R.id.myapply_price)
    TextView mMyapplyPrice;
    @Bind(R.id.myapply_adress)
    TextView mMyapplyAdress;
    @Bind(R.id.myapply_state)
    TextView mMyapplyState;
    @Bind(R.id.myapply_extra)
    TextView mMyapplyExtra;
    private MyApplyPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apply);
        ButterKnife.bind(this);
        mPresenter=new MyApplyPresenter(MyApplyActivity.this);
        mPresenter.setUI();
    }

    @OnClick({R.id.myapply_back, R.id.myapply_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myapply_back:
                MyApplyActivity.this.finish();
                break;
            case R.id.myapply_phone:
                break;
        }
    }

    @Override
    public void setImage(String url) {
        Glide.with(getBaseContext()).load(url).into(mMyapplyImg);

    }

    @Override
    public void setName(String name) {
        mMyapplyName.setText(name);

    }

    @Override
    public void setPrice(int price) {
        //

    }

    @Override
    public void setAdress(String adress) {
        mMyapplyAdress.setText(adress);
    }

    @Override
    public void setState(String state) {
mMyapplyState.setText(state);
    }

    @Override
    public void setExtra(String extra) {
mMyapplyExtra.setText(extra);
    }

    @Override
    public int getId() {
        Intent intent=getIntent();
        int id = intent.getIntExtra("apply_id", 0);
        return id;
    }
}
