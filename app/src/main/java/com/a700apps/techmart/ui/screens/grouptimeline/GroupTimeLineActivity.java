package com.a700apps.techmart.ui.screens.grouptimeline;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.ui.screens.groupmemberdetails.GroupActivity;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.utils.ActivityUtils;

/**
 * Created by samir salah on 8/17/2017.
 */

public class GroupTimeLineActivity extends AppCompatActivity {
    TextView mGroupMember;
    ImageView imageView4;
    ImageView mProfileImageView, mNotificationImageView;
    private static final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouptimeline);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setAdapter(new NotificationAdapter(this));
        rv.setLayoutManager(new LinearLayoutManager(this));
        imageView4 = (ImageView) findViewById(R.id.imageView4);

        mProfileImageView = (ImageView) findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) findViewById(R.id.new_profile);

        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(GroupTimeLineActivity.this, EditProfileActivity.class, false);
            }
        });

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(GroupTimeLineActivity.this, NotificationActivity.class, false);


            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

//        mGroupMember = (TextView) findViewById(R.id.tv_group_member);
//        mGroupMember.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityUtils.openActivity(GroupTimeLineActivity.this, GroupActivity.class, false);
//            }
//        });


    }

    private static class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

        Context context;
        private static final int NOTIF_TYPE_ALARM = 0;
        private static final int NOTIF_TYPE_CONNECT = 1;
        private static final int NOTIF_TYPE_FOLLOW = 2;
        private static final int NOTIF_TYPE_POST = 3;
        private static final int NOTIF_TYPE_VIDEO = 4;


        public NotificationAdapter(Context context) {
            this.context = context;
        }

        @Override
        public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView;
            switch (viewType) {
                case NOTIF_TYPE_ALARM:
                    noteView = inflater.inflate(R.layout.group_post, parent, false);
                    break;
                case NOTIF_TYPE_CONNECT:
                    noteView = inflater.inflate(R.layout.gouptimeline_item, parent, false);
                    break;
                case NOTIF_TYPE_FOLLOW:
                    noteView = inflater.inflate(R.layout.gouptimeline_item, parent, false);
                    break;
                case NOTIF_TYPE_POST:
                    noteView = inflater.inflate(R.layout.gouptimeline_item, parent, false);
                    break;
                case NOTIF_TYPE_VIDEO:
                    noteView = inflater.inflate(R.layout.gouptimeline_item, parent, false);
                    break;
                default:
                    noteView = inflater.inflate(R.layout.gouptimeline_item, parent, false);
                    break;
            }
            return new NotificationAdapter.ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(NotificationAdapter.ViewHolder viewHolder, int position) {
            final int itemType = getItemViewType(position);
            switch (itemType) {
                case NOTIF_TYPE_ALARM:
                    viewHolder.uploadImageBtn.setOnClickListener(viewHolder);
                case NOTIF_TYPE_CONNECT:
                case NOTIF_TYPE_FOLLOW:
                case NOTIF_TYPE_POST:
                case NOTIF_TYPE_VIDEO:
            }
        }

        @Override
        public int getItemViewType(int position) {
            int type = position;
            switch (type) {
                case 0:
                    return NOTIF_TYPE_ALARM;
                case 1:
                    return NOTIF_TYPE_CONNECT;
                case 2:
                    return NOTIF_TYPE_FOLLOW;
                case 3:
                    return NOTIF_TYPE_POST;
                case 4:
                    return NOTIF_TYPE_VIDEO;
                default:
            }
            return super.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView uploadImageBtn;

            public ViewHolder(View itemView) {
                super(itemView);
                uploadImageBtn = (ImageView) itemView.findViewById(R.id.iv_upload_image);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                int viewId = v.getId();
                switch (viewId) {
                    case R.id.iv_upload_image:
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        if (context instanceof Activity) {
                            ((Activity) context).startActivityForResult(Intent.createChooser(intent,
                                    "Select Picture"), SELECT_PICTURE);
                        }
                        break;
                    default:
                        //ToDo for all item click
                        break;
                }
            }
        }
    }
}
