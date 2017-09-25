package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by samir salah on 9/12/2017.
 */

public class LikeData {

    @SerializedName("result")
    @Expose
    public Like likeData;

    @SerializedName("ISResultHasData")
    @Expose
    public int ISResultHasData;
}
