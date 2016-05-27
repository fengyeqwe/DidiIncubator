package com.didiincubator.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.didiincubator.R;

public class HistoryActivity extends AppCompatActivity {
    ImageView imageHistory;
    ListView historyListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        initListener();
    }

    private void initView() {
        imageHistory= (ImageView) findViewById(R.id.history_back);
        historyListView= (ListView) findViewById(R.id.history_listView);
    }

    private void initListener() {
        imageHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isClickable()){
                    startActivity(new Intent(HistoryActivity.this,MainActivity.class));
                    finish();
                }
            }
        });
    }
}
