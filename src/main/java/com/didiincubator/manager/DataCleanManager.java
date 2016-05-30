package com.didiincubator.manager;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by 何晓 on 2016/5/30.
 */
//清除缓存
public class DataCleanManager {

    //删除方法，这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
    public static  void delerFileByDirectory(File directory){
        if (directory!=null&&directory.exists()&&directory.isDirectory()){
            for (File item:directory.listFiles()) {
                item.delete();
            }
        }
    }

    //清除内部缓存
    public static void cleanIntenalCache(Context context){
        delerFileByDirectory(context.getCacheDir());
    }

    //清除本应用所有数据库
    public static void cleanDatabase(Context context){
        delerFileByDirectory(new File("/data/data/"+context.getPackageName()+"/databases"));
    }

    //清除本应用的sharedpreference偏好设置
    public  static  void cleanSharedPreference(Context context){
        delerFileByDirectory(new File("data/data/"+context.getPackageName()+"shared_prefs"));
    }

    //清除/data/data/包、files下的内容
    public static  void cleanFiles(Context context){
        delerFileByDirectory(context.getFilesDir());
    }

    //清除外部cache下的内容
    public static  void cleanExernalCache(Context context){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            delerFileByDirectory(context.getExternalCacheDir());
        }
    }

    //清除自定义路径下的文件，使用需小心，不能误删，而且支持目录下的文件删除
    public static void cleanCustomCache(String filepath){
            delerFileByDirectory(new File(filepath));
    }

    //清除本应用的所有数据
    public static  void cleanAll(Context context,String... filepath){
        cleanExernalCache(context);
        cleanExernalCache(context);
        cleanDatabase(context);
        cleanSharedPreference(context);
       cleanFiles(context);
        for (String files:filepath) {
            cleanCustomCache(files);
        }
    }
}
