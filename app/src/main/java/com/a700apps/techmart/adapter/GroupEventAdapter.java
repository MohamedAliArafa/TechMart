package com.a700apps.techmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.GroupTimeLine;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsActivity;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsGroupActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.Globals;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by samir salah on 9/13/2017.
 */

public class GroupEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TimeLineData.ResultEntity> mTimeLineList;
    Context context;
    private static final int NOTIF_TYPE_EVENT= 1;
    private static final int NOTIF_TYPE_LOAD= 3;
    boolean isLoadingAdded = false;


    public GroupEventAdapter(Context context,List<TimeLineData.ResultEntity> TimeLineList) {
        this.context = context;
        this.mTimeLineList =TimeLineList;

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHoldermain, final int position) {

//        ViewHolder viewHolder = (ViewHolder) viewHoldermain;
        TimeLineData.ResultEntity timeLineItem = mTimeLineList.get(position);
        final int itemType = getItemViewType(position);
        Log.e("timeLineItem.getType()",timeLineItem.getType()+"");

        switch (itemType) {
            case NOTIF_TYPE_EVENT:
                ViewHolderEvent viewHolderEvent = (ViewHolderEvent)viewHoldermain;
                viewHolderEvent.mDateTextView.setText(timeLineItem.getGroupName());
                viewHolderEvent.mDescribtionTextView.setText(timeLineItem.getDescr());
                viewHolderEvent.mTitleTextView.setText(timeLineItem.getTitle());
                viewHolderEvent.tv_username.setText(timeLineItem.getPostedByName());
                if (AppUtils.isEventInCal(context,timeLineItem.getTitle())){
                    Log.e("added to calender",AppUtils.isEventInCal(context,timeLineItem.getTitle())+"");
                    viewHolderEvent.tv_add_calender.setText("Added to calendar");
                    viewHolderEvent.tv_add_calender.setEnabled(false);
                    viewHolderEvent.tv_add_calender.setTextColor(context.getResources().getColor(R.color.light_color_text));
                }
                Glide.with(context)
                        .load(MainApi.IMAGE_IP+timeLineItem.getImage()).placeholder(R.drawable.placeholder)
                        .into(viewHolderEvent.mEventImageView);
                viewHolderEvent.contain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Globals.R_Index_group=position;
                        openDetails(context, "Event", mTimeLineList, position);
                    }
                });

                viewHolderEvent.addCalenderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra(CalendarContract.Events.TITLE,  timeLineItem.getTitle());
                        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                timeLineItem.getStartDate());
                        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                timeLineItem.getEndDate());
                        intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
                        intent.putExtra(CalendarContract.Events.DESCRIPTION, timeLineItem.getDescr());
                        context.startActivity(intent);
                    }
                });
                viewHolderEvent.tv_add_calender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra(CalendarContract.Events.TITLE, timeLineItem.getTitle());
                        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                timeLineItem.getStartDate());
                        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                timeLineItem.getEndDate());
                        intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
                        intent.putExtra(CalendarContract.Events.DESCRIPTION, timeLineItem.getDescr());
                        context.startActivity(intent);
                    }
                });

                viewHolderEvent.shareBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, Globals.ShareLink);
                        sendIntent.setType("text/plain");
                        context.startActivity(Intent.createChooser(sendIntent, "Select"));
                    }
                });
                viewHolderEvent.tv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, Globals.ShareLink);
                        sendIntent.setType("text/plain");
                        context.startActivity(Intent.createChooser(sendIntent, "Select"));
                    }
                });

                break;
            case NOTIF_TYPE_LOAD:
                final GroupEventAdapter.LoadingVH loadHolder = (GroupEventAdapter.LoadingVH) viewHoldermain;
                loadHolder.progressBar.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                break;

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView;

        if (viewType == NOTIF_TYPE_EVENT) {
            noteView = inflater.inflate(R.layout.timeline_first_item, parent, false);
//        setValues(new ViewHolderPost(noteView),noteView);
            return new ViewHolderEvent(noteView);
        } else {
            noteView = inflater.inflate(R.layout.item_progress, parent, false);
//        setValues(new ViewHolderPost(noteView),noteView);
            return new LoadingVH(noteView);
        }


