package com.makarioworks.app.medmanager.model;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by ndu on 4/3/18.
 */

public class Converters {

    @TypeConverter
    public Date fromTimeStamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null){
            return null;
        }else {
            return  date.getTime();
        }
    }

}
