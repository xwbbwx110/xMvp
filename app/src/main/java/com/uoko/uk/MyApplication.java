package com.uoko.uk;

import android.app.Application;

import com.uoko.uk.net.RetrofitUtil;
import com.uoko.uk.net.ServerUrl;
import com.net.retrofit.util.ContextUtils;

/**
 * Created by dubai on 2017/12/12.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtils.init(this);
        RetrofitUtil.getInstance().init(this);
    }
}
