package com.didiincubator.View;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.didiincubator.Adapter.HistoryAdapter;
import com.didiincubator.Beans.DidiBean;
import com.didiincubator.R;
import com.didiincubator.utils.HistoryHelper;
import com.didiincubator.utils.HistoryTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements ExpandableListView.OnGroupClickListener {
    private static final int NOHTTP_WHAT = 1;
    ImageView imageHistory;
    PullToRefreshExpandableListView historyListView;

    SQLiteDatabase mDataBase;
    //sqlite辅助类
    HistoryHelper mHistoryHelper;
    private Cursor mCursor;
    private ArrayList<String> times;
    private ArrayList<ArrayList<DidiBean>> didis;

    private HistoryAdapter adaper;
    private Gson gson;
    private RequestQueue mRequestQueue;
    int position = 0;
    private int countCursor;//记录使用了几条Cursor数据
    private String isTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        //设置刷新加载
        initListener();
        initData();
        initUpdate();
    }

    private void initUpdate() {
        historyListView.getRefreshableView().setAdapter(adaper);
        historyListView.getRefreshableView().setGroupIndicator(null);
        historyListView.getRefreshableView().setDivider(null);
        historyListView.getRefreshableView().setSelector(android.R.color.transparent);
        historyListView.getRefreshableView().setOnGroupClickListener(this);
        historyListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                //getAdapterData();


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

            }
        });

        historyListView.setMode(PullToRefreshBase.Mode.BOTH);
      //  historyListView.getLoadingLayoutProxy(false,true).setPullLabel(getString(R.string.pull_to_refresh_from_bottom_pull_label));
        historyListView.getLoadingLayoutProxy(false,true).setPullLabel("loading");
       // historyListView.getLoadingLayoutProxy(false,true).setRefreshingLabel(getString(R.string.rc_notice_data_is_loading));
        historyListView.getLoadingLayoutProxy(false,true).setRefreshingLabel("freshing");
       // historyListView.getLoadingLayoutProxy(false,true).setReleaseLabel(getString(R.string.pull_to_refresh_from_bottom_release_label
       // ));
        historyListView.getLoadingLayoutProxy(false,true).setReleaseLabel("???");




    }

    //加载孵化器浏览信息
    private void initData() {
         isTime = null;//用来判断日期是否一致
        times = new ArrayList<String>();
        didis = new ArrayList<ArrayList<DidiBean>>();
        mDataBase = mHistoryHelper.getReadableDatabase();
        String sql = "select * from " + HistoryTable.Field.TABLE_NAME + " order by " + HistoryTable.Field._ID + " desc";
        mCursor = mDataBase.rawQuery(sql, null);
        countCursor=0;
        getAdapterData();//获取每次界面展示的数据
        adaper = new HistoryAdapter(HistoryActivity.this, times, didis);


        //设置列表默认展开；


    }

    private void getAdapterData() {
        int count=0;
        while (mCursor.moveToPosition(countCursor)/*&&count<3*/) {
            countCursor++;count++;
            String time = mCursor.getString(mCursor.getColumnIndex(HistoryTable.Field.HISTORY_TIME));
            int didi_id = mCursor.getInt(mCursor.getColumnIndex(HistoryTable.Field.HISTORY_DIDI_ID));
            if (!time.equals(isTime)) {
                times.add(time);
                isTime = time;
                didis.add(new ArrayList<DidiBean>());
            }
            DidiBean didi = getDidi(didi_id, times.size() - 1);//网络请求获取孵化器信息
        }
        getDidi(1, times.size());//无意义，为了调用最后一次打开列表
    }

    private void initView() {
        imageHistory = (ImageView) findViewById(R.id.history_back);
        historyListView = (PullToRefreshExpandableListView) findViewById(R.id.history_list);
        mHistoryHelper = new HistoryHelper(HistoryActivity.this);
        mRequestQueue = NoHttp.newRequestQueue();
        gson = new Gson();


    }

    private void initListener() {
        imageHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isClickable()) {
                    finish();
                }
            }
        });
    }

    //网络请求获取didi,what表示日期
    private DidiBean getDidi(int didi_id, int what) {
        DidiBean didi = new DidiBean();
        String url = "http://10.201.1.152:8080/Didiweb/DidiServlet";
        Request<JSONArray> request = NoHttp.createJsonArrayRequest(url);
        request.add("method", "select");
        request.add("id", didi_id);
        mRequestQueue.add(what, request, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {
                //Toast.makeText(HistoryActivity.this,"onstart".toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                //获取didiBean对象
                Log.e("Nohttp", "success");
                JSONArray result = response.get();
                // Toast.makeText(HistoryActivity.this,result.toString(),Toast.LENGTH_LONG).show();
                List<DidiBean> list = gson.fromJson(result.toString(), new TypeToken<List<DidiBean>>() {
                }.getType());
                if (what < didis.size()) {
                    didis.get(what).add(list.get(0));
                }
                Log.e("aaaaaaaa", what + "---" + position);
                if (what > position) {

                      historyListView.getRefreshableView().expandGroup(what - 1);
                    position = what;
                }

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
        return didi;
    }
    //historylistviewde click方法
    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }

}
