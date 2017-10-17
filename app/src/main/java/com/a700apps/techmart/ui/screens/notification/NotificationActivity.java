package com.a700apps.techmart.ui.screens.notification;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.a700apps.techmart.data.model.NoficationData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.message.ChatActivity;
import com.a700apps.techmart.ui.screens.notification.Ui.Activity.NotificationHolderActivity;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity implements NotificationView {

    NotificationPresenter presenter;
    EmptyRecyclerView rv;
    TextView nextTextView, previosTextView;


    int currentPage = 1;
    int itemsPerPage = 10;


    Dialog dialogsLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        rv = (EmptyRecyclerView) findViewById(R.id.recyclerView);
        rv.setAdapter(new NotificationAdapter(this, null));
        rv.setLayoutManager(new LinearLayoutManager(this));


        nextTextView = findViewById(R.id.textView39);
        previosTextView = findViewById(R.id.textView40);

        presenter = new NotificationPresenter();
        presenter.attachView(this);

        if (AppUtils.isInternetAvailable(this)) {
            presenter.loadData(PreferenceHelper.getUserId(this), currentPage, itemsPerPage);
        } else {
            showToast(getString(R.string.check_internet));
        }

        if (currentPage == 1) {
            previosTextView.setVisibility(View.GONE);
        }
        nextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadData(PreferenceHelper.getUserId(NotificationActivity.this), ++currentPage, itemsPerPage);
                if (previosTextView.getVisibility() == View.GONE) {
                    previosTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        previosTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage > 1) {
                    presenter.loadData(PreferenceHelper.getUserId(NotificationActivity.this), --currentPage, itemsPerPage);
                    if (currentPage == 1) {
                        previosTextView.setVisibility(View.GONE);
                    }
                }
            }
        });

        findViewById(R.id.imageView19).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void loadData(List<NoficationData.Result> list) {
        if (list.size() == 0) {
            rv.setEmptyView(findViewById(R.id.emptyIndiscator));
            nextTextView.setVisibility(View.GONE);
        } else {
            if (nextTextView.getVisibility() == View.GONE)
                nextTextView.setVisibility(View.VISIBLE);
        }

        rv.setAdapter(new NotificationAdapter(this, list));
    }

    @Override
    public void afterConnectSuccess() {
    }

    @Override
    public void afterFail() {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        dialogsLoading = new loadingDialog().showDialog(this);
    }

    @Override
    public void dismissLoad() {
        dialogsLoading.dismiss();
    }


    private static class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context context;
        private static final int NOTIF_TYPE_ALARM = 0;
        private static final int NOTIF_TYPE_VIDEO = 4;


        private static final int NOTIF_TYPE_CONNECT = 9;
        private static final int NOTIF_TYPE_ONE_TO_ONE = 3;
        private static final int NOTIF_TYPE_APPROVE_MEMBER_EVENT = 5;
        private static final int NOTIF_TYPE_USER_ATTENED_MY_EVENT = 14;
        private static final int NOTIF_TYPE_APPROVE_ONE_TO_ONE = 6;
        private static final int NOTIF_TYPE_POST = 2;
        private static final int NOTIF_TYPE_EVENT = 1;
        private static final int NOTIF_TYPE_FOLLOW = 10;
        private static final int NOTIF_TYPE_APPROVE_ON_POST = 4;
        private static final int NOTIF_TYPE_SOME_ONE_LIKE_POST = 8;
        private static final int NOTIF_TYPE_SOME_ONE_COMMENT_POST = 13;
        private static final int NOTIF_TYPE_GROUP_REQUEST = 15;
        private static final int NOTIF_TYPE_SOMEONE_APPROVED_CONNECTION_REQUEST = 11;
        private static final int NOTIF_TYPE_SOMEONE_ADDED_TO_GROUP_BY_ADMIN = 12;
        private static final int NOTIF_TYPE_MESSAGE = 7;

        List<NoficationData.Result> list = new ArrayList<>();

        public NotificationAdapter(Context context, List<NoficationData.Result> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView;
            switch (viewType) {
                case NOTIF_TYPE_ALARM:
                    noteView = inflater.inflate(R.layout.notif_item_alarm, parent, false);
                    return new DefaultHolder(noteView);
                case NOTIF_TYPE_CONNECT:
                    noteView = inflater.inflate(R.layout.notif_item_connect, parent, false);
                    return new ConnectViewHolder(noteView);
                case NOTIF_TYPE_FOLLOW:
                    noteView = inflater.inflate(R.layout.notif_item_follow, parent, false);
                    return new FollowViewHolder(noteView);
                case NOTIF_TYPE_POST:
                    noteView = inflater.inflate(R.layout.notif_item_post, parent, false);
                    return new PostAddedViewHolder(noteView);
                case NOTIF_TYPE_EVENT:
                    noteView = inflater.inflate(R.layout.notif_item_alarm, parent, false);
                    return new EventAddedViewHolder(noteView);
                case NOTIF_TYPE_GROUP_REQUEST:
                    noteView = inflater.inflate(R.layout.notif_item_group_approved, parent, false);
                    return new GroupViewHolder(noteView);
                case NOTIF_TYPE_MESSAGE:
                    noteView = inflater.inflate(R.layout.notif_item_new_message, parent, false);
                    return new ChatViewHolder(noteView);
                case NOTIF_TYPE_VIDEO:
                    noteView = inflater.inflate(R.layout.notif_item_video, parent, false);
                    return new DefaultHolder(noteView);
                default:
                    noteView = inflater.inflate(R.layout.notif_item_connect, parent, false);
                    return new DefaultHolder(noteView);
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//            final int itemType = getItemViewType(list.get(position).getTypeID());
            final int itemType = viewHolder.getItemViewType();
            Log.e("ItemType " + position, "---->" + itemType);
            NoficationData.Result result = list.get(position);
            switch (itemType) {

                case NOTIF_TYPE_ALARM:
                    break;
                case NOTIF_TYPE_CONNECT:

                    ConnectViewHolder holder = (ConnectViewHolder) viewHolder;
                    holder.name.setText(result.getRelativeUserName());//
                    holder.message.setText(result.getMessage());//
                    Glide.with(context).load(MainApi.IMAGE_IP + result.getIcon()).placeholder(R.drawable.ic_profile).into(holder.profile);

                    break;
                case NOTIF_TYPE_FOLLOW:
                    FollowViewHolder followViewHolder = (FollowViewHolder) viewHolder;
                    if (list.get(position).getTypeID() == NOTIF_TYPE_FOLLOW) {
                        followViewHolder.nameTv.setText(result.getRelativeUserName());// + " Followed You"
                    } else {
                        followViewHolder.nameTv.setText(result.getRelativeUserName() + " Accepted your connection request");//
                        followViewHolder.itemNameTv.setText(result.getMessage());//
                        followViewHolder.followToHide.setVisibility(View.INVISIBLE);
                        followViewHolder.youToHide.setVisibility(View.INVISIBLE);
                    }

                    Glide.with(context).load(MainApi.IMAGE_IP + result.getIcon()).placeholder(R.drawable.ic_profile).into(followViewHolder.profileImageView);

                    break;
                case NOTIF_TYPE_POST:

                    result = list.get(position);

                    PostAddedViewHolder postAddedViewHolder = (PostAddedViewHolder) viewHolder;
                    postAddedViewHolder.nameTv.setText(result.getRelativeUserName());
                    postAddedViewHolder.itemNameTv.setText(result.getMessage());
                    if (result.getTypeID() == NOTIF_TYPE_SOME_ONE_LIKE_POST) {
                        Glide.with(context).load(R.drawable.ic_like_active)
                                .placeholder(R.drawable.ic_like).into(postAddedViewHolder.profileImageView);
                    } else if (result.getTypeID() == NOTIF_TYPE_SOME_ONE_COMMENT_POST) {
                        Glide.with(context).load(R.drawable.ic_comment)
                                .placeholder(R.drawable.ic_comment).into(postAddedViewHolder.profileImageView);
                    } else {
//                        Log.e("ICON" , MainApi.IMAGE_IP+result.getIcon().toString());
                        Glide.with(context).load(MainApi.IMAGE_IP + result.getIcon().toString())
                                .placeholder(R.drawable.ic_profile).into(postAddedViewHolder.profileImageView);
                    }
                    break;
                case NOTIF_TYPE_EVENT:
                    result = list.get(position);
                    EventAddedViewHolder eventAddedViewHolder = (EventAddedViewHolder) viewHolder;
                    eventAddedViewHolder.nameTv.setText(result.getRelativeUserName());
                    eventAddedViewHolder.eventDetailsTv.setText(result.getMessage());
                    eventAddedViewHolder.itemTimeTv.setText(result.getCreatedDate());
                    break;

                case NOTIF_TYPE_GROUP_REQUEST:
                    result = list.get(position);
                    GroupViewHolder groupViewHolder = (GroupViewHolder) viewHolder;
                    groupViewHolder.messageTv.setText(result.getMessage());
                    groupViewHolder.descriptionTv.setText(result.getGroupName());
                    break;

                case NOTIF_TYPE_MESSAGE:
                    result = list.get(position);
                    ChatViewHolder chatViewHolder = (ChatViewHolder) viewHolder;
                    chatViewHolder.messageTv.setText(result.getItemName());
                    chatViewHolder.descriptionTv.setText(result.getRelativeUserName());
                    break;
                case NOTIF_TYPE_VIDEO:
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
//            if (position < list.size()) {
            int type = list.get(position).getTypeID();
            switch (type) {

/////////////////////////////////////
                case NOTIF_TYPE_POST:
//                    TYPE_EVENT_OR_POST = 1;
                    return NOTIF_TYPE_POST;

                case NOTIF_TYPE_APPROVE_ON_POST:
//                    TYPE_EVENT_OR_POST = 1;
                    return NOTIF_TYPE_POST;

                case NOTIF_TYPE_SOME_ONE_COMMENT_POST:
//                    TYPE_EVENT_OR_POST = 1;
                    return NOTIF_TYPE_POST;

                case NOTIF_TYPE_SOME_ONE_LIKE_POST:
//                    TYPE_EVENT_OR_POST = 1;
                    return NOTIF_TYPE_POST;
////////////////////////////////////////
                case NOTIF_TYPE_EVENT:
//                    TYPE_EVENT_OR_POST = 2;
                    return NOTIF_TYPE_EVENT;

                case NOTIF_TYPE_ONE_TO_ONE:
                    return NOTIF_TYPE_EVENT;

                case NOTIF_TYPE_APPROVE_ONE_TO_ONE:
                    return NOTIF_TYPE_EVENT;

                case NOTIF_TYPE_APPROVE_MEMBER_EVENT:
                    return NOTIF_TYPE_EVENT;

                case NOTIF_TYPE_USER_ATTENED_MY_EVENT:
                    return NOTIF_TYPE_EVENT;
////////////////////////////////////
                case NOTIF_TYPE_CONNECT:
                    return NOTIF_TYPE_CONNECT;
//////////////////////////////////////
                case NOTIF_TYPE_FOLLOW:
//                    TYPE_CONNECT_OR_FOLLOW = 1;
                    return NOTIF_TYPE_FOLLOW;

                case NOTIF_TYPE_SOMEONE_APPROVED_CONNECTION_REQUEST:
                    return NOTIF_TYPE_FOLLOW;

                ///////////////////////////////////////
                case NOTIF_TYPE_GROUP_REQUEST:
                    return NOTIF_TYPE_GROUP_REQUEST;

                case NOTIF_TYPE_SOMEONE_ADDED_TO_GROUP_BY_ADMIN:
                    return NOTIF_TYPE_GROUP_REQUEST;
////////////////////////////////////////////
                case NOTIF_TYPE_MESSAGE:
                    return NOTIF_TYPE_MESSAGE;
                /////////////////////////////////////////////////

                case 102:
                    return NOTIF_TYPE_ALARM;

                case 100:
                    return NOTIF_TYPE_VIDEO;
            }
            return super.getItemViewType(position);
//            }
//            return 0;
        }

        @Override
        public int getItemCount() {
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        public class ConnectViewHolder extends RecyclerView.ViewHolder implements NotificationView, View.OnClickListener {

            Button connectButton, ignoreButton;
            TextView name, message;
            ImageView profile;


            NotificationPresenter presenter;
            int position = 0;
//            Dialog dialogsLoading;

            public ConnectViewHolder(View itemView) {
                super(itemView);
                presenter = new NotificationPresenter();
                presenter.attachView(this);
//                dialogsLoading = new loadingDialog().showDialog(context);
                connectButton = itemView.findViewById(R.id.connectBtn);
                ignoreButton = itemView.findViewById(R.id.ignoreBtn);
                profile = itemView.findViewById(R.id.imageView14);

                name = itemView.findViewById(R.id.nameTv);
                message = itemView.findViewById(R.id.messageTv);
                itemView.setOnClickListener(this);
                connectButton.setOnClickListener(this);
                ignoreButton.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.connectBtn:
                        if (AppUtils.isInternetAvailable(context)) {
                            position = getAdapterPosition();
                            presenter.sendConnect(list.get(getAdapterPosition()).getRelativeUserID(), PreferenceHelper.getUserId(context), "true");
                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.ignoreBtn:
                        if (AppUtils.isInternetAvailable(context)) {
//                        position = getAdapterPosition();
                            presenter.sendConnect(list.get(getAdapterPosition()).getRelativeUserID(), PreferenceHelper.getUserId(context), "false");
                            presenter.deleteNotification(list.get(getAdapterPosition()).getID());
//                        list.remove(position);
//                        afterConnectSuccess();

                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }

            @Override
            public void loadData(List<NoficationData.Result> list) {

            }

            @Override
            public void afterConnectSuccess() {
                list.remove(position);
                notifyItemRemoved(position);
            }

            @Override
            public void afterFail() {
                Toast.makeText(context, "Some error happened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showToast(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showLoading() {
//                dialogsLoading.show();
            }

            @Override
            public void dismissLoad() {
//                dialogsLoading.dismiss();
            }
        }

        public class PostAddedViewHolder extends RecyclerView.ViewHolder implements NotificationView, View.OnClickListener {

//            Button connectButton, ignoreButton;
//            TextView name, message;

            TextView nameTv, itemNameTv, viewPost;
            ImageView dismiss, profileImageView, likeImage, viewImageView;


            NotificationPresenter presenter;

            public PostAddedViewHolder(View itemView) {
                super(itemView);
                presenter = new NotificationPresenter();
                presenter.attachView(this);


                nameTv = itemView.findViewById(R.id.textView15);
                itemNameTv = itemView.findViewById(R.id.textView36);
                viewPost = itemView.findViewById(R.id.textView37);
                dismiss = itemView.findViewById(R.id.imageView16);
                profileImageView = itemView.findViewById(R.id.imageView14);
                likeImage = itemView.findViewById(R.id.imageView14);
                viewImageView = itemView.findViewById(R.id.imageView15);

                likeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notif_white));
                itemView.findViewById(R.id.textView34).setVisibility(View.INVISIBLE);
                viewPost.setOnClickListener(this);
                dismiss.setOnClickListener(this);
                viewImageView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.textView37:
                        if (AppUtils.isInternetAvailable(context)) {
                            NoficationData.Result data = list.get(getAdapterPosition());
                            Intent intent = new Intent(context, NotificationHolderActivity.class);
                            intent.putExtra("holder", "like");
                            intent.putExtra("data", data);
                            intent.putExtra("type", list.get(getAdapterPosition()).getTypeID());
                            context.startActivity(intent);

                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.imageView15:
                        if (AppUtils.isInternetAvailable(context)) {
                            NoficationData.Result data2 = list.get(getAdapterPosition());
                            Intent intent2 = new Intent(context, NotificationHolderActivity.class);
                            intent2.putExtra("holder", "like");
                            intent2.putExtra("data", data2);
                            intent2.putExtra("type", list.get(getAdapterPosition()).getTypeID());
                            context.startActivity(intent2);
                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.imageView16:
                        if (AppUtils.isInternetAvailable(context)) {
//                        list.remove(getAdapterPosition());
                            presenter.deleteNotification(list.get(getAdapterPosition()).getID());
//                        notifyItemRemoved(getAdapterPosition());
                            break;
                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        }
                }
            }


            @Override
            public void loadData(List<NoficationData.Result> list) {

            }

            @Override
            public void afterConnectSuccess() {
                int position = getAdapterPosition();
                list.remove(position);
                notifyItemRemoved(position);
            }

            @Override
            public void afterFail() {

            }

            @Override
            public void showToast(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showLoading() {

            }

            @Override
            public void dismissLoad() {

            }


        }

        public class EventAddedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,NotificationView {

//            Button connectButton, ignoreButton;
//            TextView name, message;

            TextView nameTv, itemTimeTv, addToCalenderTv, eventDetailsTv;
            ImageView dismiss;
            NotificationPresenter presenter;

            public EventAddedViewHolder(View itemView) {
                super(itemView);

                presenter = new NotificationPresenter();
                presenter.attachView(this);
                nameTv = itemView.findViewById(R.id.textView15);
                itemTimeTv = itemView.findViewById(R.id.textView35);
                eventDetailsTv = itemView.findViewById(R.id.textView36);
                addToCalenderTv = itemView.findViewById(R.id.textView37);
                dismiss = itemView.findViewById(R.id.imageView16);


                addToCalenderTv.setOnClickListener(this);
                dismiss.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.textView37:
                        if (AppUtils.isInternetAvailable(context)) {
                            NoficationData.Result data = list.get(getAdapterPosition());
                            Intent intent = new Intent(context, NotificationHolderActivity.class);
                            intent.putExtra("holder", "like");
                            intent.putExtra("data", data);
                            intent.putExtra("type", list.get(getAdapterPosition()).getTypeID());
                            context.startActivity(intent);

                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        }
//                        Calendar cal = Calendar.getInstance();
//
//                        Intent intent = new Intent(Intent.ACTION_EDIT);
//                        intent.setType("vnd.android.cursor.item/event");
//                        intent.putExtra(CalendarContract.Events.TITLE, list.get(getAdapterPosition()).getItemName());
//                        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
//                                cal.getTime().getTime());
//                        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
//                                cal.getTime().getTime());
//                        intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
//                        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Tech Mart Event");
//                        context.startActivity(intent);
                        break;
                    case R.id.imageView16:
                        if (AppUtils.isInternetAvailable(context)) {
                            presenter.deleteNotification(list.get(getAdapterPosition()).getID());
//                            list.remove(getAdapterPosition());
//                            notifyItemRemoved(getAdapterPosition());
                            break;
                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        }

                }
            }
            @Override
            public void loadData(List<NoficationData.Result> list) {

            }

            @Override
            public void afterConnectSuccess() {
                int position = getAdapterPosition();
                list.remove(position);
                notifyItemRemoved(position);
            }

            @Override
            public void afterFail() {

            }

            @Override
            public void showToast(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showLoading() {

            }

            @Override
            public void dismissLoad() {

            }

        }

        public class FollowViewHolder extends RecyclerView.ViewHolder implements NotificationView, View.OnClickListener {

            TextView nameTv, itemNameTv, viewPost;
            ImageView dismiss, profileImageView, viewImage;
            TextView followToHide, youToHide;
            //            LikesPresenter presenter;
            NotificationPresenter presenter;

            public FollowViewHolder(View itemView) {
                super(itemView);
//                presenter = new LikesPresenter();
//                presenter.attachView(this);

                presenter = new NotificationPresenter();
                presenter.attachView(this);

                nameTv = itemView.findViewById(R.id.textView15);
                itemNameTv = itemView.findViewById(R.id.textView36);
                viewPost = itemView.findViewById(R.id.textView37);
                dismiss = itemView.findViewById(R.id.imageView16);
                viewImage = itemView.findViewById(R.id.imageView15);
                profileImageView = itemView.findViewById(R.id.imageView14);
                followToHide = itemView.findViewById(R.id.textView34);
                youToHide = itemView.findViewById(R.id.textView35);
//                if (list.get(getAdapterPosition()).getTypeID() == NOTIF_TYPE_SOMEONE_APPROVED_CONNECTION_REQUEST) {
//                    itemView.findViewById(R.id.textView34).setVisibility(View.INVISIBLE);
//                    itemView.findViewById(R.id.textView35).setVisibility(View.INVISIBLE);
//                }
                viewPost.setOnClickListener(this);
                dismiss.setOnClickListener(this);
                viewImage.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.textView37:
                        if (AppUtils.isInternetAvailable(context)) {
                            NoficationData.Result data = list.get(getAdapterPosition());
                            Intent intent = new Intent(context, NotificationHolderActivity.class);
                            intent.putExtra("holder", "follow");
                            intent.putExtra("RelativId", data.getRelativeUserID());
                            intent.putExtra("GroupId", data.getGroupID());
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.imageView15:
                        if (AppUtils.isInternetAvailable(context)) {
                            NoficationData.Result data2 = list.get(getAdapterPosition());
                            Intent intent2 = new Intent(context, NotificationHolderActivity.class);
                            intent2.putExtra("holder", "follow");
                            intent2.putExtra("RelativId", data2.getRelativeUserID());
                            intent2.putExtra("GroupId", data2.getGroupID());
                            context.startActivity(intent2);

                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.imageView16:
                        if (AppUtils.isInternetAvailable(context)) {
                            presenter.deleteNotification(list.get(getAdapterPosition()).getID());
                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        }
//                        list.remove(getAdapterPosition());
//                        notifyItemRemoved(getAdapterPosition());
                        break;
                }
            }


            @Override
            public void loadData(List<NoficationData.Result> list) {

            }

            @Override
            public void afterConnectSuccess() {
                int position = getAdapterPosition();
                list.remove(position);
                notifyItemRemoved(position);
            }

            @Override
            public void afterFail() {

            }

            @Override
            public void showToast(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showLoading() {

            }

            @Override
            public void dismissLoad() {

            }

        }

        public class GroupViewHolder extends RecyclerView.ViewHolder implements FollowView, View.OnClickListener {

            TextView descriptionTv, messageTv, viewPost;

//            LikesPresenter presenter;

            public GroupViewHolder(View itemView) {
                super(itemView);
//                presenter = new LikesPresenter();
//                presenter.attachView(this);


                descriptionTv = itemView.findViewById(R.id.descriptionTv);
                messageTv = itemView.findViewById(R.id.messageTv);
                viewPost = itemView.findViewById(R.id.viewTv);

                viewPost.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.viewTv:
                        if (AppUtils.isInternetAvailable(context)) {
                            Intent intent = new Intent(context, HomeActivity.class);
                            intent.putExtra("holder", "groupTimeLine");
                            intent.putExtra("selectedCategory", list.get(getAdapterPosition()).getGroupID());
                            Globals.CAME_FROM_NOTIFICATION_TO_GROUP = true;
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        }

                        break;
//                    case R.id.imageView16:
//                        list.remove(getAdapterPosition());
//                        notifyItemRemoved(getAdapterPosition());
//                        break;
                }
            }


            @Override
            public void showToast(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void viewProfile() {

            }

            @Override
            public void dismiss() {

            }
        }

        public class ChatViewHolder extends RecyclerView.ViewHolder implements FollowView, View.OnClickListener {

            TextView descriptionTv, messageTv, viewPost;
            ImageView imageView15;
//            LikesPresenter presenter;

            public ChatViewHolder(View itemView) {
                super(itemView);
//                presenter = new LikesPresenter();
//                presenter.attachView(this);


                descriptionTv = itemView.findViewById(R.id.descriptionTv);
                messageTv = itemView.findViewById(R.id.messageTv);
                viewPost = itemView.findViewById(R.id.viewTv);
                imageView15 = itemView.findViewById(R.id.imageView15);

                viewPost.setOnClickListener(this);
                imageView15.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.viewTv:
//                        Log.e("POSITION", getAdapterPosition() + "         " + list.get(getAdapterPosition()).getID());
                        if (AppUtils.isInternetAvailable(context)) {
                            Intent intent = new Intent(context, ChatActivity.class);
                            intent.putExtra("RelativeID", list.get(getAdapterPosition()).getRelativeUserID());
                            intent.putExtra("ReciverName", list.get(getAdapterPosition()).getRelativeUserName());
                            intent.putExtra("ReciverPhoto", list.get(getAdapterPosition()).getIcon().toString());
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.imageView15:
                        if (AppUtils.isInternetAvailable(context)) {
                            Intent intent2 = new Intent(context, ChatActivity.class);
                            intent2.putExtra("RelativeID", list.get(getAdapterPosition()).getRelativeUserID());
                            intent2.putExtra("ReciverName", list.get(getAdapterPosition()).getRelativeUserName());
                            intent2.putExtra("ReciverPhoto", list.get(getAdapterPosition()).getIcon().toString());
                            context.startActivity(intent2);
                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();

                        }
                        break;
//                    case R.id.imageView16:
//                        list.remove(getAdapterPosition());
//                        notifyItemRemoved(getAdapterPosition());
//                        break;
                }
            }


            @Override
            public void showToast(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void viewProfile() {

            }

            @Override
            public void dismiss() {

            }
        }

        public class DefaultHolder extends RecyclerView.ViewHolder {


            public DefaultHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
