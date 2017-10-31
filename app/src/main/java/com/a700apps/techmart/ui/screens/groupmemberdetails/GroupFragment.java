package com.a700apps.techmart.ui.screens.groupmemberdetails;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.GroupUsersData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileFragment;
import com.a700apps.techmart.ui.screens.profile.MemberProfileFragment;
import com.a700apps.techmart.utils.CustomButton;
import com.a700apps.techmart.utils.CustomTextView;
import com.a700apps.techmart.utils.DateTimePicker.CustomLightTextView;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment implements GroupMemberView {

    private GroupMemberPresenter presenter;
    Button button7;
    EmptyRecyclerView rv, rv2;
    ImageView Icon;
    int mId;
    TextView mNoData, tv_admin, tv_member,mDescTextView,mNameGroupTextView;
    public AVLoadingIndicatorView indicatorView;
    View view;

    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_group, container, false);

        presenter = new GroupMemberPresenter();
        presenter.attachView(this);
        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);

//        AppUtils.hideSoftKeyboard(this);
        mId = getArguments().getInt("string_key", 0);
        presenter.GroupUsers(mId, PreferenceHelper.getUserId(getActivity()), getActivity());
        rv = (EmptyRecyclerView) view.findViewById(R.id.recyclerView);
        Icon = (ImageView) view.findViewById(R.id.imageView26);
        mDescTextView = (CustomLightTextView)view. findViewById(R.id.textView55);
        mNameGroupTextView = (CustomTextView)view. findViewById(R.id.textView52);
        rv2 = (EmptyRecyclerView) view.findViewById(R.id.recyclerView2);
        mNoData = (CustomLightTextView) view.findViewById(R.id.tv_nodata);
        tv_admin = (TextView) view.findViewById(R.id.tv_admin);
        tv_member = (TextView) view.findViewById(R.id.tv_member);
        mNoData = (CustomLightTextView) view.findViewById(R.id.tv_nodata);
        button7 = (CustomButton) view.findViewById(R.id.button7);

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

//                Intent myIntent = new Intent(getActivity(), GroupsTimLineActivity.class);
//                myIntent.putExtra("selectedCategory", mId);
//                startActivity(myIntent);
            }
        });
        return view;
    }

    @Override
    public void showLoadingProgress() {
//        indicatorView.setVisibility(View.VISIBLE);
//        indicatorView.show();
    }

    @Override
    public void dismissLoadingProgress() {
//        indicatorView.hide();
    }

    @Override
    public void updateUi(GroupUsersData.ResultEntity groupusers) {

        Log.e("name",groupusers.getName());
        mNameGroupTextView.setText(groupusers.getName());
        mDescTextView.setText(groupusers.getDescription());
        Glide.with(this)
                .load(MainApi.IMAGE_IP + groupusers.getIcon())
                .into(Icon);

        if (groupusers.getBoardMemebes().size() == 0) {
            rv.setEmptyView(view.findViewById(R.id.tv_nodata));
        }
        tv_member.setText(String.valueOf(groupusers.getBoardMemebes().size()));
        tv_admin.setText(String.valueOf(groupusers.getOtheMemebes().size()));
        rv.setAdapter(new AdminAdapter(getActivity(), groupusers.getBoardMemebes(), mId));
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        if (groupusers.getOtheMemebes().size() == 0) {
            rv2.setEmptyView(view.findViewById(R.id.tv_memeber));
        }
        rv2.setAdapter(new GroupActivity.MemberAdapter(getActivity(), groupusers.getOtheMemebes(), mId));
        int numberOfColumns = 3;
        rv2.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
//        rv2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

    }

    private static class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
        private List<GroupUsersData.ResultEntity.BoardMemebesEntity> mGroupUsersList;

        Context context;
        private int GroupId;

        public AdminAdapter(Context context, List<GroupUsersData.ResultEntity.BoardMemebesEntity> TimeLineList, int mId) {
            this.context = context;
            this.mGroupUsersList = TimeLineList;
            this.GroupId = mId;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.group_admin_item, parent, false);
            return new ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {

            final GroupUsersData.ResultEntity.BoardMemebesEntity timeLineItem = mGroupUsersList.get(position);
            Glide.with(context)
                    .load(MainApi.IMAGE_IP + timeLineItem.getPhoto()).placeholder(R.drawable.ic_profile)
                    .into(viewHolder.profile);
            viewHolder.mName.setText(timeLineItem.getName());
            viewHolder.Company.setText(timeLineItem.getCompany());
            viewHolder.position.setText(timeLineItem.getPosition());
            viewHolder.Details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (timeLineItem.getUserID().equals(PreferenceHelper.getUserId(context))) {
                        Globals.CAME_FROM_GROUP_MEMBER_TO_MPROFILE = true;
                        ((HomeActivity) context). openFragment(EditProfileFragment.class, null);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("RelativId", timeLineItem.getUserID());
                        bundle.putInt("GroupId", GroupId);
                        Globals.userId = timeLineItem.getUserID();
                        ((HomeActivity) context).openFragment(MemberProfileFragment.class, bundle);
                    }

//                    Bundle bundle = new Bundle();
//                    bundle.putString("RelativId", timeLineItem.getUserID());
//                    bundle.putInt("GroupId", GroupId);
//                    Globals.userId = timeLineItem.getUserID();
//                    ((HomeActivity) context).openFragment(MemberProfileFragment.class, bundle);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mGroupUsersList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView profile;
            TextView mName, Company, position;
            Button Details;

            public ViewHolder(View itemView) {
                super(itemView);
                profile = (ImageView) itemView.findViewById(R.id.imageView29);
                mName = (TextView) itemView.findViewById(R.id.textView67);
                Company = (TextView) itemView.findViewById(R.id.textView68);
                position = (TextView) itemView.findViewById(R.id.textView69);
                Details = (Button) itemView.findViewById(R.id.button8);
            }
        }
    }

//    static class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
//        private List<GroupUsersData.ResultEntity.OtheMemebesEntity> mGroupUsersList;
//        Context context;
//        private int GroupId;
//
//        public MemberAdapter(Context context, List<GroupUsersData.ResultEntity.OtheMemebesEntity> TimeLineList, int mId) {
//            this.context = context;
//            this.mGroupUsersList = TimeLineList;
//            this.GroupId = mId;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            Context context = parent.getContext();
//            LayoutInflater inflater = LayoutInflater.from(context);
//            View noteView = inflater.inflate(R.layout.group_member_item, parent, false);
//            return new ViewHolder(noteView);
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder viewHolder, int position) {
//            final GroupUsersData.ResultEntity.OtheMemebesEntity timeLineItem = mGroupUsersList.get(position);
////            viewHolder.profile_pic.
//            Glide.with(context)
//                    .load(MainApi.IMAGE_IP + timeLineItem.getPhoto()).placeholder(R.drawable.ic_profile)
//                    .into(viewHolder.profile_pic);
//
//            viewHolder.profile_pic.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("RelativId", timeLineItem.getUserID());
//                    bundle.putInt("GroupId", GroupId);
//                    Globals.userId = timeLineItem.getUserID();
//                    ((HomeActivity) context).openFragment(MemberProfileFragment.class, bundle);
//                }
//            });
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return mGroupUsersList.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//            ImageView profile_pic;
//
//            public ViewHolder(View itemView) {
//                super(itemView);
//                itemView.setOnClickListener(this);
//                profile_pic = (ImageView) itemView.findViewById(R.id.imageView27);
//            }
//
//            @Override
//            public void onClick(View v) {
//                int position = getAdapterPosition();
//            }
//        }
//    }
}




