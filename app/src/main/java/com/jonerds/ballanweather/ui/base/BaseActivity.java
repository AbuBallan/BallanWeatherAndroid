package com.jonerds.ballanweather.ui.base;


import android.os.Bundle;

import com.jonerds.ballanweather.WeatherApp;
import com.jonerds.ballanweather.di.component.ActivityComponent;
import com.jonerds.ballanweather.di.component.DaggerActivityComponent;
import com.jonerds.ballanweather.di.module.ActivityModule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(WeatherApp.getApplicationComponent(this))
                .build();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    protected abstract void setUp();

}