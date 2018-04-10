package com.makarioworks.app.medmanager.ui.mainview;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ndu on 4/9/18.
 */

public class DateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    DialogListener mListerner;
    public Date dateObject;

    public interface DialogListener {
        public void passSelectedDate(String date);
        public void passDateObject(Date date);
        public Date selectedDate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListerner = (DialogListener) context;
        }catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement DialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String selectedDate = String.valueOf(dayOfMonth) +"-"+String.valueOf(month)+"-"+String.valueOf(year);
        mListerner.passSelectedDate(selectedDate);
        mListerner.passDateObject(new Date(dayOfMonth, month, year));
//        dateObject = new Date(dayOfMonth, month, year);
    }

    public Date selectedDate() {
        return dateObject;
    }
}
