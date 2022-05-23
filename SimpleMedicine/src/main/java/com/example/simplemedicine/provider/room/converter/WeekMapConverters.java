package com.example.simplemedicine.provider.room.converter;

import androidx.room.TypeConverter;

import com.example.simplemedicine.util.WeekDayEnum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class WeekMapConverters {

    @TypeConverter
    public static Map<WeekDayEnum, Boolean> fromString(String value) {
        Type listType = new TypeToken<Map<WeekDayEnum, Boolean>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromMap(Map<WeekDayEnum, Boolean> map) {
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return json;
    }

}
