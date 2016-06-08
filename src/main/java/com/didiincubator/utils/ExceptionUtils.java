package com.didiincubator.utils;

import android.content.Context;
import android.widget.Toast;
import com.yolanda.nohttp.error.ClientError;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.UnKnownHostError;

/**
 * Created by 何晓 on 2016/6/2.
 */
public class ExceptionUtils {
    Context context;
    public ExceptionUtils(Context context){
        this.context=context;
    }
    public void exceptionMessage(Exception exception,int responseCode){
        if (exception instanceof ClientError){//客户端错误
            if (responseCode==400){//服务器未能理解请求
                Toast.makeText(context, "错误的请求，服务器表示不能理解", Toast.LENGTH_SHORT).show();
            }else if (responseCode==404){
                Toast.makeText(context, "错误的请求，服务器表示找不到", Toast.LENGTH_SHORT).show();
            }
        }else if (exception instanceof ServerError){//服务器错误
            if (responseCode==500){
                Toast.makeText(context, "服务器遇到不可预知的情况....", Toast.LENGTH_SHORT).show();
            }else if (responseCode==504){
                Toast.makeText(context, "网关超时", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "服务器遇到不可预知的状况", Toast.LENGTH_SHORT).show();
            }
        }else if (exception instanceof NetworkError){//网络不好
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }else if (exception instanceof TimeoutError){//请求超时
            Toast.makeText(context, "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
        }else if (exception instanceof UnKnownHostError){//找不到服务器
            Toast.makeText(context, "未发现服务器", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
        }
    }
}
