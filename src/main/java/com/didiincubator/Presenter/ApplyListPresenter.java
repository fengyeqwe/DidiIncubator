package com.didiincubator.Presenter;

import android.os.Handler;
import android.os.Message;

import com.didiincubator.Model.ApplyListModel;
import com.didiincubator.Model.IApplyListModel;
import com.didiincubator.View.ApplyListActivity;
import com.didiincubator.View.IApplyListView;

/**
 * Created by 枫叶1 on 2016/6/7.
 */
public class ApplyListPresenter {
    IApplyListModel mApplyListModel;
    IApplyListView mApplyListView;

    public ApplyListPresenter(ApplyListActivity view){
        this.mApplyListModel=new ApplyListModel();
        this.mApplyListView=view;
    }
    public void getData(int user_id,Handler handler){

        mApplyListModel.download(user_id,handler);
    }
}
