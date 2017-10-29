package com.a700apps.techmart.ui.screens.userlikes;

import android.app.Dialog;
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
import com.a700apps.techmart.data.model.UserLike;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.groupmemberdetails.GroupActivity;
import com.a700apps.techmart.ui.screens.groupmemberdetails.GroupMemberPresenter;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimLineActivity;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.profile.MemberProfile;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;
import com.bumptech.glide.Glide;

import java.util.List;

import static com.a700apps.techmart.R.id.button7;

/**
 * Created by samir salah on 9/15/2017.
 */

public class UserLikesActivity extends AppCompatActivity implements UserLikeView {

    UserLikesPresenter presenter;
    int mId;
    RecyclerView rv;
    ImageView back;
    Dialog dialogsLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_user);
        mId = getIntent().getIntExtra("string_key", 0);
        presenter = new UserLikesPresenter();
        presenter.attachView(this);
//        AppUtils.hideSoftKeyboard(this);
        presenter.getUserLikes(mId, this);
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        back = (ImageView) findViewById(R.id.imageView2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void showLoadingProgress() {
        dialogsLoading = new loadingDialog().showDialog(this);
    }

    @Override
    public void dismissLoadingProgress() {
        dialogsLoading.dismiss();
    }

    @Override
    public void update(List<UserLike> userLikes) {
        rv.setAdapter(new AdminAdapter(this, userLikes, mId));
        rv.setLayoutManager(new LinearLayoutManager(this));
    }


    private static class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
        private List<UserLike> mGroupUsersList;
        private int GroupId;
        Context context;

        public AdminAdapter(Context context, List<UserLike> TimeLineList, int mId) {
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
        public void onBindViewHolder(AdminAdapter.ViewHolder viewHolder, int position) {

            final UserLike timeLineItem = mGroupUsersList.get(position);
//            viewHolder.profile_pic.
            Glide.with(context)
                    .load(MainApi.IMAGE_IP + timeLineItem.Photo).placeholder(R.drawable.ic_profile)
                    .into(viewHolder.profile);
            viewHolder.mName.setText(timeLineItem.Name);
            viewHolder.Company.setText(timeLineItem.Company);
            viewHolder.position.setText(timeLineItem.Position);

            viewHolder.Details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    Globals.CAME_FROM_NOTIFICATION_TO_GROUP = true;
                    intent.putExtra("profileHolder", timeLineItem.UserID);
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