//        }
//        return null;
//
    }



    @Override
    public int getItemViewType(int position) {
        if (mTimeLineList.get(position) == null) {
            return NOTIF_TYPE_LOAD;
        } else {
            switch (mTimeLineList.get(position).getType()) {
                case NOTIF_TYPE_EVENT:
                    return NOTIF_TYPE_EVENT;
                case NOTIF_TYPE_LOAD:
                    return NOTIF_TYPE_LOAD;
            }
        }
        return NOTIF_TYPE_LOAD;
    }

    @Override
    public int getItemCount() {
        Log.e("test",mTimeLineList.size()+"");
        return mTimeLineList == null ? 0 : mTimeLineList.size();
    }


    public class LoadingVH extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingVH(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.loadmore_progress);
        }
    }

    public class ViewHolderEvent extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mEventImageView ,shareBtn, addCalenderBtn;
        TextView mTitleTextView,mDescribtionTextView,mDateTextView,mGroupNameTextView,tv_add_calender,tv_username,tv_share;
        RelativeLayout contain;
        public ViewHolderEvent(View itemView) {
            super(itemView);
            tv_add_calender = (TextView) itemView.findViewById(R.id.tv_add_calender);

            mEventImageView = (ImageView) itemView.findViewById(R.id.iv_event);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            mDescribtionTextView = (TextView) itemView.findViewById(R.id.tv_description);
            mDateTextView = (TextView) itemView.findViewById(R.id.tv_date);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            tv_share = (TextView) itemView.findViewById(R.id.tv_share);

            contain = (RelativeLayout) itemView.findViewById(R.id.contain);

            shareBtn = (ImageView) itemView.findViewById(R.id.iv_share);
            addCalenderBtn = (ImageView) itemView.findViewById(R.id.iv_add_calender);
            itemView.setOnClickListener(this);
//                shareBtn.setOnClickListener(this);
//                addCalenderBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            int viewId = v.getId();
            switch (viewId) {
                case R.id.iv_share:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, Globals.ShareLink);
                    sendIntent.setType("text/plain");
                    context.startActivity(Intent.createChooser(sendIntent, "Select"));
                    break;

                case R.id.iv_add_calender:
                    Calendar cal = Calendar.getInstance();
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra(CalendarContract.Events.TITLE, "Event");
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                            cal.getTime().getTime());
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                            cal.getTime().getTime());
                    intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, "Tech Mart Event");
                    context.startActivity(intent);
                    break;
            }
        }
    }

    static void openDetails(Context context, String type, List<TimeLineData.ResultEntity> mTimeLineList, int index) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("Type", type);
        intent.putExtra("Index", index);
        intent.putParcelableArrayListExtra("Timeline", (ArrayList<? extends Parcelable>) mTimeLineList);
        context.startActivity(intent);
    }


    public void add(TimeLineData.ResultEntity mc) {
        mTimeLineList.add(mc);
        notifyItemInserted(mTimeLineList.size() - 1);
    }

    public void addAll(List<TimeLineData.ResultEntity> mcList) {
        for (TimeLineData.ResultEntity mc : mcList) {
            add(mc);
        }
    }

    public void remove(TimeLineData.ResultEntity city) {
        int position = mTimeLineList.indexOf(city);
        if (position > -1) {
            mTimeLineList.remove(position);
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
        TimeLineData.ResultEntity load = new TimeLineData.ResultEntity();
        load.setType(3);
        add(load);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mTimeLineList.size() - 1;
        TimeLineData.ResultEntity item = getItem(position);

        if (item != null) {
            mTimeLineList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public TimeLineData.ResultEntity getItem(int position) {
        return mTimeLineList.get(position);
    }

}


