package com.jonerds.ballanweather.ui.intro;

import com.jonerds.ballanweather.ui.base.MvpPresenter;

public interface IntroMvpPresenter<V extends IntroMvpView> extends MvpPresenter<V> {

    void onNext(int current);

    void onSelect(int pos);

    void onGrantedLocationPermission();

    void onDenyLocationPermission();

    void onSelectAutomaticLocation();

    void onSelectManualLocation();

}
