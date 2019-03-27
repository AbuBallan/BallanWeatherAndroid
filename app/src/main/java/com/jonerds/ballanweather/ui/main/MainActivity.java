package com.jonerds.ballanweather.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.observers.DisposableSingleObserver;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.jonerds.ballanweather.R;
import com.jonerds.ballanweather.data.DataManager;
import com.jonerds.ballanweather.data.model.DayWeather;
import com.jonerds.ballanweather.data.model.HourWeather;
import com.jonerds.ballanweather.data.model.WeatherResponse;
import com.jonerds.ballanweather.databinding.ActivityMainBinding;
import com.jonerds.ballanweather.ui.base.BaseActivity;
import com.jonerds.ballanweather.ui.cities.CitiesActivity;
import com.jonerds.ballanweather.ui.intro.IntroActivity;
import com.jonerds.ballanweather.ui.intro.adapter.IntroAdapter;
import com.jonerds.ballanweather.ui.main.adapter.HourlyForecastAdapter;
import com.jonerds.ballanweather.ui.main.adapter.WeekForecastAdapter;
import com.jonerds.ballanweather.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainMvpView {

    public static final int RC_SELECT_CITY = 54;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    enum MainLayouts {
        CONTENT, LOADING, ERROR, NO_INTERNET
    }

    @Inject
    MainMvpPresnter<MainMvpView> mPresenter;

    @Inject
    HourlyForecastAdapter mHourlyForecastAdapter;

    @Inject
    WeekForecastAdapter mWeekForecastAdapter;

    @Inject
    LinearLayoutManager mVerticalLinearLayoutManager;

    @Inject
    LinearLayoutManager mHorizontalLinearLayoutManager;

    @Inject
    GravitySnapHelper mGravitySnapHelper;

    private ActivityMainBinding mBinding;

    private MainLayouts selectedLayout;

    private boolean isViewRendered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setView(this);

        getActivityComponent().inject(this);
        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {

        setUpToolbar(mBinding.contentLayout.toolbar);
        setUpCollapsingToolbar(mBinding.contentLayout.appbarLayout, mBinding.contentLayout.collapsingToolbarLayout);
        supUpHourlyForecastRV(mBinding.contentLayout.hourlyForecastRecyclerView);
        supUpWeekForecastRV(mBinding.contentLayout.weekForecastRecyclerView);
        setUpSwipeRefreshLayout();

        mPresenter.onViewPrepared();

    }

    private void setUpSwipeRefreshLayout() {
        mBinding.contentLayout.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onRefresh();
            }
        });
    }


    private void supUpHourlyForecastRV(RecyclerView recyclerView) {
        mHorizontalLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(mHorizontalLinearLayoutManager);
        recyclerView.setAdapter(mHourlyForecastAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        mGravitySnapHelper.attachToRecyclerView(recyclerView);
    }

    private void supUpWeekForecastRV(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(mVerticalLinearLayoutManager);
        recyclerView.setAdapter(mWeekForecastAdapter);
    }

    private void setUpCollapsingToolbar(AppBarLayout appBarLayout, final CollapsingToolbarLayout collapsingToolbarLayout) {
        collapsingToolbarLayout.setTitle(" ");
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }


    private void setUpToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    @BindingAdapter({"android:status_time", "android:status_desc"})
    public static void setWeatherStatusImageView(ImageView imageView, int time, String desc) {

        boolean isNight = time <= 500 || time >= 1900;
        int res = (isNight) ? R.drawable.clear_night : R.drawable.clear_day;

        if (desc.toLowerCase().contains("partly cloudy")) {
            res = (isNight) ? R.drawable.partly_cloudy_night : R.drawable.partly_cloudy;
        } else if (desc.toLowerCase().contains("cloudy")) {
            res = R.drawable.cloudy_weather;
        } else if (desc.toLowerCase().contains("haze")) {
            res = R.drawable.haze_weather;
        } else if (desc.toLowerCase().contains("rainy")) {
            res = R.drawable.rainy_weather;
        } else if (desc.toLowerCase().contains("windy")) {
            res = R.drawable.windy_weather;
        }

        Glide
                .with(imageView.getContext())
                .load(res)
                .into(imageView);
    }

    @BindingAdapter({"android:placeholder_time", "android:placeholder_desc"})
    public static void setWeatherPlaceholderImageView(ImageView imageView, int time, String desc) {

        if (desc == null) return;

        boolean isNight = time <= 500 || time >= 1900;
        int res = (isNight) ? R.drawable.clear_nigh_placeholder : R.drawable.clear_day_pleaceholder;

        if (desc.toLowerCase().contains("partly cloud")) {
            res = (isNight) ? R.drawable.partylecloudy_night_placeholder : R.drawable.partylcloudy_day_placeholder;
        } else if (desc.toLowerCase().contains("cloud")) {
            res = (isNight) ? R.drawable.cloudy_night_placeholder : R.drawable.cloudy_day_placeholder;
        } else if (desc.toLowerCase().contains("fog")) {
            res = R.drawable.fog_placeholder;
        } else if (desc.toLowerCase().contains("rain")) {
            res = R.drawable.rain_placeholder;
        } else if (desc.toLowerCase().contains("wind")) {
            res = R.drawable.wind_placeholder;
        } else if (desc.toLowerCase().contains("wind")) {
            res = R.drawable.snow_placeholder;
        }

        Glide
                .with(imageView.getContext())
                .load(res)
                .into(imageView);
    }

    @Override
    public void toggleDetailsExpandedLayout() {
        if (mBinding.contentLayout.expandDetailsConstraintLayout.isExpanded()) {
            mBinding.contentLayout.expandDetailsConstraintLayout.setExpanded(false);
            mBinding.contentLayout.expandDetailsConstraintLayout.toggle();
            mBinding.contentLayout.expandedDetailsImageView.setImageResource(R.drawable.ic_expand_more);
        } else {
            mBinding.contentLayout.expandDetailsConstraintLayout.setExpanded(true);
            mBinding.contentLayout.expandDetailsConstraintLayout.toggle();
            mBinding.contentLayout.expandedDetailsImageView.setImageResource(R.drawable.ic_expand_less);
        }
    }

    @Override
    public void showContentLayout() {

        isViewRendered = true;

        if (selectedLayout == MainLayouts.CONTENT)
            return;

        if (selectedLayout != null) hideLayout(selectedLayout);

        mBinding.contentLayout.rootLayout.setVisibility(View.VISIBLE);
        selectedLayout = MainLayouts.CONTENT;

    }

    @Override
    public void showLoadingLayout() {

        isViewRendered = false;

        if (selectedLayout == MainLayouts.LOADING)
            return;

        if (selectedLayout != null) hideLayout(selectedLayout);

        mBinding.loadingLayout.rootLayout.setVisibility(View.VISIBLE);
        mBinding.loadingLayout.loadingLottieView.playAnimation();
        selectedLayout = MainLayouts.LOADING;
    }

    @Override
    public void showErrorLayout() {

        if (selectedLayout == MainLayouts.ERROR)
            return;

        if (selectedLayout != null) hideLayout(selectedLayout);

        mBinding.errorLayout.rootLayout.setVisibility(View.VISIBLE);
        mBinding.errorLayout.errorLottieView.playAnimation();
        selectedLayout = MainLayouts.ERROR;
    }

    @Override
    public void showNoInternetLayout() {

        if (selectedLayout == MainLayouts.NO_INTERNET)
            return;

        if (selectedLayout != null) hideLayout(selectedLayout);

        mBinding.noInternetLayout.rootLayout.setVisibility(View.VISIBLE);
        mBinding.noInternetLayout.noInternetLottieView.playAnimation();
        selectedLayout = MainLayouts.NO_INTERNET;
    }

    private void hideLayout(MainLayouts selectedLayout) {

        switch (selectedLayout) {
            case CONTENT:
                mBinding.contentLayout.rootLayout.setVisibility(View.GONE);
                break;
            case ERROR:
                mBinding.errorLayout.rootLayout.setVisibility(View.GONE);
                mBinding.errorLayout.errorLottieView.cancelAnimation();
                break;
            case LOADING:
                mBinding.loadingLayout.rootLayout.setVisibility(View.GONE);
                mBinding.loadingLayout.loadingLottieView.cancelAnimation();
                break;
            case NO_INTERNET:
                mBinding.noInternetLayout.rootLayout.setVisibility(View.GONE);
                mBinding.noInternetLayout.noInternetLottieView.cancelAnimation();
                break;
        }
    }

    @Override
    public void renderWeatherData(HourWeather currentHourWeather, DayWeather currentDayWeather, List<DayWeather> weekWeather) {

        mBinding.setCurrentCondition(currentHourWeather);
        mBinding.setDayWeather(currentDayWeather);
        mBinding.setCity(currentDayWeather.getCity());

        mHourlyForecastAdapter.setHourWeatherList(currentDayWeather.getHourly());
        mWeekForecastAdapter.setDayWeatherList(weekWeather);
    }

    @Override
    public void openCitiesActivity() {
        Intent intent = CitiesActivity.getStartIntent(this);
        startActivityForResult(intent, RC_SELECT_CITY);
    }

    @Override
    public void openIntroActivity() {
        Intent intent = IntroActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFabClicked() {
        mPresenter.onFabClicked();
    }

    @Override
    public void dismissSwipeRefreshLayout() {
        mBinding.contentLayout.swiperefresh.setRefreshing(false);
    }

    @Override
    public void showSwipeRefreshLayout() {
        mBinding.contentLayout.swiperefresh.setRefreshing(true);
    }

    @Override
    public void onRetry() {
        mPresenter.onRetry();
    }


    public void showErrorMessage(String errorMessage) {
        final Snackbar snackbar = Snackbar
                .make(mBinding.contentLayout.rootLayout, errorMessage, Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction(R.string.reload, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onReload();
                snackbar.dismiss();
            }
        });

        snackbar.setActionTextColor(Color.YELLOW);

        snackbar.show();
    }

    @Override
    public void onUnknownError(String error) {
        if (isViewRendered) {
            showErrorMessage(error);
            dismissSwipeRefreshLayout();
        } else
            showErrorLayout();
    }

    @Override
    public void onTimeout() {
        if (isViewRendered) {
            showErrorMessage(getResources().getString(R.string.no_internet_message));
            dismissSwipeRefreshLayout();
        } else
            showNoInternetLayout();
    }

    @Override
    public void onNetworkError() {
        if (isViewRendered) {
            showErrorMessage(getResources().getString(R.string.no_internet_message));
            dismissSwipeRefreshLayout();
        } else
            showNoInternetLayout();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SELECT_CITY && resultCode == RESULT_OK){
            mPresenter.renderNewCity();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
