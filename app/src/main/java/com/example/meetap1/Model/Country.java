package com.example.meetap1.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Country implements Parcelable {

    private String nicename;
    public final static Creator<Country> CREATOR = new Creator<Country>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        public Country[] newArray(int size) {
            return (new Country[size]);
        }

    };

    protected Country(Parcel in) {
        this.nicename = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Country() {
    }

    public String getNicename() {
        return nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(nicename);
    }

    public int describeContents() {
        return 0;
    }
}
