package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samir salah on 9/10/2017.
 */

public class TimeLine {

    @SerializedName("ID")
    @Expose
    public int ID;
    @SerializedName("GroupID")
    @Expose
    public int GroupID;

    @SerializedName("CreatedBY")
    @Expose
    public String CreatedBY;

    @SerializedName("Title")
    @Expose
    public String Title;

    @SerializedName("Descr")
    @Expose
    public String Descr;

    @SerializedName("Longtude")
    @Expose
    public int Longtude;

    @SerializedName("Latitude")
    @Expose
    public int Latitude;

    @SerializedName("StartDate")
    @Expose
    public String StartDate;

    @SerializedName("EndDate")
    @Expose
    public String EndDate;

    @SerializedName("Image")
    @Expose
    public String Image;

    @SerializedName("CreationDate")
    @Expose
    public String CreationDate;

    @SerializedName("AttendantCount")
    @Expose
    public int AttendantCount;

    @SerializedName("IsOneToOneMeeting")
    @Expose
    public boolean IsOneToOneMeeting;

    @SerializedName("IsPublic")
    @Expose
    public boolean IsPublic;

    @SerializedName("ApprovedBy")
    @Expose
    public String ApprovedBy;

    @SerializedName("ApprovedDate")
    @Expose
    public String ApprovedDate;

    @SerializedName("Status")
    @Expose
    public int Status;

    @SerializedName("LikeCount")
    @Expose
    public int LikeCount;

    @SerializedName("CommentCount")
    @Expose
    public int CommentCount;

    @SerializedName("Type")
    @Expose
    public int Type;

    @SerializedName("GroupName")
    @Expose
    public String GroupName;

    @SerializedName("PostedByName")
    @Expose
    public String PostedByName;

    @SerializedName("IsLike")
    @Expose
    public boolean IsLike;

    @SerializedName("IsGoing")
    @Expose
    public boolean IsGoing;

    @SerializedName("LocationName")
    @Expose
    public String LocationName;

    @SerializedName("StartTime")
    @Expose
    public String StartTime;

    @SerializedName("EndTime")
    @Expose
    public String EndTime;


    /////


}
