package com.jonerds.ballanweather.ui.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.jonerds.ballanweather.R;
import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.databinding.ActivityLocationBinding;
import com.jonerds.ballanweather.ui.base.BaseActivity;
import com.jonerds.ballanweather.utils.AppConstants;

import java.util.List;

import javax.inject.Inject;

public class LocationActivity extends BaseActivity implements LocationMvpView {

    private static int RC_LOCATION_PERMISSION = 56;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, LocationActivity.class);
    }

    @Inject
    LocationMvpPresenter<LocationMvpView> mPresenter;

    private ActivityLocationBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        mBinding.setView(this);
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {

    }

    @Override
    public boolean checkLocationPermission() {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION ))
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, RC_LOCATION_PERMISSION);
        else
            mPresenter.onDenyLocationPermission();
    }

    @Override
    public Pair<Double, Double> getLatLong() {
        if (checkLocationPermission()) {
            Location location = getLastKnownLocation();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            return new Pair<>(latitude, longitude);
        }
        return null;
    }

    private Location getLastKnownLocation() {
        if (checkLocationPermission()) {
            LocationManager lm = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            List<String> providers = lm.getProviders(true);
            Location bestLocation = null;
            for (String provider : providers) {
                Location l = lm.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
            }
            return bestLocation;
        }
        return null;
    }

    @Override
    public void renderCity(City city){

        mBinding.cityTextView.setText(city.getId().split(",")[0]);
        mBinding.locationDescTextView.setText(String.format(getResources().getString(R.string.location_desc), city.getId()));
        mBinding.applyButton.setVisibility(View.VISIBLE);
        mBinding.locationLottieView.playAnimation();

    }

    @Override
    public void onApply() {
        mPresenter.onApply();
    }

    @Override
    public void onCancel() {
        mPresenter.onCancel();
    }

    @Override
    public void onPermissionError() {
        mBinding.cityTextView.setText(getResources().getString(R.string.permission_error));
        mBinding.locationDescTextView.setText(getResources().getString(R.string.permission_error_desc));
    }

    @Override
    public void successFinish(City city) {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.CITY_ID, city.getId());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void cancelFinish() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_LOCATION_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                mPresenter.onGrantedLocationPermission();
            else
                mPresenter.onDenyLocationPermission();
        }
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
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
