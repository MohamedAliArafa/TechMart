package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by samir salah on 9/9/2017.
 */

public class CategoryGroupsData {

    @SerializedName("result")
    @Expose
    public List<CategoryGroups> CategoryGroups;

    @SerializedName("ISResultHasData")
    @Expose
    public int ISResultHasData;
}
