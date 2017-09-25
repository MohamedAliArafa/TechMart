package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samir salah on 9/13/2017.
 */

public class MyProfile {


    @SerializedName("ID")
    @Expose
    public String ID;

    @SerializedName("Password")
    @Expose
    public String Password;

    @SerializedName("LinkedInID")
    @Expose
    public String LinkedInID;

    @SerializedName("PostsCount")
    @Expose
    public int PostsCount;

    @SerializedName("FollowersCount")
    @Expose
    public int FollowersCount;

    @SerializedName("FriendsCount")
    @Expose
    public int FriendsCount;

    @SerializedName("RegisterDate")
    @Expose
    public String RegisterDate;

    @SerializedName("GroupCount")
    @Expose
    public int GroupCount;

    @SerializedName("EventCount")
    @Expose
    public int EventCount;

    @SerializedName("IsFollowed")
    @Expose
    public boolean IsFollowed;

    @SerializedName("IsConntected")
    @Expose
    public boolean IsConntected;

    @SerializedName("UserID")
    @Expose
    public String UserID;

    @SerializedName("Name")
    @Expose
    public String Name;
    @SerializedName("Email")
    @Expose
    public String Email;

    @SerializedName("Photo")
    @Expose
    public String Photo;

    @SerializedName("Company")
    @Expose
    public String Company;

    @SerializedName("Position")
    @Expose
    public String Position;

    @SerializedName("Phone")
    @Expose
    public String Phone;


}

