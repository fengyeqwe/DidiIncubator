package com.didiincubator.Adapter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by 何晓 on 2016/5/15.
 */
public class MyOrientationListener implements SensorEventListener {

        private SensorManager manager;//传感器管理者
        private Context mContext;//上下文
        private Sensor mSensor;//传感器有三个参数x,y,z
        private float lastX;//坐标x;
        private OnOrientataionListener mOrientataionListener;

        public void setOrientataionListener(OnOrientataionListener mOrientataionListener) {
            this.mOrientataionListener = mOrientataionListener;
        }

        MyOrientationListener(Context context) {
            this.mContext = context;
        }

        //开始监听
        public void start() {
            //拿到传感器
            manager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
            if (manager != null) {
                //获得方向传感器
                mSensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            }
            if (mSensor != null) {
                    //第一个参数监听，第二个参数，传感器，第三个参数：精度
                manager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
            }

        }

        //停止监听
        public void stop() {
            //停止定位
            manager.unregisterListener(this);
        }

        //方向发生改变
        @Override
        public void onSensorChanged(SensorEvent event) {
            //判断是否是方向传感器
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                float x = event.values[SensorManager.DATA_X];//得到x的值
                if (Math.abs(x - lastX) > 1.0) {//进行比对，如果大于1度，通过接口回调通知主界面进行更新方向
                    if (mOrientataionListener != null) {
                        mOrientataionListener.onOrientationChanged(x);
                    }
                }
                lastX = x;
            }
        }

        //经纬度发生改变
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    public  interface OnOrientataionListener{
        void onOrientationChanged(float x);
    }
}


