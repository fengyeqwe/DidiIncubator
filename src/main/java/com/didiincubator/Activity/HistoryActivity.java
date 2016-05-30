package com.didiincubator.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.midi.MidiDeviceService;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.didiincubator.Adapter.HistoryAdapter;
import com.didiincubator.Beans.DidiBean;
import com.didiincubator.R;
import com.didiincubator.utils.HistoryHelper;
import com.didiincubator.utils.HistoryTable;

import java.net.PortUnreachableException;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    ImageView imageHistory;
    ExpandableListView historyListView;

    SQLiteDatabase mDataBase;
    //
    HistoryHelper mHistoryHelper;
    private Cursor mCursor;
    private ArrayList<String> times;
    private ArrayList<ArrayList<DidiBean>> didis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        initListener();
        initData();
    }

    private void initData() {
        String isTime=null;//用来判断日期是否一致
        times= new ArrayList<String>();
        didis=new ArrayList<ArrayList<DidiBean>>();
        mDataBase=mHistoryHelper.getReadableDatabase();
        String sql="select * from "+ HistoryTable.Field.TABLE_NAME;
        mCursor=mDataBase.rawQuery(sql,null);
        while (mCursor.moveToNext()) {
            String time = mCursor.getString(mCursor.getColumnIndex(HistoryTable.Field.HISTORY_TIME));
            int didi_id = mCursor.getInt(mCursor.getColumnIndex(HistoryTable.Field.HISTORY_DIDI_ID));
            if (!time.equals(isTime)) {
                times.add(time);
                isTime=time;
                didis.add(new ArrayList<DidiBean>());
            }
            DidiBean didi=new DidiBean();
            didi.setId(didi_id);
            didis.get(times.size()-1).add(didi);
        }
        historyListView.setAdapter(new HistoryAdapter(HistoryActivity.this,times,didis));
        //设置列表默认展开；
        int groupCount=historyListView.getCount();
        for (int i = 0; i < groupCount; i++) {
            historyListView.expandGroup(i);
        }
    }

    private void initView() {
        imageHistory= (ImageView) findViewById(R.id.history_back);
        historyListView= (ExpandableListView) findViewById(R.id.history_list);
        mHistoryHelper=new HistoryHelper(HistoryActivity.this);
    }

    private void initListener() {
        imageHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isClickable()){
                    finish();
                }
            }
        });
    }
}
