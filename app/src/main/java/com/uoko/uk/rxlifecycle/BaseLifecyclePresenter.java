package com.uoko.uk.rxlifecycle;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.uoko.uk.mvp.presenter.BasePresenter;
import com.uoko.uk.mvp.view.BaseMVPView;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * 作者: xwb on 2017/12/11
 * 描述:管理生命周期的presenter和activity的分开
 */

public class BaseLifecyclePresenter<V extends BaseMVPView> extends BasePresenter<V> implements
        LifecycleProvider<Integer>{

    private final BehaviorSubject<Integer> lifecycleSubject = BehaviorSubject.create();

    @Override
    public Observable<Integer> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(Integer event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifeCyclePresenter.bindPresenter(lifecycleSubject);
    }

    /**
     * presenter 建立的时候发送一个事件
     * @param view
     */
    @Override
    public void attachView(V view) {
        super.attachView(view);
        lifecycleSubject.onNext(PresenterEvent.ATTACH);
    }

    /**
     * presenter 销毁的时候发送一个事件
     */
    @Override
    public void detachView() {
        super.detachView();
        lifecycleSubject.onNext(PresenterEvent.DETACH);
    }
}
