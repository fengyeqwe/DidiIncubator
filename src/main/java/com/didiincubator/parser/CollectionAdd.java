package com.didiincubator.parser;

import com.baidu.location.Address;
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
//点击收藏按钮，把选中的孵化器添加到收藏中
public class CollectionAdd {
    public static  final  int NOHTTP_ADD=0x003;
    RequestQueue queue;
    public CollectionAdd(){
        queue= NoHttp.newRequestQueue();
    }
    public void addIncubator(int id){
        String method="add";
        int user_id=1;
        int didi_id=id;
        String url="http://115.28.78.82:8080/Didiweb/collectionServlet";
        Request<JSONArray> request=NoHttp.createJsonArrayRequest(url, RequestMethod.GET);
        request.add("method",method);
        request.add("user_id",user_id);
        request.add("didi_id",didi_id);
        queue.add(NOHTTP_ADD, request, new OnResponseListener<JSONArray>() {
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
