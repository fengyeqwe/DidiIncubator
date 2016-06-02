package com.didiincubator.Beans;

/**
 * Created by 何晓 on 2016/6/2.
 */
public class Collection {
    private int id;//收藏表的id
    private int user_id;//用户的id;
    private int didi_id;//孵化器的id;

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

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", didi_id=" + didi_id +
                '}';
    }
}
