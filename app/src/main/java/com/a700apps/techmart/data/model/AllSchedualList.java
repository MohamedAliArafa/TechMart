package com.a700apps.techmart.data.model;

import java.util.List;

/**
 * Created by halima.reda on 9/17/2017.
 */

public class AllSchedualList {

    /**
     * result : [{"ID":4,"GroupID":1,"CreatedBY":"6a64a68e-a4db-4721-85f1-0469d0070067","Title":"titlelllllll","Descr":"Descr","Longtude":12,"Latitude":15,"StartDate":"2017-09-10T00:00:00","StartDateST":"2017-09-10T00:00:00","EndDate":"2017-10-01T00:00:00","EndDateST":"2017-10-01T00:00:00","Image":"/UploadedImages/Image.jpg","MediaFile":"werqwerqwer.jpg","CreationDate":"2017-08-30T13:49:24.833","CreationDateST":"2017-08-30T13:49:24","AttendantCount":7,"IsOneToOneMeeting":true,"IsPublic":true,"ApprovedBy":"null","ApprovedDate":"null","ApprovedDateST":"","Status":1,"LocationName":"null","StartTime":"null","EndTime":"null"}]
     * ISResultHasData : 1
     */

    private int ISResultHasData;
    private List<ResultEntity> result;

    public void setISResultHasData(int ISResultHasData) {
        this.ISResultHasData = ISResultHasData;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public int getISResultHasData() {
        return ISResultHasData;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public static class ResultEntity {
        /**
         * ID : 4
         * GroupID : 1
         * CreatedBY : 6a64a68e-a4db-4721-85f1-0469d0070067
         * Title : titlelllllll
         * Descr : Descr
         * Longtude : 12
         * Latitude : 15
         * StartDate : 2017-09-10T00:00:00
         * StartDateST : 2017-09-10T00:00:00
         * EndDate : 2017-10-01T00:00:00
         * EndDateST : 2017-10-01T00:00:00
         * Image : /UploadedImages/Image.jpg
         * MediaFile : werqwerqwer.jpg
         * CreationDate : 2017-08-30T13:49:24.833
         * CreationDateST : 2017-08-30T13:49:24
         * AttendantCount : 7
         * IsOneToOneMeeting : true
         * IsPublic : true
         * ApprovedBy : null
         * ApprovedDate : null
         * ApprovedDateST :
         * Status : 1
         * LocationName : null
         * StartTime : null
         * EndTime : null
         */

        private int ID;
        private int GroupID;
        private String CreatedBY;
        private String Title;
        private String Descr;
        private double Longtude;
        private double Latitude;
        private String StartDate;
        private String StartDateST;
        private String EndDate;
        private String EndDateST;
        private String Image;
        private String MediaFile;
        private String CreationDate;
        private String CreationDateST;
        private int AttendantCount;
        private boolean IsOneToOneMeeting;
        private boolean IsPublic;
        private String ApprovedBy;
        private String ApprovedDate;
        private String ApprovedDateST;
        private int Status;
        private String LocationName;
        private String StartTime;
        private String EndTime;

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

        public void setStartDateST(String StartDateST) {
            this.StartDateST = StartDateST;
        }

        public void setEndDate(String EndDate) {
            this.EndDate = EndDate;
        }

        public void setEndDateST(String EndDateST) {
            this.EndDateST = EndDateST;
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

        public void setCreationDateST(String CreationDateST) {
            this.CreationDateST = CreationDateST;
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

        public void setApprovedDateST(String ApprovedDateST) {
            this.ApprovedDateST = ApprovedDateST;
        }

        public void setStatus(int Status) {
            this.Status = Status;
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

        public String getStartDateST() {
            return StartDateST;
        }

        public String getEndDate() {
            return EndDate;
        }

        public String getEndDateST() {
            return EndDateST;
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

        public String getCreationDateST() {
            return CreationDateST;
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

        public String getApprovedDateST() {
            return ApprovedDateST;
        }

        public int getStatus() {
            return Status;
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
