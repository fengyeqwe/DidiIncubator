package com.didiincubator.Model;


import com.didiincubator.Beans.DidiBean;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import org.json.JSONArray;

/**
 * Created by 枫叶1 on 2016/5/24.
 */
public class DetailModel implements IdetailModel {
    private Object result;
    RequestQueue queue;
    Gson gson=new Gson();
    OnResponseListener onResponseListener=new OnResponseListener() {
        @Override
        public void onStart(int i) {

        }

        @Override
        public void onSucceed(int i, Response response) {
            result=response.get();
            didi=gson.fromJson(result.toString(),DidiBean.class);



        }

        @Override
        public void onFailed(int i, String s, Object o, Exception e, int i1, long l) {

        }

        @Override
        public void onFinish(int i) {

        }
    };
    private DidiBean didi;


    @Override
    public void setId(int id) {

    }

    @Override
    public void load(int id) {
        queue= NoHttp.newRequestQueue();
        String url="http://10.201.1.213:8080/Didiweb/DidiServlet";
        Request<JSONArray> request= NoHttp.createJsonArrayRequest(url);
        request.add("method","select");
        request.add("id",id);
        queue.add(1,request,onResponseListener);
    }

    @Override
    public DidiBean getLoad() {
        return didi;
    }


}
