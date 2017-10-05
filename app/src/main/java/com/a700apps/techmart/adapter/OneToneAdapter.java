package com.a700apps.techmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.model.OneToOneModel;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.screens.meeting.MeetingActivity;
import com.a700apps.techmart.ui.screens.meetingone.MeetingonetooneActivity;
import com.a700apps.techmart.ui.screens.profile.MemberProfile;
import com.a700apps.techmart.utils.Globals;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by samir.salah on 10/3/2017.
 */

public class OneToneAdapter extends RecyclerView.Adapter<OneToneAdapter.ViewHolder>implements NetworkResponseListener<PostData> {
    private List<MyConnectionList.ResultEntity> mGroupUsersList;
    private int GroupId;
    Context context;
    OneToOneModel mModel;
    onUserSelected onUserSelected;
    public OneToneAdapter(Context context, List<MyConnectionList.ResultEntity> TimeLineList,int mId,OneToOneModel model) {
        this.context = context;
        this.mGroupUsersList = TimeLineList;
        this.GroupId=mId;
        this.mModel=model;

        onUserSelected = (OneToneAdapter.onUserSelected) context;
    }

    @Override
    public OneToneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.group_admin_item, parent, false);
        return new OneToneAdapter.ViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(OneToneAdapter.ViewHolder viewHolder, final int position) {

        final   MyConnectionList.ResultEntity timeLineItem = mGroupUsersList.get(position);
//            viewHolder.profile_pic.
        Glide.with(context)
                .load(MainApi.IMAGE_IP +  timeLineItem.getName()).placeholder(R.drawable.ic_profile)
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

        viewHolder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Globals.oneToOneId = timeLineItem.getUserID();
                onUserSelected.onSelectUser(timeLineItem.getUserID() , timeLineItem.getName());
//                getLike(mModel,timeLineItem);
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
            JSONObject registerBody = MainApiHelper.addEvent(mModel.getStartTime(),mModel.getEndTime(),mModel.getLongtud(), mModel.getLatitude(), mModel.getAddress(), mModel.getGroupId(), mModel.getCreatedby(), mModel.getTitle(), mModel.getDescr(), mModel.getStartDate(), mModel.getEndDate(), timeLineItem.getUserID(), true,
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
//            int position = getAdapterPosition();
//            context.startActivity(new Intent(context, MemberProfile.class));
        }
    }


    public interface onUserSelected{
        void onSelectUser(String userId , String username);
    }
}
