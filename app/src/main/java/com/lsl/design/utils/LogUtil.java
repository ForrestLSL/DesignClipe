package com.lsl.design.utils;

import android.util.Log;

/**
 * Created by Forrest
 * on 2017/2/25 08:57
 */

public class LogUtil {
    //如果值为true时，打印log，同时不会发送本机的数据
    //上线前一定要设置为false
    public static final  boolean DEBUG = true;

    private static String TAG="QHQ_DEBUG=====";


    public static void d(String str){
        if (DEBUG){
            Log.d(TAG,""+str);
        }
    }
    public static void i(String str){
        if (DEBUG){
            Log.i(TAG,""+str);
        }
    }
    public static void w(String str){
        if (DEBUG){
            Log.w(TAG,""+str);
        }
    }
    public static void e(String str){
        if (DEBUG){
            Log.e(TAG,""+str);
        }
    }

}
