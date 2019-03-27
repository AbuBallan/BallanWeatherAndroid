package com.jonerds.ballanweather.data.model;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DayWeather extends RealmObject {

    @PrimaryKey
    private String id;

    private City city;

    private long date;

    private String sunrise;

    private String sunset;

    private int maxTemp;

    private int minTemp;

    private RealmList<HourWeather> hourly;

    public DayWeather() {
    }

    public DayWeather(String id, City city, long date, String sunrise, String sunset, int maxTemp, int minTemp, RealmList<HourWeather> hourly) {
        this.id = id;
        this.city = city;
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.hourly = hourly;
    }

    public float getPrecipiation() {
        float sum = 0;
        for (HourWeather hourWeather: hourly) {
            sum+= hourWeather.getPrecipiation();
        }
        return sum / 24;
    }

    public int getHumidity() {
        int sum = 0;
        for (HourWeather hourWeather: hourly) {
            sum+= hourWeather.getHumidity();
        }
        return sum / 24;
    }

    public int getPressure() {
        int sum = 0;
        for (HourWeather hourWeather: hourly) {
            sum += hourWeather.getPressure();
        }
        return sum / 24;
    }

    public int getUvIndex() {
        int sum = 0;
        for (HourWeather hourWeather: hourly) {
            sum+= hourWeather.getUvIndex();
        }
        return sum / 24;
    }

    public int getWindSpeed() {
        int sum = 0;
        for (HourWeather hourWeather: hourly) {
            sum+= hourWeather.getWindSpeed();
        }
        return sum / 24;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Date getDate() {
        Date date = new Date();
        date.setTime(this.date);
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public RealmList<HourWeather> getHourly() {
        return hourly;
    }

    public void setHourly(RealmList<HourWeather> hourly) {
        this.hourly = hourly;
    }
}
