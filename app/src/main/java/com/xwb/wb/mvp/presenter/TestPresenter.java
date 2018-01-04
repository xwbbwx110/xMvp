package com.xwb.wb.mvp.presenter;

import com.xwb.wb.enity.response.WeatherBean;
import com.xwb.wb.mvp.contract.TestContract;
import com.xwb.wb.mvp.mode.TestMode;
import com.xwb.wb.rxlifecycle.BaseLifecyclePresenter;
import com.xwb.wb.utils.Addition;
import com.xwb.wb.utils.UKObeserver;

import java.util.concurrent.TimeUnit;

/**
 * 作者: xwb on 2017/12/12
 * 描述:
 */

public class TestPresenter extends BaseLifecyclePresenter<TestContract.ITestView> {

    TestMode testMode;

    public TestPresenter(){
        testMode =  new TestMode();
        //---如果这个presenter 用到多个mode层 ，在构造方法里面一起实例化
    }
    public void getWeatherToCity(){

        testMode.getWeatherToCity("101010100")
                .delay(1, TimeUnit.SECONDS)

                .compose(Addition.<WeatherBean>toSchedulers())//附加 线程控制

                .compose(this.<WeatherBean>bindToLifecycle())   //在presenter  deatch事件到来时，取消订阅

//                .compose(Addition.<WeatherBean>showLoadMsg(getView().getLoadingToLayout()
//                        ,"愿你历经千帆，归来仍是少年"))//附加一个 订阅时 显示loading 提示

                .compose(Addition.<WeatherBean>showLoadMsg(getView().getLoadingToDialog().setShowMsg("愿你历经千帆，归来仍是少年"),
                        getView().getCurAc().getSupportFragmentManager()))//附加一个 订阅时 显示loading 提示

                .subscribe(new UKObeserver<WeatherBean>() {
                    @Override
                    public void onUKNext(WeatherBean weatherBean) {
                        getView().changeInfo(weatherBean.weatherinfo.toString());
                        getView().getLoadingToDialog().dismiss();
                    }

                    @Override
                    public void onUKError(Throwable e) {

                    }
                    @Override
                    public void onUKComplete() {

                    }
                });


    }



}
