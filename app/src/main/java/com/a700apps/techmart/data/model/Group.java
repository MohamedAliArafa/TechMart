package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by khaled.badawy on 10/29/2017.
 */

public class Group {
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


    public class Result{
        @SerializedName("UserID")
        @Expose
        private String userID;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Photo")
        @Expose
        private String photo;
        @SerializedName("Company")
        @Expose
        private String company;
        @SerializedName("Position")
        @Expose
        private String position;


        @SerializedName("RoleInGroup")
        @Expose
        private int RoleInGroup;


        @SerializedName("Phone")
        @Expose
        private Object phone;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getRoleInGroup() {
            return RoleInGroup;
        }

        public void setRoleInGroup(int roleInGroup) {
            RoleInGroup = roleInGroup;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

    }
}
