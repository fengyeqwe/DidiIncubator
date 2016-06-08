package com.didiincubator.Model;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.didiincubator.Beans.ApplyBean;
import com.didiincubator.Beans.ApplyListBean;
import com.didiincubator.Beans.DidiBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 枫叶1 on 2016/6/6.
 */
public class ApplyListModel implements IApplyListModel {
    public static final String MYHTTP = "http://10.201.1.46:8080/Didiweb/";
    public static final int MESSAGE_WHAT = 0x111;
    public RequestQueue mQueue;
    public Gson mGson;
    public ApplyListModel(){
        mQueue= NoHttp.newRequestQueue();
        mGson=new Gson();
    }
    @Override
    public ArrayList<ApplyBean> download(int user_id, final Handler handler) {
        String url=MYHTTP+"applyServlet";
        Request<JSONArray> request=NoHttp.createJsonArrayRequest(url);
        request.add("method","selectUser");
        request.add("user_id",user_id);
        mQueue.add(1, request, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                JSONArray result = response.get();
                List<ApplyListBean> list = mGson.fromJson(result.toString(), new TypeToken<List<ApplyListBean>>() {
                }.getType());
                ArrayList list1=new ArrayList();
                list1.add(list);//bundle传递集合需要包装；
                Message msg = new Message();
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("list",list1);
                msg.what = MESSAGE_WHAT;
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

        return null;
    }
}
