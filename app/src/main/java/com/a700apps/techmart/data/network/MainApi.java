package com.a700apps.techmart.data.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.a700apps.techmart.data.model.AllGroupUsers;
import com.a700apps.techmart.data.model.AllMessageList;
import com.a700apps.techmart.data.model.AllSchedualList;
import com.a700apps.techmart.data.model.CategoryData;
import com.a700apps.techmart.data.model.CategoryGroupsData;
import com.a700apps.techmart.data.model.ChangeReciveNotifcationData;
import com.a700apps.techmart.data.model.CommentData;
import com.a700apps.techmart.data.model.FriendMessage;
import com.a700apps.techmart.data.model.Group;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.GroupUsersData;
import com.a700apps.techmart.data.model.JoinGroupData;
import com.a700apps.techmart.data.model.JoinGroupRequestsData;
import com.a700apps.techmart.data.model.LikeData;
import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.model.MyProfileData;
import com.a700apps.techmart.data.model.NoficationData;
import com.a700apps.techmart.data.model.NotificationDataLike;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.model.PredifinedData;
import com.a700apps.techmart.data.model.SendMessageResponse;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.model.StatisticModel;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.model.UserData;
import com.a700apps.techmart.data.model.UserGroupData;
import com.a700apps.techmart.data.model.UserLikeData;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by samir.salah on 9/5/2017.
 */

public class MainApi {

//    public static final String API_LINK = "http://108.179.204.213:8073/api/";
    public static final String API_LINK = "http://23.236.154.106:8084/api/"; // for development
    public static final String JSON_TYPE = "application/json";
    public static final String IMAGE_IP = "http://23.236.154.106:8084";
//    public static final String IMAGE_IP = "http://108.179.204.213:8073";
    public static final String TAG_DATE_PICKER = "datepicker";

