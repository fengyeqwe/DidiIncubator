package com.didiincubator.Model;

import com.didiincubator.Beans.ApplyBean;

/**
 * Created by 枫叶1 on 2016/6/2.
 */
public interface IApplyModel {
    //向服务器提交订单
    Boolean submit(ApplyBean applyBean);
}
