package com.xwb.wb.utils;


import android.support.v4.app.FragmentManager;

import com.xwb.wb.ui.dialog.BaseWBDialog;
import com.xwb.wb.ui.widget.LoadingConstraintLayout;

import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: xwb on 2017/12/12
 * 描述:合并一些，附加操作，不打破链式
 */

public class Addition {

    /**
     * 曾加线程控制
     * @param <T>
     * @return
     */
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



    /**
     * 在订阅时，显示加载 layout
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T,T> showLoadMsg(final LoadingConstraintLayout ukloadlayout){

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> tObservable) {
                return tObservable.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        ukloadlayout.inLoading("");
                    }
                });
            }
        };
    }


    /**
     * 在订阅时，显示加载 layout, 带消息提示
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T,T> showLoadMsg(final LoadingConstraintLayout ukloadlayout, final String loadStr){

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> tObservable) {
                return tObservable.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        ukloadlayout.inLoading(loadStr);
                    }
                });
            }
        };
    }


    /**
     * 在订阅时，显示加载消息dialog
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T,T> showLoadMsg(final BaseWBDialog dialog, final FragmentManager fragmentManager){

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> tObservable) {
                return tObservable.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        dialog.show(fragmentManager,new Random().nextInt(1000)+"x");
                    }
                });
            }
        };
    }

}
