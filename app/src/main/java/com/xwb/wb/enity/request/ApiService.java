package com.xwb.wb.enity.request;


import com.xwb.wb.enity.response.WeatherBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("data/cityinfo/{cityId}.html")
    Observable<WeatherBean> requestWeather(@Path("cityId") String cityId);
}
