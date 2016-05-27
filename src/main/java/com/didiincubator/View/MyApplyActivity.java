package com.didiincubator.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.didiincubator.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyApplyActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apply);
        ButterKnife.bind(this);
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
}
