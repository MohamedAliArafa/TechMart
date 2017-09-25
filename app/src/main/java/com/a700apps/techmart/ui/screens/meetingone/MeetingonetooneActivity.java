package com.a700apps.techmart.ui.screens.meetingone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.LikeData;
import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.model.OneToOneModel;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.model.UserLike;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.screens.creatEvent.CreatEventActivity;
import com.a700apps.techmart.ui.screens.meeting.MeetingActivity;
import com.a700apps.techmart.ui.screens.profile.MemberProfile;
import com.a700apps.techmart.ui.screens.userlikes.UserLikesActivity;
import com.a700apps.techmart.ui.screens.userlikes.UserLikesPresenter;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by samir.salah on 9/17/2017.
 */

public class MeetingonetooneActivity extends AppCompatActivity implements MeetingoneView {
    OnetoOnePresenter presenter;
    int mId;
    RecyclerView rv;
    OneToOneModel model = new OneToOneModel();
    OneToOneModel mDataModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_user);
        mId = getIntent().getIntExtra("string_key", 0);
        presenter = new OnetoOnePresenter();
        presenter.attachView(this);
//        AppUtils.hideSoftKeyboard(this);
//        model
        Intent i = getIntent();
         mDataModel = (OneToOneModel)i.getSerializableExtra("MyClass");
        presenter.getOneToOne(PreferenceHelper.getUserId(this),mId, this);
        rv = (RecyclerView) findViewById(R.id.recyclerView);

    }

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void dismissLoadingProgress() {

    }

    @Override
    public void update(List<MyConnectionList.ResultEntity> userMeeting) {
        rv.setAdapter(new AdminAdapter(this, userMeeting,mId,mDataModel));
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private static class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder>implements NetworkResponseListener<PostData> {
        private List<MyConnectionList.ResultEntity> mGroupUsersList;
        private int GroupId;
        Context context;
        OneToOneModel mModel;
        public AdminAdapter(Context context, List<MyConnectionList.ResultEntity> TimeLineList,int mId,OneToOneModel model) {
            this.context = context;
            this.mGroupUsersList = TimeLineList;
            this.GroupId=mId;
            this.mModel=model;
        }

        @Override
        public AdminAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.group_admin_item, parent, false);
            return new AdminAdapter.ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(AdminAdapter.ViewHolder viewHolder, final int position) {

           final   MyConnectionList.ResultEntity timeLineItem = mGroupUsersList.get(position);
//            viewHolder.profile_pic.
            Glide.with(context)
                    .load(MainApi.IMAGE_IP +  timeLineItem.getName())
                    .into(viewHolder.profile);
            viewHolder.mName.setText(timeLineItem.getName());
            viewHolder.Company.setText(timeLineItem.getCompany());
            viewHolder.position.setText(timeLineItem.getPosition());

            viewHolder.Details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MemberProfile.class);
                    intent.putExtra("RelativId", timeLineItem.getUserID());
                    intent.putExtra("GroupId", GroupId);
                    context.startActivity(intent);
                }
            });

            viewHolder.  mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                   sendEvent(innerModle.getLongitude(), innerModle.getLatitude(), getCompleteAddressString(innerModle.getLongitude(), innerModle.getLatitude()), desired_string, PreferenceHelper.getUserId(CreatEventActivity.this),
//                                title.getText().toString(), Desc.getText().toString(), mStartDate, mEndDate, "", true, mImagePath, "", "", false, CreatEventActivity.this);
////
                    getLike(mModel,timeLineItem);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mGroupUsersList.size();
        }

        @Override
        public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
//            if (isDetachView()) return;
//            view.dismissLoadingProgress();
            PostData userNetworkData = (PostData) networkResponse.data;
            int errorCode = userNetworkData.ISResultHasData;
            Toast.makeText(context,"Event Sent",Toast.LENGTH_LONG).show();
            ((MeetingActivity)context).finish();
//            if (errorCode == 1) {
//                view.UpdateUi(userNetworkData.postData);
//            }
        }

        @Override
        public void networkOperationFail(Throwable throwable) {

        }

        void getLike(OneToOneModel mModel, MyConnectionList.ResultEntity timeLineItem) {
            try {
                JSONObject registerBody = MainApiHelper.addEvent(mModel.getLongtud(), mModel.getLatitude(), mModel.getAddress(), mModel.getGroupId(), mModel.getCreatedby(), mModel.getTitle(), mModel.getDescr(), mModel.getStartDate(), mModel.getEndDate(), timeLineItem.getUserID(), true,
                        mModel.getImage(), "", mModel.getCreationDate(),
                        false);
                MainApi.sendEvent(registerBody, this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView profile;
            TextView mName, Company, position;
            Button Details;
            ConstraintLayout mContainer;
            public ViewHolder(View itemView) {
                super(itemView);

                        mContainer=(ConstraintLayout)itemView.findViewById(R.id.container);
                profile = (ImageView) itemView.findViewById(R.id.imageView29);
                mName = (TextView) itemView.findViewById(R.id.textView67);
                Company = (TextView) itemView.findViewById(R.id.textView68);
                position = (TextView) itemView.findViewById(R.id.textView69);
//                itemView.findViewById(R.id.button8).setOnClickListener(this);
                Details = (Button) itemView.findViewById(R.id.button8);


            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                context.startActivity(new Intent(context, MemberProfile.class));
            }
        }
    }
}
