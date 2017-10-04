package com.a700apps.techmart.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class InnerModle implements Parcelable {

    private Double Longitude;
    private Double Latitude;
    private String address;

    public InnerModle() {
    }

    public InnerModle(Double longitude, Double latitude,String address) {

        Longitude = longitude;
        Latitude = latitude;
        address = address;

    }

    protected InnerModle(Parcel in) {
        Longitude = in.readDouble();
        Latitude = in.readDouble();
        address = in.readString();
    }

    public static final Creator<InnerModle> CREATOR = new Creator<InnerModle>() {
        @Override
        public InnerModle createFromParcel(Parcel in) {
            return new InnerModle(in);
        }

        @Override
        public InnerModle[] newArray(int size) {
            return new InnerModle[size];
        }
    };

    public Double getLongitude() {
        return Longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLongitude(Double longitude) {

        Longitude = longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(Longitude);
        parcel.writeDouble(Latitude);
    }
}
