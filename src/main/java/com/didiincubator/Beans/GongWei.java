package com.didiincubator.Beans;

import java.io.Serializable;

/**
 * Created by 何晓 on 2016/5/31.
 */
public class GongWei implements Serializable{
    private int id;
    private int countExpended;
    private int countRemainer;
    private double price;
    private String areaAvg;

    public GongWei() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountExpended() {
        return countExpended;
    }

    public void setCountExpended(int countExpended) {
        this.countExpended = countExpended;
    }

    public int getCountRemainer() {
        return countRemainer;
    }

    public void setCountRemainer(int countRemainer) {
        this.countRemainer = countRemainer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAreaAvg() {
        return areaAvg;
    }

    public void setAreaAvg(String areaAvg) {
        this.areaAvg = areaAvg;
    }

    @Override
    public String toString() {
        return "GongWei{" +
                "id=" + id +
                ", countExpended=" + countExpended +
                ", countRemainer=" + countRemainer +
                ", price=" + price +
                ", areaAvg='" + areaAvg + '\'' +
                '}';
    }
}
