package com.didiincubator.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.didiincubator.Beans.Incubator;
import com.didiincubator.View.DetailActivity;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

/**
 * Created by 何晓 on 2016/5/30.
 */
public class CollectionListener {
    PullToRefreshListView listView;
    List<Incubator> list;
    Context context;
    public CollectionListener(PullToRefreshListView listView, List<Incubator> list, Context context){
        this.listView=listView;
        this.list=list;
        this.context=context;
    }
    public   void ListenerLong(){
        //listView.getRefreshableView()
        listView.getRefreshableView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Incubator incubator=list.get(position-1);
                //Toast.makeText(context, "长按了我"+position+"id:"+id, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("确定删除这条记录吗")//
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(context, "确定"+which, Toast.LENGTH_SHORT).show();
                            }
                        })//
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                return true;
            }
        });

    }

    public  void ListenerShort() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Incubator incubator=list.get(position);
                //Toast.makeText(context, "短按了我" + position, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, DetailActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
