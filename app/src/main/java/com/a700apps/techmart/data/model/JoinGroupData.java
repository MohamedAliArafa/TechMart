package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by samir salah on 9/10/2017.
 */

public class JoinGroupData {

    @SerializedName("result")
    @Expose
    public JoinGroup result;

    @SerializedName("ISResultHasData")
    @Expose
    public int ISResultHasData;

}
