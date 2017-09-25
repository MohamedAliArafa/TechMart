package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samir salah on 9/13/2017.
 */

public class MyProfileData {


    @SerializedName("result")
    @Expose
    public MyProfile user;

    @SerializedName("ISResultHasData")
    @Expose
    public int ISResultHasData;
}
