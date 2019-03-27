package com.jonerds.ballanweather.ui.base;

public interface MvpPresenter <V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

}