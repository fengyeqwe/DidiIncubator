package com.didiincubator.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.didiincubator.R;

public class CollectionActivity extends AppCompatActivity {
    ImageView imageCollection;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
        initListener();
    }


    private void initView() {
        imageCollection= (ImageView) findViewById(R.id.collection_back);
        listView= (ListView) findViewById(R.id.collection_listview);
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
}
