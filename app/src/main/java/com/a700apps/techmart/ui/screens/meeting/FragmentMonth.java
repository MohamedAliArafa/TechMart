package com.a700apps.techmart.ui.screens.meeting;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.AllSchedualList;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMonth extends Fragment implements MettingView {

    private MettingPressenter presenter;
    private RecyclerView schedualMonthList;
    private ImageView eventImage;
    private TextView titleTextView;
    private TextView descTextView;
    private TextView dateTextView;
    private TextView noEvent;
    private ImageView favoritImageView;
    private ImageView deleteImageView;
    private Button showMapImageView;
    private ConstraintLayout constraintLayout;
    private String currentDay, dayAfterMonth;
    public AVLoadingIndicatorView indicatorView;

    public FragmentMonth() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_month, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);

    }

    private void findViews(View view) {
        schedualMonthList = (RecyclerView) view.findViewById(R.id.recyclerView);
        noEvent = (TextView) view.findViewById(R.id.no_event);
        schedualMonthList.setLayoutManager(new LinearLayoutManager(getActivity()));
        constraintLayout = (ConstraintLayout) view.findViewById(R.id.custom_layout);
        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        findCustomView(view);
        presenter = new MettingPressenter();
        presenter.attachView(this);
        getDate();
        presenter.getSchedual(getActivity(), PreferenceHelper.getUserId(getActivity()), currentDay, dayAfterMonth);
    }


    private void getDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        currentDay = year + "-" + month + "-" + day;
        Log.e("CurrentDay", currentDay);

        int afterMonth = month + 1;
        cal.set(year, afterMonth, day);
        int _currentDay = cal.get(Calendar.DAY_OF_MONTH);
        int _currentYear = cal.get(Calendar.YEAR);
        int _currentMonth = cal.get(Calendar.MONTH);

        dayAfterMonth = _currentYear + "-" + _currentMonth + "-" + _currentDay;
        Log.e("dayAfterMonth", dayAfterMonth);
    }


    private void findCustomView(View view) {
        eventImage = (ImageView) view.findViewById(R.id.event_image);
        titleTextView = (TextView) view.findViewById(R.id.title_text_view);
        descTextView = (TextView) view.findViewById(R.id.desc_text_view);
        dateTextView = (TextView) view.findViewById(R.id.date_text_view);
        favoritImageView = (ImageView) view.findViewById(R.id.favorit_image_view);
        deleteImageView = (ImageView) view.findViewById(R.id.delete_image_view);
        showMapImageView = (Button) view.findViewById(R.id.show_map_image_view);

    }


    @Override
    public void drawUiData(List<AllSchedualList.ResultEntity> schedualList) {
        constraintLayout.setVisibility(View.VISIBLE);
        setFirstItem(schedualList);

        MettingDaysAdapter mettingDaysAdapter = new MettingDaysAdapter(getActivity(), schedualList);
        schedualMonthList.setAdapter(mettingDaysAdapter);
    }

    @Override
    public void showProgress() {
        indicatorView.setVisibility(View.VISIBLE);
        indicatorView.show();

    }

    @Override
    public void dismissProgress() {
        indicatorView.hide();

    }

    @Override
    public void noEvent() {
        noEvent.setVisibility(View.VISIBLE);
    }

    private void setFirstItem(final List<AllSchedualList.ResultEntity> schedualList) {
        Glide.with(getActivity()).load(MainApi.IMAGE_IP + schedualList.get(0).getImage())
                .into(eventImage);
        titleTextView.setText(schedualList.get(0).getTitle());
        descTextView.setText(schedualList.get(0).getDescr());
        showMapImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show external map

                if (schedualList.get(0).getLatitude() > 0 && schedualList.get(0).getLongtude() > 0) {
                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", schedualList.get(0).getLatitude(), schedualList.get(0).getLongtude());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    getActivity().startActivity(intent);
                }
            }
        });

    }


    private static class MettingDaysAdapter extends RecyclerView.Adapter<MettingDaysAdapter.ViewHolder> {

        private Context context;
        private List<AllSchedualList.ResultEntity> schedualList;

        public MettingDaysAdapter(Context context) {
            this.context = context;
        }

        public MettingDaysAdapter(Context context, List<AllSchedualList.ResultEntity> schedualList) {
            this.context = context;
            this.schedualList = schedualList;
        }

        @Override
        public MettingDaysAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.meeting_recycler_item, parent, false);
            return new MettingDaysAdapter.ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(MettingDaysAdapter.ViewHolder viewHolder, int position) {
            viewHolder.titleTextView.setText(schedualList.get(position).getTitle());
            viewHolder.descrTextView.setText(schedualList.get(position).getDescr());
            String string = schedualList.get(position).getStartDate();
            Calendar calender = Calendar.getInstance();
            int day = 0,Month=0;
            String monthString ="";
            try {
                calender.setTime(new SimpleDateFormat("yyyy-MM-d").parse(string));
//                calender.add(Calendar.MONTH,1);
                day = calender.get(Calendar.DAY_OF_MONTH);
                Month=  calender.get(Calendar.MONTH);
                 monthString = new DateFormatSymbols().getMonths()[Month];
                Log.e("monthString", monthString);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            viewHolder.textView56.setText(String.valueOf(day)+"\n"+monthString);


        }

        @Override
        public int getItemCount() {
            return schedualList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private LinearLayout linearLayout9;
            private TextView textView56;
            private TextView textView59;
            private TextView titleTextView;
            private TextView descrTextView;

            public ViewHolder(View view) {
                super(view);
                linearLayout9 = (LinearLayout) view.findViewById(R.id.linearLayout9);
                textView56 = (TextView) view.findViewById(R.id.textView56);
                textView59 = (TextView) view.findViewById(R.id.textView59);
                titleTextView = (TextView) view.findViewById(R.id.title_text_view);
                descrTextView = (TextView) view.findViewById(R.id.Descr_text_view);
            }
        }
    }

}