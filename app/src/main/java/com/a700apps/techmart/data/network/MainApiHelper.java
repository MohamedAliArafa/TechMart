package com.a700apps.techmart.data.network;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir.salah on 9/5/2017.
 */

public class MainApiHelper {


    public static JSONObject createRegisterBody(String fullName, String password, String email, String mobile,
                                                String photo, String companyName, String position,
                                                String deviceId, String pnToken) throws JSONException {
        JSONObject body = new JSONObject();
        body.put("Name", fullName);
        body.put("Password", password);
        body.put("Phone", mobile);
        body.put("Email", email);
        body.put("Photo", photo);
        body.put("Company", companyName);
        body.put("Position", position);
        body.put("DeviceID", deviceId);
        body.put("DeviceToken", pnToken);

        return body;
    }

    public static JSONObject createLoginBody(String email, String password, String deviceId, String firebaseToken) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Password", password);
        jsonObject.put("Email", email);
        jsonObject.put("DeviceID", deviceId);
        jsonObject.put("DeviceToken", firebaseToken);
        return jsonObject;
    }


    public static JSONObject createForgetPasswordBody(String userMail, String appId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", userMail);
        return jsonObject;
    }

    //
    public static JSONObject getCategoryGroups(String id, String userId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ID", id);
        jsonObject.put("UserID", userId);
        return jsonObject;
    }

    public static JSONObject joinGroups(String GroupID, String userId, String Role) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("GroupID", GroupID);
        jsonObject.put("UserID", userId);
        jsonObject.put("RequestedRole", Role);
        return jsonObject;
    }

    public static JSONObject getTimeLine(String UserID, String Type) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserID", UserID);
        jsonObject.put("Type", Type);
        return jsonObject;
    }

    public static JSONObject getGroupTimeLine(String UserID, int GroupID, String Type) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserID", UserID);
        jsonObject.put("GroupID", GroupID);
        jsonObject.put("Type", Type);
        return jsonObject;
    }

    public static JSONObject getUserGroup(String UserID) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserID", UserID);
        return jsonObject;
    }

    public static JSONObject getUserLike(String UserID, int PostID, String Islike) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserID", UserID);
        jsonObject.put("PostID", PostID);
        jsonObject.put("Islike", Islike);
        return jsonObject;
    }

    public static JSONObject getUserLikePost( int PostID) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("PostID", PostID);
        return jsonObject;
    }

    public static JSONObject postComment(String UserID, int PostID, String Comment) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserID", UserID);
        jsonObject.put("PostID", PostID);
        jsonObject.put("Comment", Comment);
        return jsonObject;

    }


    public static JSONObject getPostComment( int PostID) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("PostID", PostID);
        return jsonObject;

    }

    public static JSONObject addPost(int GroupID, String CreatedBY, String Title, String Descr, String Image, String MediaFile, String CreationDate, boolean IsPublic) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("GroupID", GroupID);
        jsonObject.put("CreatedBY", CreatedBY);
        jsonObject.put("Title", Title);
        jsonObject.put("Descr", Descr);
        jsonObject.put("Image", Image);
        jsonObject.put("MediaFile", MediaFile);
        jsonObject.put("CreationDate", CreationDate);
        jsonObject.put("IsPublic", IsPublic);
        return jsonObject;
    }

    public static JSONObject addEvent(double Longtude,double Latitude,String LocationName,int GroupID, String CreatedBY, String Title, String Descr,String StartDate, String EndDate,
                                     String OneToOnPartener,boolean IsOneToOneMeeting,  String Image, String MediaFile, String CreationDate, boolean IsPublic) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("GroupID", GroupID);
        jsonObject.put("CreatedBY", CreatedBY);
        jsonObject.put("Title", Title);
        jsonObject.put("Descr", Descr);
        jsonObject.put("StartDate", StartDate);
        jsonObject.put("EndDate", EndDate);
        jsonObject.put("OneToOnPartener", OneToOnPartener);
        jsonObject.put("IsOneToOneMeeting", IsOneToOneMeeting);
        jsonObject.put("Image", Image);
        jsonObject.put("MediaFile", MediaFile);
        jsonObject.put("CreationDate", CreationDate);
        jsonObject.put("IsPublic", IsPublic);
        jsonObject.put("Longtude", Longtude);
        jsonObject.put("Latitude", Latitude);
        jsonObject.put("LocationName", LocationName);
        return jsonObject;
    }

    public static JSONObject getMyProfile( String UserID) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserID", UserID);
        return jsonObject;
    }

    public static JSONObject getMemberProfile( String UserID,String RelativeID,int GroupID) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("RelativeID", RelativeID);
        jsonObject.put("UserID", UserID);
        jsonObject.put("GroupID", GroupID);
        return jsonObject;
    }

    public static JSONObject getGroupUsers( int id,String UserID) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ID", id);
        jsonObject.put("UserID", UserID);
        return jsonObject;
    }

    public static JSONObject getUsersInbox(String UserID) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserID", UserID);
        return jsonObject;
    }

    public static JSONObject getFriendMessage(String UserID, String RelativeID) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserID", UserID);
        jsonObject.put("RelativeID", RelativeID);
        return jsonObject;
    }

    public static JSONObject sendMessage(String UserID, String RelativeID, String Message) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("SenderUserID", UserID);
        jsonObject.put("ReciverUserID", RelativeID);
        jsonObject.put("Message", Message);
        return jsonObject;
    }

    public static JSONObject follow(String RelativeID, String UserID,String follow) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("RelativeID", RelativeID);
        jsonObject.put("UserID", UserID);
        jsonObject.put("Follow", follow);
        return jsonObject;
    }

    public static JSONObject connect(String RelativeID, String UserID,String follow) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("RelativeID", RelativeID);
        jsonObject.put("UserID", UserID);
        jsonObject.put("Connect", follow);
        return jsonObject;
    }

    public static JSONObject saveProfileData(String UserID, String Name,String LinkedinProfile,
                                             String photo,String company,String position,String phone) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserID", UserID);
        jsonObject.put("Name", Name);
        jsonObject.put("LinkedinProfile", LinkedinProfile);
        jsonObject.put("Photo", photo);
        jsonObject.put("Company", company);
        jsonObject.put("Position", position);
        jsonObject.put("Phone", phone);
        return jsonObject;
    }

    public static JSONObject getMyConnectinGroup( String UserID,int GroupID) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserID", UserID);
        jsonObject.put("GroupID", GroupID);
        return jsonObject;
    }

    public static JSONObject getMyConnectionList(String UserID) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserID", UserID);
        return jsonObject;
    }

    public static JSONObject getSchedual(String UserID, String StartDate, String EndDate) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserID", UserID);
        jsonObject.put("StartDate", StartDate);
        jsonObject.put("EndDate", EndDate);
        return jsonObject;
    }

    public static JSONObject sendAttendee(String EventID, String UserID, String IsGoing) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("EventID", EventID);
        jsonObject.put("UserID", UserID);
        jsonObject.put("IsGoing", IsGoing);
        return jsonObject;
    }
}
