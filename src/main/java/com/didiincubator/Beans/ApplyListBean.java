package com.didiincubator.Beans;

import java.io.Serializable;

/**
 * Created by fengye1 on 2016/6/7.
 *申请表使用的所有数据
 */
public class ApplyListBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private DidiBean didi;
    private ApplyBean apply;
    private ApplyResultBean applyresult;
    private GongWei gongwei;


    public void setDidi(DidiBean didi) {
        this.didi = didi;
    }

    public void setApply(ApplyBean apply) {
        this.apply = apply;
    }

    public void setApplyresult(ApplyResultBean applyresult) {
        this.applyresult = applyresult;
    }

    public void setGongwei(GongWei gongwei) {
        this.gongwei = gongwei;
    }

    public DidiBean getDidi() {
        return didi;
    }

    public ApplyBean getApply() {
        return apply;
    }

    public ApplyResultBean getApplyresult() {
        return applyresult;
    }

    public GongWei getGongwei() {
        return gongwei;
    }

    public ApplyListBean() {
    }
}
