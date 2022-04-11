package com.example.simplemedicine.util;

import androidx.annotation.NonNull;

import com.example.simplemedicine.model.Hour;

import java.util.Comparator;

public class HourComparator implements Comparator<Hour> {

    @Override
    public int compare(@NonNull Hour o1, @NonNull Hour o2) {
        if(o1.getHours() != o2.getHours())
            return (o1.getHours() > o2.getHours()? 1: -1);
        else
            return (o1.getMinutes() > o2.getMinutes()? 1: -1);
    }
}