package com.jonerds.ballanweather.ui.splash;

import com.jonerds.ballanweather.data.DataManager;
import com.jonerds.ballanweather.ui.base.BasePresenter;
import com.jonerds.ballanweather.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V>
        implements SplashMvpPresenter<V>{

    @Inject
    public SplashPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

        // free memory
        getDataManager().deleteOldWeather();

        if (getDataManager().getLastOpenedCity() != null)
            getMvpView().openMainActivity();
        else
            getMvpView().openIntroActivity();
    }
}
