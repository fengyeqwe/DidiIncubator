package com.didiincubator.Beans;

import java.io.Serializable;

/**
 * Created by 何晓 on 2016/5/16.
 */
public class InMapInfo implements Serializable{
    private double latitude;//经纬度
    private double longtitude;
    private int imageId;
    private String name;//名字
    private String type;//类型
    private String sketch;//孵化器简述
    private int number;//工位数
    private String  distance;//距离
   /* public static List<InMapInfo> inMapInfos=new ArrayList<>();
    static {

    }*/
    public InMapInfo(double latitude, double longtitude, int imageId, String name,
                     String type, String sketch, int number, String distance) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.imageId = imageId;
        this.name = name;
        this.type = type;
        this.sketch = sketch;
        this.number = number;
        this.distance = distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSketch() {
        return sketch;
    }

    public void setSketch(String sketch) {
        this.sketch = sketch;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
