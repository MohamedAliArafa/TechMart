package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samir.salah on 9/5/2017.
 */

public class UserData {


    @SerializedName("result")
    @Expose
    public User user;

    @SerializedName("ISResultHasData")
    @Expose
    public int ISResultHasData;






}
