package com.makarioworks.app.medmanager.ui.mainview;



import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.makarioworks.app.medmanager.R;
import com.makarioworks.app.medmanager.model.Medication;

import java.util.Date;

/**
 * Created by ndu on 4/4/18.
 */

public class AddMedicationDialogFragment extends android.support.v4.app.DialogFragment implements View.OnClickListener {
    public static final String TAG = "AddDialogFragment";

    AddMedicationDialogListener mListener;
    EditText nameText, descText, durationText, startDateEditText, endDateEditText, dateDate;
    Button submitButton,startDate, stopDate;
    Date sDate, eDate;
    Medication medication;

    // Get the date object from the MainActivity
    Intent i;

    boolean dateIsSet = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_medication, null, false);

        // Build the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        i = getActivity().getIntent();

        submitButton = view.findViewById(R.id.dialog_button);
        nameText = view.findViewById(R.id.dialog_name);
        descText = view.findViewById(R.id.dialog_descr);
        durationText = view.findViewById(R.id.dialog_duration);
        startDate = view.findViewById(R.id.dialog_start_date);
        stopDate = view.findViewById(R.id.dialog_stop_date);
        startDateEditText = view.findViewById(R.id.dialog_start_date_editText);
        endDateEditText = view.findViewById(R.id.dialog_end_date_editText);

        submitButton.setOnClickListener(this);
        // Use the Click listener for setDate and stop date buttons
        startDate.setOnClickListener(this);
        stopDate.setOnClickListener(this);

        startDateEditText.setOnClickListener(this);
        endDateEditText.setOnClickListener(this);

        // Set a new Instance of Medication while i set the other attributes using getters & Setters
        medication =  new Medication();
        // The start date is initialized to be the current date
        sDate = new Date();
        eDate = new Date();
        medication.setStart(sDate);
        medication.setEnd(eDate);

        startDateEditText.setText(String.valueOf(sDate));
        endDateEditText.setText(String.valueOf(eDate));

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_button:
                // Check for the fields filled, if true then proceed to register the medication
                // Name, Description and duration of Medication should'nt be empty
                if (nameText.getText().toString().isEmpty() ||
                        descText.getText().toString().isEmpty() ||
                        durationText.getText().toString().isEmpty()){
                    // Notify the user of the unfilled field
                    Log.d(TAG, "Some field(s) is/are Empty");
                    mListener.makeAToast("You left some fields unfilled");

                }else {
                    if (!dateIsSet){
                        mListener.makeAToast("You didn't set the date");
                    }
                    // Validate the Information fed into the medication attributes
                    String name = nameText.getText().toString();
                    String desc = descText.getText().toString();
                    String duration = durationText.getText().toString();

                    // Set the attributes
                    medication.setName(name);
                    medication.setDesc(desc);
                    medication.setFreq(duration);

                    // Send the complete medication object to the activity to be used
                    mListener.onSubmitClicked(medication);
                    Log.d(TAG, "Submitted Successfully");
                }
                Log.d(TAG, "Submission received");
                break;
            case R.id.dialog_start_date:
                // Get the dialog to take in the start date and set the dateIsSet to be true
                showStartDateFragment();
                sDate = (Date) i.getExtras().get("date");

                dateIsSet = true;

                Log.d(TAG, "start button init");
                break;
            case R.id.dialog_stop_date:
                // Show the dialog to take in the end date and set the dateIsSet to be true
                showStartDateFragment();
                eDate = (Date) i.getExtras().get("date");

                Log.d(TAG, "end button init");
                break;
            case R.id.dialog_start_date_editText:
                Log.d(TAG, "start button init");
                break;
            case R.id.dialog_end_date_editText:
                Log.d(TAG, "end button init");
                break;
        }
    }

    private void showStartDateFragment() {
        mListener.renderDateFragment();
    }

    public interface AddMedicationDialogListener{
        public void onSubmitClicked(Medication medication);
        public void renderDateFragment();

        void makeAToast(String s);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddMedicationDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement AddMedicationDialogListener");
        }
    }

    public void handleDateTouch(EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }
}
