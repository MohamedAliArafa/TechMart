package com.a700apps.techmart.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khaled.badawy on 10/18/2017.
 */

public class StatisticModel {
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

        @SerializedName("TotalPostCount")
        @Expose
        private Integer totalPostCount;
        @SerializedName("ApprovedPostCount")
        @Expose
        private Integer approvedPostCount;
        @SerializedName("TotalEventCount")
        @Expose
        private Integer totalEventCount;
        @SerializedName("ApprovedEventsCount")
        @Expose
        private Integer approvedEventsCount;
        @SerializedName("TotalJoinRequestCount")
        @Expose
        private Integer totalJoinRequestCount;
        @SerializedName("ApprovedJoinrequestCount")
        @Expose
        private Integer approvedJoinrequestCount;

        public Integer getTotalPostCount() {
            return totalPostCount;
        }

        public void setTotalPostCount(Integer totalPostCount) {
            this.totalPostCount = totalPostCount;
        }

        public Integer getApprovedPostCount() {
            return approvedPostCount;
        }

        public void setApprovedPostCount(Integer approvedPostCount) {
            this.approvedPostCount = approvedPostCount;
        }

        public Integer getTotalEventCount() {
            return totalEventCount;
        }

        public void setTotalEventCount(Integer totalEventCount) {
            this.totalEventCount = totalEventCount;
        }

        public Integer getApprovedEventsCount() {
            return approvedEventsCount;
        }

        public void setApprovedEventsCount(Integer approvedEventsCount) {
            this.approvedEventsCount = approvedEventsCount;
        }

        public Integer getTotalJoinRequestCount() {
            return totalJoinRequestCount;
        }

        public void setTotalJoinRequestCount(Integer totalJoinRequestCount) {
            this.totalJoinRequestCount = totalJoinRequestCount;
        }

        public Integer getApprovedJoinrequestCount() {
            return approvedJoinrequestCount;
        }

        public void setApprovedJoinrequestCount(Integer approvedJoinrequestCount) {
            this.approvedJoinrequestCount = approvedJoinrequestCount;
        }
    }
}
