package com.example.simplemedicine.provider.room.converter;

import androidx.room.TypeConverter;

import com.example.simplemedicine.model.HourModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class HourConverters {

    @TypeConverter
    public static HourModel fromString(String value) {
        Type listType = new TypeToken<HourModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromHour(HourModel hour) {
        Gson gson = new Gson();
        String json = gson.toJson(hour);
        return json;
    }

}