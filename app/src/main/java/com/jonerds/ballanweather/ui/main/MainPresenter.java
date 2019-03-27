package com.jonerds.ballanweather.ui.main;

import android.util.Log;

import com.jonerds.ballanweather.data.DataManager;
import com.jonerds.ballanweather.data.api.wrapper.CallbackWrapper;
import com.jonerds.ballanweather.data.model.WeatherResponse;
import com.jonerds.ballanweather.ui.base.BasePresenter;
import com.jonerds.ballanweather.utils.rx.SchedulerProvider;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresnter<V> {

    @Inject
    public MainPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(final V mvpView) {

        super.onAttach(mvpView);

        getMvpView().showLoadingLayout();

    }

    @Override
    public void onRefresh() {
        onViewPrepared();
    }

    @Override
    public void onViewPrepared() {

        String cityName = getDataManager().getLastOpenedCity();

        if (cityName == null){
            getMvpView().openIntroActivity();
            return;
        }

        getCompositeDisposable().add(
                Observable.concatArrayEager(

                        getDataManager().getWeatherForecastFromRealm(cityName)
                                .subscribeOn(getSchedulerProvider().ui())
                                .observeOn(getSchedulerProvider().ui())
                                .materialize()
                                .filter(new Predicate<Notification<WeatherResponse>>() {
                                    @Override
                                    public boolean test(Notification<WeatherResponse> weatherResponseNotification) throws Exception {
                                        return !weatherResponseNotification.isOnError();
                                    }
                                })
                                .<WeatherResponse>dematerialize()
                                .debounce(400, TimeUnit.MICROSECONDS),

                        getDataManager().getWeatherForecastFromApi(cityName)
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .materialize()
                                .flatMap(new Function<Notification<WeatherResponse>, ObservableSource<Notification<WeatherResponse>>>() {
                                    @Override
                                    public ObservableSource<Notification<WeatherResponse>> apply(Notification<WeatherResponse> weatherResponseNotification) throws Exception {

                                        if (weatherResponseNotification.isOnError())
                                            return Observable.just(weatherResponseNotification).delay(2, TimeUnit.SECONDS)
                                                    .subscribeOn(getSchedulerProvider().io())
                                                    .observeOn(getSchedulerProvider().ui());
                                        else
                                            return Observable.just(weatherResponseNotification)
                                                    .subscribeOn(getSchedulerProvider().io())
                                                    .observeOn(getSchedulerProvider().ui());
                                    }
                                })
                                .map(new Function<Notification<WeatherResponse>, Notification<WeatherResponse>>() {
                                    @Override
                                    public Notification<WeatherResponse> apply(final Notification<WeatherResponse> weatherResponseNotification) throws Exception {

                                        if (weatherResponseNotification.isOnError())
                                            CallbackWrapper.handleError(getMvpView(), weatherResponseNotification.getError());

                                        return weatherResponseNotification;
                                    }
                                })
                                .filter(new Predicate<Notification<WeatherResponse>>() {
                                    @Override
                                    public boolean test(Notification<WeatherResponse> weatherResponseNotification) throws Exception {
                                        return !weatherResponseNotification.isOnError();
                                    }
                                })
                                .<WeatherResponse>dematerialize()
                                .map(new Function<WeatherResponse, WeatherResponse>() {
                                    @Override
                                    public WeatherResponse apply(final WeatherResponse weatherResponse) throws Exception {

                                        // save data
                                        Completable.create(new CompletableOnSubscribe() {
                                            @Override
                                            public void subscribe(CompletableEmitter emitter) throws Exception {
                                                getDataManager().saveWeatherList(weatherResponse.getDayWeather());
                                                emitter.onComplete();
                                            }
                                        }).subscribeOn(getSchedulerProvider().ui()).subscribe();

                                        return weatherResponse;
                                    }
                                })
                                .debounce(400, TimeUnit.MICROSECONDS)
                )
                        .observeOn(getSchedulerProvider().ui())
                        .subscribeWith(new DisposableObserver<WeatherResponse>() {
                            @Override
                            public void onNext(WeatherResponse weatherResponse) {
                                getMvpView().renderWeatherData(weatherResponse.getCurrentCondition(), weatherResponse.getDayWeather().get(0), weatherResponse.getDayWeather());
                                getMvpView().showContentLayout();
                                getMvpView().dismissSwipeRefreshLayout();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }

    @Override
    public void onFabClicked() {
        getMvpView().openCitiesActivity();
    }

    @Override
    public void onReload() {
        getMvpView().showSwipeRefreshLayout();
        onRefresh();
    }

    @Override
    public void renderNewCity() {
        getMvpView().showLoadingLayout();
        onViewPrepared();
    }

    @Override
    public void onRetry() {
        getMvpView().showLoadingLayout();
        onViewPrepared();
    }

}
