package com.example.connect.Model;

public class User {
    private String id;
    private String username;
    private String bio;
    private String imageurl;
    private String fullname;

    public User(String id, String userName, String bio, String imageUrl, String fullName) {
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.imageurl = imageUrl;
        this.fullname = fullname;
    }

    public User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageUrl() {
        return imageurl;
    }

    public void setImageUrl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getFullName() {
        return fullname;
    }

    public void setFullName(String fullname) {
        this.fullname = fullname;
    }
}
