package com.net.retrofit.http;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.net.retrofit.util.NetWorkUtils;
import com.net.retrofit.view.SimpleLoadDialog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by helin on 2016/10/10 15:49.
 */

public  abstract class ProgressSubscriber<T>   implements ProgressCancelListener,Observer<T>{


    private SimpleLoadDialog dialogHandler;
    private String errorMsg ;
    String msg ;
    private Context mContext ;
    public ProgressSubscriber(Context context , String msg , String errorMsg) {
        dialogHandler = new SimpleLoadDialog(context,this,true , msg);
        this.msg = msg ;
        this.errorMsg = errorMsg ;
        this.mContext = context ;
    }

    public ProgressSubscriber(Context context , String msg) {
        dialogHandler = new SimpleLoadDialog(context,this,true , msg);
        this.msg = msg ;
        this.mContext = context ;
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }


    /**
     * 显示Dialog
     */
    public void showProgressDialog(){
        if (dialogHandler != null) {
//            dialogHandler.obtainMessage(SimpleLoadDialog.SHOW_PROGRESS_DIALOG).sendToTarget();
            dialogHandler.show();
        }
    }
    private String TAG = "gson";
    @Override
    public void onNext(T t) {
        Log.e(TAG, "ProgressSubscriber  T _onNext");
        _onNext(t);
    }

    /**
     * 隐藏Dialog
     */
    private void dismissProgressDialog(){
        if (dialogHandler != null) {
//            dialogHandler.obtainMessage(SimpleLoadDialog.DISMISS_PROGRESS_DIALOG).sendToTarget();
            dialogHandler.dismiss();
            dialogHandler=null;
        }
    }
    @Override
    public void onError(Throwable e) {
        Log.e(TAG , "ProgressSubscriber  T onError e = " + e  + " , errorMsg = " + errorMsg);
        e.printStackTrace();

        if (!NetWorkUtils.isNetworkAvailable()) { //这里自行替换判断网络的代码
            errorMsg = "网络不可用" ;
            _onError(errorMsg);
        } else if (e instanceof ApiException) {
            _onError(e.getMessage());
        } else {
            _onError("失败，请稍后再试...");
        }
        if (errorMsg != null && !errorMsg.isEmpty()){
            Toast.makeText(mContext , errorMsg , Toast.LENGTH_SHORT).show();
        }

        dismissProgressDialog();
    }


    @Override
    public void onCancelProgress() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private Disposable mDisposable ;
    @Override
    public void onSubscribe(Disposable d) {
        mDisposable =d ;
    }

    protected abstract void _onNext(T t);
    protected abstract void _onError(String message);
}
