package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khaled.badawy on 9/24/2017.
 */

public class ChangePassword {

    @SerializedName("OldPassword")
    @Expose
    private String oldPassword;
    @SerializedName("NewPassword")
    @Expose
    private String newPassword;
    @SerializedName("UserID")
    @Expose
    private String userID;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


}
