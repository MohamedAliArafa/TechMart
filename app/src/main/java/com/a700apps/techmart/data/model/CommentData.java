package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by samir salah on 9/13/2017.
 */

public class CommentData {


    @SerializedName("result")
    @Expose
    public List<Comment> comment;

    @SerializedName("ISResultHasData")
    @Expose
    public int ISResultHasData;
}
