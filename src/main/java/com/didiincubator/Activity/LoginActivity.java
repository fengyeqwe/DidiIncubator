package com.didiincubator.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.didiincubator.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.utils.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


public class LoginActivity extends Activity implements View.OnClickListener {

    SharedPreferences sp;
    //AutoCompleteTextView username2;
    CheckBox jizhumimaButton;
    CheckBox zidongdengluButton;
    Button dengluButton;
    EditText username2;
    EditText password2;
    Button mBtnBindPhone;
    ImageButton sinaLoginButton;
    ImageButton qqLoginButton;
    Button zhuceButton;
    String APPKey = "12c642e24be2a";
    String AppSecret = "6a20f3cf7232612975faacf0afb693a7";

    // 整个平台的Controller,负责管理整个SDK的配置、操作等处理
    UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.share");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化sdk
        SMSSDK.initSDK(this, APPKey, AppSecret, true);
        //创建实例对象
        sp = this.getSharedPreferences("passwordFile", MODE_PRIVATE);
        zidongdengluButton = (CheckBox) findViewById(R.id.zidongdenglukuang);
        jizhumimaButton = (CheckBox) findViewById(R.id.jizhumimakuang);
        mBtnBindPhone = (Button) findViewById(R.id.shoujidenglu);
        sinaLoginButton = (ImageButton) findViewById(R.id.wbdenglu);
        qqLoginButton = (ImageButton) findViewById(R.id.qqdenglu);
        zhuceButton = (Button) findViewById(R.id.lijizhuce);
        dengluButton = (Button) findViewById(R.id.denglu);
        username2 = (EditText) findViewById(R.id.zhanghao);
        password2 = (EditText) findViewById(R.id.mima);
        jizhumimaButton.setOnClickListener(this);
        zidongdengluButton.setOnClickListener(this);
        sinaLoginButton.setOnClickListener(this);
        qqLoginButton.setOnClickListener(this);
        mBtnBindPhone.setOnClickListener(this);
        zhuceButton.setOnClickListener(this);
        dengluButton.setOnClickListener(this);
        //判断记住密码多选框的状态
        if (sp.getBoolean("ISCHECK", false)) {
            //设置默认是记录密码状态
            jizhumimaButton.setChecked(true);
            username2.setText(sp.getString("USER_NAME", ""));
            password2.setText(sp.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if (sp.getBoolean("AUTO_ISCHECK", false)) {
                //设置默认是自动登录状态
                zidongdengluButton.setChecked(true);
                //跳转界面
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        }

     /*   username2.setThreshold(1);// 输入1个字母就开始自动提示*/
        password2.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        // 隐藏密码为InputType.TYPE_TEXT_VARIATION_PASSWORD，也就是0x81
        // 显示密码为InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD，也就是0x91
        loginRongIM();//连接融云

        /*Intent data = getIntent();		// 获取 Intent
        if(data != null){
            String uname = data.getStringExtra("uname");
            String upass = data.getStringExtra("upass");
            username.setText(uname);
            password.setText(upass);
        }*/
        // 配置需要分享的相关平台
        configPlatforms();
        // 设置分享的内容
        setShareContent();
        /*//首字母提示
        username2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String[] allUserName = new String[sp.getAll().size()];
                allUserName = sp.getAll().keySet().toArray(new String[0]);
                // sp.getAll()返回一张hash map
                // keySet()得到的是a set of the keys.
                // hash map是由key-value组成的
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_dropdown_item_1line, allUserName);
                username2.setAdapter(adapter);// 设置数据适配器
            }

            @Override
            public void afterTextChanged(Editable s) {
                password2.setText(sp.getString(username2.getText().toString(), ""));// 自动输入密码
            }
        });*/
    }

    /**
     * 根据不同的平台设置不同的分享内容</br>
     */
    private void setShareContent() {

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(LoginActivity.this
                , "1105330645", "qizrvnP5AuHIs2ks");
        qZoneSsoHandler.addToSocialSDK();
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能");

    }

    /**
     * 配置分享平台参数
     */
    private void configPlatforms() {
        // 添加新浪sso授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        //添加QQ、QZone平台
        addQQQZonePlatform();


    }

    private void addQQQZonePlatform() {
        String appId = "1105330645";
        String appkey = "qizrvnP5AuHIs2ks";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginActivity.this,
                appId, appkey);
        qqSsoHandler.setTargetUrl("http://www.umeng.com");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(LoginActivity.this, appId, appkey);
        qZoneSsoHandler.addToSocialSDK();
    }


