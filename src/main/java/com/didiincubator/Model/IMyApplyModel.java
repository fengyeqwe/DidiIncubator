package com.didiincubator.Model;

import android.os.Handler;

import com.didiincubator.Beans.ApplyBean;
import com.didiincubator.Beans.ApplyResultBean;
import com.didiincubator.Beans.DidiBean;

/**
 * Created by 枫叶1 on 2016/6/3.
 * 申请详情
 */
public interface IMyApplyModel {
    //获取 申请
    ApplyBean getApply(int apply_id,final Handler handler);
    //获取孵化器信息
    DidiBean getDidi(int didi_id,final Handler handler);
    //获取申请结果
    ApplyResultBean getApplyResult(int apply_id,final Handler handler);

}
