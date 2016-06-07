package com.didiincubator.Model;

import android.database.CursorJoiner;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.didiincubator.Beans.ApplyBean;
import com.didiincubator.Beans.ApplyResultBean;
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
 * Created by 枫叶1 on 2016/6/3.
 * 申请详情
 */
public class MyApplyModel implements IMyApplyModel{
    public static final String MYHTTP = "http://10.201.1.46:8080/Didiweb/";
    public static final int MSG_1 = 0X1;
    public static final int MSG_2 = 0X2;
    public static final int MSG_3 = 0X3;
    private RequestQueue mQueue;
    private Gson mGson;
    private ApplyBean getApplyresult;//getApply()请求结果
    private DidiBean didiresult;
    private ApplyResultBean applyResultresult;

    public MyApplyModel(){
        mQueue= NoHttp.newRequestQueue();
        mGson=new Gson();
    }
    @Override
    public ApplyBean getApply(int apply_id, final  Handler handler ) {
        String url= MYHTTP +"applyServlet";
        final Request<JSONArray> request=NoHttp.createJsonArrayRequest(url);
        request.add("method","select");
        request.add("id",apply_id);
        mQueue.add(1, request, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                JSONArray result = response.get();
                List<ApplyBean> list = mGson.fromJson(result.toString(), new TypeToken<List<ApplyBean>>() {
                }.getType());
                ApplyBean applyBean = list.get(0);
                Message msg=new Message();
                Bundle bundle=new Bundle();
                bundle.putSerializable("applyBean",applyBean);
                msg.setData(bundle);
                msg.what=MSG_1;
                handler.sendMessage(msg);


            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });

        return getApplyresult ;
    }

    @Override
    public DidiBean getDidi(int didi_id, final Handler handler) {
        String url= MYHTTP +"DidiServlet";
        final Request<JSONArray> request=NoHttp.createJsonArrayRequest(url);
        request.add("method","select");
        request.add("id",didi_id);

        mQueue.add(1, request, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                JSONArray result = response.get();
                List<DidiBean> list = mGson.fromJson(result.toString(), new TypeToken<List<DidiBean>>() {
                }.getType());
                Message msg=new Message();
                DidiBean didi = list.get(0);
                Bundle bundle=new Bundle();
                bundle.putSerializable("didi",didi);
                msg.setData(bundle);
                msg.what=MSG_2;
                handler.sendMessage(msg);



            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });

        return didiresult ;

    }

    @Override
    public ApplyResultBean getApplyResult(int apply_id,final Handler handler) {
        String url= MYHTTP +"applyresultServlet";
        final Request<JSONArray> request=NoHttp.createJsonArrayRequest(url);
        request.add("method","select");
        request.add("id",apply_id);

        mQueue.add(1, request, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                JSONArray result = response.get();
                List<ApplyResultBean> list = mGson.fromJson(result.toString(), new TypeToken<List<ApplyResultBean>>() {
                }.getType());
                Message msg=new Message();
                ApplyResultBean applyResultBean = list.get(0);
                Log.e("myapply",applyResultBean.getState());
                Bundle bundle=new Bundle();
                bundle.putSerializable("applyResult",applyResultBean);
                msg.setData(bundle);
                msg.what=MSG_3;
                handler.sendMessage(msg);



            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
        return applyResultresult;
    }
}
