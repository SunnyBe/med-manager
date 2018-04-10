package com.makarioworks.app.medmanager.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.makarioworks.app.medmanager.model.Converters;
import com.makarioworks.app.medmanager.model.Medication;

/**
 * Created by ndu on 4/1/18.
 */

@Database(entities = {Medication.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MedicationDatabase extends RoomDatabase {

    public static MedicationDatabase instance;
    public  abstract MedicationDao medicationDao();

    public static MedicationDatabase getDatabaseInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                            MedicationDatabase.class, "medication-db")
                            .allowMainThreadQueries()
                            .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
