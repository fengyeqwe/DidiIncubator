package com.didiincubator.Model;


import com.didiincubator.Beans.DidiBean;

/**
 * Created by 枫叶1 on 2016/5/24.
 */
public interface IdetailModel {
    void setId(int id);
    void load(int id);
    DidiBean getLoad();
}
