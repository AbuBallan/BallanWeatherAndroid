package com.jonerds.ballanweather.ui.cities;

import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.ui.base.MvpPresenter;

public interface CitiesMvpPresenter<V extends CitiesMvpView> extends MvpPresenter<V> {

    void onSearchTextChanged(String oldQuery, String newQuery);

    void onSearchSuggestionClicked(City city);

    void onSelectCity(City city);

    void onMyLocationClicked();

}
