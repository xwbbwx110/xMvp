package com.xwb.wb.utils;

import com.uoko.uk.BuildConfig;
import com.xwb.wb.MyApplication;
import com.xwb.wb.enity.BaseWBHttpResult;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者: xwb on 2017/12/22
 * 描述:提前处理一个操作,这里不用filter filter 会直接消费掉事件，不往下游发了
 *
 */

public abstract class UKObeserver<T extends BaseWBHttpResult> implements Observer<T>{
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if(BuildConfig.DEBUG)
            MyApplication.showMsgl("响应的code："+t.code+"");

//        if(t.isSuccess()){
            onUKNext(t);
//        }else{
//
//        }

        onUKComplete();
    }

    @Override
    public void onError(Throwable e) {
        onUKError(e);
    }

    @Override
    public void onComplete() {//takeuntil 起作用，虽然不会调用 onNext 但是会调用此方法，所以不在这个方法里面做收尾操作
        //不做任何操作 移步到onUKComplete();
    }


    public abstract void  onUKNext(T t);

    public abstract void onUKError(Throwable e);

    public abstract void onUKComplete();


}
