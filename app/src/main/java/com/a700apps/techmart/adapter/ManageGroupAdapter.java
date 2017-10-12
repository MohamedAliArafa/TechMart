package com.a700apps.techmart.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.UserGroup;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.BoardMemberTimelineFragment;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimeLineFragment;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.Globals;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by samir.salah on 10/10/2017.
 */

public class ManageGroupAdapter extends RecyclerView.Adapter<ManageGroupAdapter.ViewHolder> {

    private List<UserGroup> mUserGroupList;
    private final Context context;

    public ManageGroupAdapter(Context context, List<UserGroup> UserGroupList) {
        this.context = context;
        this.mUserGroupList = UserGroupList;
    }

    @Override
    public ManageGroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.my_groub_list_item, parent, false);

        return new ManageGroupAdapter.ViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(ManageGroupAdapter.ViewHolder viewHolder, int position) {
        UserGroup mUserGroupItem = mUserGroupList.get(position);
        viewHolder.mNameTextView.setText(mUserGroupItem.Name);

        viewHolder.mCreateDate.setText(  String.valueOf(AppUtils.getDate(mUserGroupItem.CreationDate)));
        viewHolder.mNameTextView.setText(mUserGroupItem.Name);
        viewHolder.mNumber.setText(String.valueOf(mUserGroupItem.MemberCount));
        Glide.with(context)
                .load(MainApi.IMAGE_IP + mUserGroupItem.Icon).placeholder(R.drawable.placeholder)
                .into(viewHolder.mGroupImageView);
    }

    @Override
    public int getItemCount() {
        return mUserGroupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mGroupImageView;
        TextView mNameTextView, mCreateDate, mNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            mGroupImageView = (ImageView) itemView.findViewById(R.id.iv_group);
            mNameTextView = (TextView) itemView.findViewById(R.id.tv_name);
            mCreateDate = (TextView) itemView.findViewById(R.id.tv_creat_date);
            mNumber = (TextView) itemView.findViewById(R.id.tv_member_number);
            ActivityUtils.applyLightFont(mNameTextView);
            ActivityUtils.applyLightFont(mCreateDate);
            ActivityUtils.applyLightFont(mNumber);
            itemView.findViewById(R.id.view_detail_btn).setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
//            Intent myIntent = new Intent(context, GroupsTimLineActivity.class);
//            myIntent.putExtra("selectedCategory", mUserGroupList.get(position).ID);
//            context.startActivity(myIntent);
//            ((MyGroubListActivity) context).finish();

            Bundle bundle = new Bundle();
            bundle.putInt("selectedCategory" ,mUserGroupList.get(position).ID );
            Globals.GROUP_ID = mUserGroupList.get(position).ID;
            ((HomeActivity)context).openFragment(BoardMemberTimelineFragment.class , bundle);

        }
    }}
