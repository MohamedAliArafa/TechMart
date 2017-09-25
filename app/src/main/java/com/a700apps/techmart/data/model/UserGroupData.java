package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by samir salah on 9/11/2017.
 */

public class UserGroupData {

    @SerializedName("result")
    @Expose
    public List<UserGroup> userGroup;

    @SerializedName("ISResultHasData")
    @Expose
    public int ISResultHasData;
}