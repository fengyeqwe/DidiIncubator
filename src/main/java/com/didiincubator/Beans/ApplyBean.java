package com.didiincubator.Beans;

import java.io.Serializable;

/**
 * Created by 枫叶1 on 2016/6/2.
 */
public class ApplyBean implements Serializable {
    private int id;
    private int user_id;//用户id
    private int didi_id;//孵化器id
    private int count;//工位数量
    private int time;//租房时间
    private String lookhousetime;//看房时间
    private String extra;//特殊说明

    public ApplyBean() {
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDidi_id() {
        return didi_id;
    }

    public void setDidi_id(int didi_id) {
        this.didi_id = didi_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getLookhousetime() {
        return lookhousetime;
    }

    public void setLookhousetime(String lookhousetime) {
        this.lookhousetime = lookhousetime;
    }
}
