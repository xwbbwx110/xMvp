package com.xwb.wb.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.xwb.wb.mvp.presenter.InstallPresenter;
import com.xwb.wb.mvp.view.BaseMVPView;
import com.xwb.wb.rxbus.RxBus;
import com.xwb.wb.rxlifecycle.BaseLifecyclePresenter;
import com.xwb.wb.ui.activity.BaseWBActivity;

/**
 * 作者: xwb on 2017/12/14
 * 描述:
 */

public abstract class BaseWBFragment<P extends BaseLifecyclePresenter> extends RxFragment implements BaseMVPView {

    private P mPresenter;

    BaseWBActivity mActivity;

    public BaseWBActivity getmActivity() {
        return mActivity;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof BaseWBActivity){
            mActivity = (BaseWBActivity) activity;
        }else{
            throw new RuntimeException("此fragment只能用在,BaseUKActivity中，检查你的activity");
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //放到子线程去做方法读取操作和订阅操作，因为在类方法多的时候，反射会存在效率问题，在主线程做的话
                RxBus.get().register(BaseWBFragment.this);
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(BaseWBFragment.this);
        if(mPresenter!=null){
            mPresenter.detachView();//解除绑定
        }
    }


    public P getmPresenter() {
        if(mPresenter!=null)
            return mPresenter;

        if(mPresenter==null){
            InstallPresenter annotation =  getClass().getAnnotation(InstallPresenter.class);
            Class<P> aClass = null;
            if(annotation!=null){
                aClass = (Class<P>) annotation.presenterObject();
            }else{
                throw new RuntimeException("@InstallPresenter(presenterObject =  xx.class)注解,没有声明");
            }
            mPresenter = createPresenter(aClass);
        }
        return mPresenter;
    }

    private P createPresenter(Class<P> presenterClass){
        try {
            mPresenter = presenterClass.newInstance();
            mPresenter.attachView(this);//第一次创建的时候绑定view
            return mPresenter;
        } catch (Exception e) {
            throw new RuntimeException("@InstallPresenter(presenterObject = xx.class)注解,没有声明");
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view,mActivity);
        initData(mActivity, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getlayoutId(),container,false);
    }

    protected abstract int getlayoutId();
    protected abstract void initView(View view, Activity ac);
    protected abstract void initData(Activity ac, Bundle savedInstanceState);



    protected void launchActivity(Class<? extends Activity> clazz){
        mActivity.getmIntentExecutor().launchActivity(clazz);
    }

    protected void launchActivity(Class<? extends Activity> clazz, Bundle data){
        mActivity.getmIntentExecutor().launchActivity(clazz,data);
    }

    protected void launchActivity(Class<? extends Activity> clazz, Bundle data, int flags){
        mActivity.getmIntentExecutor().launchActivity(clazz,data,flags);

    }

}
