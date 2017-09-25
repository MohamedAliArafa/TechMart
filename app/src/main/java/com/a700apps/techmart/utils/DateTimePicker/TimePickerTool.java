package com.a700apps.techmart.utils.DateTimePicker;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by halima.reda on 8/25/2016.
 */
public class TimePickerTool extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "DatePickerFragment";
    private String from;
    DateTimeInterface dateInterface;
    private int[] selectedDate;
    public TimePickerTool() {
    }

    @SuppressLint("ValidFragment")
    public TimePickerTool(DateTimeInterface dateInterface, String fromStr) {
        from = fromStr;
        this.dateInterface = dateInterface;

    }

    @SuppressLint("ValidFragment")
    public TimePickerTool(int[] selectedDate, DateTimeInterface dateInterface, String fromStr) {
        from = fromStr;
        this.dateInterface = dateInterface;
        this.selectedDate = selectedDate;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        if (selectedDate != null) {
            c.set(selectedDate[0], selectedDate[1], selectedDate[2]);
        }

        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int day = c.get(Calendar.SECOND);
        // Create a new instance of DatePickerDialog and return it
//        return new DatePickerDialog(getActivity(), this, year, month, day);
        return new TimePickerDialog(getActivity(), this, hourOfDay, minute , false);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }
}
