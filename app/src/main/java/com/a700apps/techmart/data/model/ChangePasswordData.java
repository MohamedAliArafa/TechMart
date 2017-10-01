package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khaled.badawy on 9/24/2017.
 */

public class ChangePasswordData {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("ISResultHasData")
    @Expose
    private Integer iSResultHasData;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Integer getISResultHasData() {
        return iSResultHasData;
    }

    public void setISResultHasData(Integer iSResultHasData) {
        this.iSResultHasData = iSResultHasData;
    }
}
