package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samir salah on 9/13/2017.
 */

public class UserLike {


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


}
