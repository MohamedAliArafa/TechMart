package com.a700apps.techmart.data.network;

import com.a700apps.techmart.data.model.AllMessageList;
import com.a700apps.techmart.data.model.AllSchedualList;
import com.a700apps.techmart.data.model.CategoryData;
import com.a700apps.techmart.data.model.CategoryGroupsData;
import com.a700apps.techmart.data.model.CommentData;
import com.a700apps.techmart.data.model.FriendMessage;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.GroupUsersData;
import com.a700apps.techmart.data.model.JoinGroupData;
import com.a700apps.techmart.data.model.LikeData;
import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.model.MyProfileData;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.model.SendMessageResponse;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.model.UploadObject;
import com.a700apps.techmart.data.model.UserData;
import com.a700apps.techmart.data.model.UserGroupData;
import com.a700apps.techmart.data.model.UserLikeData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by samir.salah on 9/5/2017.
 */

public interface ApiInterface {

    @POST("User/Register")
    Observable<UserData> registerUser(@Body RequestBody body);

    @POST("User/login")
    Observable<UserData> loginUser(@Body RequestBody body);

    @POST("User/LinkedINLogin")
    Observable<UserData> linkedLoginUser(@Body RequestBody body);

    @POST("User/forgetuserpassword")
    Observable<Error> forgetPassword(@Body RequestBody body);

    @POST("Group/GetCategory")
    Observable<CategoryData> getCategory();

    @POST("Group/GetCategoryGroups")
    Observable<CategoryGroupsData> getCategorygroup(@Body RequestBody body);

    @POST("Group/JoinGroup")
    Observable<JoinGroupData> getJoingroup(@Body RequestBody body);

    @POST("timeline/getmytimeline")
    Observable<TimeLineData> getTimeLine(@Body RequestBody body);

    @POST("timeline/GetGroupTimeLine")
    Observable<GroupTimeLineData> getGroupTimeLine(@Body RequestBody body);

    @POST("Group/GetUserGroups")
    Observable<UserGroupData> getUserGroup(@Body RequestBody body);

    @POST("timeline/LikePost")
    Observable<LikeData> getLike(@Body RequestBody body);

    @POST("timeline/GetUserLikePost")
    Observable<UserLikeData> getUserLike(@Body RequestBody body);

    @POST("timeline/addpost")
    Observable<PostData> sendPostData(@Body RequestBody body);

    @POST("timeline/addevent")
    Observable<PostData> sndEventData(@Body RequestBody body);

    @POST("timeline/Comment")
    Observable<PostData> postComment(@Body RequestBody body);

    @POST("timeline/GetPostComment")
    Observable<CommentData> getPostComment(@Body RequestBody body);

    @POST("user/GetMyProfile")
    Observable<MyProfileData> getMyProfile(@Body RequestBody body);

    @POST("user/GetMemberProfile")
    Observable<MyProfileData> getMyProfileMember(@Body RequestBody body);

    @POST("Group/GetGroupUsers")
    Observable<GroupUsersData> getGroupUser(@Body RequestBody body);

    @Multipart
    @POST("uploader/Upload")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    @POST("message/GetUserInbox")
    Observable<AllMessageList> getUserInbox(@Body RequestBody body);

    @POST("Message/GetFriendMessages")
    Observable<FriendMessage> getFriendMessage(@Body RequestBody body);

    @POST("Message/send")
    Observable<SendMessageResponse> sendMessage(@Body RequestBody body);

    @POST("user/Follow")
    Observable<PostData> sendFollow(@Body RequestBody body);

    @POST("user/Connect")
    Observable<PostData> sendConnect(@Body RequestBody body);

    @POST("User/UpdateProfile")
    Observable<PostData> saveProfile(@Body RequestBody body);


    @POST("user/GetMyConnectionListInGroup")
    Observable<MyConnectionList> getMyConnectionListGroup(@Body RequestBody body);


    @POST("user/GetMyConnectionList")
    Observable<MyConnectionList> getMyConnectionList(@Body RequestBody body);

    @POST("timeline/GetSchedual")
    Observable<AllSchedualList> getSchedual(@Body RequestBody body);

    @POST("timeline/AttendEvent")
    Observable<PostData> sendAttendee(@Body RequestBody body);

}










