package com.a700apps.techmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.GroupTimeLine;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsActivity;
import com.a700apps.techmart.utils.CustomTextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir salah on 9/13/2017.
 */

public class GroupViewPagerAdapter extends PagerAdapter {


    private List<TimeLineData.ResultEntity> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;
    int positionItem;

    public GroupViewPagerAdapter(Context context, List<TimeLineData.ResultEntity> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (imageModelArrayList.size()>5){
            return 5;
        }else {
            return imageModelArrayList.size();
        }
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.sliding_image_viewpager, view, false);
        positionItem = position;

        final TimeLineData.ResultEntity timelineItem = imageModelArrayList.get(position);
        assert imageLayout != null;
        final ImageView mSlideImageView = (ImageView) imageLayout
                .findViewById(R.id.iv_slider);
        ImageView mCalenderImageView = (ImageView) imageLayout
                .findViewById(R.id.imageView);


        TextView mTime = (TextView) imageLayout.findViewById(R.id.tv_time);
        TextView mTitle = (CustomTextView) imageLayout.findViewById(R.id.tv_events);
        TextView mDesc = (CustomTextView) imageLayout.findViewById(R.id.tv_events_title);
        TextView mAttendees = (TextView) imageLayout.findViewById(R.id.imageView25);


        mDesc.setText(timelineItem.getTitle());
        if (timelineItem.getType() == 1) {
            mTitle.setText("Event");
            mTime.setVisibility(View.VISIBLE);
            mCalenderImageView.setVisibility(View.VISIBLE);
//            if (String.valueOf(timelineItem.getAttendantCount())!=null){
//                mAttendees.setVisibility(View.VISIBLE);
//
//                mAttendees.setText(String.valueOf(timelineItem.getAttendantCount()+"\n"+"Attendees"));
//            }else {
//                mAttendees.setVisibility(View.GONE);
            }
//        } else
            if (timelineItem.getType() == 2) {
            mTitle.setText("Post");

            mTime.setVisibility(View.GONE);
            mCalenderImageView.setVisibility(View.GONE);
        }

        Glide.with(context)
                .load(MainApi.IMAGE_IP + timelineItem.getImage())
                .into(mSlideImageView);

        view.addView(imageLayout, 0);

        return imageLayout;
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}