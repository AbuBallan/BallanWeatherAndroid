package com.jonerds.ballanweather;

import android.content.SharedPreferences;

import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.data.prefs.AppPrefsHelper;
import com.jonerds.ballanweather.utils.AppConstants;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PrefsHelperTest {

    public static final String TEST_CITY = "Amman, Jordan";

    City mCity;

    AppPrefsHelper mHelper;

    @Mock
    SharedPreferences mMockSharedPreferences;

    @Mock
    SharedPreferences.Editor mMockEditor;

    AppPrefsHelper mBrokenHelper;

    @Mock
    SharedPreferences mMockBrokenSharedPreferences;

    @Mock
    SharedPreferences.Editor mMockBrokenEditor;

    @Before
    public void initMocks(){
        mCity = new City(TEST_CITY);
        mHelper = createMockSharedPreferencesHelper();
        mBrokenHelper = createMockBrokenSharedPreferencesHelper();
    }

    @Test
    public void sharedPreferencesHelper_SaveAndReadCity (){
        boolean success = mHelper.setLastOpenedCity(mCity.getId());

        assertThat("Checking that SharedPreferenceEntry.save... returns true",
                success, CoreMatchers.is(true));

        City city = new City(mHelper.getLastOpenedCity());

        assertThat("name ..", mCity.getId(), CoreMatchers.is(CoreMatchers.equalTo(city.getId())));

    }

    @Test
    public void sharedPreferencesHelper_SaveCityFailed_ReturnsFalse(){
        boolean success = mBrokenHelper.setLastOpenedCity(mCity.getId());
        assertThat("Checking that SharedPreferenceEntry.save... returns false",
                success, CoreMatchers.is(false));
    }

    private AppPrefsHelper createMockBrokenSharedPreferencesHelper() {

        Mockito.when(mMockBrokenEditor.commit()).thenReturn(false);

        Mockito.when(mMockBrokenSharedPreferences.edit()).thenReturn(mMockBrokenEditor);

        return new AppPrefsHelper(mMockBrokenSharedPreferences, mMockBrokenEditor);
    }

    public AppPrefsHelper createMockSharedPreferencesHelper() {
        Mockito.when(mMockSharedPreferences.getString(Mockito.eq(AppConstants.LAST_OPENED_CITY), Mockito.anyString()))
                .thenReturn(mCity.getId());

        Mockito.when(mMockEditor.commit()).thenReturn(true);

        Mockito.when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);

        return new AppPrefsHelper(mMockSharedPreferences, mMockEditor);

    }
}
