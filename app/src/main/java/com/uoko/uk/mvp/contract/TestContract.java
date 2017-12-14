package com.uoko.uk.mvp.contract;

import com.uoko.uk.enity.response.WeatherBean;
import com.uoko.uk.mvp.mode.BaseUKMode;
import com.uoko.uk.mvp.view.BaseMVPView;

import io.reactivex.Observable;

/**
 * 作者: xwb on 2017/12/12
 * 描述:
 */

public interface TestContract {


    interface ITestView extends BaseMVPView{

        void changeInfo(String info);

    }

    interface  ITestMode extends BaseUKMode{

        Observable<WeatherBean> getWeatherToCity(String cityId);

    }


}
