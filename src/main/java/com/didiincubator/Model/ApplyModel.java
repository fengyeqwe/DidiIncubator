package com.didiincubator.Model;

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
    public RequestQueue mQueue=NoHttp.newRequestQueue();
    @Override
    public Boolean submit(ApplyBean applyBean) {
        Gson gson=new Gson();
        String applyBeanJson = gson.toJson(applyBean);
        Request<String> request= NoHttp.createStringRequest("http://10.201.1.152:8080/Didiweb/applyServlet");
        request.add("method","add" );
        request.add("Json",applyBeanJson);
        mQueue.add(1, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                //

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
        return true;
    }
}
