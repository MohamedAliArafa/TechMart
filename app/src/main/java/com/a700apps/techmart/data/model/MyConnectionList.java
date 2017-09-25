package com.a700apps.techmart.data.model;

import java.util.List;

/**
 * Created by Dev_halima on 9/16/2017.
 */

public class MyConnectionList {

    /**
     * result : [{"UserID":"6a64a68e-a4db-4721-85f1-0469d0070067","Name":"User FullName","Email":"darwish@700apss.com","Photo":"/UploadedImages/00201006159633","Company":"null","Position":"null","Phone":"null"}]
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
         * UserID : 6a64a68e-a4db-4721-85f1-0469d0070067
         * Name : User FullName
         * Email : darwish@700apss.com
         * Photo : /UploadedImages/00201006159633
         * Company : null
         * Position : null
         * Phone : null
         */

        private String UserID;
        private String Name;
        private String Email;
        private String Photo;
        private String Company;
        private String Position;
        private String Phone;

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public void setPhoto(String Photo) {
            this.Photo = Photo;
        }

        public void setCompany(String Company) {
            this.Company = Company;
        }

        public void setPosition(String Position) {
            this.Position = Position;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getUserID() {
            return UserID;
        }

        public String getName() {
            return Name;
        }

        public String getEmail() {
            return Email;
        }

        public String getPhoto() {
            return Photo;
        }

        public String getCompany() {
            return Company;
        }

        public String getPosition() {
            return Position;
        }

        public String getPhone() {
            return Phone;
        }
    }
}
