package com.jonerds.ballanweather.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.jonerds.ballanweather.di.ApplicationContext;
import com.jonerds.ballanweather.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ApplicationModule.class)
public class SharedPreferencesModule {

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences (@ApplicationContext Context context){
        return context.getSharedPreferences(AppConstants.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public SharedPreferences.Editor provideEditor (SharedPreferences sharedPreferences){
        return sharedPreferences.edit();
    }
}
