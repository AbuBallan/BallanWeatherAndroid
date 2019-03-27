package com.jonerds.ballanweather.di.component;

import android.app.Application;
import android.content.Context;

import com.jonerds.ballanweather.WeatherApp;
import com.jonerds.ballanweather.data.DataManager;
import com.jonerds.ballanweather.di.ApplicationContext;
import com.jonerds.ballanweather.di.module.ApplicationModule;
import com.jonerds.ballanweather.di.module.DataManagerModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DataManagerModule.class})
public interface ApplicationComponent {

    void inject(WeatherApp app);

    DataManager getDataManager();

    @ApplicationContext
    Context getContext();

}