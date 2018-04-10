package com.makarioworks.app.medmanager.ui.mainview;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.makarioworks.app.medmanager.R;
import com.makarioworks.app.medmanager.model.Medication;
import com.makarioworks.app.medmanager.room.MedicationDatabase;
import com.makarioworks.app.medmanager.room.MedicationDao;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DateFragment.DialogListener, AddMedicationDialogFragment.AddMedicationDialogListener {

    public static final String TAG = "MainActiviy";

    private List<Medication> medicationList;
    private RecyclerView medListRecycler;
    private MedicationListAdapter medListAdapter;

    private TextView infoText;

    private Bundle shareBundles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize views
        initViews();

        // Get all Medication
        getAllMedication();

        //deleteMedication("Test Medic");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_new_action:
                // Add new Medication
                Log.d(TAG, "New Action would be added");

                // Show the pop up dialog to take in the new medication
                addMedicationDialog();

                break;
            case R.id.menu_search_action:
                // search new Medication
                Log.d(TAG, "Search All Medication for the medication");
                break;

            case R.id.menu_clear_medication:
                // Delete the table
                deleteAllMedication();
                break;
            case R.id.menu_profile:
                break;

            case R.id.menu_logout:
                break;
        }
        return true;
    }
    /**
     * ------- Initialize all View
     */
    private void initViews(){
        // Get the textinfo box
        infoText = findViewById(R.id.main_info_tv);
        // Get and Add attributes to the recycler view
        medListRecycler = findViewById(R.id.main_recycler_view);
        medListRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        medListRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }

    /**
     * Handle click events
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }



    /**
     * ---- All DB request using Async Task
     */
    public void addMedication(final Medication medication) {
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                MedicationDatabase.getDatabaseInstance(getApplicationContext()).medicationDao().insertAllMedication(medication);
                getAllMedication();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.d("MainActivity", "Completed input");
            }
        }.execute();
    }

    public void findMedication(String medText) {
        new AsyncTask<String, Void, Medication>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Medication doInBackground(String... strings) {
                return null;
            }

            @Override
            protected void onPostExecute(Medication medication) {
                super.onPostExecute(medication);

            }
        }.execute();
    }

    public void getAllMedication() {
        new AsyncTask<Void, Void, List<Medication>>(){

            @Override
            protected List<Medication> doInBackground(Void... voids) {
                // Make the call
                medicationList = MedicationDatabase.getDatabaseInstance(getApplicationContext()).medicationDao().getAllMedication();
                return medicationList;
            }

            @Override
            protected void onPostExecute(List<Medication> medications) {
                if (medications.isEmpty()){
                    infoText.setVisibility(View.VISIBLE);
                    infoText.setText("No medication yet ...");
                }

                Log.d(TAG, "medication size "+medications.size());
                for (Medication medication : medications) {
//                    testText.setText(medication.getName());
                    // create a new instance of adapter
                    medListAdapter = new MedicationListAdapter(getApplicationContext(), medicationList);
                    // Set the recycler view adapter
                    medListRecycler.setAdapter(medListAdapter);

                    Log.d(TAG, "medication size "+medications);
                }
            }
        }.execute();
    }

    public void deleteAllMedication() {
        new AsyncTask<Void, Void, Medication>() {

            @Override
            protected Medication doInBackground(Void... voids) {
                MedicationDatabase.getDatabaseInstance(getApplicationContext()).medicationDao().deleteTable();
                return null;
            }

            @Override
            protected void onPostExecute(Medication medication) {
                super.onPostExecute(medication);
                Log.v(TAG, "Post execute for delete ALL");
            }
        }.execute();
    }

    public void deleteMedication(final String medicationText) {
        new AsyncTask<Void, Void, Medication>() {

            @Override
            protected Medication doInBackground(Void... voids) {
                // Get the medication with this name
                Medication medic = MedicationDatabase.getDatabaseInstance(getApplicationContext()).medicationDao().findByName(medicationText);

                MedicationDatabase.getDatabaseInstance(getApplicationContext()).medicationDao().delete(medic);
                return medic;
            }

            @Override
            protected void onPostExecute(Medication medication) {
                super.onPostExecute(medication);
                Log.v(TAG, "Post execute for delete ");
            }
        }.execute();
    }

    @Override
    public void passSelectedDate(String date) {

    }

    /**
     * The date object passed in from the date fragment which will be passed back to the
     * AddMedicationFragment
     * @param date
     */

    @Override
    public void passDateObject(Date date) {
        startActivity(new Intent().putExtra("date", date));
        Log.v(TAG, "Date processed: "+date);
    }

    @Override
    public Date selectedDate() {
        return null;
    }

    private void addMedicationDialog() {
        android.support.v4.app.DialogFragment addMedFrag = new AddMedicationDialogFragment();
        addMedFrag.show(getSupportFragmentManager(), "medication");
    }


    @Override
    public void onSubmitClicked(Medication medication) {
        addMedication(medication);
        Log.v(TAG, "Submited new one: "+medication.getName());
        Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void renderDateFragment() {
        android.support.v4.app.DialogFragment dateFragment = new DateFragment();
        dateFragment.show(getSupportFragmentManager(), "datePicker");

        Log.v(TAG, "Show date picker and take in the result of the selection");
    }

    @Override
    public void makeAToast(String s) {
        Toast.makeText(this, "Error: "+s, Toast.LENGTH_SHORT).show();
    }
}
