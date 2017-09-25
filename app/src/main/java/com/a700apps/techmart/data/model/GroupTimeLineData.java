package com.a700apps.techmart.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by samir salah on 9/10/2017.
 */

public class GroupTimeLineData {
//
//    @SerializedName("result")
//    @Expose
//    public List<GroupTimeLine> timeline;
//
//    @SerializedName("ISResultHasData")
//    @Expose
//    public int ISResultHasData;

    /**
     * result : [{"ID":5,"GroupID":3,"CreatedBY":"de6b2319-0880-463c-8f97-2547e8164a64","Title":"second post ","Descr":"hdhbd jdhhdh jdhhd","Longtude":0,"Latitude":0,"StartDate":"null","EndDate":"null","Image":"/UploadedImages/201709073162640961.JPG","MediaFile":"null","CreationDate":"2017-09-07T05:16:03","AttendantCount":0,"IsOneToOneMeeting":false,"IsPublic":true,"ApprovedBy":"null","ApprovedDate":"null","Status":1,"LikeCount":-1,"CommentCount":5,"Type":2,"GroupName":"group5","PostedByName":"user one","IsLike":false,"IsGoing":false,"LocationName":"","StartTime":"00:00:00","EndTime":"00:00:00"}]
     * ISResultHasData : 1
     */

    private int ISResultHasData;
    private List<GroupTimeLineData.ResultEntity> result;

    public void setISResultHasData(int ISResultHasData) {
        this.ISResultHasData = ISResultHasData;
    }

    public void setResult(List<GroupTimeLineData.ResultEntity> result) {
        this.result = result;
    }

    public int getISResultHasData() {
        return ISResultHasData;
    }

    public List<GroupTimeLineData.ResultEntity> getResult() {
        return result;
    }

    public static class ResultEntity implements Parcelable{
        /**
         * ID : 5
         * GroupID : 3
         * CreatedBY : de6b2319-0880-463c-8f97-2547e8164a64
         * Title : second post
         * Descr : hdhbd jdhhdh jdhhd
         * Longtude : 0
         * Latitude : 0
         * StartDate : null
         * EndDate : null
         * Image : /UploadedImages/201709073162640961.JPG
         * MediaFile : null
         * CreationDate : 2017-09-07T05:16:03
         * AttendantCount : 0
         * IsOneToOneMeeting : false
         * IsPublic : true
         * ApprovedBy : null
         * ApprovedDate : null
         * Status : 1
         * LikeCount : -1
         * CommentCount : 5
         * Type : 2
         * GroupName : group5
         * PostedByName : user one
         * IsLike : false
         * IsGoing : false
         * LocationName :
         * StartTime : 00:00:00
         * EndTime : 00:00:00
         */

        private int ID;
        private int GroupID;
        private String CreatedBY;
        private String Title;
        private String Descr;
        private double Longtude;
        private double Latitude;
        private String StartDate;
        private String EndDate;
        private String Image;
        private String MediaFile;
        private String CreationDate;
        private int AttendantCount;
        private boolean IsOneToOneMeeting;
        private boolean IsPublic;
        private String ApprovedBy;
        private String ApprovedDate;
        private int Status;
        private int LikeCount;
        private int CommentCount;
        private int Type;
        private String GroupName;
        private String PostedByName;
        private boolean IsLike;
        private boolean IsGoing;
        private String LocationName;
        private String StartTime;
        private String EndTime;

        protected ResultEntity(Parcel in) {
            ID = in.readInt();
            GroupID = in.readInt();
            CreatedBY = in.readString();
            Title = in.readString();
            Descr = in.readString();
            Longtude = in.readDouble();
            Latitude = in.readDouble();
            StartDate = in.readString();
            EndDate = in.readString();
            Image = in.readString();
            MediaFile = in.readString();
            CreationDate = in.readString();
            AttendantCount = in.readInt();
            IsOneToOneMeeting = in.readByte() != 0;
            IsPublic = in.readByte() != 0;
            ApprovedBy = in.readString();
            ApprovedDate = in.readString();
            Status = in.readInt();
            LikeCount = in.readInt();
            CommentCount = in.readInt();
            Type = in.readInt();
            GroupName = in.readString();
            PostedByName = in.readString();
            IsLike = in.readByte() != 0;
            IsGoing = in.readByte() != 0;
            LocationName = in.readString();
            StartTime = in.readString();
            EndTime = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ID);
            dest.writeInt(GroupID);
            dest.writeString(CreatedBY);
            dest.writeString(Title);
            dest.writeString(Descr);
            dest.writeDouble(Longtude);
            dest.writeDouble(Latitude);
            dest.writeString(StartDate);
            dest.writeString(EndDate);
            dest.writeString(Image);
            dest.writeString(MediaFile);
            dest.writeString(CreationDate);
            dest.writeInt(AttendantCount);
            dest.writeByte((byte) (IsOneToOneMeeting ? 1 : 0));
            dest.writeByte((byte) (IsPublic ? 1 : 0));
            dest.writeString(ApprovedBy);
            dest.writeString(ApprovedDate);
            dest.writeInt(Status);
            dest.writeInt(LikeCount);
            dest.writeInt(CommentCount);
            dest.writeInt(Type);
            dest.writeString(GroupName);
            dest.writeString(PostedByName);
            dest.writeByte((byte) (IsLike ? 1 : 0));
            dest.writeByte((byte) (IsGoing ? 1 : 0));
            dest.writeString(LocationName);
            dest.writeString(StartTime);
            dest.writeString(EndTime);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ResultEntity> CREATOR = new Creator<ResultEntity>() {
            @Override
            public ResultEntity createFromParcel(Parcel in) {
                return new ResultEntity(in);
            }

            @Override
            public ResultEntity[] newArray(int size) {
                return new ResultEntity[size];
            }
        };

