package com.jonerds.ballanweather.ui.location;

import com.jonerds.ballanweather.ui.base.MvpPresenter;

public interface LocationMvpPresenter<V extends LocationMvpView> extends MvpPresenter<V> {

    void onGrantedLocationPermission();

    void onDenyLocationPermission();

    void onApply();

    void onCancel();
}
