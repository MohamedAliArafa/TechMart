package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by samir.salah on 10/11/2017.
 */

public class AllGroupUsers {

    @SerializedName("result")
    @Expose
    public List<User> userGroup;

    @SerializedName("ISResultHasData")
    @Expose
    public int ISResultHasData;
}
