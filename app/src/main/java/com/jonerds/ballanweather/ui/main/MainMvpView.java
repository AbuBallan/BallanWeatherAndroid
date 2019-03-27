package com.jonerds.ballanweather.ui.main;

import com.jonerds.ballanweather.data.model.DayWeather;
import com.jonerds.ballanweather.data.model.HourWeather;
import com.jonerds.ballanweather.ui.base.MvpView;

import java.util.List;

public interface MainMvpView extends MvpView {

    void toggleDetailsExpandedLayout();

    void showContentLayout();

    void showLoadingLayout();

    void showErrorLayout();

    void showNoInternetLayout();

    void renderWeatherData(HourWeather currentHourWeather, DayWeather currentDayWeather, List<DayWeather> weekWeather);

    void openCitiesActivity();

    void openIntroActivity();

    void onFabClicked();

    void dismissSwipeRefreshLayout();

    void showSwipeRefreshLayout();

    void onRetry();

}
