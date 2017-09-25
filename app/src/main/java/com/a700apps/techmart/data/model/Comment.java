package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samir salah on 9/13/2017.
 */

public class Comment {

    @SerializedName("ID")
    @Expose
    public int ID;

    @SerializedName("PostID")
    @Expose
    public int PostID;

    @SerializedName("UserID")
    @Expose
    public String UserID;

    @SerializedName("Comment")
    @Expose
    public String Comment;

    @SerializedName("UserName")
    @Expose
    public String UserName;

    @SerializedName("UserImage")
    @Expose
    public String UserImage;
    @SerializedName("CreationDate")
    @Expose
    public String CreationDate;
}
