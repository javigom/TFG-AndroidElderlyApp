package com.example.simplemedicine.provider.room.converter;

import androidx.room.TypeConverter;

import com.example.simplemedicine.model.HourModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class HourListConverters {

    @TypeConverter
    public static List<HourModel> fromString(String value) {
        Type listType = new TypeToken<List<HourModel>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<HourModel> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}