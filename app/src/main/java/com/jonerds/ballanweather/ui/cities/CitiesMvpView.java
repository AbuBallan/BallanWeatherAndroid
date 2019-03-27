package com.jonerds.ballanweather.ui.cities;

import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.ui.base.MvpView;

import java.util.List;

public interface CitiesMvpView extends MvpView {

    void showSearchViewProgress();

    void hideSearchViewProgress();

    void clearSearchViewSuggestions();

    void swapSearchSuggestions(List<City> cities);

    void renderSavedCities(List<City> cityList);

    void onSelectCity(City city);

    void successFinish(City city);

    void dismissKeyboard();

    void openLocationActivity();
}
