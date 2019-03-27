package com.jonerds.ballanweather.data.realm;

import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.data.model.DayWeather;
import com.jonerds.ballanweather.data.model.HourWeather;
import com.jonerds.ballanweather.data.model.WeatherResponse;
import com.jonerds.ballanweather.utils.CommonUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;

public class AppRealmHelper implements RealmHelper {

    private static final String TAG = "AppRealmHelper";

    private Realm realm;

    @Inject
    public AppRealmHelper(Realm realm) {
        this.realm = realm;
    }

    @Override
    public Observable<CollectionChange<RealmResults<City>>> getSavedCities() {
        return realm.where(City.class).findAllAsync().asChangesetObservable();
    }

    @Override
    public void addCity(City city) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(city);
        realm.commitTransaction();
    }

    @Override
    public Observable<WeatherResponse> getWeatherForecast(final String cityName) {

        return realm.where(DayWeather.class).equalTo("city.id", cityName).greaterThanOrEqualTo("date", CommonUtils.getMidnightDateAsLong()).findAllAsync().asFlowable()
                .map(new Function<RealmResults<DayWeather>, WeatherResponse>() {
                    @Override
                    public WeatherResponse apply(RealmResults<DayWeather> dayWeathers) throws Exception {

                        HourWeather currentCondition = dayWeathers.get(0).getHourly().get(CommonUtils.getHoursAsInt() / 100);
                        return new WeatherResponse(dayWeathers, currentCondition);

                    }
                })
                .toObservable();

    }

    @Override
    public void saveWeatherList(List<DayWeather> dayWeatherList) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(dayWeatherList);
        realm.commitTransaction();
    }

    @Override
    public void deleteOldDateWeather() {
        realm.beginTransaction();
        realm.where(DayWeather.class).lessThan("date", CommonUtils.getMidnightDateAsLong()).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }
}
