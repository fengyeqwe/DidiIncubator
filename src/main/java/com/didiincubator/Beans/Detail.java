package com.didiincubator.Beans;

import java.io.Serializable;

/**
 * Created by ��Ҷ1 on 2016/6/1.
 */
public class Detail implements Serializable {
    private int id;
    private int floor;//����
    private String area;//���
    private String high;//���
    private String property;//��ҵ

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Detail() {
    }
}
