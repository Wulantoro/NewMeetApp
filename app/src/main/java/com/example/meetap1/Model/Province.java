package com.example.meetap1.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Province implements Parcelable
{

    private String state_province_name;
    public final static Creator<Province> CREATOR = new Creator<Province>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Province createFromParcel(Parcel in) {
            return new Province(in);
        }

        public Province[] newArray(int size) {
            return (new Province[size]);
        }

    }
            ;

    protected Province(Parcel in) {
        this.state_province_name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Province() {
    }

    public String getStateProvinceName() {
        return state_province_name;
    }

    public void setStateProvinceName(String stateProvinceName) {
        this.state_province_name = stateProvinceName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(state_province_name);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return this.state_province_name;
    }

}
