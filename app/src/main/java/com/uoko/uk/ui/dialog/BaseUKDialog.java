package com.uoko.uk.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.trello.rxlifecycle2.components.support.RxDialogFragment;
import com.uoko.uk.rxbus.RxBus;
import com.uoko.uk.ui.activity.BaseUKActivity;

/**
 * 作者: xwb on 2017/12/14
 * 描述:
 */

public abstract class BaseUKDialog extends RxDialogFragment{
    BaseUKActivity mActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof BaseUKActivity){
            mActivity = (BaseUKActivity) activity;
        }else{
            throw new RuntimeException("此dialog只能用在,BaseUKActivity中，检查你的activity");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        return inflater.inflate(getlayoutId(),container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view,mActivity);
        initData(mActivity, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //放到子线程去做方法读取操作和订阅操作，因为在类方法多的时候，反射会存在效率问题，在主线程做的话
                RxBus.get().register(BaseUKDialog.this);
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(BaseUKDialog.this);
    }

    /**
     * 设置Dialog点击外部区域是否隐藏
     * @param cancel
     */
    protected void setCanceledOnTouchOutside(boolean cancel) {
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(cancel);
        }

    }

    protected void setDialogHeight(int hei) {
        setDialogWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, hei);
    }

    protected void setDialogWidth(int wid){
        setDialogWidthAndHeight(wid, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    protected void setDialogWidthAndHeight(int wid,int hei){
        getDialog().getWindow().setLayout(wid,hei);
    }

    protected abstract int getlayoutId();
    protected abstract void initView(View view, Activity ac);
    protected abstract void initData(Activity ac, Bundle savedInstanceState);
}
