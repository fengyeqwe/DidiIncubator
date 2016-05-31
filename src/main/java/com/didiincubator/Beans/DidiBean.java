package com.didiincubator.Beans;

import java.io.Serializable;

/**
 * Created by 枫叶1 on 2016/5/24.
 * 孵化器信息表对象
 */
public class DidiBean implements Serializable{
    private static final long serialVersionUID = 5192843888413143548L;
    private int id;
    private String name;//名称
    private String sketch;//简述
    private int detail_id;//详细介绍id
<<<<<<< HEAD
    private int station_id;//工位情况id
    private double coordinateX;//位置坐标
    private double coordinateY;
    private String type_didi;//孵化器项目类型
    private String phoneNumber;//电话
=======
    private int Station_id;//工位情况id
    private float coordinateX;
    private float coordinateY;//位置坐标
    private String type_didi;//孵化器项目类型
    private String phonenumber;//联系电话
>>>>>>> fc187efa990c4452ceb077917b97b62b249b3a82
    private String headPortrait;//头像

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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

    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    public String getType_didi() {
        return type_didi;
    }

    public void setType_didi(String type_didi) {
        this.type_didi = type_didi;
    }

<<<<<<< HEAD
    public double getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
=======
    public String getType_didi() {
        return type_didi;
>>>>>>> fc187efa990c4452ceb077917b97b62b249b3a82
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public float getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(float coordinateX) {
        this.coordinateX = coordinateX;
    }

    public float getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(float coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public DidiBean() {

<<<<<<< HEAD
=======
    public DidiBean(int id, String name, String sketch, int detail_id, int station_id, float coordinateX, float coordinateY, String type_didi, String phonenumber, String headPortrait) {
        this.id = id;
        this.name = name;
        this.sketch = sketch;
        this.detail_id = detail_id;
        Station_id = station_id;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.type_didi = type_didi;
        this.phonenumber = phonenumber;
        this.headPortrait = headPortrait;
>>>>>>> fc187efa990c4452ceb077917b97b62b249b3a82
    }


    @Override
    public String toString() {
        return "DidiBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sketch='" + sketch + '\'' +
                ", detail_id=" + detail_id +
<<<<<<< HEAD
                ", station_id=" + station_id +
                ", coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", type_didi='" + type_didi + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
=======
                ", Station_id=" + Station_id +
                ", coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", type_didi='" + type_didi + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
>>>>>>> fc187efa990c4452ceb077917b97b62b249b3a82
                ", headPortrait='" + headPortrait + '\'' +
                '}';
    }
}
