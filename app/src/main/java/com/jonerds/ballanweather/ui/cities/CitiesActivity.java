package com.jonerds.ballanweather.ui.cities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.jonerds.ballanweather.R;
import com.jonerds.ballanweather.data.DataManager;
import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.data.model.SearchResponse;
import com.jonerds.ballanweather.databinding.ActivityCitiesBinding;
import com.jonerds.ballanweather.ui.base.BaseActivity;
import com.jonerds.ballanweather.ui.cities.adapter.CitiesAdapter;
import com.jonerds.ballanweather.ui.location.LocationActivity;
import com.jonerds.ballanweather.ui.main.MainActivity;
import com.jonerds.ballanweather.utils.AppConstants;
import com.jonerds.ballanweather.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CitiesActivity extends BaseActivity implements CitiesMvpView {

    public static final int RC_SELECT_CITY = 54;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, CitiesActivity.class);
        return intent;
    }

    @Inject
    CitiesMvpPresenter<CitiesMvpView> mPresenter;

    @Inject
    CitiesAdapter mCitiesAdapter;

    @Inject
    LinearLayoutManager mVerticalLinearLayoutManager;

    private ActivityCitiesBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cities);

        getActivityComponent().inject(this);
        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {

        setUpCitiesRecyclerView(mBinding.citiesRecyclerView);
        setupFloatingSearch(mBinding.floatingSearchView);

    }

    private void setUpCitiesRecyclerView(RecyclerView citiesRecyclerView) {

        citiesRecyclerView.setLayoutManager(mVerticalLinearLayoutManager);
        mCitiesAdapter.setMvpView(this);
        citiesRecyclerView.setAdapter(mCitiesAdapter);

    }

    private void setupFloatingSearch (final FloatingSearchView searchView){

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                mPresenter.onSearchTextChanged(oldQuery, newQuery);
            }
        });

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                mPresenter.onSearchSuggestionClicked((City) searchSuggestion);
            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });

        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.my_location)
                    mPresenter.onMyLocationClicked();
            }
        });

        searchView.setOnSuggestionsListHeightChanged(new FloatingSearchView.OnSuggestionsListHeightChanged() {
            @Override
            public void onSuggestionsListHeightChanged(float newHeight) {
                mBinding.citiesRecyclerView.setTranslationY(newHeight);
            }
        });
    }


    @Override
    public void showSearchViewProgress() {
        mBinding.floatingSearchView.showProgress();
    }

    @Override
    public void hideSearchViewProgress() {
        mBinding.floatingSearchView.hideProgress();
    }

    @Override
    public void clearSearchViewSuggestions() {
        mBinding.floatingSearchView.clearSuggestions();
    }

    @Override
    public void swapSearchSuggestions(List<City> cities) {
        mBinding.floatingSearchView.swapSuggestions(cities);
    }

    @Override
    public void renderSavedCities(List<City> cityList) {
        mCitiesAdapter.setCityList(cityList);
    }

    @Override
    public void onSelectCity(City city) {
        mPresenter.onSelectCity(city);
    }

    @Override
    public void successFinish(City city) {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.CITY_ID, city.getId());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void openLocationActivity() {
        Intent intent = LocationActivity.getStartIntent(this);
        startActivityForResult(intent, RC_SELECT_CITY);
    }



    @Override
    public void onUnknownError(String error) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.unknown_error)
                .setIcon(R.drawable.ic_error_red)
                .setCancelable(true)
                .setPositiveButton(R.string.ok, null)
                .setMessage(error)
                .show();

        hideSearchViewProgress();
    }

    @Override
    public void onTimeout() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.timeout)
                .setIcon(R.drawable.ic_error_red)
                .setCancelable(true)
                .setPositiveButton(R.string.ok, null)
                .setMessage(R.string.no_internet_message)
                .show();

        hideSearchViewProgress();
    }

    @Override
    public void onNetworkError() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.network_error)
                .setIcon(R.drawable.ic_error_red)
                .setCancelable(true)
                .setPositiveButton(R.string.ok, null)
                .setMessage(R.string.no_internet_message)
                .show();

        hideSearchViewProgress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SELECT_CITY && resultCode == RESULT_OK){
            mPresenter.onSelectCity(new City(data.getStringExtra(AppConstants.CITY_ID)));
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
