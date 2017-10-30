package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samir salah on 9/9/2017.
 */

public class CategoryGroups {

    @SerializedName("ID")
    @Expose
    public int ID;
    @SerializedName("Name")
    @Expose
    public String Name;
    @SerializedName("Icon")
    @Expose
    public String Icon;

    @SerializedName("CreationDate")
    @Expose
    public String CreationDate;

    @SerializedName("MemberCount")
    @Expose
    public int MemberCount;

    @SerializedName("IsJoinRequestPending")
    @Expose
    public boolean IsJoinRequestPending;

    public boolean isJoinRequestPending() {
        return IsJoinRequestPending;
    }

    public void setJoinRequestPending(boolean joinRequestPending) {
        IsJoinRequestPending = joinRequestPending;
    }
}
