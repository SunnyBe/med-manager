package com.makarioworks.app.medmanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by ndu on 3/31/18.
 */
@Entity(tableName = "medication")
public class Medication {
    // Attributes of a medication
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;  // The id of the medication

    @ColumnInfo(name = "name")
    public String name; // The name of the medication

    @ColumnInfo(name = "description")
    public String desc;    // The description of the medication

    @ColumnInfo(name = "frequency")
    public String freq;    // The frequency for taking the medication

    @ColumnInfo(name = "start_time")
    public Date start;     // The starting time for the drug

    @ColumnInfo(name = "end_time")
    public Date end;       // The ending time of the drug

    // Empty Constructor
    public Medication(){
        this.start = new Date();
    }

    // Other Constructor
    public Medication(String name, String desc, String freq, Date start, Date end) {
        this.name = name;
        this.desc = desc;
        this.freq = freq;
        this.start = start;
        this.end = end;
    }

    // Other Constructor
//    public Medication(int id, String name, String desc, String freq, Date start, Date end) {
//        this.id = id;
//        this.name = name;
//        this.desc = desc;
//        this.freq = freq;
//        this.start = start;
//        this.end = end;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
