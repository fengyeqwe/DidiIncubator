package com.didiincubator.listener;
import com.yolanda.nohttp.Response;
//回调结果
/**
 * Created by 何晓 on 2016/5/31.
 */
public interface HttpListener<T> {
    void onSucceed(int what, Response<T> response);
    void onFailed(int what,String url,Object tag,Exception exception,int responseCode,long networkMillis);
}
