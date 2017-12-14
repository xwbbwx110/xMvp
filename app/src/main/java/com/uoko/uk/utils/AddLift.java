package com.uoko.uk.utils;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: xwb on 2017/12/12
 * 描述:
 */

public class AddLift{

    public static <T> ObservableTransformer<T,T> toSchedulers(){

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                        .observeOn( AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io());
            }
        };
    }

}
