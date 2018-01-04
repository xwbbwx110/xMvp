package com.xwb.wb.mvp.mode;

import com.xwb.wb.enity.request.WBRequest;
import com.xwb.wb.enity.response.WeatherBean;
import com.xwb.wb.mvp.contract.TestContract;
import com.xwb.wb.net.RetrofitUtil;

import io.reactivex.Observable;

/**
 * 作者: xwb on 2017/12/12
 * 描述:Mode 层，用来拉取获取等，方便其他的presenter 重用
 */

public class TestMode implements TestContract.ITestMode {

    @Override
    public Observable<WeatherBean> getWeatherToCity(String cityId) {
        //----这里可以做一些其他操作，读取存取数据等
        //-------
        new WBRequest.Builder()
                .query("xx","xx")
                .query("xxx","xx")
                .query("xx","xx")
                .query("xxx","xx")
                .buid()
                .getQuery();

        new WBRequest.Builder()
                .form("xx","xx")
                .form("xx","xxx")
                .buid()
                .getForm();

        return  RetrofitUtil.getAPi()
                .requestWeather(cityId);
    }
}
