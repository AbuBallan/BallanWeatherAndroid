package com.jonerds.ballanweather;

import android.app.Activity;
import android.app.Application;

import com.jonerds.ballanweather.di.component.ApplicationComponent;
import com.jonerds.ballanweather.di.component.DaggerApplicationComponent;
import com.jonerds.ballanweather.di.module.ApplicationModule;

import io.realm.Realm;


public class WeatherApp extends Application {

    private ApplicationComponent mApplicationComponent;

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static ApplicationComponent getApplicationComponent(Activity activity){
        return ((WeatherApp)activity.getApplication()).getApplicationComponent();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

}
