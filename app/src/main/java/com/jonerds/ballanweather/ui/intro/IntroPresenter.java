package com.jonerds.ballanweather.ui.intro;

import com.jonerds.ballanweather.data.DataManager;
import com.jonerds.ballanweather.ui.base.BasePresenter;
import com.jonerds.ballanweather.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class IntroPresenter<V extends IntroMvpView> extends BasePresenter<V> implements IntroMvpPresenter<V> {

    @Inject
    public IntroPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }


    @Override
    public void onNext(int current) {
        if (current == 0){

            getMvpView().nextSlide();

        }else if (current == 1){

            if (getMvpView().checkLocationPermission())
                getMvpView().nextSlide();
            else
                getMvpView().requestLocationPermission();

        }else if (current == 2){

            if (getDataManager().getLastOpenedCity() != null)
                getMvpView().openMainActivity();
            else
                getMvpView().showMessage(getMvpView().SELECT_LOCATION_ERROR);

        }
    }

    @Override
    public void onSelect(int pos) {
        if (pos == 0){

            getMvpView().hideNext();

        }else if (pos == 1){

            getMvpView().showNext();

        }else if (pos == 2){

            getMvpView().setNextText(getMvpView().DONE_TEXT);

        }
    }

    @Override
    public void onGrantedLocationPermission() {
        getMvpView().nextSlide();
    }

    @Override
    public void onDenyLocationPermission() {
        getMvpView().nextSlide();
    }

    @Override
    public void onSelectAutomaticLocation() {
        getMvpView().openLocationActivity();
    }

    @Override
    public void onSelectManualLocation() {
        getMvpView().openCitiesActivity();
    }
}
