package com.haetech.smokecounter.database;

import androidx.room.TypeConverter;

import java.sql.Date;

public class Converters {
    @TypeConverter
    public static Date toDate(Long l){
        return l == null ? null: new Date(l);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null: date.getTime();
    }
}