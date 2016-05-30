package com.didiincubator.utils;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import io.rong.imkit.RongIM;

/**
 * Created by fengye on 2016/5/26.
 */
public class DidiApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        RongIM.init(this);
    }
}
