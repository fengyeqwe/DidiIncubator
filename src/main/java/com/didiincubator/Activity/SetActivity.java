package com.didiincubator.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.didiincubator.R;

public class SetActivity extends AppCompatActivity {
    ImageView imageSet;
    CheckBox checkUpdate,checkSend;
    TextView textClearJL,textClearHC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initView();
        initListener();
    }


    private void initView() {
        imageSet= (ImageView) findViewById(R.id.set_back);
        checkUpdate= (CheckBox) findViewById(R.id.set_update);
        checkSend= (CheckBox) findViewById(R.id.set_sendMessage);
        textClearJL= (TextView) findViewById(R.id.set_clearSouSuo);
        textClearHC= (TextView) findViewById(R.id.set_clearHc);
    }


    private void initListener() {
        imageSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isClickable()){
                    startActivity(new Intent(SetActivity.this,MainActivity.class));
                    finish();
                }
            }
        });
    }
}
