package com.a700apps.techmart.data.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by samir salah on 9/13/2017.
 */

public class GroupUsersData {
    /**
     * result : {"BoardMemebes":[{"UserID":"6a64a68e-a4db-4721-85f1-0469d0070067","Name":"User FullName","Email":"darwish@700apss.com","Photo":"00201006159633","Company":"null","Position":"null"}],"OtheMemebes":[{"UserID":"6a64a68e-a4db-4721-85f1-0469d0070067","Name":"User FullName","Email":"darwish@700apss.com","Photo":"00201006159633","Company":"null","Position":"null"}],"ID":1,"Name":"group1","Icon":"icon1","CreationDate":"2017-08-22T11:03:28.587","MemberCount":0}
     * ISResultHasData : 1
     */

    private ResultEntity result;
    private int ISResultHasData;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setISResultHasData(int ISResultHasData) {
        this.ISResultHasData = ISResultHasData;
    }

    public ResultEntity getResult() {
        return result;
    }

    public int getISResultHasData() {
        return ISResultHasData;
    }

    public static class ResultEntity {
        /**
         * BoardMemebes : [{"UserID":"6a64a68e-a4db-4721-85f1-0469d0070067","Name":"User FullName","Email":"darwish@700apss.com","Photo":"00201006159633","Company":"null","Position":"null"}]
         * OtheMemebes : [{"UserID":"6a64a68e-a4db-4721-85f1-0469d0070067","Name":"User FullName","Email":"darwish@700apss.com","Photo":"00201006159633","Company":"null","Position":"null"}]
         * ID : 1
         * Name : group1
         * Icon : icon1
         * CreationDate : 2017-08-22T11:03:28.587
         * MemberCount : 0
         */

        private int ID;
        private String Name;
        private String Icon;
        private String CreationDate;
        private int MemberCount;
        private List<BoardMemebesEntity> BoardMemebes;
        private List<OtheMemebesEntity> OtheMemebes;

        public void setID(int ID) {
            this.ID = ID;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public void setIcon(String Icon) {
            this.Icon = Icon;
        }

        public void setCreationDate(String CreationDate) {
            this.CreationDate = CreationDate;
        }

        public void setMemberCount(int MemberCount) {
            this.MemberCount = MemberCount;
        }

        public void setBoardMemebes(List<BoardMemebesEntity> BoardMemebes) {
            this.BoardMemebes = BoardMemebes;
        }

        public void setOtheMemebes(List<OtheMemebesEntity> OtheMemebes) {
            this.OtheMemebes = OtheMemebes;
        }

        public int getID() {
            return ID;
        }

        public String getName() {
            return Name;
        }

        public String getIcon() {
            return Icon;
        }

        public String getCreationDate() {
            return CreationDate;
        }

        public int getMemberCount() {
            return MemberCount;
        }

        public List<BoardMemebesEntity> getBoardMemebes() {
            return BoardMemebes;
        }

        public List<OtheMemebesEntity> getOtheMemebes() {
            return OtheMemebes;
        }

        public static class BoardMemebesEntity {
            /**
             * UserID : 6a64a68e-a4db-4721-85f1-0469d0070067
             * Name : User FullName
             * Email : darwish@700apss.com
             * Photo : 00201006159633
             * Company : null
             * Position : null
             */

            private String UserID;
            private String Name;
            private String Email;
            private String Photo;
            private String Company;
            private String Position;

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
        }

        public static class OtheMemebesEntity {
            /**
             * UserID : 6a64a68e-a4db-4721-85f1-0469d0070067
             * Name : User FullName
             * Email : darwish@700apss.com
             * Photo : 00201006159633
             * Company : null
             * Position : null
             */

            private String UserID;
            private String Name;
            private String Email;
            private String Photo;
            private String Company;
            private String Position;

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
        }
    }
}
