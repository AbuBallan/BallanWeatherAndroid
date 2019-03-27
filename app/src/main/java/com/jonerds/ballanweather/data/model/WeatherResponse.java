package com.jonerds.ballanweather.data.model;

import com.google.gson.annotations.JsonAdapter;
import com.jonerds.ballanweather.data.api.deserializer.WeatherApiDeserializer;

import java.util.List;

@JsonAdapter(WeatherApiDeserializer.class)
public class WeatherResponse {

    private List<DayWeather> mDayWeather;

    private HourWeather currentCondition;

    public WeatherResponse(List<DayWeather> dayWeather, HourWeather currentCondition) {
        this.mDayWeather = dayWeather;
        this.currentCondition = currentCondition;
    }

    public List<DayWeather> getDayWeather() {
        return mDayWeather;
    }

    public void setDayWeather(List<DayWeather> dayWeather) {
        this.mDayWeather = dayWeather;
    }

    public HourWeather getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(HourWeather currentCondition) {
        this.currentCondition = currentCondition;
    }
}
