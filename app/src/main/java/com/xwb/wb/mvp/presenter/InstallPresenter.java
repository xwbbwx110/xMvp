package com.xwb.wb.mvp.presenter;

import com.xwb.wb.rxlifecycle.BaseLifecyclePresenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者: xwb on 2017/12/11
 * 描述:注解创建Presenter实例，在用到的时候创建
 */

@Inherited //表示可以被继承
@Retention(RetentionPolicy.RUNTIME)  //可以在运行时通过反射拿到
public @interface InstallPresenter {
    Class<? extends BaseLifecyclePresenter> presenterObject();
}
