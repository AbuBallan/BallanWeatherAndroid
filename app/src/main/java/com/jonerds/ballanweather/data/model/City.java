package com.jonerds.ballanweather.data.model;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class City extends RealmObject  implements SearchSuggestion {

    @PrimaryKey
    private String id;

    public City() {
    }

    public City(String id) {
        this.id = id;
    }

    public City(Parcel in) {
        this.id = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getBody() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

}
