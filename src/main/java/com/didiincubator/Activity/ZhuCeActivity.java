package com.didiincubator.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.didiincubator.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ZhuCeActivity extends AppCompatActivity implements View.OnClickListener {
    Button huoquyanzhengma;
    Button fuwuxieyiButton;
    EditText usernameE;
    EditText shuruyanzhengma;
    EditText passwordE;
    Button zhuceButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        shuruyanzhengma = (EditText) findViewById(R.id.shuruyanzhengma);
        huoquyanzhengma = (Button) findViewById(R.id.huoquyanzhengma);
        zhuceButton = (Button) findViewById(R.id.zhuce);
        fuwuxieyiButton = (Button) findViewById(R.id.fuwuxieyi);
        usernameE = (EditText) findViewById(R.id.username);
        passwordE = (EditText) findViewById(R.id.password);
        shuruyanzhengma.setOnClickListener(this);
        huoquyanzhengma.setOnClickListener(this);
        zhuceButton.setOnClickListener(this);
        fuwuxieyiButton.setOnClickListener(this);
        usernameE.setOnClickListener(this);
        passwordE.setOnClickListener(this);
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
                String name = usernameE.getText().toString();
                String password = passwordE.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "用户名密码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    //使用POST方法向服务器发送数据
                    PostThread postThread = new PostThread(name, password);
                    postThread.start();
                    //finish();//  关闭当前 Activity:传递数据给当前 Activity 的调用者,调用者将执行 onActivityResult 方法
                }
                break;
            case  R.id.huoquyanzhengma:

                break;
            default:
                break;
        }
    }

    //子线程：使用POST方法向服务器发送用户名、密码等数据
    class PostThread extends Thread {

        String name;
        String password;

        public PostThread(String name, String password) {
            this.name = name;
            this.password = password;
        }

        @Override
        public void run() {
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://10.201.1.152:8080/Didiweb/userServlet";
            //第二步：生成使用POST方法的请求对象
            HttpPost httpPost = new HttpPost(url);
            //NameValuePair对象代表了一个需要发往服务器的键值对
            NameValuePair pair1 = new BasicNameValuePair("name", name);
            NameValuePair pair2 = new BasicNameValuePair("password", password);
            //将准备好的键值对对象放置在一个List当中
            ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("method", "add"));
            pairs.add(pair1);
            pairs.add(pair2);
            try {
                //创建代表请求体的对象（注意，是请求体）
                HttpEntity requestEntity = new UrlEncodedFormEntity(pairs);
                //将请求体放置在请求对象当中
                httpPost.setEntity(requestEntity);
                //执行请求对象
                try {
                    //第三步：执行请求对象，获取服务器发还的相应对象
                    HttpResponse response = httpClient.execute(httpPost);
                    System.out.println(response.getStatusLine().getStatusCode());
                    //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
                    if (response.getStatusLine().getStatusCode() == 200) {

                        //第五步：从相应对象当中取出数据，放到entity当中
                        HttpEntity entity = response.getEntity();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(entity.getContent()));
                        String result = reader.readLine();
                        Log.d("HTTP", "POST:" + result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}


