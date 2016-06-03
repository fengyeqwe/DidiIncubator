package com.didiincubator.parser;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import org.json.JSONArray;

/**
 * Created by 何晓 on 2016/6/2.
 */
//取消选中的收藏的孵化器
public class CollectionCancle {
    public static  final  int NOHTTP_Cancle=0x004;
    RequestQueue queue;
    public CollectionCancle(){
        queue= NoHttp.newRequestQueue();
    }
    public void cancleIncubator(int id){
        String method="delete";
        int user_id=1;
        int didi_id=id;
        String url="http://10.201.1.152:8080/Didiweb/collectionServlet";
        Request<JSONArray> request=NoHttp.createJsonArrayRequest(url, RequestMethod.GET);
        request.add("method",method);
        request.add("user_id",user_id);
        request.add("didi_id",didi_id);
        queue.add(NOHTTP_Cancle, request, new OnResponseListener<JSONArray>() {
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
    }
}