    private static ApiInterface getApi() {
        //Retrofit interceptor.
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        // Retrofit.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_LINK)
                .client(client).addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiInterface.class);
    }

    @NonNull
    private static RequestBody getRequestBody(JSONObject jsonBody) {
        return RequestBody.create(MediaType.parse(JSON_TYPE), jsonBody.toString());
    }

    public static void registerUser(JSONObject jsonBody, final NetworkResponseListener<UserData> responseListener) {
        RequestBody requestBody = getRequestBody(jsonBody);
        getApi().registerUser(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserData>() {

                    @Override
                    public void onNext(UserData value) {
                        final NetworkResponse<UserData> networkResponse = new NetworkResponse<>();
//                        networkResponse.requestType = AppConstant.NetworkOperationsTypes.REGISTER;
                        networkResponse.data = value;
                        responseListener.networkOperationSuccess(networkResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        responseListener.networkOperationFail(e);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    public static void registerLinkedUser(JSONObject jsonBody, final NetworkResponseListener<UserData> responseListener) {
        RequestBody requestBody = getRequestBody(jsonBody);
        getApi().registerLinkedUser(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserData>() {

                    @Override
                    public void onNext(UserData value) {
                        final NetworkResponse<UserData> networkResponse = new NetworkResponse<>();
//                        networkResponse.requestType = AppConstant.NetworkOperationsTypes.REGISTER;
                        networkResponse.data = value;
                        responseListener.networkOperationSuccess(networkResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        responseListener.networkOperationFail(e);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    public static void loginUser(JSONObject body, final NetworkResponseListener<UserData> responseListener) {
        getApi().loginUser(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
            }

            @Override
            public void onNext(UserData userNetworkData) {
                NetworkResponse<UserData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getCategoryGroup(final NetworkResponseListener<CategoryData> responseListener) {
        getApi().getCategory().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<CategoryData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
            }

            @Override
            public void onNext(CategoryData userNetworkData) {
                NetworkResponse<CategoryData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getGroupCategory(JSONObject body, final NetworkResponseListener<CategoryGroupsData> responseListener) {
        getApi().getCategorygroup(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<CategoryGroupsData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
            }

            @Override
            public void onNext(CategoryGroupsData userNetworkData) {
                NetworkResponse<CategoryGroupsData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void joinGroup(JSONObject body, final NetworkResponseListener<JoinGroupData> responseListener) {
        getApi().getJoingroup(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JoinGroupData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
            }

            @Override
            public void onNext(JoinGroupData userNetworkData) {
                NetworkResponse<JoinGroupData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getTimeLine(JSONObject body, final NetworkResponseListener<TimeLineData> responseListener) {
        getApi().getTimeLine(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TimeLineData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(TimeLineData userNetworkData) {
                NetworkResponse<TimeLineData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }


    public static void getGroupTimeLine(JSONObject body, final NetworkResponseListener<TimeLineData> responseListener) {
        getApi().getGroupTimeLine(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TimeLineData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
            }

            @Override
            public void onNext(TimeLineData userNetworkData) {
                NetworkResponse<TimeLineData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getUserGroup(JSONObject body, final NetworkResponseListener<UserGroupData> responseListener) {
        getApi().getUserGroup(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserGroupData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(UserGroupData userNetworkData) {
                NetworkResponse<UserGroupData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getGroupUsers(JSONObject body, final NetworkResponseListener<GroupUsersData> responseListener) {
        getApi().getGroupUser(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<GroupUsersData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(GroupUsersData userNetworkData) {
                NetworkResponse<GroupUsersData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }


    public static void getLikeTimeLine(JSONObject body, final NetworkResponseListener<LikeData> responseListener) {
        getApi().getLike(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<LikeData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(LikeData userNetworkData) {
                NetworkResponse<LikeData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void sendPost(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().sendPostData(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(PostData userNetworkData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void sendEvent(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().sndEventData(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(PostData userNetworkData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void sendComment(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().postComment(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(PostData userNetworkData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getPostComment(JSONObject body, final NetworkResponseListener<CommentData> responseListener) {
        getApi().getPostComment(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<CommentData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(CommentData userNetworkData) {
                NetworkResponse<CommentData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getUserLike(JSONObject body, final NetworkResponseListener<UserLikeData> responseListener) {
        getApi().getUserLike(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserLikeData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(UserLikeData userNetworkData) {
                NetworkResponse<UserLikeData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getMyProfile(JSONObject body, final NetworkResponseListener<MyProfileData> responseListener) {
        getApi().getMyProfile(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MyProfileData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(MyProfileData userNetworkData) {
                NetworkResponse<MyProfileData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }


    public static void getMyProfileMember(JSONObject body, final NetworkResponseListener<MyProfileData> responseListener) {
        getApi().getMyProfileMember(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MyProfileData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(MyProfileData userNetworkData) {
                NetworkResponse<MyProfileData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getUsersInbox(JSONObject body, final NetworkResponseListener<AllMessageList> responseListener) {
        getApi().getUserInbox(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AllMessageList>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(AllMessageList userNetworkData) {
                NetworkResponse<AllMessageList> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getFriendMessage(JSONObject body, final NetworkResponseListener<FriendMessage> responseListener) {
        getApi().getFriendMessage(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<FriendMessage>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(FriendMessage userNetworkData) {
                NetworkResponse<FriendMessage> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void sendMessage(JSONObject body, final NetworkResponseListener<SendMessageResponse> responseListener) {
        getApi().sendMessage(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SendMessageResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(SendMessageResponse userNetworkData) {
                NetworkResponse<SendMessageResponse> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void sendFollow(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().sendFollow(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(PostData userNetworkData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void sendConnect(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().sendConnect(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(PostData userNetworkData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void approveFriendRequest(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().approveFriendRequest(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(PostData userNetworkData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }


    public static void respondFriendRequest(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().respondFriendRequest(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(PostData userNetworkData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void saveProfile(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().saveProfile(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(PostData userNetworkData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getSchedual(JSONObject body, final NetworkResponseListener<AllSchedualList> responseListener) {
        getApi().getSchedual(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AllSchedualList>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(AllSchedualList userNetworkData) {
                NetworkResponse<AllSchedualList> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getMyConnectionList(JSONObject body, final NetworkResponseListener<MyConnectionList> responseListener) {
        getApi().getMyConnectionList(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MyConnectionList>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(MyConnectionList userNetworkData) {
                NetworkResponse<MyConnectionList> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void deleteNotification(JSONObject body, final NetworkResponseListener<ServerResponse> responseListener) {
        getApi().deleteNotification(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ServerResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(ServerResponse userNetworkData) {
                NetworkResponse<ServerResponse> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }


    public static void getMyConnectionListGroup(JSONObject body, final NetworkResponseListener<MyConnectionList> responseListener) {
        getApi().getMyConnectionListGroup(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MyConnectionList>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(MyConnectionList userNetworkData) {
                NetworkResponse<MyConnectionList> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }


    public static void sendAttendee(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().sendAttendee(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(PostData userNetworkData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void ChangeReciveNotifcationSetting(JSONObject body, final NetworkResponseListener<ChangeReciveNotifcationData> responseListener) {
        getApi().ChangeReciveNotifcationSetting(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ChangeReciveNotifcationData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(ChangeReciveNotifcationData userNetworkData) {
                NetworkResponse<ChangeReciveNotifcationData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }


    public static void ChangeLoginPassword(JSONObject body, final NetworkResponseListener<ChangeReciveNotifcationData> responseListener) {
        getApi().ChangeLoginPassword(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ChangeReciveNotifcationData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(ChangeReciveNotifcationData changePasswordData) {
                NetworkResponse<ChangeReciveNotifcationData> networkResponse = new NetworkResponse<>();
                networkResponse.data = changePasswordData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void ChangeProfilePhoto(JSONObject body, final NetworkResponseListener<ChangeReciveNotifcationData> responseListener) {
        getApi().ChangeProfilePhoto(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ChangeReciveNotifcationData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(ChangeReciveNotifcationData changePasswordData) {
                NetworkResponse<ChangeReciveNotifcationData> networkResponse = new NetworkResponse<>();
                networkResponse.data = changePasswordData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }


    public static void GetRelativeEventByUserID(JSONObject body, final NetworkResponseListener<TimeLineData> responseListener) {
        getApi().GetRelativeEventByUserID(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TimeLineData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(TimeLineData serverResponse) {
                NetworkResponse<TimeLineData> networkResponse = new NetworkResponse<>();
                networkResponse.data = serverResponse;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });


    }


    public static void GetRelativeGroupByUserID(JSONObject body, final NetworkResponseListener<UserGroupData> responseListener) {
        getApi().GetRelativeGroupByUserID(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserGroupData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(UserGroupData changePasswordData) {
                NetworkResponse<UserGroupData> networkResponse = new NetworkResponse<>();
                networkResponse.data = changePasswordData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }


    public static void GetPreDefindMessage(final NetworkResponseListener<PredifinedData> responseListener) {
        getApi().GetPreDefindMessage().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PredifinedData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(PredifinedData changePasswordData) {
                NetworkResponse<PredifinedData> networkResponse = new NetworkResponse<>();
                networkResponse.data = changePasswordData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }


    public static void GetNotifications(JSONObject body, final NetworkResponseListener<NoficationData> responseListener) {
        getApi().getNotifications(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<NoficationData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(NoficationData changePasswordData) {
                NetworkResponse<NoficationData> networkResponse = new NetworkResponse<>();
                networkResponse.data = changePasswordData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getTimeLineById(JSONObject body, final NetworkResponseListener<NotificationDataLike> responseListener) {
        getApi().getTimeLineById(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<NotificationDataLike>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(NotificationDataLike changePasswordData) {
                NetworkResponse<NotificationDataLike> networkResponse = new NetworkResponse<>();
                networkResponse.data = changePasswordData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }
    // Member

    public static void getMemberTimeLine(JSONObject body, final NetworkResponseListener<GroupTimeLineData> responseListener) {
        getApi().getTimelineMember(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<GroupTimeLineData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
            }

            @Override
            public void onNext(GroupTimeLineData userNetworkData) {
                NetworkResponse<GroupTimeLineData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getTimeLineItemByID(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().getTimeLineItemByID(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
            }

            @Override
            public void onNext(PostData userNetworkData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void removeMember(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().removeMember(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
            }

            @Override
            public void onNext(PostData userNetworkData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getAllGroupUsers(JSONObject body, final NetworkResponseListener<Group> responseListener) {
        getApi().getAllGroupUsers(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Group>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
            }

            @Override
            public void onNext(Group userNetworkData) {
                NetworkResponse<Group> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getJoinGroupRequests(JSONObject body, final NetworkResponseListener<JoinGroupRequestsData> responseListener) {
        getApi().getJoinGroupRequests(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JoinGroupRequestsData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
            }

            @Override
            public void onNext(JoinGroupRequestsData userNetworkData) {
                NetworkResponse<JoinGroupRequestsData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void manageTimelineItem(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().manageTimeLineItem(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
            }

            @Override
            public void onNext(PostData userNetworkData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }


    public static void editTimelineItem(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().editeTimeLineItem(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
            }

            @Override
            public void onNext(PostData userNetworkData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void manageGroupBoard(JSONObject body, final NetworkResponseListener<UserGroupData> responseListener) {
        getApi().manageGroup(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserGroupData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(UserGroupData userNetworkData) {
                NetworkResponse<UserGroupData> networkResponse = new NetworkResponse<>();
                networkResponse.data = userNetworkData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void editTimeLineItem(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().editeTimeLineItem(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(PostData changePasswordData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = changePasswordData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }


    public static void changeRequestStatus(JSONObject body, final NetworkResponseListener<PostData> responseListener) {
        getApi().changeRequestStatus(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PostData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(PostData changePasswordData) {
                NetworkResponse<PostData> networkResponse = new NetworkResponse<>();
                networkResponse.data = changePasswordData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }

    public static void getGroupStatistics(JSONObject body, final NetworkResponseListener<StatisticModel> responseListener) {
        getApi().getGroupStatistics(getRequestBody(body)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<StatisticModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                responseListener.networkOperationFail(e);
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(StatisticModel changePasswordData) {
                NetworkResponse<StatisticModel> networkResponse = new NetworkResponse<>();
                networkResponse.data = changePasswordData;
                responseListener.networkOperationSuccess(networkResponse);
            }
        });
    }
}


