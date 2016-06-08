package com.didiincubator.Activity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.didiincubator.Adapter.CollectionAdapter;
import com.didiincubator.Beans.Collection;
import com.didiincubator.Beans.DidiBean;
import com.didiincubator.R;
import com.didiincubator.listener.CollectionListener;
import com.didiincubator.utils.ExceptionUtils;
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
     //public  static final int NOHTTP_WHAT_TEST = 0x005;
     public static final int NOHTTP_INCUBATORID=0x001;
    ImageView imageCollection;
    PullToRefreshListView listView;
    List<DidiBean> list;
    List<Integer>  incubatorID=new ArrayList<>();
    CollectionAdapter adapter;//适配器
    DidiBean incubator;//孵化器
    Collection collection;//收藏表
    CollectionListener collectionListener;//listview的监听
    RequestQueue queue;
    private Handler handler=new Handler(){
        public void dispatchMessage(Message message){
            if (1==message.what){
                anmiationCancle();
                doGet(incubatorID);
            }
        }
    };
    //加载动画
    ProgressBar progressBar;
    TextView loadTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
        getIncubatorID();
        //doGet(incubatorID);//获取网络数据，并进行解析
        handler.sendMessageDelayed(handler.obtainMessage(1),2000);
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
        progressBar= (ProgressBar) findViewById(R.id.load_progressBar);
        loadTextView= (TextView) findViewById(R.id.load_textView);
        queue= NoHttp.newRequestQueue();
        list=new ArrayList<>();
        collectionListener=new CollectionListener(listView,list,CollectionActivity.this,queue,adapter);
        collectionListener.ListenerLong();
        collectionListener.ListenerShort();
    }
    //从收藏表的到孵化器id
    private void getIncubatorID(){

        String method="selectall";
       // String name="";
        String url="http://115.28.78.82:8080/Didiweb/collectionServlet";
        final Request<JSONArray> request=NoHttp.createJsonArrayRequest(url,RequestMethod.GET);
        request.add("method",method);
        queue.add(NOHTTP_INCUBATORID, request, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                JSONArray result=response.get();
                for (int i=0;i<result.length();i++){
                    collection=new Collection();
                    try {
                        JSONObject object=result.getJSONObject(i);
                        collection.setDidi_id(object.getInt("didi_id"));
                        //int id=collection.getDidi_id();
                        int  id=collection.getDidi_id();
                        incubatorID.add(id);
                       // Toast.makeText(CollectionActivity.this, "didi_id:"+incubatorID.toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                ExceptionUtils exceptionUtils = new ExceptionUtils(CollectionActivity.this);
                exceptionUtils.exceptionMessage(exception, responseCode);
                exceptionError();
            }

            @Override
            public void onFinish(int what) {

            }
        });
     }
    //解析得到收藏的孵化器的信息
    private void doGet(List<Integer> lists) {
            //Toast.makeText(this, "lists"+lists, Toast.LENGTH_SHORT).show();
            int ids=0;
            for (int i=0;i<lists.size();i++) {
                //ids = lists.get(i);
                // }
                String method = "selectID";
                int id = lists.get(i);
            String url = "http://115.28.78.82:8080/Didiweb/DidiServlet";
                final Request<JSONArray> request = NoHttp.createJsonArrayRequest(url, RequestMethod.GET);
                request.add("method", method);
                request.add("id", id);
            queue.add(NOHTTP_INCUBATORID, request, new OnResponseListener<JSONArray>() {
                @Override
                public void onStart(int what) {

                }

                @Override
                public void onSucceed(int what, Response<JSONArray> response) {
                    if (what == NOHTTP_INCUBATORID) {
                        JSONArray result = response.get();
                        //Toast.makeText(CollectionActivity.this,result.toString(),Toast.LENGTH_LONG).show();
                        for (int i = 0; i < result.length(); i++) {
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
                    ExceptionUtils exceptionUtils = new ExceptionUtils(CollectionActivity.this);
                    exceptionUtils.exceptionMessage(exception, responseCode);
                    exceptionError();
                }

                @Override
                public void onFinish(int what) {

                }
            });
        }
    }


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

                new LoadDataAsyncTask(CollectionActivity.this,incubatorID).execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //if (list.size()==)
                new LoadDataAsyncTask(CollectionActivity.this,incubatorID).execute();//执行下载数据
            }
        });

    }

    static  class  LoadDataAsyncTask extends AsyncTask<Void,Void,String> {
        private CollectionActivity collectionActivity;
        private List<Integer> listID;
        public LoadDataAsyncTask(CollectionActivity nohttpActivity,List<Integer> listID){
            this.collectionActivity=nohttpActivity;
            this.listID=listID;
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

            collectionActivity.doGet(listID);
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
    //当加载出数据时，加载动画消失
    public  void anmiationCancle(){
        progressBar.setVisibility(View.GONE);
        loadTextView.setVisibility(View.GONE);
    }
    //当出现异常错误时显示的图片
    public void exceptionError(){
        ImageView imageView=new ImageView(CollectionActivity.this);
        imageView.setImageResource(R.drawable.loadfail);
        setContentView(imageView);
    }

}
