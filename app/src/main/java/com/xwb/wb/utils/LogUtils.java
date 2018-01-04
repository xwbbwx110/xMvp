package com.xwb.wb.utils;

import android.util.Log;

import com.uoko.uk.BuildConfig;

/**
 * Created by dubai on 2017/12/1.
 */

public class LogUtils {

    private static final String TAG = "housekeeper";

    public static void e(String key , String value){
        if (BuildConfig.DEBUG){
            Log.e(key , value);
        }
    }

    public static void d(String key , String value){
        if (BuildConfig.DEBUG){
            Log.d(key , value);
        }
    }

    public static void e( String value){
        if (BuildConfig.DEBUG){
            Log.e(TAG , value);
        }
    }

    public static void d(String value){
        if (BuildConfig.DEBUG){
            Log.d(TAG , value);
        }
    }


}
