package com.xwb.wb.utils;

import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

public class RXclick {
    //点击防抖
    public static void  addClick(View view, Consumer consumer){
        RxView.clicks(view).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(consumer);
    }

    public static void addLongClick(View view,  Consumer consumer){
        RxView.longClicks(view).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(consumer);
    }

}
