package com.net.retrofit.http;


import android.content.Context;
import android.widget.Toast;


import com.net.retrofit.util.NetWorkUtils;
import com.uoko.uk.utils.LogUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by helin on 2016/10/10 15:49.
 */

public  abstract class MySubscriber<T> implements Observer<T> {
    private Context mContext ;
    private String errorMsg ;

    public MySubscriber(Context context  ) {
        mContext = context ;
    }

    public MySubscriber(Context context , String errormsg ) {
        mContext = context ;
        errorMsg = errormsg ;
    }
    @Override
    public void onSubscribe(Disposable d) {

    }
    @Override
    public void onComplete() {
    }

    private String TAG = "Subscriber";
    @Override
    public void onNext(T t) {
        LogUtils.e(TAG, "ProgressSubscriber  T _onNext");
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e(TAG , "MySubscriber  T onError e = " + e );

        if (!NetWorkUtils.isNetworkAvailable()) { //这里自行替换判断网络的代码
            errorMsg = "网络不可用" ;
        }
        if (errorMsg != null && !errorMsg.isEmpty()){
            Toast.makeText(mContext , errorMsg , Toast.LENGTH_SHORT);
        }

        _onError(e.getMessage());
    }

    protected abstract void _onNext(T t);
    protected abstract void _onError(String message);
}
