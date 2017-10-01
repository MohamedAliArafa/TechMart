package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by khaled.badawy on 9/26/2017.
 */

public class PredifinedData {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("ISResultHasData")
    @Expose
    private Integer iSResultHasData;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getISResultHasData() {
        return iSResultHasData;
    }

    public void setISResultHasData(Integer iSResultHasData) {
        this.iSResultHasData = iSResultHasData;
    }

    public class Result {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("Text")
        @Expose
        private String text;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }

}