        public void setID(int ID) {
            this.ID = ID;
        }

        public void setGroupID(int GroupID) {
            this.GroupID = GroupID;
        }

        public void setCreatedBY(String CreatedBY) {
            this.CreatedBY = CreatedBY;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public void setDescr(String Descr) {
            this.Descr = Descr;
        }

        public void setLongtude(int Longtude) {
            this.Longtude = Longtude;
        }

        public void setLatitude(int Latitude) {
            this.Latitude = Latitude;
        }

        public void setStartDate(String StartDate) {
            this.StartDate = StartDate;
        }

        public void setEndDate(String EndDate) {
            this.EndDate = EndDate;
        }

        public void setImage(String Image) {
            this.Image = Image;
        }

        public void setMediaFile(String MediaFile) {
            this.MediaFile = MediaFile;
        }

        public void setCreationDate(String CreationDate) {
            this.CreationDate = CreationDate;
        }

        public void setAttendantCount(int AttendantCount) {
            this.AttendantCount = AttendantCount;
        }

        public void setIsOneToOneMeeting(boolean IsOneToOneMeeting) {
            this.IsOneToOneMeeting = IsOneToOneMeeting;
        }

        public void setIsPublic(boolean IsPublic) {
            this.IsPublic = IsPublic;
        }

        public void setApprovedBy(String ApprovedBy) {
            this.ApprovedBy = ApprovedBy;
        }

        public void setApprovedDate(String ApprovedDate) {
            this.ApprovedDate = ApprovedDate;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public void setLikeCount(int LikeCount) {
            this.LikeCount = LikeCount;
        }

        public void setCommentCount(int CommentCount) {
            this.CommentCount = CommentCount;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public void setGroupName(String GroupName) {
            this.GroupName = GroupName;
        }

        public void setPostedByName(String PostedByName) {
            this.PostedByName = PostedByName;
        }

        public void setIsLike(boolean IsLike) {
            this.IsLike = IsLike;
        }

        public void setIsGoing(boolean IsGoing) {
            this.IsGoing = IsGoing;
        }

        public void setLocationName(String LocationName) {
            this.LocationName = LocationName;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }

        public int getID() {
            return ID;
        }

        public int getGroupID() {
            return GroupID;
        }

        public String getCreatedBY() {
            return CreatedBY;
        }

        public String getTitle() {
            return Title;
        }

        public String getDescr() {
            return Descr;
        }

        public double getLongtude() {
            return Longtude;
        }

        public double getLatitude() {
            return Latitude;
        }

        public String getStartDate() {
            return StartDate;
        }

        public String getEndDate() {
            return EndDate;
        }

        public String getImage() {
            return Image;
        }

        public String getMediaFile() {
            return MediaFile;
        }

        public String getCreationDate() {
            return CreationDate;
        }

        public int getAttendantCount() {
            return AttendantCount;
        }

        public boolean getIsOneToOneMeeting() {
            return IsOneToOneMeeting;
        }

        public boolean getIsPublic() {
            return IsPublic;
        }

        public String getApprovedBy() {
            return ApprovedBy;
        }

        public String getApprovedDate() {
            return ApprovedDate;
        }

        public int getStatus() {
            return Status;
        }

        public int getLikeCount() {
            return LikeCount;
        }

        public int getCommentCount() {
            return CommentCount;
        }

        public int getType() {
            return Type;
        }

        public String getGroupName() {
            return GroupName;
        }

        public String getPostedByName() {
            return PostedByName;
        }

        public boolean getIsLike() {
            return IsLike;
        }

        public boolean getIsGoing() {
            return IsGoing;
        }

        public String getLocationName() {
            return LocationName;
        }

        public String getStartTime() {
            return StartTime;
        }

        public String getEndTime() {
            return EndTime;
        }
    }

}
