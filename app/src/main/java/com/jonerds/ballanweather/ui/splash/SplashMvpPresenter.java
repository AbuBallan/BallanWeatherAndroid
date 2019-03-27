package com.jonerds.ballanweather.ui.splash;

import com.jonerds.ballanweather.di.PerActivity;
import com.jonerds.ballanweather.ui.base.MvpPresenter;

@PerActivity
public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {
}
