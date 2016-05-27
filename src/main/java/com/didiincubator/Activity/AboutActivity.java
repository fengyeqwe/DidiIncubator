package com.didiincubator.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.didiincubator.R;

public class AboutActivity extends AppCompatActivity {
    ImageView aboutBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        initListener();
    }

    private void initView() {
        aboutBack= (ImageView) findViewById(R.id.about_back);
    }

    private void initListener() {
        aboutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isClickable()){
                    startActivity(new Intent(AboutActivity.this,MainActivity.class));
                    finish();
                }
            }
        });
    }
}
