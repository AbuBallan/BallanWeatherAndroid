package com.jonerds.ballanweather.data.api.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jonerds.ballanweather.data.model.HourWeather;
import com.jonerds.ballanweather.data.model.WeatherResponse;
import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.data.model.DayWeather;
import com.jonerds.ballanweather.utils.CommonUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;

// for simplification
public class WeatherApiDeserializer implements JsonDeserializer<WeatherResponse> {
    @Override
    public WeatherResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject dataJsonObject = json.getAsJsonObject().getAsJsonObject("data");

        String cityName = dataJsonObject.getAsJsonArray("request").get(0).getAsJsonObject().get("query").getAsString();

        JsonObject currentConditionJsonObject = dataJsonObject.getAsJsonArray("current_condition").get(0).getAsJsonObject();

        HourWeather currentCondition = new HourWeather(
                CommonUtils.getHourAsInt(currentConditionJsonObject.get("observation_time").getAsString()),
                Integer.parseInt(currentConditionJsonObject.get("temp_C").getAsString()),
                currentConditionJsonObject.get("weatherDesc").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString(),
                Integer.parseInt(currentConditionJsonObject.get("windspeedKmph").getAsString()),
                Float.parseFloat(currentConditionJsonObject.get("precipMM").getAsString()),
                Integer.parseInt(currentConditionJsonObject.get("humidity").getAsString()),
                Integer.parseInt(currentConditionJsonObject.get("pressure").getAsString()),
                Integer.parseInt(currentConditionJsonObject.get("cloudcover").getAsString()),
                Integer.parseInt(currentConditionJsonObject.get("FeelsLikeC").getAsString()),
                Integer.parseInt(currentConditionJsonObject.get("uvIndex").getAsString())
        );

        JsonArray weatherJsonArray = dataJsonObject.getAsJsonArray("weather");
        List<DayWeather> dayWeatherList = new ArrayList<>(8);
        for (JsonElement jsonElement : weatherJsonArray) {
            JsonObject weatherJsonObject = jsonElement.getAsJsonObject();

            long date = CommonUtils.getDateAsLong(weatherJsonObject.get("date").getAsString());

            JsonArray hourlyJsonArray = weatherJsonObject.getAsJsonArray("hourly");
            RealmList<HourWeather> hourWeatherList = new RealmList<>();
            for (JsonElement hourJsonElement : hourlyJsonArray) {
                JsonObject hourJsonObject = hourJsonElement.getAsJsonObject();

                int time = Integer.parseInt(hourJsonObject.get("time").getAsString());

                hourWeatherList.add(new HourWeather(
                        cityName + "_" + date + "_" + time,
                        time,
                        Integer.parseInt(hourJsonObject.get("tempC").getAsString()),
                        hourJsonObject.get("weatherDesc").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString(),
                        Integer.parseInt(hourJsonObject.get("windspeedKmph").getAsString()),
                        Float.parseFloat(hourJsonObject.get("chanceofrain").getAsString()),
                        Integer.parseInt(hourJsonObject.get("humidity").getAsString()),
                        Integer.parseInt(hourJsonObject.get("pressure").getAsString()),
                        Integer.parseInt(hourJsonObject.get("cloudcover").getAsString()),
                        Integer.parseInt(hourJsonObject.get("FeelsLikeC").getAsString()),
                        Integer.parseInt(hourJsonObject.get("uvIndex").getAsString())
                ));
            }

            dayWeatherList.add(new DayWeather(
                    cityName + "_" + date,
                    null,
                    date,
                    weatherJsonObject.getAsJsonArray("astronomy").get(0).getAsJsonObject().get("sunrise").getAsString(),
                    weatherJsonObject.getAsJsonArray("astronomy").get(0).getAsJsonObject().get("sunset").getAsString(),
                    Integer.parseInt(weatherJsonObject.get("maxtempC").getAsString()),
                    Integer.parseInt(weatherJsonObject.get("mintempC").getAsString()),
                    hourWeatherList
                    ));
        }

        City city = new City(cityName);
        for (DayWeather dayWeather : dayWeatherList)
            dayWeather.setCity(city);
        return new WeatherResponse(dayWeatherList, currentCondition);
    }
}
