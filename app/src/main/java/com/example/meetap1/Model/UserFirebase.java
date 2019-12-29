package com.example.meetap1.Model;

public class UserFirebase {
    private String id;
    private String username;
    private String email;
    private String imageURL;
    private String password;
    private String Status;

    public UserFirebase() {

    }

    public UserFirebase(String id, String username, String email, String imageURL, String password, String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.imageURL = imageURL;
        this.password = password;
        Status = status;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
