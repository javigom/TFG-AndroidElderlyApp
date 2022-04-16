package com.example.simplemedicine.util.converter;

import androidx.room.TypeConverter;

import com.example.simplemedicine.model.medication.WeekDaysEnum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class WeekMapConverters {

    @TypeConverter
    public static Map<WeekDaysEnum, Boolean> fromString(String value) {
        Type listType = new TypeToken<Map<WeekDaysEnum, Boolean>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromMap(Map<WeekDaysEnum, Boolean> map) {
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return json;
    }

}
