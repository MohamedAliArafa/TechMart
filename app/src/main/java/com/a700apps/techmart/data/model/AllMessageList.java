package com.a700apps.techmart.data.model;

import java.util.List;

/**
 * Created by samir.salah on 9/14/2017.
 */

public class AllMessageList {

    /**
     * result : [{"ID":4,"SenderUserID":"4c1bf02f-b5b7-4f70-859d-fb5ef31f3c51","ReciverUserID":"1f443dc9-43f7-4f92-9e3f-76805cd0823c","Message":"test message test message test message","SenderName":"samir","ReciverName":"TechMart Member2","SenderPhoto":"/storage/emulated/0/Pictures/Instagram/IMG_20170829_174721_444.jpg","ReciverPhoto":"20170906221444358961.JPG","IsRead":false,"SendingDate":"2017-09-14T09:59:30.47","SendingDateST":"2017-09-14T09:59:30","ReadingDateTime":null,"ReadingDateTimeST":"","SenderDeleted":false,"SenderDeleteDate":"null","SenderDeleteDateST":"","ReciverDeleted":false,"ReciverDeleteDate":"null","ReciverDeleteDateST":""}]
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
         * SenderUserID : 4c1bf02f-b5b7-4f70-859d-fb5ef31f3c51
         * ReciverUserID : 1f443dc9-43f7-4f92-9e3f-76805cd0823c
         * Message : test message test message test message
         * SenderName : samir
         * ReciverName : TechMart Member2
         * SenderPhoto : /storage/emulated/0/Pictures/Instagram/IMG_20170829_174721_444.jpg
         * ReciverPhoto : 20170906221444358961.JPG
         * IsRead : false
         * SendingDate : 2017-09-14T09:59:30.47
         * SendingDateST : 2017-09-14T09:59:30
         * ReadingDateTime : null
         * ReadingDateTimeST :
         * SenderDeleted : false
         * SenderDeleteDate : null
         * SenderDeleteDateST :
         * ReciverDeleted : false
         * ReciverDeleteDate : null
         * ReciverDeleteDateST :
         */

        private int ID;
        private String SenderUserID;
        private String ReciverUserID;
        private String Message;
        private String SenderName;
        private String ReciverName;
        private String SenderPhoto;
        private String ReciverPhoto;
        private boolean IsRead;
        private String SendingDate;
        private String SendingDateST;
        private String ReadingDateTime;
        private String ReadingDateTimeST;
        private boolean SenderDeleted;
        private String SenderDeleteDate;
        private String SenderDeleteDateST;
        private boolean ReciverDeleted;
        private String ReciverDeleteDate;
        private String ReciverDeleteDateST;

        public void setID(int ID) {
            this.ID = ID;
        }

        public void setSenderUserID(String SenderUserID) {
            this.SenderUserID = SenderUserID;
        }

        public void setReciverUserID(String ReciverUserID) {
            this.ReciverUserID = ReciverUserID;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public void setSenderName(String SenderName) {
            this.SenderName = SenderName;
        }

        public void setReciverName(String ReciverName) {
            this.ReciverName = ReciverName;
        }

        public void setSenderPhoto(String SenderPhoto) {
            this.SenderPhoto = SenderPhoto;
        }

        public void setReciverPhoto(String ReciverPhoto) {
            this.ReciverPhoto = ReciverPhoto;
        }

        public void setIsRead(boolean IsRead) {
            this.IsRead = IsRead;
        }

        public void setSendingDate(String SendingDate) {
            this.SendingDate = SendingDate;
        }

        public void setSendingDateST(String SendingDateST) {
            this.SendingDateST = SendingDateST;
        }

        public void setReadingDateTime(String ReadingDateTime) {
            this.ReadingDateTime = ReadingDateTime;
        }

        public void setReadingDateTimeST(String ReadingDateTimeST) {
            this.ReadingDateTimeST = ReadingDateTimeST;
        }

        public void setSenderDeleted(boolean SenderDeleted) {
            this.SenderDeleted = SenderDeleted;
        }

        public void setSenderDeleteDate(String SenderDeleteDate) {
            this.SenderDeleteDate = SenderDeleteDate;
        }

        public void setSenderDeleteDateST(String SenderDeleteDateST) {
            this.SenderDeleteDateST = SenderDeleteDateST;
        }

        public void setReciverDeleted(boolean ReciverDeleted) {
            this.ReciverDeleted = ReciverDeleted;
        }

        public void setReciverDeleteDate(String ReciverDeleteDate) {
            this.ReciverDeleteDate = ReciverDeleteDate;
        }

        public void setReciverDeleteDateST(String ReciverDeleteDateST) {
            this.ReciverDeleteDateST = ReciverDeleteDateST;
        }

        public int getID() {
            return ID;
        }

        public String getSenderUserID() {
            return SenderUserID;
        }

        public String getReciverUserID() {
            return ReciverUserID;
        }

        public String getMessage() {
            return Message;
        }

        public String getSenderName() {
            return SenderName;
        }

        public String getReciverName() {
            return ReciverName;
        }

        public String getSenderPhoto() {
            return SenderPhoto;
        }

        public String getReciverPhoto() {
            return ReciverPhoto;
        }

        public boolean getIsRead() {
            return IsRead;
        }

        public String getSendingDate() {
            return SendingDate;
        }

        public String getSendingDateST() {
            return SendingDateST;
        }

        public String getReadingDateTime() {
            return ReadingDateTime;
        }

        public String getReadingDateTimeST() {
            return ReadingDateTimeST;
        }

        public boolean getSenderDeleted() {
            return SenderDeleted;
        }

        public String getSenderDeleteDate() {
            return SenderDeleteDate;
        }

        public String getSenderDeleteDateST() {
            return SenderDeleteDateST;
        }

        public boolean getReciverDeleted() {
            return ReciverDeleted;
        }

        public String getReciverDeleteDate() {
            return ReciverDeleteDate;
        }

        public String getReciverDeleteDateST() {
            return ReciverDeleteDateST;
        }
    }
}
