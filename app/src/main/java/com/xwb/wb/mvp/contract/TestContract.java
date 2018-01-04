package com.xwb.wb.mvp.contract;

import com.xwb.wb.enity.response.WeatherBean;
import com.xwb.wb.mvp.mode.BaseMode;
import com.xwb.wb.mvp.view.BaseMVPView;

import io.reactivex.Observable;

/**
 * 作者: xwb on 2017/12/12
 * 描述:
 */

public interface TestContract {


    interface ITestView extends BaseMVPView{

        void changeInfo(String info);

    }

    interface  ITestMode extends BaseMode {

        Observable<WeatherBean> getWeatherToCity(String cityId);

    }


}
