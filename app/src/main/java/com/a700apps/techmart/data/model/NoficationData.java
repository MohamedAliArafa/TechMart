package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by khaled.badawy on 9/27/2017.
 */

public class NoficationData {
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


    public class Result implements Serializable{

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("TypeID")
        @Expose
        private Integer typeID;
        @SerializedName("TypeName")
        @Expose
        private String typeName;
        @SerializedName("Icon")
        @Expose
        private Object icon;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("UserID")
        @Expose
        private String userID;
        @SerializedName("UserName")
        @Expose
        private String userName;
        @SerializedName("IsRead")
        @Expose
        private Boolean isRead;
        @SerializedName("IsDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("RelativeUserID")
        @Expose
        private String relativeUserID;
        @SerializedName("RelativeUserName")
        @Expose
        private String relativeUserName;
        @SerializedName("CreatedDate")
        @Expose
        private String createdDate;
        @SerializedName("ItemID")
        @Expose
        private Integer itemID;
        @SerializedName("ItemName")
        @Expose
        private String itemName;
        @SerializedName("GroupID")
        @Expose
        private Integer groupID;
        @SerializedName("GroupName")
        @Expose
        private String groupName;
        @SerializedName("Image")
        @Expose
        private String image;
        @SerializedName("descr")
        @Expose
        private String descr;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public Integer getTypeID() {
            return typeID;
        }

        public void setTypeID(Integer typeID) {
            this.typeID = typeID;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
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

        public Boolean getIsRead() {
            return isRead;
        }

        public void setIsRead(Boolean isRead) {
            this.isRead = isRead;
        }

        public Boolean getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getRelativeUserID() {
            return relativeUserID;
        }

        public void setRelativeUserID(String relativeUserID) {
            this.relativeUserID = relativeUserID;
        }

        public String getRelativeUserName() {
            return relativeUserName;
        }

        public void setRelativeUserName(String relativeUserName) {
            this.relativeUserName = relativeUserName;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public Integer getItemID() {
            return itemID;
        }

        public void setItemID(Integer itemID) {
            this.itemID = itemID;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public Integer getGroupID() {
            return groupID;
        }

        public void setGroupID(Integer groupID) {
            this.groupID = groupID;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

    }
}
