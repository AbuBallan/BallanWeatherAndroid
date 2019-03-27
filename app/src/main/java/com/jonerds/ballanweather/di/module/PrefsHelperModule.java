package com.jonerds.ballanweather.di.module;


import com.jonerds.ballanweather.data.prefs.AppPrefsHelper;
import com.jonerds.ballanweather.data.prefs.PrefsHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = SharedPreferencesModule.class)
public class PrefsHelperModule {

    @Provides
    @Singleton
    public PrefsHelper providePrefsHelper (AppPrefsHelper appPrefsHelper){
        return appPrefsHelper;
    }

}
