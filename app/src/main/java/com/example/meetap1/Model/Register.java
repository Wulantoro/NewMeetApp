package com.example.meetap1.Model;


import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Register implements Parcelable
{

    private String email;

    public final static Parcelable.Creator<Register> CREATOR = new Creator<Register>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Register createFromParcel(Parcel in) {
            return new Register(in);
        }

        public Register[] newArray(int size) {
            return (new Register[size]);
        }

    }
            ;

    protected Register(Parcel in) {
        this.email = ((String) in.readValue((String.class.getClassLoader())));

    }

    public Register() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(email);

    }

    public int describeContents() {
        return 0;
    }

}
