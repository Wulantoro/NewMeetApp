package com.example.meetap1.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserId implements Parcelable {

    private String id;
    private String username;
    private String isActive;
    private String isAuthenticate;
    private String email;
    private String fullname;
    private String gender;
    private String district;
    private String province;
    private String city;
    private String country;
    private String birthdate;
    private String college;
    private String company;
    private String merriege;
    private String photo;
    private String handphoneNumber;
    private String created;
    private String update;
    private String status;
    private String photo_file;

    public final static Parcelable.Creator<UserId> CREATOR = new Creator<UserId>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserId createFromParcel(Parcel in) {
            return new UserId(in);
        }

        public UserId[] newArray(int size) {
            return (new UserId[size]);
        }

    };

    protected UserId(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.isActive = ((String) in.readValue((String.class.getClassLoader())));
        this.isAuthenticate = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.fullname = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((String) in.readValue((String.class.getClassLoader())));
        this.district = ((String) in.readValue((String.class.getClassLoader())));
        this.province = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.birthdate = ((String) in.readValue((String.class.getClassLoader())));
        this.college = ((String) in.readValue((String.class.getClassLoader())));
        this.company = ((String) in.readValue((String.class.getClassLoader())));
        this.merriege = ((String) in.readValue((String.class.getClassLoader())));
        this.photo = ((String) in.readValue((String.class.getClassLoader())));
        this.handphoneNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.created = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.update = ((String) in.readValue((String.class.getClassLoader())));
        this.photo_file = ((String) in.readValue((String.class.getClassLoader())));
    }

    public UserId() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsAuthenticate() {
        return isAuthenticate;
    }

    public void setIsAuthenticate(String isAuthenticate) {
        this.isAuthenticate = isAuthenticate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMerriege() {
        return merriege;
    }

    public void setMerriege(String merriege) {
        this.merriege = merriege;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getHandphoneNumber() {
        return handphoneNumber;
    }

    public void setHandphoneNumber(String handphoneNumber) {
        this.handphoneNumber = handphoneNumber;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoto_file() {
        return photo_file;
    }

    public void setPhoto_file(String photo_file) {
        this.photo_file = photo_file;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(username);
        dest.writeValue(isActive);
        dest.writeValue(isAuthenticate);
        dest.writeValue(email);
        dest.writeValue(fullname);
        dest.writeValue(gender);
        dest.writeValue(district);
        dest.writeValue(province);
        dest.writeValue(city);
        dest.writeValue(country);
        dest.writeValue(birthdate);
        dest.writeValue(college);
        dest.writeValue(company);
        dest.writeValue(merriege);
        dest.writeValue(photo);
        dest.writeValue(handphoneNumber);
        dest.writeValue(created);
        dest.writeValue(status);
        dest.writeValue(update);
        dest.writeValue(photo_file);
    }

    public int describeContents() {
        return 0;
    }
}
