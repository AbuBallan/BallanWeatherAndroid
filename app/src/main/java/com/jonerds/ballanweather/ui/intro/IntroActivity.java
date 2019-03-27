package com.jonerds.ballanweather.ui.intro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jonerds.ballanweather.R;
import com.jonerds.ballanweather.databinding.ActivityIntroBinding;
import com.jonerds.ballanweather.ui.base.BaseActivity;
import com.jonerds.ballanweather.ui.cities.CitiesActivity;
import com.jonerds.ballanweather.ui.customviewpager.CustomViewPager;
import com.jonerds.ballanweather.ui.intro.adapter.IntroAdapter;
import com.jonerds.ballanweather.ui.location.LocationActivity;
import com.jonerds.ballanweather.ui.main.MainActivity;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import javax.inject.Inject;

public class IntroActivity extends BaseActivity implements IntroMvpView {

    private static int RC_LOCATION_PERMISSION = 56;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, IntroActivity.class);
        return intent;
    }

    private ActivityIntroBinding mBinding;

    @Inject
    IntroMvpPresenter<IntroMvpView> mPresenter;

    @Inject
    IntroAdapter mIntroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_intro);
        mBinding.setView(this);
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {

        setUpViewPager(mBinding.viewPager);
        setUpDotsIndicator(mBinding.dotsIndicator, mBinding.viewPager);

    }

    private void setUpDotsIndicator(DotsIndicator dotsIndicator, CustomViewPager viewPager) {
        dotsIndicator.setDotsClickable(false);
        dotsIndicator.setViewPager(viewPager);
    }

    private void setUpViewPager(CustomViewPager viewPager) {
        viewPager.setPagingEnabled(false);
        mIntroAdapter.setMvpView(this);
        viewPager.setAdapter(mIntroAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.onSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    // no network calls in intro activity
    @Override
    public void onUnknownError(String error) {
    }

    // no network calls in intro activity
    @Override
    public void onTimeout() {
    }

    // no network calls in intro activity
    @Override
    public void onNetworkError() {
    }

    @Override
    public void onNext() {

        mPresenter.onNext(mBinding.viewPager.getCurrentItem());

    }

    @Override
    public void nextSlide() {

        int current = mBinding.viewPager.getCurrentItem();
        if (current < mBinding.viewPager.getChildCount() - 1)
            mBinding.viewPager.setCurrentItem(current + 1);

    }

    @Override
    public void openMainActivity() {

        Intent intent = MainActivity.getStartIntent(this);
        startActivity(intent);
        finish();

    }

    @Override
    public void hideNext() {
        mBinding.nextButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNext() {
        mBinding.nextButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setNextText(int code) {
        if (code == DONE_TEXT)
            mBinding.nextButton.setText(R.string.done);
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
    public void showMessage(int code) {
        if (code == SELECT_LOCATION_ERROR){
            Toast.makeText(this, R.string.select_location_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSelectAutomaticLocation() {
        mPresenter.onSelectAutomaticLocation();
    }

    @Override
    public void onSelectManualLocation() {
        mPresenter.onSelectManualLocation();
    }

    @Override
    public void openCitiesActivity() {
        Intent intent = CitiesActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void openLocationActivity() {
        Intent intent = LocationActivity.getStartIntent(this);
        startActivity(intent);
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
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
