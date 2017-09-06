package com.ramesh.weatherapp;

/**
 * Created by Ramesh Kumar on 8/31/17.
 */

public class User {

    private String fullname;
    private String email;
    private String FBId;
    private String imageURL;
    private String fbAccessToken;

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFBId(String FBId) {
        this.FBId = FBId;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setFbAccessToken(String fbAccessToken) {
        this.fbAccessToken = fbAccessToken;
    }

    public String getFbAccessToken() {
        return fbAccessToken;
    }

    public String getFullname() {
        return fullname;
    }
}
