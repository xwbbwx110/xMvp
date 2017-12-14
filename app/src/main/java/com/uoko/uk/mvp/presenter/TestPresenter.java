package com.uoko.uk.mvp.presenter;

import com.uoko.uk.enity.response.WeatherBean;
import com.uoko.uk.mvp.contract.TestContract;
import com.uoko.uk.mvp.mode.TestMode;
import com.uoko.uk.rxlifecycle.BaseLifecyclePresenter;

import io.reactivex.functions.Consumer;

/**
 * 作者: xwb on 2017/12/12
 * 描述:
 */

public class TestPresenter extends BaseLifecyclePresenter<TestContract.ITestView>{

    TestMode testMode;

    public TestPresenter(){
        testMode =  new TestMode();
        //---如果这个presenter 用到多个mode层 ，在构造方法里面一起实例化
    }
    public void getWeatherToCity(){
                testMode.getWeatherToCity("101010100")
                        .compose(this.<WeatherBean>bindToLifecycle())   //在presenter  deatch事件到来时，取消订阅
                        .subscribe(new Consumer<WeatherBean>() {
                        @Override
                        public void accept(WeatherBean weatherBean) throws Exception {
                            getView().changeInfo(weatherBean.weatherinfo.toString());
                        }
                });

    }



}
