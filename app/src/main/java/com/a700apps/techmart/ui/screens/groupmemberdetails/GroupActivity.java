package com.a700apps.techmart.ui.screens.groupmemberdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.GroupUsersData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.comment.CommentActivity;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimLineActivity;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.mygroup.MyGroubListActivity;
import com.a700apps.techmart.ui.screens.profile.MemberProfile;
import com.a700apps.techmart.ui.screens.userlikes.UserLikesActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

public class GroupActivity extends AppCompatActivity implements GroupMemberView {
    private GroupMemberPresenter presenter;
    Button button7;
    EmptyRecyclerView rv, rv2;
    ImageView Icon;
    int mId;
    TextView mNoData,tv_admin,tv_member;
    public AVLoadingIndicatorView indicatorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        presenter = new GroupMemberPresenter();
        presenter.attachView(this);
        indicatorView= (AVLoadingIndicatorView) findViewById(R.id.avi);

//        AppUtils.hideSoftKeyboard(this);
        mId = getIntent().getIntExtra("string_key", 0);
        presenter.GroupUsers(mId, PreferenceHelper.getUserId(this), this);
        rv = (EmptyRecyclerView) findViewById(R.id.recyclerView);
        Icon = (ImageView) findViewById(R.id.imageView26);

        rv2 = (EmptyRecyclerView) findViewById(R.id.recyclerView2);
        mNoData = (TextView) findViewById(R.id.tv_nodata);
        tv_admin = (TextView) findViewById(R.id.tv_admin);
        tv_member = (TextView) findViewById(R.id.tv_member);
        mNoData = (TextView) findViewById(R.id.tv_nodata);
        button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(GroupActivity.this, GroupsTimLineActivity.class);
                myIntent.putExtra("selectedCategory", mId);
                startActivity(myIntent);

            }
        });
    }

    @Override
    public void showLoadingProgress() {
        indicatorView.setVisibility(View.VISIBLE);
        indicatorView.show();
    }

    @Override
    public void dismissLoadingProgress() {
        indicatorView.hide();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

//        ActivityUtils.openActivity(GroupActivity.this, GroupsTimLineActivity.class, true);

        Intent myIntent = new Intent(GroupActivity.this, GroupsTimLineActivity.class);
        myIntent.putExtra("selectedCategory", mId);
        startActivity(myIntent);

    }

    @Override
    public void updateUi(GroupUsersData.ResultEntity groupusers) {


        Glide.with(this)
                .load(MainApi.IMAGE_IP + groupusers.getIcon())
                .into(Icon);

        if (groupusers.getBoardMemebes().size()==0){
//            mNoData.setVisibility(View.VISIBLE);
            rv.setEmptyView(findViewById(R.id.tv_nodata));
        }
        tv_member.setText(String.valueOf(groupusers.getBoardMemebes().size()));
        tv_admin.setText(String.valueOf(groupusers.getOtheMemebes().size()));
        rv.setAdapter(new AdminAdapter(this, groupusers.getBoardMemebes(), mId));
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (groupusers.getOtheMemebes().size()==0){
            rv2.setEmptyView(findViewById(R.id.tv_nodata));

        }
        rv2.setAdapter(new MemberAdapter(this, groupusers.getOtheMemebes(), mId));
        rv2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

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
                    Intent intent = new Intent(context, MemberProfile.class);
                    intent.putExtra("RelativId", timeLineItem.getUserID());
                    intent.putExtra("GroupId", GroupId);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mGroupUsersList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

            @Override
            public void onClick(View v) {


            }
        }
    }

    static class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
        private List<GroupUsersData.ResultEntity.OtheMemebesEntity> mGroupUsersList;
        Context context;
        private int GroupId;

        public MemberAdapter(Context context, List<GroupUsersData.ResultEntity.OtheMemebesEntity> TimeLineList, int mId) {
            this.context = context;
            this.mGroupUsersList = TimeLineList;
            this.GroupId = mId;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.group_member_item, parent, false);
            return new ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            final GroupUsersData.ResultEntity.OtheMemebesEntity timeLineItem = mGroupUsersList.get(position);
//            viewHolder.profile_pic.
            Glide.with(context)
                    .load(MainApi.IMAGE_IP + timeLineItem.getPhoto()).placeholder(R.drawable.ic_profile)
                    .into(viewHolder.profile_pic);

            viewHolder.profile_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MemberProfile.class);
                    intent.putExtra("RelativId", timeLineItem.getUserID());
                    intent.putExtra("GroupId", GroupId);
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return mGroupUsersList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView profile_pic;

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                profile_pic = (ImageView) itemView.findViewById(R.id.imageView27);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
            }
        }
    }
}
