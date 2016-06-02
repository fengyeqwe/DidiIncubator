package com.didiincubator.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.didiincubator.Activity.CollectionActivity;
import com.didiincubator.Adapter.CollectionAdapter;
import com.didiincubator.Beans.DidiBean;
import com.didiincubator.View.DetailActivity;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by 何晓 on 2016/5/30.
 */
//删除收藏里面的条目
public class CollectionListener {
    static final int NOHTTP_WHAT_TEST = 0x002;
    PullToRefreshListView listView;
    List<DidiBean> list;
    Context context;
    RequestQueue queue;
    CollectionAdapter adapter;
    public CollectionListener(PullToRefreshListView listView, List<DidiBean> list, Context context, RequestQueue queue, CollectionAdapter adapter){
        this.listView=listView;
        this.list=list;
        this.context=context;
        this.queue=queue;
        this.adapter=adapter;
    }
    public   void ListenerLong(){
        //listView.getRefreshableView()
        listView.getRefreshableView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
                DidiBean incubator=list.get(position-1);
                final String method="delete";
                final String name=incubator.getName();
                Toast.makeText(context,name,Toast.LENGTH_LONG).show();
                //Toast.makeText(context, "长按了我"+position+"id:"+id, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("确定删除这条记录吗");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(context, "确定"+which, Toast.LENGTH_SHORT).show();
                                String  url="http://115.28.78.82:8080/Didiweb/DidiServlet";
                                final Request<JSONArray> request= NoHttp.createJsonArrayRequest(url, RequestMethod.GET);
                                request.add("method",method);
                                request.add("name",name);
                                queue.add( NOHTTP_WHAT_TEST, request, new OnResponseListener<JSONArray>() {
                                    @Override
                                    public void onStart(int what) {

                                    }

                                    @Override
                                    public void onSucceed(int what, Response<JSONArray> response) {

                                        adapter.notifyDataSetChanged();
                                        //parent.notify();
                                    }

                                    @Override
                                    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

                                    }

                                    @Override
                                    public void onFinish(int what) {

                                    }
                                });
                        //adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
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
