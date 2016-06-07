package com.didiincubator.View;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.didiincubator.Adapter.ApplyAdapter;
import com.didiincubator.Beans.ApplyBean;
import com.didiincubator.Beans.ApplyListBean;
import com.didiincubator.Presenter.ApplyListPresenter;
import com.didiincubator.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

/**
 * 申请列表页面
 */

public class ApplyListActivity extends AppCompatActivity implements IApplyListView {
    PullToRefreshListView mListView;
    ImageView mImageViewback;
    ArrayList<ApplyListBean> mList;
    private ApplyAdapter myadapter;
    public static final int MESSAGE_WHAT = 0x111;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;
    private ApplyListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_list);
        initView();
       initData();
        initListener();


    }

    private void initData() {
       mPresenter= new ApplyListPresenter(this);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == MESSAGE_WHAT) {
                   ArrayList list= msg.getData().getParcelableArrayList("list");
                    mList= (ArrayList< ApplyListBean>) list.get(0);
                    Log.e("apply",mList.toString());
                    myadapter = new ApplyAdapter(ApplyListActivity.this, mList);
                    mListView.setAdapter(myadapter);


                }
            }
        };
        mPresenter.getData(1,handler);//用户id不确定

    }

    private void initListener() {
        mImageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyListActivity.this.finish();
            }
        });
        //设置下拉监听事件
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME |
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                new ApplyListTask().execute();


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME |
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                new ApplyListTask().execute();
            }
        });

    }


    private void initView() {
        mImageViewback= (ImageView) findViewById(R.id.apply_list_back);
        mListView = (PullToRefreshListView) findViewById(R.id.apply_list_listview);
        mList=new ArrayList<>();

    }

    @Override
    public ArrayList<ApplyBean> getData() {
        return null;
    }
    class ApplyListTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            initData();
            myadapter.notifyDataSetChanged();
            mListView.onRefreshComplete();
            super.onPostExecute(s);
        }
    }


}
