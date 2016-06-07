package com.didiincubator.View;

/**
 * Created by 枫叶1 on 2016/6/3.
 * 申请详情
 */
public interface IMyApplyView {
    //图片
   void setImage(String url);
    //孵化器名
    void setName(String name);
    //价格
    void setPrice(int price);
    //地址
    void setAdress(String adress);
    //受理状态
    void setState(String state);
    //特殊说明
    void setExtra(String extra);
    //申请的id
    int getId();
 //联系电话
 void phone(String phonenumber);

}
