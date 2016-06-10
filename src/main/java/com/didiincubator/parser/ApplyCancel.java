package com.didiincubator.parser;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import org.json.JSONArray;

/**
 * Created by 枫叶1 on 2016/6/10.
 * 取消申请
 * 未设置返回值
 */
public class ApplyCancel {
    public static final String MYHTTP = "http://10.201.1.46:8080/Didiweb/";
    RequestQueue mQueue;
    public ApplyCancel(){
        mQueue= NoHttp.newRequestQueue();
    }
    public boolean cancelApply(int id){
        String url=MYHTTP+"applyServlet";
        Request<JSONArray> request=NoHttp.createJsonArrayRequest(url);
        request.add("method","delete");
        request.add("id",id);
        mQueue.add(1, request, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });

        return false;
    }
}
