package com.didiincubator.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.didiincubator.Adapter.ApplyAdapter;
import com.didiincubator.Beans.ApplyListBean;
import com.didiincubator.parser.ApplyCancel;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yolanda.nohttp.RequestQueue;

import java.util.ArrayList;
import java.util.List;

/**pu
 * Created by 枫叶1 on 2016/6/10.
 */
public class ApplyListListener {
    private final Context context;
    private final ApplyAdapter adapter;

    PullToRefreshListView mListView;
    ArrayList<ApplyListBean> mList;
    RequestQueue mQueue;
    public ApplyListListener(Context context, PullToRefreshListView mListView, ApplyAdapter adapter, ArrayList<ApplyListBean> list){
        this.context=context;
        this.mListView=mListView;
        this.adapter=adapter;
        Log.e("dfsdfsf",list.toString());
        this.mList=list;
    }
    public void ListenerLong(){
        mListView.getRefreshableView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("是否取消本次申请");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("fsdlfsdfsa",mList.toString());
                        new ApplyCancel().cancelApply(mList.get(position-1).getApply().getId());
                        adapter.notifyDataSetChanged();
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

}
