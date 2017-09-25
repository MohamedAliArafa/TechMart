package com.a700apps.techmart.data.model;

import java.io.Serializable;

/**
 * Created by samir.salah on 9/17/2017.
 */

public class OneToOneModel implements Serializable{
    double Longtud;
    double latitude;
    String address;
    int GroupId;
    String createdby;
    String title;

    public double getLongtud() {
        return Longtud;
    }

    public void setLongtud(double longtud) {
        Longtud = longtud;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getOneToOnPartener() {
        return OneToOnPartener;
    }

    public void setOneToOnPartener(String oneToOnPartener) {
        OneToOnPartener = oneToOnPartener;
    }

    public boolean isOneToOneMeeting() {
        return IsOneToOneMeeting;
    }

    public void setOneToOneMeeting(boolean oneToOneMeeting) {
        IsOneToOneMeeting = oneToOneMeeting;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMediaFile() {
        return MediaFile;
    }

    public void setMediaFile(String mediaFile) {
        MediaFile = mediaFile;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public boolean isPublic() {
        return IsPublic;
    }

    public void setPublic(boolean aPublic) {
        IsPublic = aPublic;
    }

    String Descr;
    String StartDate;
    String EndDate;
    String OneToOnPartener;
    boolean IsOneToOneMeeting;
    String Image;
    String MediaFile;
    String CreationDate;
    boolean IsPublic;
}
