package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samir.salah on 10/9/2017.
 */

public class JoinGroupRequests {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("GroupID")
    @Expose
    private Integer groupID;
    @SerializedName("RequestedRole")
    @Expose
    private Integer requestedRole;
    @SerializedName("RequestDate")
    @Expose
    private String requestDate;
    @SerializedName("RequestDateST")
    @Expose
    private String requestDateST;
    @SerializedName("RequestStatus")
    @Expose
    private Integer requestStatus;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public Integer getRequestedRole() {
        return requestedRole;
    }

    public void setRequestedRole(Integer requestedRole) {
        this.requestedRole = requestedRole;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestDateST() {
        return requestDateST;
    }

    public void setRequestDateST(String requestDateST) {
        this.requestDateST = requestDateST;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

}
