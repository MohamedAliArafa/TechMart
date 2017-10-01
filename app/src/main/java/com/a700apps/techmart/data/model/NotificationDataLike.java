package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khaled.badawy on 9/29/2017.
 */

public class NotificationDataLike {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("ISResultHasData")
    @Expose
    private Integer iSResultHasData;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Integer getISResultHasData() {
        return iSResultHasData;
    }

    public void setISResultHasData(Integer iSResultHasData) {
        this.iSResultHasData = iSResultHasData;
    }

    public class Result {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("GroupID")
        @Expose
        private Integer groupID;
        @SerializedName("CreatedBY")
        @Expose
        private String createdBY;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Descr")
        @Expose
        private String descr;
        @SerializedName("Longtude")
        @Expose
        private Double longtude;
        @SerializedName("Latitude")
        @Expose
        private Double latitude;
        @SerializedName("StartDate")
        @Expose
        private String startDate;
        @SerializedName("StartDateST")
        @Expose
        private String startDateST;
        @SerializedName("EndDate")
        @Expose
        private String endDate;
        @SerializedName("EndDateST")
        @Expose
        private String endDateST;
        @SerializedName("Image")
        @Expose
        private String image;
        @SerializedName("MediaFile")
        @Expose
        private Object mediaFile;
        @SerializedName("CreationDate")
        @Expose
        private String creationDate;
        @SerializedName("CreationDateST")
        @Expose
        private String creationDateST;
        @SerializedName("AttendantCount")
        @Expose
        private Integer attendantCount;
        @SerializedName("IsOneToOneMeeting")
        @Expose
        private Boolean isOneToOneMeeting;
        @SerializedName("IsPublic")
        @Expose
        private Boolean isPublic;
        @SerializedName("ApprovedBy")
        @Expose
        private Object approvedBy;
        @SerializedName("ApprovedDate")
        @Expose
        private Object approvedDate;
        @SerializedName("ApprovedDateST")
        @Expose
        private String approvedDateST;
        @SerializedName("Status")
        @Expose
        private Integer status;
        @SerializedName("LikeCount")
        @Expose
        private Integer likeCount;
        @SerializedName("CommentCount")
        @Expose
        private Integer commentCount;
        @SerializedName("Type")
        @Expose
        private Integer type;
        @SerializedName("GroupName")
        @Expose
        private String groupName;
        @SerializedName("PostedByName")
        @Expose
        private String postedByName;
        @SerializedName("IsLike")
        @Expose
        private Boolean isLike;
        @SerializedName("IsGoing")
        @Expose
        private Boolean isGoing;
        @SerializedName("LocationName")
        @Expose
        private Object locationName;
        @SerializedName("StartTime")
        @Expose
        private String startTime;
        @SerializedName("EndTime")
        @Expose
        private String endTime;
        @SerializedName("StartTimeST")
        @Expose
        private String startTimeST;
        @SerializedName("EndTimeST")
        @Expose
        private String endTimeST;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public Integer getGroupID() {
            return groupID;
        }

        public void setGroupID(Integer groupID) {
            this.groupID = groupID;
        }

        public String getCreatedBY() {
            return createdBY;
        }

        public void setCreatedBY(String createdBY) {
            this.createdBY = createdBY;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public Double getLongtude() {
            return longtude;
        }

        public void setLongtude(Double longtude) {
            this.longtude = longtude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getStartDateST() {
            return startDateST;
        }

        public void setStartDateST(String startDateST) {
            this.startDateST = startDateST;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEndDateST() {
            return endDateST;
        }

        public void setEndDateST(String endDateST) {
            this.endDateST = endDateST;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Object getMediaFile() {
            return mediaFile;
        }

        public void setMediaFile(Object mediaFile) {
            this.mediaFile = mediaFile;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public String getCreationDateST() {
            return creationDateST;
        }

        public void setCreationDateST(String creationDateST) {
            this.creationDateST = creationDateST;
        }

        public Integer getAttendantCount() {
            return attendantCount;
        }

        public void setAttendantCount(Integer attendantCount) {
            this.attendantCount = attendantCount;
        }

        public Boolean getIsOneToOneMeeting() {
            return isOneToOneMeeting;
        }

        public void setIsOneToOneMeeting(Boolean isOneToOneMeeting) {
            this.isOneToOneMeeting = isOneToOneMeeting;
        }

        public Boolean getIsPublic() {
            return isPublic;
        }

        public void setIsPublic(Boolean isPublic) {
            this.isPublic = isPublic;
        }

        public Object getApprovedBy() {
            return approvedBy;
        }

        public void setApprovedBy(Object approvedBy) {
            this.approvedBy = approvedBy;
        }

        public Object getApprovedDate() {
            return approvedDate;
        }

        public void setApprovedDate(Object approvedDate) {
            this.approvedDate = approvedDate;
        }

        public String getApprovedDateST() {
            return approvedDateST;
        }

        public void setApprovedDateST(String approvedDateST) {
            this.approvedDateST = approvedDateST;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(Integer likeCount) {
            this.likeCount = likeCount;
        }

        public Integer getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(Integer commentCount) {
            this.commentCount = commentCount;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getPostedByName() {
            return postedByName;
        }

        public void setPostedByName(String postedByName) {
            this.postedByName = postedByName;
        }

        public Boolean getIsLike() {
            return isLike;
        }

        public void setIsLike(Boolean isLike) {
            this.isLike = isLike;
        }

        public Boolean getIsGoing() {
            return isGoing;
        }

        public void setIsGoing(Boolean isGoing) {
            this.isGoing = isGoing;
        }

        public Object getLocationName() {
            return locationName;
        }

        public void setLocationName(Object locationName) {
            this.locationName = locationName;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStartTimeST() {
            return startTimeST;
        }

        public void setStartTimeST(String startTimeST) {
            this.startTimeST = startTimeST;
        }

        public String getEndTimeST() {
            return endTimeST;
        }

        public void setEndTimeST(String endTimeST) {
            this.endTimeST = endTimeST;
        }
    }
}
