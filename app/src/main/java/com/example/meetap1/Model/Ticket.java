package com.example.meetap1.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ticket implements Parcelable
{

    private String id;
    private String user_id;
    private String title;
    private String content;
    private String category_id;
    private String images;
    private String status;
    private String is_active;
    private String created;
    public final static Parcelable.Creator<Ticket> CREATOR = new Creator<Ticket>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        public Ticket[] newArray(int size) {
            return (new Ticket[size]);
        }

    }
            ;

    protected Ticket(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.user_id = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.content = ((String) in.readValue((String.class.getClassLoader())));
        this.category_id = ((String) in.readValue((String.class.getClassLoader())));
        this.images = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.is_active = ((String) in.readValue((String.class.getClassLoader())));
        this.created = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Ticket() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(user_id);
        dest.writeValue(title);
        dest.writeValue(content);
        dest.writeValue(category_id);
        dest.writeValue(images);
        dest.writeValue(status);
        dest.writeValue(is_active);
        dest.writeValue(created);
    }

    public int describeContents() {
        return 0;
    }

}
