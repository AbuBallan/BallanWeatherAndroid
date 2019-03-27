package com.jonerds.ballanweather.data;

import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.data.model.DayWeather;
import com.jonerds.ballanweather.data.model.SearchResponse;
import com.jonerds.ballanweather.data.model.WeatherResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;

public interface DataManager {

    String getLastOpenedCity ();

    void setLastOpenedCity (String cityName);

    Observable<CollectionChange<RealmResults<City>>> getSavedCities();

    void addSavedCity(City city);

    Observable<WeatherResponse> getWeatherForecastFromRealm(String cityName);

    Observable<WeatherResponse> getWeatherForecastFromApi(String cityName);

    Observable<SearchResponse> searchForCity(String query);

    Observable<City> searchForCity(double lat, double log);

    void deleteOldWeather();

    void saveWeatherList(List<DayWeather> dayWeatherList);

    Observable<List<City>> findCitiesSuggestions (String query);


}
