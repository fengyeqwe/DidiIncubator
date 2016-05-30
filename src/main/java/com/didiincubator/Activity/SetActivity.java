package com.didiincubator.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.didiincubator.R;
import com.didiincubator.manager.DataCleanManager;

public class SetActivity extends AppCompatActivity {
    public  final  static  String FILEPATH="com.didiincubator";
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

    //返回
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
        //缓存
        textClearHC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isClickable()){
                    AlertDialog.Builder builder=new AlertDialog.Builder(SetActivity.this);
                    builder.setMessage("确定清除缓存吗？")//
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           // if (dialog.)
                            DataCleanManager.cleanAll(SetActivity.this,FILEPATH);
                            Toast.makeText(SetActivity.this, "确定"+dialog.toString(), Toast.LENGTH_SHORT).show();
                            //if (which)
                        }
                    })//
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

                }
            }
        });
    }
}
