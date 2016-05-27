package com.didiincubator.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.didiincubator.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ZhuCeActivity extends AppCompatActivity implements View.OnClickListener {
    Button fuwuxieyiButton;
    EditText username;
    EditText password;
    Button zhuceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        zhuceButton= (Button) findViewById(R.id.zhuce);
        fuwuxieyiButton = (Button) findViewById(R.id.fuwuxieyi);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        zhuceButton.setOnClickListener(this);
        fuwuxieyiButton.setOnClickListener(this);
        username.setOnClickListener(this);
        password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fuwuxieyi:
                Intent intent = new Intent();
                intent.setClass(ZhuCeActivity.this, FuwuxieyiActivity.class);
                startActivity(intent);
                break;
            case R.id.zhuce:
                String uname = username.getText().toString();
                String upass = password.getText().toString();
                if (TextUtils.isEmpty(uname) || TextUtils.isEmpty(upass)) {
                    Toast.makeText(this, "用户名密码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                    // 使用 Intent 保存数据
                    Intent data = new Intent(this,LoginActivity.class);
                    data.putExtra("uname", uname);
                    data.putExtra("upass", upass);
                    startActivity(data);
                    run();
                    //finish();//  关闭当前 Activity:传递数据给当前 Activity 的调用者,调用者将执行 onActivityResult 方法
                }
                break;
            default:
                break;
        }
    }

    private void run() {
        /**
         * 请求地址
         */
        JSONObject userJSON = new JSONObject();
        try {
            userJSON.put("name",username);
            userJSON.put("password",password);
           // JSONObject object=new JSONObject();
            //object.put("user",userJSON);
            String content=String.valueOf(userJSON);
            String url="/UserServlet";
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                OutputStream os=connection.getOutputStream();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
