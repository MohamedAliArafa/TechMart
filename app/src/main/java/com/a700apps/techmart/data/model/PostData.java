package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samir salah on 9/13/2017.
 */

public class PostData {

    @SerializedName("result")
    @Expose
    public post postData;

    @SerializedName("ISResultHasData")
    @Expose
    public int ISResultHasData;
}
