package com.jonerds.ballanweather.data.api;

import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.data.model.SearchResponse;
import com.jonerds.ballanweather.data.model.WeatherResponse;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface ApiHelper {

    Observable<WeatherResponse> getWeatherForecast(String cityName);

    Observable<SearchResponse> searchForCity(String query);

    Observable<City> searchForCity(double lat, double log);

}
