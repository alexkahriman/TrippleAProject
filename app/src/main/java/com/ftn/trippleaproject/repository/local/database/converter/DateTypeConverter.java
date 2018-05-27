package com.ftn.trippleaproject.repository.local.database.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateTypeConverter {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
