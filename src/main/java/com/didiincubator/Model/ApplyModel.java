package com.didiincubator.Model;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.didiincubator.Beans.ApplyBean;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

/**
 * Created by 枫叶1 on 2016/6/2.
 */
public class ApplyModel implements IApplyModel {
    public static final int MSG = 0x111;//子线程向主线程发送 的信息
    public static final String MYHTTP = "http://10.201.1.46:8080/Didiweb/";
    public RequestQueue mQueue=NoHttp.newRequestQueue();

    private int result;

    @Override
    public int submit(ApplyBean applyBean, final Handler handler) {

        Gson gson=new Gson();
        String applyBeanJson = gson.toJson(applyBean);
        String url= MYHTTP +"applyServlet";
        Request<String> request= NoHttp.createStringRequest(url);
        request.add("method","add" );
        request.add("Json",applyBeanJson);

        mQueue.add(1, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
             int id=  Integer.parseInt(response.get()) ;
                Message msg = new Message();
                msg.what = MSG;
                Bundle bundle=new Bundle();
                bundle.putInt("apply_id",id);
                msg.setData(bundle);
                handler.sendMessage(msg);

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
        return result;
    }
}
