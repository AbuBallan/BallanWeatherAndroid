package com.jonerds.ballanweather.di.module;

import com.jonerds.ballanweather.data.AppDataManager;
import com.jonerds.ballanweather.data.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiHelperModule.class, RealmHelperModule.class, PrefsHelperModule.class})
public class DataManagerModule {

    @Provides
    @Singleton
    public DataManager provideDataManager(AppDataManager appDataManager){
        return appDataManager;
    }
}
