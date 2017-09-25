package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samir.salah on 9/7/2017.
 */

public class Category {

    @SerializedName("ID")
    @Expose
    public int ID;
    @SerializedName("Name")
    @Expose
    public String Name;
    @SerializedName("Icon")
    @Expose
    public String Icon;

}
