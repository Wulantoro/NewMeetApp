package com.example.meetap1.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable
{

    private String id;
    private String name;
    private String isActive;
    private String created;
    private String updated;
    public final static Creator<Category> CREATOR = new Creator<Category>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        public Category[] newArray(int size) {
            return (new Category[size]);
        }

    }
            ;

    protected Category(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.isActive = ((String) in.readValue((String.class.getClassLoader())));
        this.created = ((String) in.readValue((String.class.getClassLoader())));
        this.updated = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Category() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(isActive);
        dest.writeValue(created);
        dest.writeValue(updated);
    }

    public int describeContents() {
        return 0;
    }
}
