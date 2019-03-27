package com.jonerds.ballanweather.data.model;

import com.google.gson.annotations.JsonAdapter;
import com.jonerds.ballanweather.data.api.deserializer.SearchApiDeserializer;
import com.jonerds.ballanweather.data.model.City;

import java.util.List;

@JsonAdapter(SearchApiDeserializer.class)
public class SearchResponse {

    private List<City> results;

    public SearchResponse(List<City> results) {
        this.results = results;
    }

    public List<City> getResults() {
        return results;
    }

    public void setResults(List<City> results) {
        this.results = results;
    }
}
