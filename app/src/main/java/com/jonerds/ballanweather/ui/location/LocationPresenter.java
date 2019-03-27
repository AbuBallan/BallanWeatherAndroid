package com.jonerds.ballanweather.ui.location;

import android.util.Pair;

import com.jonerds.ballanweather.data.DataManager;
import com.jonerds.ballanweather.data.api.wrapper.CallbackWrapper;
import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.ui.base.BasePresenter;
import com.jonerds.ballanweather.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class LocationPresenter<V extends LocationMvpView> extends BasePresenter<V> implements LocationMvpPresenter<V> {

    private City currentCity = null;

    @Inject
    public LocationPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

        if (getMvpView().checkLocationPermission())
            onGrantedLocationPermission();
        else
            getMvpView().requestLocationPermission();

    }

    @Override
    public void onGrantedLocationPermission() {

        Pair<Double, Double> latLong = getMvpView().getLatLong();

        getCompositeDisposable().add(
                getDataManager().searchForCity(latLong.first, latLong.second)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribeWith(new CallbackWrapper<City>(getMvpView()) {
                    @Override
                    protected void onSuccess(City city) {
                        currentCity = city;
                        getMvpView().renderCity(city);
                    }
                })
        );

    }

    @Override
    public void onDenyLocationPermission() {
        getMvpView().onPermissionError();
    }

    @Override
    public void onApply() {
        if (currentCity != null){
            getDataManager().addSavedCity(currentCity);
            getDataManager().setLastOpenedCity(currentCity.getId());
            getMvpView().successFinish(currentCity);
        }
    }

    @Override
    public void onCancel() {
        getMvpView().cancelFinish();
    }
}
