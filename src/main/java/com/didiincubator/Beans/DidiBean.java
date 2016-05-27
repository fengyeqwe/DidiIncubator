package com.didiincubator.Beans;

import java.io.Serializable;

/**
 * Created by 枫叶1 on 2016/5/24.
 */
public class DidiBean implements Serializable{
    private static final long serialVersionUID = 5192843888413143548L;
    private int id;
    private String name;//名称
    private String sketch;//简述
    private int detail_id;//详细介绍id
    private int Station_id;//工位情况id
    private String coordinate;//位置坐标
    private String type_didi;//孵化器项目类型
    private String headPortrait;//头像

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

    public int getStation_id() {
        return Station_id;
    }

    public void setStation_id(int station_id) {
        Station_id = station_id;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getType_didi() {
        return type_didi;
    }

    public void setType_didi(String type_didi) {
        this.type_didi = type_didi;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public DidiBean() {
    }

    public DidiBean(int id, String name, String sketch, int detail_id, int station_id, String coordinate, String type_didi, String headPortrait) {
        this.id = id;
        this.name = name;
        this.sketch = sketch;
        this.detail_id = detail_id;
        Station_id = station_id;
        this.coordinate = coordinate;
        this.type_didi = type_didi;
        this.headPortrait = headPortrait;
    }

    @Override
    public String toString() {
        return "DidiBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sketch='" + sketch + '\'' +
                ", detail_id=" + detail_id +
                ", Station_id=" + Station_id +
                ", coordinate='" + coordinate + '\'' +
                ", type_didi='" + type_didi + '\'' +
                ", headPortrait='" + headPortrait + '\'' +
                '}';
    }
}
