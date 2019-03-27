package com.jonerds.ballanweather.ui.location;

import android.util.Pair;

import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.ui.base.MvpView;

public interface LocationMvpView extends MvpView {

    boolean checkLocationPermission();

    void requestLocationPermission();

    Pair<Double, Double> getLatLong();

    void renderCity(City city);

    void onApply();

    void onCancel();

    void onPermissionError();

    void successFinish(City city);

    void cancelFinish ();

}
