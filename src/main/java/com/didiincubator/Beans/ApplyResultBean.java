package com.didiincubator.Beans;

import java.io.Serializable;

/**
 * Created by 枫叶1 on 2016/6/3.
 * 申请结果表--对象
 */
public class ApplyResultBean implements Serializable{
    private int id;//以申请表主键为外键
    private String state;//申请状态
    private String result;//申请结果
    private String extra="";//特殊说明

    public ApplyResultBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
