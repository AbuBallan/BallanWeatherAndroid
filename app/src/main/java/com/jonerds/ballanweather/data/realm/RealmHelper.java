package com.jonerds.ballanweather.data.realm;

import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.data.model.DayWeather;
import com.jonerds.ballanweather.data.model.WeatherResponse;

import java.util.List;

import io.reactivex.Observable;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;


public interface RealmHelper {

    Observable<CollectionChange<RealmResults<City>>> getSavedCities();

    void addCity(City city);

    Observable<WeatherResponse> getWeatherForecast(String cityName);

    void saveWeatherList(List<DayWeather> dayWeatherList);

    void deleteOldDateWeather();
}
