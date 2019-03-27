package com.jonerds.ballanweather.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class HourWeather extends RealmObject {

    @PrimaryKey
    private String id;

    private int time;

    private int temp;

    private String desc;

    private int windSpeed;

    private float precipiation;

    private int humidity;

    private int pressure;

    private int cloudCover;

    private int feelsLike;

    private int uvIndex;

    public HourWeather() {
    }


    public HourWeather(int time, int temp, String desc, int windSpeed, float precipiation, int humidity, int pressure, int cloudCover, int feelsLike, int uvIndex) {
        this.time = time;
        this.temp = temp;
        this.desc = desc;
        this.windSpeed = windSpeed;
        this.precipiation = precipiation;
        this.humidity = humidity;
        this.pressure = pressure;
        this.cloudCover = cloudCover;
        this.feelsLike = feelsLike;
        this.uvIndex = uvIndex;
    }

    public HourWeather(String id, int time, int temp, String desc, int windSpeed, float precipiation, int humidity, int pressure, int cloudCover, int feelsLike, int uvIndex) {
        this.id = id;
        this.time = time;
        this.temp = temp;
        this.desc = desc;
        this.windSpeed = windSpeed;
        this.precipiation = precipiation;
        this.humidity = humidity;
        this.pressure = pressure;
        this.cloudCover = cloudCover;
        this.feelsLike = feelsLike;
        this.uvIndex = uvIndex;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getPrecipiation() {
        return precipiation;
    }

    public void setPrecipiation(float precipiation) {
        this.precipiation = precipiation;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }

    public int getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(int feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
    }
}
