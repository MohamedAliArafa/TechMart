package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khaled.badawy on 9/25/2017.
 */

public class ChangePhotoData {

    @SerializedName("result")
    @Expose
    private ResultSetting result;
    @SerializedName("ISResultHasData")
    @Expose
    private Integer iSResultHasData;

    public ResultSetting getResult() {
        return result;
    }

    public void setResult(ResultSetting result) {
        this.result = result;
    }

    public Integer getISResultHasData() {
        return iSResultHasData;
    }

    public void setISResultHasData(Integer iSResultHasData) {
        this.iSResultHasData = iSResultHasData;
    }

}
