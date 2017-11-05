package com.a700apps.techmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.Globals;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by samir salah on 9/11/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<TimeLineData.ResultEntity> mTimeLineList;
    Context context;
    private static final int NOTIF_TYPE_EVENT = 1;


    public EventAdapter(Context context, List<TimeLineData.ResultEntity> TimeLineList) {
        this.context = context;
        this.mTimeLineList = TimeLineList;

    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        TimeLineData.ResultEntity timeLineItem = mTimeLineList.get(position);
        final int itemType = getItemViewType(position);
        Log.e("timeLineItem.getType()", timeLineItem.getType() + "");

        switch (itemType) {
            case NOTIF_TYPE_EVENT:
                ViewHolder viewHolderEvent = (ViewHolder) viewHolder;
                viewHolderEvent.mDateTextView.setText(timeLineItem.getStartDate());
                viewHolderEvent.mDescribtionTextView.setText(timeLineItem.getDescr());
                viewHolderEvent.mTitleTextView.setText(timeLineItem.getTitle());

                Glide.with(context)
                        .load(MainApi.IMAGE_IP + timeLineItem.getImage()).placeholder(R.drawable.placeholder)
                        .into(viewHolderEvent.mEventImageView);

                viewHolderEvent.contain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Globals.R_Index = position;

                        openDetails(context, "Event", mTimeLineList, position);
                    }
                });
                break;

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView;
//        switch (viewType) {
//            case NOTIF_TYPE_EVENT:
        noteView = inflater.inflate(R.layout.relative_events_item, parent, false);
        return new ViewHolder(noteView);


//        }
//        return null;
//
    }


    @Override
    public int getItemViewType(int position) {
        int type = position;
        Log.e("type", mTimeLineList.get(position).getType() + "");
        switch (mTimeLineList.get(position).getType()) {
            case 1:
                return NOTIF_TYPE_EVENT;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        Log.e("test", mTimeLineList.size() + "");
        return mTimeLineList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mEventImageView, shareBtn, addCalenderBtn;
        TextView mTitleTextView, mDescribtionTextView, mDateTextView, mGroupNameTextView, tv_add_calender,tv_share;
        RelativeLayout contain;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_add_calender = (TextView) itemView.findViewById(R.id.tv_add_calender);
            tv_share = (TextView) itemView.findViewById(R.id.tv_share);

            mEventImageView = (ImageView) itemView.findViewById(R.id.iv_event);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            mDescribtionTextView = (TextView) itemView.findViewById(R.id.tv_description);
            mDateTextView = (TextView) itemView.findViewById(R.id.tv_date);
            contain = (RelativeLayout) itemView.findViewById(R.id.contain);
            ActivityUtils.applyLightFont(mDescribtionTextView);
            ActivityUtils.applyLightFont(tv_add_calender);
            ActivityUtils.applyLightFont(tv_share);
            shareBtn = (ImageView) itemView.findViewById(R.id.iv_share);
            addCalenderBtn = (ImageView) itemView.findViewById(R.id.iv_add_calender);
            itemView.setOnClickListener(this);
            shareBtn.setOnClickListener(this);
            addCalenderBtn.setOnClickListener(this);
            tv_add_calender.setOnClickListener(this);
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
                case R.id.tv_add_calender:
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

//        ActivityUtils.openActivity(context, DetailsActivity.class, false);
    }
}



