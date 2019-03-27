package com.jonerds.ballanweather.di.module;

import com.jonerds.ballanweather.data.realm.AppRealmHelper;
import com.jonerds.ballanweather.data.realm.RealmHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class RealmHelperModule {

    @Provides
    @Singleton
    public RealmHelper provideRealmHelper (AppRealmHelper appRealmHelper){
        return appRealmHelper;
    }

    @Provides
    @Singleton
    public Realm provideRealm(){
        return Realm.getDefaultInstance();
    }
}
