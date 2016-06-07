package com.didiincubator.Model;

import android.os.Handler;

import com.didiincubator.Beans.ApplyBean;

import java.util.ArrayList;

/**
 *
 * Created by 枫叶1 on 2016/6/6.
 */
public interface IApplyListModel {
    //下载申请表信息
    ArrayList<ApplyBean> download(int user_id, Handler handler);
}
