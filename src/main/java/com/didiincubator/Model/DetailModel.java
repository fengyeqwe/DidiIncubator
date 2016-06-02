package com.didiincubator.Model;


import com.didiincubator.Beans.DidiBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by 枫叶1 on 2016/5/24.
 */
public class DetailModel implements IdetailModel {
    private Object result;
    RequestQueue queue;
    Gson gson=new Gson();

    private DidiBean didi;


    @Override
    public void setId(int id) {

    }

    @Override
    public void load(int id) {
        queue= NoHttp.newRequestQueue();
        String url="http://10.201.1.152:8080/Didiweb/DidiServlet";
        Request<JSONArray> request= NoHttp.createJsonArrayRequest(url);
        request.add("method","select");
        request.add("id",id);
        queue.add(1, request, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                List<DidiBean> list = gson.fromJson(result.toString(), new TypeToken<List<DidiBean>>() {
                }.getType());
                didi=list.get(0);

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public DidiBean getLoad() {
        return didi;
    }


}
