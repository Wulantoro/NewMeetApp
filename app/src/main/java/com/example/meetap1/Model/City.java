package com.example.meetap1.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable
{

//    private String cityName;
    private String city_name;
    public final static Creator<City> CREATOR = new Creator<City>() {


        @SuppressWarnings({
                "unchecked"
        })
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        public City[] newArray(int size) {
            return (new City[size]);
        }

    }
            ;

    protected City(Parcel in) {
        this.city_name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public City() {
    }

    public String getCityName() {
        return city_name;
    }

    public void setCityName(String cityName) {
        this.city_name = cityName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(city_name);
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return this.city_name;
    }

}