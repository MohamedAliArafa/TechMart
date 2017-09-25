package com.a700apps.techmart.data.model;

/**
 * Created by samir.salah on 9/14/2017.
 */

public class SendMessageResponse {

    /**
     * result : {"success":true,"message":"hfjdfh"}
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
         * success : true
         * message : hfjdfh
         */

        private boolean success;
        private String message;

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean getSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}
