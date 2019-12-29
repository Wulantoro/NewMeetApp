package com.example.meetap1.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Coment implements Parcelable
{

    private String id;
    private String ticket_id;
    private String user_id;
    private String content;
    private String created;
    private String updated;
    public final static Parcelable.Creator<Coment> CREATOR = new Creator<Coment>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Coment createFromParcel(Parcel in) {
            return new Coment(in);
        }

        public Coment[] newArray(int size) {
            return (new Coment[size]);
        }

    }
            ;

    protected Coment(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.ticket_id = ((String) in.readValue((String.class.getClassLoader())));
        this.user_id = ((String) in.readValue((String.class.getClassLoader())));
        this.content = ((String) in.readValue((String.class.getClassLoader())));
        this.created = ((String) in.readValue((String.class.getClassLoader())));
        this.updated = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Coment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticket_id;
    }

    public void setTicketId(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        dest.writeValue(ticket_id);
        dest.writeValue(user_id);
        dest.writeValue(content);
        dest.writeValue(created);
        dest.writeValue(updated);
    }

    public int describeContents() {
        return 0;
    }

}