package com.jonerds.ballanweather.di.module;

import com.jonerds.ballanweather.data.api.ApiHelper;
import com.jonerds.ballanweather.data.api.ApiService;
import com.jonerds.ballanweather.data.api.AppApiHelper;
import com.jonerds.ballanweather.di.SearchApiParms;
import com.jonerds.ballanweather.di.WeatherApiParms;
import com.jonerds.ballanweather.utils.AppConstants;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = RetrofitModule.class)
public class ApiHelperModule {

    @Provides
    @Singleton
    public ApiHelper provideApiHelper(AppApiHelper appApiHelper){
        return appApiHelper;
    }

    @Provides
    @Singleton
    public ApiService provideApiService (Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    @WeatherApiParms
    public Map<String,Object> provideWeatherApiParms() {
        Map<String,Object> parms = new HashMap<>();
        parms.put(AppConstants.FORMAT_PARAM, AppConstants.API_RESPONSE_JSON_FORMAT);
        parms.put(AppConstants.NUMBER_OF_DAYS_PARAM, AppConstants.NUMBER_OF_DAYS_TO_GET);
        parms.put(AppConstants.NORMAL_WEATHER_PARAM, AppConstants.TRUE);
        parms.put(AppConstants.CURRENT_CONDITIONS_PARAM, AppConstants.TRUE);
        parms.put(AppConstants.MONTHLY_CLIMATE_PARAM, AppConstants.FALSE);
        parms.put(AppConstants.SHOW_COMMENTS_PARAM, AppConstants.FALSE);
        parms.put(AppConstants.TIME_INTERVAL_PARAM, AppConstants.TIME_INTERVAL_TO_GET);
        parms.put(AppConstants.LANGUAGE_PARAM, AppConstants.LANGUAGE);
        return parms;
    }

    @Provides
    @Singleton
    @SearchApiParms
    public Map<String,Object> provideSearchApiParms() {
        Map<String,Object> parms = new HashMap<>();
        parms.put(AppConstants.FORMAT_PARAM, AppConstants.API_RESPONSE_JSON_FORMAT);
        parms.put(AppConstants.TIME_ZONE_PARAM, AppConstants.TRUE);
        parms.put(AppConstants.ONLY_POPULAR_PARAM, AppConstants.FALSE);
        parms.put(AppConstants.NUMBER_OF_SEARCH_RESULTS_PARAM, AppConstants.NUMBER_OF_SEARCH_ITEMS_TO_GET);
        return parms;
    }
}
