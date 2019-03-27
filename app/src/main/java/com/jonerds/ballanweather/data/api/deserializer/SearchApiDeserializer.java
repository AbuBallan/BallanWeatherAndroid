package com.jonerds.ballanweather.data.api.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jonerds.ballanweather.data.model.SearchResponse;
import com.jonerds.ballanweather.data.model.City;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// for simplification
public class SearchApiDeserializer implements JsonDeserializer<SearchResponse> {
    @Override
    public SearchResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonArray jsonArray = json.getAsJsonObject().getAsJsonObject("search_api").getAsJsonArray("result");

        List<City> cityList = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray){
            JsonObject result = jsonElement.getAsJsonObject();

            cityList.add(new City(result.getAsJsonArray("areaName").get(0).getAsJsonObject().get("value").getAsString()
                    + ", " + result.getAsJsonArray("country").get(0).getAsJsonObject().get("value").getAsString()));
        }
        return new SearchResponse(cityList);
    }
}
