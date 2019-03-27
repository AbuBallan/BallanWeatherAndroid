package com.jonerds.ballanweather.ui.intro;

import com.jonerds.ballanweather.ui.base.MvpView;

public interface IntroMvpView extends MvpView {

    int DONE_TEXT = 55;

    int SELECT_LOCATION_ERROR = 54;

    void onNext ();

    void nextSlide();

    void openMainActivity();

    void hideNext();

    void showNext();

    void setNextText(int code);

    boolean checkLocationPermission();

    void requestLocationPermission();

    void showMessage(int code);

    void onSelectAutomaticLocation();

    void onSelectManualLocation();

    void openCitiesActivity();

    void openLocationActivity();

}
