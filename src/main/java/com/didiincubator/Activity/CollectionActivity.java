package com.didiincubator.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.didiincubator.Adapter.CollectionAdapter;
import com.didiincubator.Beans.Incubator;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {
    private static final int NOHTTP_WHAT_TEST = 0x001;
    ImageView imageCollection;
    PullToRefreshListView listView;
    List<Incubator> list;
    CollectionAdapter adapter;
    Incubator incubator;
    DidiApplication myApplication;
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
        myApplication= (DidiApplication) getApplication();
        queue= NoHttp.newRequestQueue();
        list=new ArrayList<>();
        collectionListener=new CollectionListener(listView,list,CollectionActivity.this);
        collectionListener.ListenerLong();
        collectionListener.ListenerShort();
    }

    private void doGet() {
        String url="http://10.201.1.6:8080/didi1/didi";
        final Request<JSONArray> request= NoHttp.createJsonArrayRequest(url, RequestMethod.GET);
        queue.add(NOHTTP_WHAT_TEST, request, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }
            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                if (what == NOHTTP_WHAT_TEST) {
                    JSONArray result = response.get();
                    for (int i = 0; i < result.length(); i++) {
                        incubator = new Incubator();
                        try {
                            JSONObject object = result.getJSONObject(i);
                            incubator.setSketch(object.getString("sketch"));
                            incubator.setDetails(object.getString("details"));
                            incubator.setArea(object.getDouble("area"));
                            incubator.setNum(object.getInt("num"));
                            incubator.setLocal(object.getString("local"));
                            incubator.setType(object.getString("type"));
                            incubator.setName(object.getString("name"));
                            //Toast.makeText(NohttpActivity.this, incubator.toString(), Toast.LENGTH_LONG).show();
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

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

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
