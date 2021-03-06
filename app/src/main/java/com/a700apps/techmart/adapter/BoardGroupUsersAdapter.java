package com.a700apps.techmart.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.AllGroupUsers;
import com.a700apps.techmart.data.model.Group;
import com.a700apps.techmart.data.model.User;
import com.a700apps.techmart.data.model.UserGroup;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembermembers.BoardRemovePresenter;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembermembers.BoardRemoveView;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembertimeline.BoardTimelinePresenter;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembertimeline.BoardTimlineView;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimeLineFragment;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.profile.MemberProfileFragment;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.CustomButton;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by samir.salah on 10/11/2017.
 */

public class BoardGroupUsersAdapter extends RecyclerView.Adapter<BoardGroupUsersAdapter.ViewHolder> implements BoardRemoveView {

    private List<Group.Result> mUserGroupList;
    private final Context context;
    int Id;
    BoardRemovePresenter mPresenter;

    public BoardGroupUsersAdapter(Context context, List<Group.Result> UserGroupList, int mGroupId) {
        this.context = context;
        this.mUserGroupList = UserGroupList;
        this.Id = mGroupId;
    }

    @Override
    public BoardGroupUsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.list_all_group_users, parent, false);

        return new BoardGroupUsersAdapter.ViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(BoardGroupUsersAdapter.ViewHolder viewHolder, int position) {
        Group.Result mUserGroupItem = mUserGroupList.get(position);
        viewHolder.mNameTextView.setText(mUserGroupItem.getName());
        mPresenter = new BoardRemovePresenter();
        mPresenter.attachView(this);
        viewHolder.mCreateDate.setText(String.valueOf((mUserGroupItem.getCompany())));
        viewHolder.mNameTextView.setText(mUserGroupItem.getName());
        viewHolder.mPosition.setText(String.valueOf(mUserGroupItem.getPosition()));
        Glide.with(context)
                .load(MainApi.IMAGE_IP + mUserGroupItem.getPhoto()).placeholder(R.drawable.placeholder)
                .into(viewHolder.mGroupImageView);

        if (mUserGroupItem.getRoleInGroup() != 1) {
            viewHolder.view_remove_btn.setEnabled(false);
            viewHolder.view_remove_btn.setText("Admin");
        }
    }

    @Override
    public int getItemCount() {
        return mUserGroupList.size();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void update() {
        mPresenter.getMyGroupUsers(String.valueOf(Id), context);
    }

    @Override
    public void updateUi(Group data) {
        mUserGroupList = data.getResult();
        notifyDataSetChanged();
    }


    private void showConfirmDialog(final int position) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.logout_confirm_dialog);

        // set values for custom dialog components - text, image and button
        Button logout = (CustomButton) dialog.findViewById(R.id.logout);
        Button cancel = (CustomButton) dialog.findViewById(R.id.cancel);
        ((TextView) dialog.findViewById(R.id.logout_title)).setText("Delete user");
        ((TextView) dialog.findViewById(R.id.logout_desc)).setText("Are you sure you want to delete this user ?");
        logout.setText("Delete");
        dialog.show();

        Button declineButton = (CustomButton) dialog.findViewById(R.id.cancel);
        // if decline button is clicked, close the custom dialog
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.removeMember(
                        "" + Id,
                        mUserGroupList.get(position).getUserID(), context);
                dialog.dismiss();
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mGroupImageView;
        TextView mNameTextView, mCreateDate, mPosition;

        Button view_remove_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            mGroupImageView = (ImageView) itemView.findViewById(R.id.iv_group);
            mNameTextView = (TextView) itemView.findViewById(R.id.tv_name);
            mCreateDate = (TextView) itemView.findViewById(R.id.tv_creat_date);
            mPosition = (TextView) itemView.findViewById(R.id.tv_position);
            view_remove_btn = itemView.findViewById(R.id.view_remove_btn);
//            mNumber = (TextView) itemView.findViewById(R.id.tv_member_number);

            itemView.findViewById(R.id.view_detail_btn).setOnClickListener(this);
            itemView.findViewById(R.id.view_remove_btn).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            switch (v.getId()) {
                case R.id.view_detail_btn:
                    Bundle bundle = new Bundle();
                    bundle.putString("RelativId", mUserGroupList.get(position).getUserID());
                    bundle.putInt("GroupId", Id);
                    ((HomeActivity) context).openFragment(MemberProfileFragment.class, bundle);
                    break;
                case R.id.view_remove_btn:
                    showConfirmDialog(position);
                    break;
            }


//            Bundle bundle = new Bundle();
//            bundle.putInt("selectedCategory", mUserGroupList.get(position).ID);
//            Globals.GROUP_ID = mUserGroupList.get(position).ID;
//            ((HomeActivity) context).openFragment(GroupsTimeLineFragment.class, bundle);

        }
    }

}