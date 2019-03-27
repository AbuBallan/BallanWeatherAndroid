package com.jonerds.ballanweather.ui.cities;

import android.util.Log;

import com.jonerds.ballanweather.data.DataManager;
import com.jonerds.ballanweather.data.api.wrapper.CallbackWrapper;
import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.ui.base.BasePresenter;
import com.jonerds.ballanweather.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;

public class CitiesPresenter<V extends CitiesMvpView> extends BasePresenter<V> implements CitiesMvpPresenter<V> {

    private static final String TAG = "CitiesPresenter";

    @Inject
    public CitiesPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

        getCompositeDisposable().add(
                getDataManager().getSavedCities()
                        .subscribeOn(getSchedulerProvider().ui())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribeWith(new CallbackWrapper<CollectionChange<RealmResults<City>>>(getMvpView()) {
                                           @Override
                                           protected void onSuccess(CollectionChange<RealmResults<City>> realmResultsCollectionChange) {
                                               getMvpView().renderSavedCities(realmResultsCollectionChange.getCollection());
                                           }
                                       }
                        )
        );
    }

    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {

        if (newQuery.length() < 3) {

            getMvpView().clearSearchViewSuggestions();

        } else {

            getMvpView().showSearchViewProgress();
            getCompositeDisposable().add(
                    getDataManager().findCitiesSuggestions(newQuery)
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribeWith(new CallbackWrapper<List<City>>(getMvpView()) {
                                @Override
                                protected void onSuccess(List<City> cities) {
                                    getMvpView().swapSearchSuggestions(cities);
                                    getMvpView().hideSearchViewProgress();
                                }
                            }));

        }
    }

    @Override
    public void onSearchSuggestionClicked(City city) {
        getMvpView().dismissKeyboard();
        onSelectCity(city);
    }

    @Override
    public void onSelectCity(City city) {
        getDataManager().setLastOpenedCity(city.getId());
        getMvpView().successFinish(city);
    }

    @Override
    public void onMyLocationClicked() {
        getMvpView().openLocationActivity();
    }
}
