package com.jonerds.ballanweather.di.module;

import android.content.Context;
import android.view.Gravity;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.jonerds.ballanweather.di.ActivityContext;
import com.jonerds.ballanweather.di.PerActivity;
import com.jonerds.ballanweather.ui.cities.CitiesMvpPresenter;
import com.jonerds.ballanweather.ui.cities.CitiesMvpView;
import com.jonerds.ballanweather.ui.cities.CitiesPresenter;
import com.jonerds.ballanweather.ui.cities.adapter.CitiesAdapter;
import com.jonerds.ballanweather.ui.intro.IntroMvpPresenter;
import com.jonerds.ballanweather.ui.intro.IntroMvpView;
import com.jonerds.ballanweather.ui.intro.IntroPresenter;
import com.jonerds.ballanweather.ui.intro.adapter.IntroAdapter;
import com.jonerds.ballanweather.ui.location.LocationMvpPresenter;
import com.jonerds.ballanweather.ui.location.LocationMvpView;
import com.jonerds.ballanweather.ui.location.LocationPresenter;
import com.jonerds.ballanweather.ui.main.MainActivity;
import com.jonerds.ballanweather.ui.main.MainMvpPresnter;
import com.jonerds.ballanweather.ui.main.MainMvpView;
import com.jonerds.ballanweather.ui.main.MainPresenter;
import com.jonerds.ballanweather.ui.main.adapter.HourlyForecastAdapter;
import com.jonerds.ballanweather.ui.main.adapter.WeekForecastAdapter;
import com.jonerds.ballanweather.ui.splash.SplashMvpPresenter;
import com.jonerds.ballanweather.ui.splash.SplashMvpView;
import com.jonerds.ballanweather.ui.splash.SplashPresenter;
import com.jonerds.ballanweather.utils.rx.AppSchedulerProvider;
import com.jonerds.ballanweather.utils.rx.SchedulerProvider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider providerSchedulerProvider (){
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter
    ){
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresnter<MainMvpView> provideMainPresenter(
            MainPresenter<MainMvpView> presenter
    ){
        return presenter;
    }

    @Provides
    @PerActivity
    CitiesMvpPresenter<CitiesMvpView> provideCitiesPresenter(
            CitiesPresenter<CitiesMvpView> presenter
    ){
        return presenter;
    }

    @Provides
    @PerActivity
    LocationMvpPresenter<LocationMvpView> provideLocationPresenter(
            LocationPresenter<LocationMvpView> presenter
    ){
        return presenter;
    }

    @Provides
    @PerActivity
    IntroMvpPresenter<IntroMvpView> provideIntroPresenter(
                IntroPresenter<IntroMvpView> presenter
    ){
        return presenter;
    }

    @Provides
    @PerActivity
    HourlyForecastAdapter provideHourlyForecastAdapter(@ActivityContext Context context){
        return new HourlyForecastAdapter(context);
    }

    @Provides
    @PerActivity
    WeekForecastAdapter provideWeekForecastAdapter(@ActivityContext Context context){
        return new WeekForecastAdapter(context);
    }

    @Provides
    @PerActivity
    CitiesAdapter provideCitiesAdapter(@ActivityContext Context context){
        return new CitiesAdapter(context);
    }

    @Provides
    @PerActivity
    IntroAdapter provideIntroAdapter(@ActivityContext Context context){
        return new IntroAdapter(context);
    }

    @Provides
    LinearLayoutManager provideVerticalLinearLayoutManager(@ActivityContext Context context){
        return new LinearLayoutManager(context);
    }

    @Provides
    GravitySnapHelper provideGravitySnapHelper (){
        return new GravitySnapHelper(Gravity.START);
    }

}
