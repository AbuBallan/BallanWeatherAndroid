package com.jonerds.ballanweather.di.module;

import android.app.Application;
import android.content.Context;

import com.jonerds.ballanweather.data.AppDataManager;
import com.jonerds.ballanweather.data.DataManager;
import com.jonerds.ballanweather.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }


}
