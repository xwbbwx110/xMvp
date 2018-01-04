package com.xwb.wb.mvp.presenter;

import android.support.annotation.CallSuper;

import com.xwb.wb.mvp.view.BaseMVPView;

/**
 * 作者: xwb on 2017/12/11
 * 描述:
 */

public abstract class BasePresenter<V extends BaseMVPView>{

    protected  V view;


    @CallSuper
    public void attachView(V view){
        this.view = view;
    }

    @CallSuper
    public void detachView(){
        view = null;
    }


    public V getView(){
        return view;
    }


}
