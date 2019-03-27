package com.jonerds.ballanweather.data;

import com.jonerds.ballanweather.data.api.ApiHelper;
import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.data.model.DayWeather;
import com.jonerds.ballanweather.data.model.SearchResponse;
import com.jonerds.ballanweather.data.model.WeatherResponse;
import com.jonerds.ballanweather.data.prefs.PrefsHelper;
import com.jonerds.ballanweather.data.realm.RealmHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;

public class AppDataManager implements DataManager {

    private ApiHelper apiHelper;
    private RealmHelper realmHelper;
    private PrefsHelper prefsHelper;

    @Inject
    public AppDataManager(ApiHelper apiHelper, RealmHelper realmHelper, PrefsHelper prefsHelper) {
        this.apiHelper = apiHelper;
        this.realmHelper = realmHelper;
        this.prefsHelper = prefsHelper;
    }

    @Override
    public String getLastOpenedCity() {
        return prefsHelper.getLastOpenedCity();
    }

    @Override
    public void setLastOpenedCity(String cityName) {
        prefsHelper.setLastOpenedCity(cityName);
    }

    @Override
    public Observable<CollectionChange<RealmResults<City>>> getSavedCities() {
        return realmHelper.getSavedCities();
    }

    @Override
    public void addSavedCity(City city) {
        realmHelper.addCity(city);
    }

    @Override
    public Observable<WeatherResponse> getWeatherForecastFromRealm(String cityName) {
        return realmHelper.getWeatherForecast(cityName);
    }

    @Override
    public Observable<WeatherResponse> getWeatherForecastFromApi(String cityName) {
        return apiHelper.getWeatherForecast(cityName);
    }

    @Override
    public Observable<SearchResponse> searchForCity(String query) {
        return apiHelper.searchForCity(query);
    }

    @Override
    public Observable<City> searchForCity(double lat, double log) {
        return apiHelper.searchForCity(lat, log);
    }

    @Override
    public void deleteOldWeather() {
        realmHelper.deleteOldDateWeather();
    }

    @Override
    public void saveWeatherList(List<DayWeather> dayWeatherList) {
        realmHelper.saveWeatherList(dayWeatherList);
    }

    @Override
    public Observable<List<City>> findCitiesSuggestions (String query){
        return searchForCity(query)
                .flatMap(new Function<SearchResponse, ObservableSource<List<City>>>() {
                    @Override
                    public ObservableSource<List<City>> apply(SearchResponse searchResponse) throws Exception {
                        return Observable.just(searchResponse.getResults());
                    }
                });
    }


}
