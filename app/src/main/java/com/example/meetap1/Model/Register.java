package com.example.meetap1.Model;


import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Register implements Parcelable
{

    private String email;
    private String password;
    private String passwordBaru;
    private String passwordKonf;


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
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.passwordBaru = ((String) in.readValue((String.class.getClassLoader())));
        this.passwordKonf = ((String) in.readValue((String.class.getClassLoader())));

    }

    public Register() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordBaru() {
        return passwordBaru;
    }

    public void setPasswordBaru(String passwordBaru) {
        this.passwordBaru = passwordBaru;
    }

    public String getPasswordKonf() {
        return passwordKonf;
    }

    public void setPasswordKonf(String passwordKonf) {
        this.passwordKonf = passwordKonf;
    }



    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(email);
        dest.writeValue(password);
        dest.writeValue(passwordBaru);
        dest.writeValue(passwordKonf);

    }

    public int describeContents() {
        return 0;
    }

}
