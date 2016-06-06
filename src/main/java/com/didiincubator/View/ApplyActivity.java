package com.didiincubator.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.didiincubator.Beans.DidiBean;
import com.didiincubator.Presenter.ApplyPresenter;
import com.didiincubator.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyActivity extends AppCompatActivity implements IApplyView {

    @Bind(R.id.details_btn_back)
    ImageView mDetailsBtnBack;
    @Bind(R.id.details_text_title)
    TextView mDetailsTextTitle;
    @Bind(R.id.apply_name)
    EditText mApplyName;
    @Bind(R.id.apply_phone)
    EditText mApplyPhone;
    @Bind(R.id.apply_num)
    EditText mApplyNum;
    @Bind(R.id.apply_time)
    EditText mApplyTime;
    @Bind(R.id.apply_extra)
    EditText mApplyExtra;
    @Bind(R.id.apply_submit)
    Button mApplySubmit;
    @Bind(R.id.apply_looktime)
    EditText mApplyLooktime;
    private DidiBean didi;
    private ApplyPresenter applyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        applyPresenter=new ApplyPresenter(ApplyActivity.this);
        didi = (DidiBean) getIntent().getSerializableExtra("didi");
    }

    @OnClick({R.id.details_btn_back, R.id.apply_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.details_btn_back:
                ApplyActivity.this.finish();
                break;
            case R.id.apply_submit:
                submitApply();

                break;
        }
    }

    private void submitApply() {

        Handler handler = new Handler() {

            public static final int MSG = 0x111;

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == MSG) {
                    int result = msg.getData().getInt("apply_id");
                    if (result!=0){
                        //跳转申请页
                        Intent intent = new Intent(ApplyActivity.this, MyApplyActivity.class);
                        intent.putExtra("apply_id",result);
           /* Bundle mBundle = new Bundle();
            mBundle.putSerializable("didi", didi);
            intent.putExtras(mBundle);*/
                        startActivity(intent);
                    }

                }
            }

        };
        applyPresenter.submit(handler);


    }

    @Override
    public String getName() {
        return mApplyName.getText().toString();
    }

    @Override
    public int getUser_id() {
        return 1;
    }

    @Override
    public int getDidi_id() {
        return didi.getId();
    }

    @Override
    public int getPhone() {
        return Integer.parseInt(mApplyPhone.getText().toString());
    }

    @Override
    public String getLookhousetime() {
        return mApplyLooktime.getText().toString();
    }

    @Override
    public String getExtra() {
        return mApplyExtra.getText().toString();
    }

    @Override
    public int getCount() {
        return Integer.parseInt(mApplyNum.getText().toString());
    }

    @Override
    public int getTime() {
        return Integer.parseInt(mApplyTime.getText().toString());
    }


}
