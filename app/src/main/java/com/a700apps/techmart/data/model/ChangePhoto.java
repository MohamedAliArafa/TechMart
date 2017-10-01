package com.a700apps.techmart.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khaled.badawy on 9/25/2017.
 */


public class ChangePhoto {
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("Photo")
    @Expose
    private String photo;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


}
