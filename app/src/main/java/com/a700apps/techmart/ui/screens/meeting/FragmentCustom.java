package com.a700apps.techmart.ui.screens.meeting;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.AllSchedualList;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.utils.DateTimePicker.DatePickerTool;
import com.a700apps.techmart.utils.DateTimePicker.DateTimeInterface;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.Validator;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCustom extends Fragment implements MettingView, DateTimeInterface {
    private MettingPressenter presenter;
    private RecyclerView schedualCustomList;
    private TextView fromDateTextView;
    private TextView noEvent;
    private TextView toDateTextView;
    private Button searchButton;
    private String dateFrom, dateTo;
    private int[] dateFromInts, dateToInts;
    public AVLoadingIndicatorView indicatorView;


    public FragmentCustom() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);

    }

    private void findViews(View view) {
        schedualCustomList = (RecyclerView) view.findViewById(R.id.recyclerView);
        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        noEvent = (TextView) view.findViewById(R.id.no_event);
        findCustomView(view);
        schedualCustomList.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter = new MettingPressenter();
        presenter.attachView(this);


    }

    private void findCustomView(View view) {
        fromDateTextView = (TextView) view.findViewById(R.id.from_date_text_view);
        toDateTextView = (TextView) view.findViewById(R.id.to_date_text_view);
        searchButton = (Button) view.findViewById(R.id.search_button);
        dateFromInts = new int[3];
        dateToInts = new int[3];
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dateFrom != null && dateTo != null) {
                    if (Validator.checkSecondDateBiggerEqual(dateFromInts, dateToInts)) {
                        presenter.getSchedual(getActivity(), PreferenceHelper.getUserId(getActivity()), dateFrom, dateTo);
                    } else {
                        Toast.makeText(getActivity(), "Please Choose Valid Date", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please Fill All Date", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fromDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pickerBirthDate[] = new int[3];
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                pickerBirthDate[0] = day;
                pickerBirthDate[1] = month;
                pickerBirthDate[2] = year;
                showDatePickerDialog(pickerBirthDate, "dateFrom");
            }
        });
        toDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pickerBirthDate[] = new int[3];
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                pickerBirthDate[0] = day;
                pickerBirthDate[1] = month;
                pickerBirthDate[2] = year;
                showDatePickerDialog(pickerBirthDate, "dateTo");
            }
        });

    }

    public void showDatePickerDialog(int[] selectedDate, String dateFrom) {
        DialogFragment newFragment = new DatePickerTool(selectedDate, this, dateFrom);
        newFragment.show(getActivity().getSupportFragmentManager(), MainApi.TAG_DATE_PICKER);
    }

    @Override
    public void drawUiData(List<AllSchedualList.ResultEntity> schedualList) {
        MettingDaysAdapter mettingDaysAdapter = new MettingDaysAdapter(getActivity(), schedualList);
        schedualCustomList.setAdapter(mettingDaysAdapter);
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

    @Override
    public void getDateFromPicker(String fromString, int year, int month, int day, String date, boolean isDone) {
        switch (fromString) {
            case "dateFrom":
                Log.d("Date", date);
                if (isDone) {
                    dateFrom = date;
                    dateFromInts[0] = day;
                    dateFromInts[1] = month;
                    dateFromInts[2] = year;
                    fromDateTextView.setText(date);
                    Log.e("from Date ", fromDateTextView.getText().toString());

                }


                break;
            case "dateTo":
                Log.d("Date", date);
                if (isDone) {
                    dateTo = date;
                    dateToInts[0] = day;
                    dateToInts[1] = month;
                    dateToInts[2] = year;
                    toDateTextView.setText(date);
                    Log.e("from Date ", toDateTextView.getText().toString());

                }


                break;
        }
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
            String monthString ="";

            Calendar calender = Calendar.getInstance();

            int day = 0,Month=0;
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
