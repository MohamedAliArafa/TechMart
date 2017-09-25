package com.a700apps.techmart.utils.DateTimePicker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by halima.reda on 8/25/2016.
 */
public class DatePickerTool extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "DatePickerFragment";
    private String from;
    DateTimeInterface dateInterface;
    private int[] selectedDate;
    private int ODateFrame;
    private long minDate;
    private long maxDate;
    private long currentDate;

    public DatePickerTool() {
    }

    @SuppressLint("ValidFragment")
    public DatePickerTool(DateTimeInterface dateInterface, String fromStr) {
        from = fromStr;
        this.dateInterface = dateInterface;

    }

    @SuppressLint("ValidFragment")
    public DatePickerTool(int[] selectedDate, DateTimeInterface dateInterface, String fromStr) {
        from = fromStr;
        this.dateInterface = dateInterface;
        this.selectedDate = selectedDate;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        if (selectedDate != null) {
            c.set(selectedDate[2], selectedDate[1], selectedDate[0]);
        }
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }


    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Log.e(TAG, "onDateSet");
        String date = (year + "-" + (month + 1) + "-" + day);
        Log.e(TAG, date);
        dateInterface.getDateFromPicker(from, year, (month + 1), day, date, true);
//        currentDate = convertDate(day + "/" + (month + 1) + "/" + year);
//        if (currentDate > minDate && currentDate <= maxDate) {
//            dateInterface.getDateFromPicker(from, year, (month + 1), day, date,true);
//        } else {
//            Log.d(TAG, "no change");
//            dateInterface.getDateFromPicker(from, year, (month + 1), day, date,false);
//        }
    }

    public static long convertDate(String dateFormat) {
        long startDate = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateFormat);

            startDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

}
