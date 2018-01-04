package com.xwb.wb.rxlifecycle;

import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.OutsideLifecycleException;
import com.trello.rxlifecycle2.RxLifecycle;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 作者: xwb on 2017/12/11
 * 描述:自定义presenter生命周期
 */

public class RxLifeCyclePresenter {

    private static final Function<Integer,Integer> PRESENTER_LIFECYCLE = new Function<Integer, Integer>() {
        @Override
        public Integer apply(Integer lastEvent) throws Exception {
            switch (lastEvent){
                //会在activity destroy,并发送了DETACH事件时 发射
                case PresenterEvent.ATTACH:
                    return PresenterEvent.DETACH;
                case PresenterEvent.DETACH:
                    throw new OutsideLifecycleException("Cannot bind to Presenter lifecycle when outside of it.");
                default:
                    throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
            }

        }
    };


    private RxLifeCyclePresenter() {
        throw new AssertionError("No instances");
    }

    @NonNull
    public static <T> LifecycleTransformer<T> bindPresenter(@NonNull final Observable<Integer> lifecycle) {
        return RxLifecycle.bind(lifecycle, PRESENTER_LIFECYCLE);
    }

}
