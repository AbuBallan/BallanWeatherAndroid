package com.jonerds.ballanweather.data.api;


import com.jonerds.ballanweather.data.model.SearchResponse;
import com.jonerds.ballanweather.data.model.WeatherResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("weather.ashx/")
    Observable<WeatherResponse> getWeatherForecast(@QueryMap Map<String, Object> query);

    @GET("search.ashx/")
    Observable<SearchResponse> searchForCity(@QueryMap Map<String, Object> query);
}
