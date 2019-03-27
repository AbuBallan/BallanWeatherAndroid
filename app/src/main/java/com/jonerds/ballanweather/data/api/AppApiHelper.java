package com.jonerds.ballanweather.data.api;

import android.util.Log;

import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.data.model.SearchResponse;
import com.jonerds.ballanweather.data.model.WeatherResponse;
import com.jonerds.ballanweather.di.SearchApiParms;
import com.jonerds.ballanweather.di.WeatherApiParms;
import com.jonerds.ballanweather.utils.AppConstants;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;

@Singleton
public class AppApiHelper implements ApiHelper{

    private ApiService mApiService;
    private Map<String,Object> mWeatherApiParms;
    private Map<String,Object> mSearchApiParms;

    @Inject
    public AppApiHelper(ApiService apiService, @WeatherApiParms Map<String, Object> weatherApiParms, @SearchApiParms Map<String, Object> searchApiParms) {
        mApiService = apiService;
        mWeatherApiParms = weatherApiParms;
        mSearchApiParms = searchApiParms;
    }

    @Override
    public Observable<WeatherResponse> getWeatherForecast(String cityName) {
        mWeatherApiParms.put(AppConstants.CITY_PARAM, cityName);
        return mApiService.getWeatherForecast(mWeatherApiParms);
    }

    @Override
    public Observable<SearchResponse> searchForCity(String query) {
        mSearchApiParms.put(AppConstants.CITY_PARAM, query);
        return mApiService.searchForCity(mSearchApiParms);
    }

    @Override
    public Observable<City> searchForCity(double lat, double log) {
        mSearchApiParms.put(AppConstants.CITY_PARAM, lat + "," + log);
        return mApiService.searchForCity(mSearchApiParms)
                .flatMap(new Function<SearchResponse, ObservableSource<City>>() {
                    @Override
                    public ObservableSource<City> apply(SearchResponse searchResponse) throws Exception {
                        return Observable.just(searchResponse.getResults().get(0));
                    }
                });
    }

}