    /**
     * 添加所有的平台</br>
     */
    private void addCustomPlatforms() {
        // 添加QQ平台
        addQQQZonePlatform();
        mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT, SHARE_MEDIA.DOUBAN,
                SHARE_MEDIA.RENREN, SHARE_MEDIA.EMAIL, SHARE_MEDIA.EVERNOTE,
                SHARE_MEDIA.FACEBOOK, SHARE_MEDIA.GOOGLEPLUS,
                SHARE_MEDIA.INSTAGRAM, SHARE_MEDIA.LAIWANG,
                SHARE_MEDIA.LAIWANG_DYNAMIC, SHARE_MEDIA.LINKEDIN,
                SHARE_MEDIA.PINTEREST, SHARE_MEDIA.POCKET, SHARE_MEDIA.SMS,
                SHARE_MEDIA.TWITTER, SHARE_MEDIA.YIXIN,
                SHARE_MEDIA.YIXIN_CIRCLE, SHARE_MEDIA.YNOTE);
        mController.openShare(LoginActivity.this, false);
    }

    /**
     * 授权。如果授权成功，则获取用户信息
     *
     * @param platform
     */
    private void login(final SHARE_MEDIA platform) {
        mController.doOauthVerify(LoginActivity.this, platform, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                Toast.makeText(LoginActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
// 获取uid
                String uid = value.getString("uid");
                if (!TextUtils.isEmpty(uid)) {
                    //uid不为空，获取用户信息
                    getUserInfo(platform);
                } else {
                    Toast.makeText(LoginActivity.this, "授权失败...",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
                Toast.makeText(LoginActivity.this, "授权失败",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(LoginActivity.this, "授权取消",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserInfo(SHARE_MEDIA platform) {
        mController.getPlatformInfo(LoginActivity.this, platform, new SocializeListeners.UMDataListener() {
            @Override
            public void onStart() {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onComplete(int status, Map<String, Object> info) {
                String showText = "";
                if (status == StatusCode.ST_CODE_SUCCESSED) {
                    showText = "用户名：" +
                            info.get("screen_name").toString();
                    Log.d("#########", "##########" + info.toString());
                } else {
                    showText = "获取用户信息失败";
                }

                if (info != null) {
                    Toast.makeText(LoginActivity.this, info.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 注销本次登陆
     *
     * @param platform
     */
    private void logout(final SHARE_MEDIA platform) {
        mController.deleteOauth(LoginActivity.this, platform, new SocializeListeners.SocializeClientListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(int status, SocializeEntity entity) {
                String showText = "解除" + platform.toString() + "平台授权成功";
                if (status != StatusCode.ST_CODE_SUCCESSED) {
                    showText = "解除" + platform.toString() + "平台授权失败[" + status + "]";
                }
                Toast.makeText(LoginActivity.this, showText, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 如果有使用任一平台的SSO授权, 则必须在对应的activity中实现onActivityResult方法, 并添加如下代码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 根据requestCode获取对应的SsoHandler
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                resultCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.denglu:
                String uname2 = username2.getText().toString();
                String upass2 = password2.getText().toString();
                if (TextUtils.isEmpty(uname2) || TextUtils.isEmpty(upass2)) {
                    Toast.makeText(this, "用户名密码不能为空！", Toast.LENGTH_SHORT).show();
                    //假设默认用户名为fei，密码为123
                } else if (uname2.equals("fei") && upass2.equals("123")) {
                    Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
                    //登陆成功且记住密码框选中才会保存用户信息
                    if (jizhumimaButton.isChecked()) {
                        //记住用户名密码
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", uname2);
                        editor.putString("PASSWORD", upass2);
                        editor.commit();
                    }
                    //跳转界面
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.lijizhuce:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, ZhuCeActivity.class);
                startActivity(intent);
                break;
            case R.id.qqdenglu:
                login(SHARE_MEDIA.QQ);
                break;
            case R.id.wbdenglu:
                login(SHARE_MEDIA.SINA);
                break;
            case R.id.shoujidenglu:
                //注册手机号
                RegisterPage registerPage = new RegisterPage();
                //注册回调事件
                registerPage.setRegisterCallback(new EventHandler() {
                    //事件完成后调用
                    @Override
                    public void afterEvent(int event, int result, Object data) {
                        //判断结果是否已经完成
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            //获取数据data
                            HashMap<String, Object> maps = (HashMap<String, Object>) data;
                            //国家
                            String country = (String) maps.get("country");
                            //手机号
                            String phone = (String) maps.get("phone");
                            submitUserInfo(country, phone);
                        }
                    }
                });
                //显示注册界面
                registerPage.show(LoginActivity.this);
                break;
            default:
                break;
        }
    }

    //提交用户信息
    public void submitUserInfo(String country, String phone) {
        Random r = new Random();
        String uid = Math.abs(r.nextInt()) + "";
        String nickName = "didi";//昵称
        SMSSDK.submitUserInfo(uid, nickName, null, country, phone);
    }

    public void testLogin(View view) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void loginRongIM() {
        String Token = "4wcFE8VSqaANqagxr5aPfMu90HFlJJMTczluFD70cwzAXQJUxaGI37ul0pG30J1SSWdb8pdC6btb0DL7DzDZJuzlESegJ1i2";
        RongIM.connect(Token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //连接失败
            }

            @Override
            public void onSuccess(String s) {
                android.util.Log.e("MainActivity", "onSuccess__________" + s);

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                android.util.Log.e("MainActivity", "onError__________" + errorCode);
            }
        });
        //监听记住密码框按钮事件
        jizhumimaButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (jizhumimaButton.isChecked()) {
                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();
                } else {
                    System.out.println("记住密码未选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();
                }
            }
        });
        //监听自动登录按钮事件
        zidongdengluButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (zidongdengluButton.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();
                } else {
                    System.out.println("自动登录未选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });
    }


}
