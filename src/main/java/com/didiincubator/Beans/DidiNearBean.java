package com.didiincubator.Beans;

import java.io.Serializable;

/**
 * Created by 枫叶1 on 2016/6/9.
 */
public class DidiNearBean implements Serializable{
    private int id;//与孵化器id相同
    private String train;//地铁
    private String plane;//机场
    private String bus;//公交
    private int ct;//餐厅
    private int jd;//酒店
    private int jsf;//健身房
    private int yh;//银行

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getPlane() {
        return plane;
    }

    public void setPlane(String plane) {
        this.plane = plane;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public int getCt() {
        return ct;
    }

    public void setCt(int ct) {
        this.ct = ct;
    }

    public int getJd() {
        return jd;
    }

    public void setJd(int jd) {
        this.jd = jd;
    }

    public int getJsf() {
        return jsf;
    }

    public void setJsf(int jsf) {
        this.jsf = jsf;
    }

    public int getYh() {
        return yh;
    }

    public void setYh(int yh) {
        this.yh = yh;
    }

    public DidiNearBean() {
    }
}
