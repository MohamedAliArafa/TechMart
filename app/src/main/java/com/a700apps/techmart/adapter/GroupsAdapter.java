package com.a700apps.techmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.UserGroup;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupTimeLineActivity;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimLineActivity;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimeLineFragment;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.mygroup.MyGroubListActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.Globals;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by samir salah on 9/11/2017.
 */

public class GroupsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserGroup> mUserGroupList;
    private final Context context;
    boolean isLoadingAdded = false;

    private int ITEM = 1;
    private int LOAd = 2;


    public GroupsAdapter(Context context, List<UserGroup> UserGroupList) {
        this.context = context;
        this.mUserGroupList = UserGroupList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType==ITEM){
            View noteView = inflater.inflate(R.layout.my_groub_list_item, parent, false);
            return new GroupsAdapter.ViewHolder(noteView);
        }else {
            View noteView = inflater.inflate(R.layout.item_progress, parent, false);
            return new GroupsAdapter.LoadingVH(noteView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHoldermain, int position) {
        if (viewHoldermain.getItemViewType() == ITEM){
            ViewHolder viewHolder = (ViewHolder) viewHoldermain;
            UserGroup mUserGroupItem = mUserGroupList.get(position);
            viewHolder.mNameTextView.setText(mUserGroupItem.Name);

            viewHolder.mCreateDate.setText(String.valueOf(AppUtils.getDate(mUserGroupItem.CreationDate)));
            viewHolder.mNameTextView.setText(mUserGroupItem.Name);
            viewHolder.mNumber.setText(String.valueOf(mUserGroupItem.MemberCount));
            Glide.with(context)
                    .load(MainApi.IMAGE_IP + mUserGroupItem.Icon).placeholder(R.drawable.placeholder)
                    .into(viewHolder.mGroupImageView);
        }else {
            final GroupsAdapter.LoadingVH loadHolder = (GroupsAdapter.LoadingVH) viewHoldermain;
            loadHolder.progressBar.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        }
    }

    @Override
    public int getItemCount() {
        if (mUserGroupList == null) {
            return 0;
        }
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
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
//            Intent myIntent = new Intent(context, GroupsTimLineActivity.class);
//            myIntent.putExtra("selectedCategory", mUserGroupList.get(position).ID);
//            context.startActivity(myIntent);
//            ((MyGroubListActivity) context).finish();

            Bundle bundle = new Bundle();
            bundle.putInt("selectedCategory", mUserGroupList.get(position).ID);
            Globals.GROUP_ID = mUserGroupList.get(position).ID;
            ((HomeActivity) context).openFragment(GroupsTimeLineFragment.class, bundle);

        }
    }

    public class LoadingVH extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingVH(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.loadmore_progress);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (mUserGroupList.get(position) == null) {
            return LOAd;
        }
        return ITEM;
    }

    public void add(UserGroup mc) {
        mUserGroupList.add(mc);
        notifyItemInserted(mUserGroupList.size() - 1);
    }

    public void addAll(List<UserGroup> mcList) {
        for (UserGroup mc : mcList) {
            add(mc);
        }
    }

    public void remove(UserGroup city) {
        int position = mUserGroupList.indexOf(city);
        if (position > -1) {
            mUserGroupList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(null);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mUserGroupList.size() - 1;
        UserGroup item = getItem(position);

//        if (item != null) {
            mUserGroupList.remove(position);
            notifyItemRemoved(position);
//        }
    }

    public UserGroup getItem(int position) {
        return mUserGroupList.get(position);
    }
}