package com.jonerds.ballanweather.di.component;

import com.jonerds.ballanweather.di.PerActivity;
import com.jonerds.ballanweather.di.module.ActivityModule;
import com.jonerds.ballanweather.ui.base.BaseActivity;
import com.jonerds.ballanweather.ui.cities.CitiesActivity;
import com.jonerds.ballanweather.ui.intro.IntroActivity;
import com.jonerds.ballanweather.ui.location.LocationActivity;
import com.jonerds.ballanweather.ui.main.MainActivity;
import com.jonerds.ballanweather.ui.splash.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(CitiesActivity citiesActivity);

    void inject(LocationActivity locationActivity);

    void inject(IntroActivity introActivity);

}