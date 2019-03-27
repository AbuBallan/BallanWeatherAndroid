package com.jonerds.ballanweather.ui.base;

public interface MvpView {

    void onUnknownError (String error);

    void onTimeout();

    void onNetworkError();

}
