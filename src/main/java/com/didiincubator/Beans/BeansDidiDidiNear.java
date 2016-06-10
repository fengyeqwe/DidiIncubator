package com.didiincubator.Beans;

import java.io.Serializable;

/**
 * Created by 枫叶1 on 2016/6/9.
 */
public class BeansDidiDidiNear implements Serializable {
    private DidiNearBean didiNearBean;
    private DidiBean didi;

    public DidiBean getDidi() {
        return didi;
    }

    public void setDidi(DidiBean didi) {
        this.didi = didi;
    }

    public DidiNearBean getDidiNearBean() {
        return didiNearBean;
    }

    public void setDidiNearBean(DidiNearBean didiNearBean) {
        this.didiNearBean = didiNearBean;
    }

    public BeansDidiDidiNear() {
    }
}
