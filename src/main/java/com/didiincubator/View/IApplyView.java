package com.didiincubator.View;

/**
 * Created by fengye1 on 2016/5/25.
 */
public interface IApplyView {
    //申请人姓名
    String getName();
    //用户id--未实现
    int getUser_id();
    //孵化器id
    int getDidi_id();
    //入驻人数
    int getCount();
    //入驻时间
    int getTime();
    //联系电话
    int getPhone();
    //看房时间
    String getLookhousetime();
    //特殊说明
    String getExtra();
}
