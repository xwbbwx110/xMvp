package com.xwb.wb;

import android.app.Application;
import android.widget.Toast;

/**
 * Created by dubai on 2017/12/12.
 */

public class MyApplication extends Application{

    /**
     * 放这儿是原因是，在纯java类里面使用，或者 作为Toast等的content 防止类型泄漏
     */
    private static MyApplication instance;

    public static MyApplication getAppLicationContext(){

        if(instance==null)
            instance =  new MyApplication();
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }



    public static void showMsgs(String str){
        tosat(str,Toast.LENGTH_SHORT);

    }

    public static void showMsgl(String str){
        tosat(str,Toast.LENGTH_LONG);
    }

    private  static void tosat(String str,int showType){
        Toast.makeText(instance,str,showType).show();
    }


}
