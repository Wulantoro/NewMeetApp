package com.example.meetap1.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class NewTicket implements Parcelable
{

    private String username;
    private String id;
    private String user_id;
    private String title;
    private String content;
    private String category_id;
    private String status;
    private String isActive;
    private String created;
    private String images_file;
    private String jumlah_komentar;
    private String photo_profile;
    public final static Parcelable.Creator<NewTicket> CREATOR = new Creator<NewTicket>() {


        @SuppressWarnings({
                "unchecked"
        })
        public NewTicket createFromParcel(Parcel in) {
            return new NewTicket(in);
        }

        public NewTicket[] newArray(int size) {
            return (new NewTicket[size]);
        }

    }
            ;

    protected NewTicket(Parcel in) {
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.user_id = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.content = ((String) in.readValue((String.class.getClassLoader())));
        this.category_id = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.isActive = ((String) in.readValue((String.class.getClassLoader())));
        this.created = ((String) in.readValue((String.class.getClassLoader())));
        this.images_file = ((String) in.readValue((String.class.getClassLoader())));
        this.jumlah_komentar = ((String) in.readValue((String.class.getClassLoader())));
        this.photo_profile = ((String) in.readValue((String.class.getClassLoader())));
    }

    public NewTicket() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
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

    public String getCategoryId() {
        return category_id;
    }

    public void setCategoryId(String category_id) {
        this.category_id = category_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getImagesFile() {
        return images_file;
    }

    public void setImagesFile(String images_file) {
        this.images_file = images_file;
    }

    public String getJumlahKomentar() {
        return jumlah_komentar;
    }

    public void setJumlahKomentar(String jumlah_komentar) {
        this.jumlah_komentar = jumlah_komentar;
    }

    public String getPhotoProfile() {
        return photo_profile;
    }

    public void setPhotoProfile(String photo_profile) {
        this.photo_profile = photo_profile;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(username);
        dest.writeValue(id);
        dest.writeValue(user_id);
        dest.writeValue(title);
        dest.writeValue(content);
        dest.writeValue(category_id);
        dest.writeValue(status);
        dest.writeValue(isActive);
        dest.writeValue(created);
        dest.writeValue(images_file);
        dest.writeValue(jumlah_komentar);
        dest.writeValue(photo_profile);
    }

    public int describeContents() {
        return 0;
    }

}