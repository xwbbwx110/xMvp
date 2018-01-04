package com.xwb.wb.ui.activity;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xwb.wb.mvp.presenter.InstallPresenter;
import com.xwb.wb.mvp.view.BaseMVPView;
import com.xwb.wb.rxbus.RxBus;
import com.xwb.wb.rxlifecycle.BaseLifecyclePresenter;
import com.xwb.wb.ui.dialog.BaseWBDialog;
import com.xwb.wb.ui.dialog.LoadingDialog;
import com.xwb.wb.ui.widget.LoadingConstraintLayout;

/**
 * 作者: xwb on 2017/12/11
 * 描述: 带有RXjava生命周期管理的activity bindUntilEvent,bindToLifecycle
 */

public abstract class BaseWBActivity<P extends BaseLifecyclePresenter> extends RxAppCompatActivity implements BaseMVPView {

    private P mPresenter;
    private IntentExecutor mIntentExecutor;
    private LoadingDialog mCurDialog;

    protected abstract int getlayoutId();

    protected abstract void initView();

    protected abstract void initData(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        setContentView(getlayoutId());
        initView();
        initData(savedInstanceState);
        mIntentExecutor = new IntentExecutor();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //放到子线程去做方法读取操作和订阅操作，因为在类方法多的时候，反射会存在效率问题，在主线程做的话
                RxBus.get().register(BaseWBActivity.this);
            }
        }).start();

    }

    @Override
    public LoadingConstraintLayout getLoadingToLayout() {
        return loadingToLayout();
    }

    @Override
    public BaseWBDialog getLoadingToDialog() {
        return loadingToDialog();
    }

    /**
     * 默认返回null , 如果用layout loading  重写此方法
     * @return
     */
    public LoadingConstraintLayout loadingToLayout(){

        return null;
    }

    /**
     * 返回默认的 , 如果需要自定义 需在子类重写返回自定义的类型，如果用Dialog loading  重写此方法
     * @return
     */
    public BaseWBDialog loadingToDialog(){
        return mCurDialog==null?mCurDialog = LoadingDialog.newInstance():mCurDialog;
    }


    @Override
    public BaseWBActivity getCurAc() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放当前的订阅者
        RxBus.get().unRegister(BaseWBActivity.this);
        if(mPresenter!=null){
            mPresenter.detachView();//解除绑定
        }
    }

    public IntentExecutor getmIntentExecutor() {
        return mIntentExecutor;
    }


    /**
     * 获取当前的presenter,在获取的时候才创建
     * @return
     */
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




    public class IntentExecutor {

        public void launchActivity(Class<? extends Activity> clazz, int intentFlags) {
            startActivity(new Intent(BaseWBActivity.this, clazz).addFlags(intentFlags));
        }

        public void launchActivity(Class<? extends Activity> clazz) {
            startActivity(new Intent(BaseWBActivity.this, clazz));
        }

        public void launchActivity(Class<? extends Activity> clazz, Bundle data) {
            launchActivity(clazz, data, 0);
        }

        public void launchActivity(Class<? extends Activity> clazz, Bundle data, int intentFlags) {
            startActivity(new Intent(BaseWBActivity.this, clazz).putExtras(data).addFlags(intentFlags));
        }

        public void launchActivityForResult(Class<? extends Activity> clazz, int requestCode) {
            startActivityForResult(new Intent(BaseWBActivity.this, clazz), requestCode);
        }

        public void launchActivityForResult(Class<? extends Activity> clazz, int requestCode, Bundle data) {
            startActivityForResult(new Intent(BaseWBActivity.this, clazz).putExtras(data), requestCode);
        }

        public void launchService(Class<? extends Service> clazz, Bundle data) {
            startService(new Intent(BaseWBActivity.this, clazz).putExtras(data));
        }
    }
}
