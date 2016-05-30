package com.didiincubator.Beans;

import java.io.Serializable;

/**
 * Created by 何晓 on 2016/5/30.
 */
public class Incubator implements Serializable{
    private int id;
    private String sketch;//简述
    private String details;//详情
    private double area;//面积
    private int num;//工位数
    private String local;//坐标
    private String type;//类型
    private String name;//名字

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSketch() {
        return sketch;
    }
    public void setSketch(String sketch) {
        this.sketch = sketch;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public double getArea() {
        return area;
    }
    public void setArea(double area) {
        this.area = area;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Incubator{" +
                "id=" + id +
                ", sketch='" + sketch + '\'' +
                ", details='" + details + '\'' +
                ", area=" + area +
                ", num=" + num +
                ", local='" + local + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
