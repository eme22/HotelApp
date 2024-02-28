package com.eme22.pc1app.data.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

public class CustomTimePickerDialog extends TimePickerDialog {

    private final OnTimeSetListener mTimeSetListener;

    public CustomTimePickerDialog(Context context, OnTimeSetListener listener,
                                  int hourOfDay, int minute, boolean is24HourView) {
        super(context, listener::onTimeSet, hourOfDay,
                minute, is24HourView);
        mTimeSetListener = listener;
    }



    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        super.onTimeChanged(view, hourOfDay, minute);
        mTimeSetListener.onTimeChanged(view, hourOfDay, minute);
    }

    public interface OnTimeSetListener {
        void onTimeSet(TimePicker view, int hourOfDay, int minute);
        void onTimeChanged(TimePicker view, int hourOfDay, int minute);
    }

}