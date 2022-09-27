package com.example.simplemedicine.provider.room.converter;

import androidx.room.TypeConverter;

import com.example.simplemedicine.model.DateModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DateConverters {

    @TypeConverter
    public static DateModel fromString(String value) {
        Type listType = new TypeToken<DateModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromDate(DateModel date) {
        Gson gson = new Gson();
        String json = gson.toJson(date);
        return json;
    }

}