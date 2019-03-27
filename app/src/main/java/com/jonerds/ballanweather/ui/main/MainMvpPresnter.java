package com.jonerds.ballanweather.ui.main;

import com.jonerds.ballanweather.ui.base.MvpPresenter;

public interface MainMvpPresnter<V extends MainMvpView> extends MvpPresenter<V> {

    void onRefresh();

    void onViewPrepared();

    void onFabClicked();

    void onReload();

    void renderNewCity();

    void onRetry();
}
