package com.makarioworks.app.medmanager.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.makarioworks.app.medmanager.model.Medication;

import java.util.List;

/**
 * Created by ndu on 4/1/18.
 */
@Dao
public interface MedicationDao {
    public final String db = "medication_db";
    // Get all medications from medication table
    @Query("SELECT * FROM medication")
    List<Medication> getAllMedication();

    // Fetch a medication by name from the medication table
    @Query("SELECT * FROM medication WHERE name LIKE :name")
    Medication findByName(String name);

    // Fetch medications by duration from the medication table
    @Query("SELECT * FROM medication WHERE frequency LIKE :freq")
    List<Medication> getMedicationByFreq(String freq);


    @Query("SELECT COUNT(*) from medication")
    int countMedications();

    @Insert
    void insertAllMedication(Medication... medications);

    @Delete
    void delete(Medication medication);

    @Query("DELETE FROM medication")
    void deleteTable();



}
