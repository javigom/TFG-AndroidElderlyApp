package com.example.simplemedicine.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.simplemedicine.model.DateModel;
import com.example.simplemedicine.model.HourModel;
import com.example.simplemedicine.util.alarm.AlarmReceiver;

import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static DateModel getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        return new DateModel(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static WeekDayEnum getTodayWeekDay() {
        Calendar calendar = Calendar.getInstance();
        int weekday = (calendar.get(Calendar.DAY_OF_WEEK) - 2) % 7;
        return WeekDayEnum.getWeekDay(weekday);
    }

    public static void createAlarm(HourModel hour, Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour.getHours());
        calendar.set(Calendar.MINUTE, hour.getMinutes());
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent  pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
    }
}
