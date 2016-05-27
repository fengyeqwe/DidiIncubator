package com.didiincubator.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.didiincubator.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyActivity extends AppCompatActivity implements IApplyView{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.details_btn_back, R.id.apply_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.details_btn_back:
                ApplyActivity.this.finish();
                break;
            case R.id.apply_submit:
                submitApply();
                //��ת���ҵ�����ҳ��
                Intent intent=new Intent(ApplyActivity.this,MyApplyActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void submitApply() {

    }

    @Override
    public String getName() {
        return mApplyName.getText().toString();
    }

    @Override
    public int getPhone() {
        return Integer.parseInt(mApplyPhone.getText().toString());
    }

    @Override
    public int getCount() {
        return Integer.parseInt(mApplyNum.getText().toString());
    }

    @Override
    public int getTime() {
        return Integer.parseInt(mApplyTime.getText().toString());
    }

    @Override
    public String extra() {
        return mApplyExtra.getText().toString();
    }
}
