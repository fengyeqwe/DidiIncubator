package com.didiincubator.Beans;

import java.io.Serializable;

/**
 * Created by 何晓 on 2016/5/31.
 */
public class Pictures implements Serializable{
    private int id;
    private int didi_id;
    private String url;

    public Pictures() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDidi_id() {
        return didi_id;
    }

    public void setDidi_id(int didi_id) {
        this.didi_id = didi_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Pictures{" +
                "id=" + id +
                ", didi_id=" + didi_id +
                ", url='" + url + '\'' +
                '}';
    }
}
