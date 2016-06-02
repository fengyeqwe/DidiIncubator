package com.didiincubator.Activity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.didiincubator.Adapter.CollectionAdapter;
import com.didiincubator.Beans.DidiBean;
import com.didiincubator.R;
import com.didiincubator.listener.CollectionListener;
import com.didiincubator.utils.DidiApplication;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;
import com.yolanda.nohttp.error.ClientError;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.UnKnownHostError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class CollectionActivity extends AppCompatActivity {
     public  static final int NOHTTP_WHAT_TEST = 0x001;
    ImageView imageCollection;
    PullToRefreshListView listView;
    List<DidiBean> list;
    CollectionAdapter adapter;
    DidiBean incubator;
    CollectionListener collectionListener;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
        doGet();//获取网络数据，并进行解析
        initListener();//返回监听
        adapter = new CollectionAdapter(list,this);
        listView.setAdapter(adapter);
       // initListView();//这个监听位置并没有什么卵用
        listView.setMode(PullToRefreshBase.Mode.BOTH);//设置刷新加载模式
        initRefreshListView();//刷新界面
        initRefreshListener();//监听上拉加载，下拉刷新
    }

    private void initView() {
        imageCollection= (ImageView) findViewById(R.id.collection_back);
        listView= (PullToRefreshListView) findViewById(R.id.collection_listview);
        queue= NoHttp.newRequestQueue();
        list=new ArrayList<>();
        collectionListener=new CollectionListener(listView,list,CollectionActivity.this,queue,adapter);
        collectionListener.ListenerLong();
        collectionListener.ListenerShort();
    }

    private void doGet() {
        String method="selectall";
        String name="";
        String url="http://10.201.1.152:8080/Didiweb/DidiServlet";
        final Request<JSONArray> request= NoHttp.createJsonArrayRequest(url, RequestMethod.GET);
        request.add("method",method);
        request.add("name",name);
        queue.add(NOHTTP_WHAT_TEST, request, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }
            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                if (what == NOHTTP_WHAT_TEST) {
                    JSONArray result = response.get();
                    //Toast.makeText(CollectionActivity.this,result.toString(),Toast.LENGTH_LONG).show();
                    for (int i = 0; i < result.length();i++) {
                        incubator = new DidiBean();
                        try {
                            JSONObject object = result.getJSONObject(i);
                            incubator.setName(object.getString("name"));
                            incubator.setHeadPortrait(object.getString("headPortrait"));
                            //Toast.makeText(CollectionActivity.this, incubator.toString(), Toast.LENGTH_LONG).show();
                            list.add(incubator);
                            adapter.notifyDataSetChanged();
                            //Toast.makeText(myApplication,list.size()+"..", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                    if (exception instanceof ClientError){//客户端错误
                        if (responseCode==400){//服务器未能理解请求
                            Toast.makeText(CollectionActivity.this, "错误的请求，服务器表示不能理解", Toast.LENGTH_SHORT).show();
                        }else if (responseCode==404){
                            Toast.makeText(CollectionActivity.this, "错误的请求，服务器表示找不到", Toast.LENGTH_SHORT).show();
                        }
                    }else if (exception instanceof ServerError){//服务器错误
                        if (responseCode==500){
                            Toast.makeText(CollectionActivity.this, "服务器遇到不可预知的情况", Toast.LENGTH_SHORT).show();
                        }else if (responseCode==504){
                            Toast.makeText(CollectionActivity.this, "网关超时", Toast.LENGTH_SHORT).show();
                       }else{
                            Toast.makeText(CollectionActivity.this, "服务器遇到不可预知的状况", Toast.LENGTH_SHORT).show();
                        }
                    }else if (exception instanceof NetworkError){//网络不好
                        Toast.makeText(CollectionActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                    }else if (exception instanceof TimeoutError){//请求超时
                        Toast.makeText(CollectionActivity.this, "请求超时，网络不好或者服务器不稳定", Toast.LENGTH_SHORT).show();
                    }else if (exception instanceof UnKnownHostError){//找不到服务器
                        Toast.makeText(CollectionActivity.this, "未发现服务器", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(CollectionActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
    /*//解析图片
    public void getImages(){
        String method="selectall";
        String id="";
        String url="http://115.28.78.82:8080/Didiweb/DidiServlet";
        final Request<JSONArray> request= NoHttp.createJsonArrayRequest(url, RequestMethod.GET);
        request.add("method",method);
        request.add("id",id);
        queue.add(NOHTTP_WHAT_PICTURES, request, new OnResponseListener<JSONArray>() {
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
*/


//返回
    private void initListener() {
        imageCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isClickable()){
                    startActivity(new Intent(CollectionActivity.this,MainActivity.class));
                    finish();
                }
            }
        });
    }
   /* public void error(){
        errorImageView=new ImageView(CollectionActivity.this);
        errorImageView.setImageResource(R.id.);
    }*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelAll();
        queue.stop();
    }


    private void initRefreshListView() {
        ILoadingLayout startLabels =listView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");
        startLabels.setRefreshingLabel("正在刷新...");
        startLabels.setReleaseLabel("放开刷新");
        ILoadingLayout endLabels =listView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开加载...");
    }

    private void initRefreshListener() {

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉时
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                //设置刷新时间
                String label= DateUtils.formatDateTime(getApplicationContext(),System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME|DateUtils.FORMAT_SHOW_DATE|DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                new LoadDataAsyncTask(CollectionActivity.this).execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //if (list.size()==)
                new LoadDataAsyncTask(CollectionActivity.this).execute();//执行下载数据
            }
        });

    }

    static  class  LoadDataAsyncTask extends AsyncTask<Void,Void,String> {
        private CollectionActivity collectionActivity;
        public LoadDataAsyncTask(CollectionActivity nohttpActivity){
            this.collectionActivity=nohttpActivity;
        }

        @Override
        protected String doInBackground(Void... params) {
            //用一个线程来模拟刷新
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //加载数据
            collectionActivity.list.clear();

            collectionActivity.doGet();
            return "success";
        }
        //对返回值进行操作

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("success".equals(s)){
                //nohttpActivity.listView.getRefreshableView().setSelection(position);
                collectionActivity.adapter.notifyDataSetChanged();//通知数据及改变，界面刷新
                collectionActivity.listView.onRefreshComplete();//刷新完成
            }
        }
    }


}
