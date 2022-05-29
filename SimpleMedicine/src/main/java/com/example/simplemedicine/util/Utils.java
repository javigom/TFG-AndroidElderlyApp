package com.example.simplemedicine.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.simplemedicine.model.DateModel;
import com.example.simplemedicine.model.HourModel;
import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.model.NotificationModel;
import com.example.simplemedicine.util.alarm.AlarmReceiver;

import java.util.Calendar;
import java.util.List;

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

    public static int getWeekOfYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static void createAlarm(List<NotificationModel> notificationList, Context context) {

        removeAlarms(context);
        System.out.println("createAlarm");
        int numAlarms = 0;

        for(NotificationModel notification: notificationList) {
            System.out.println(notification);
            if(notification.getStartDate().compareTo(Utils.getTodayDate()) <= 0 && notification.getEndDate().compareTo(Utils.getTodayDate()) >= 0
                    && !notification.isCompleted() && WeekDayEnum.getWeekDayInt(getTodayWeekDay()) <= WeekDayEnum.getWeekDayInt(notification.getWeekDay())) {
                Intent intent = new Intent(context, AlarmReceiver.class);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                PendingIntent pendingIntent;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
                    pendingIntent = PendingIntent.getBroadcast(context, numAlarms, intent, PendingIntent.FLAG_IMMUTABLE);
                else
                    pendingIntent = PendingIntent.getBroadcast(context, numAlarms, intent, 0);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, notification.getHour().getMinutes());
                calendar.set(Calendar.HOUR_OF_DAY, notification.getHour().getHours());
                calendar.add(Calendar.DAY_OF_WEEK, (WeekDayEnum.getWeekDayInt(notification.getWeekDay()) - 1) % 7);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                System.out.println(calendar.getTime() + " numAlarm = " + numAlarms);
                numAlarms++;
            }
        }

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = myPreferences.edit();
        myEditor.putInt("number_alarms", numAlarms);
        myEditor.apply();

    }

    private static void removeAlarms(Context context) {
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int numberAlarms = myPreferences.getInt("number_alarms", 0);
        if(numberAlarms != 0) {
            for(int i = 0; i < numberAlarms; i++) {
                Intent intent = new Intent(context, AlarmReceiver.class);
                PendingIntent pendingIntent;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
                    pendingIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_IMMUTABLE);
                else
                    pendingIntent = PendingIntent.getBroadcast(context, i, intent, 0);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            }
        }
    }

    public static void createAlarmNotification(Medication medication, Context context){
        if(medication.isNotifications()){
            for(HourModel hour: medication.getHours()) {
                for(int i = 0; i < 7; i ++) {
                    Boolean isDayWeek = medication.getWeekDays().get(WeekDayEnum.getWeekDay(i));
                    if(isDayWeek != null && isDayWeek) {
                        Intent intent = new Intent(context, AlarmReceiver.class);
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        PendingIntent pendingIntent;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
                            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                        else
                            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MINUTE, hour.getMinutes());
                        calendar.set(Calendar.HOUR_OF_DAY, hour.getHours());
                        calendar.add(Calendar.DAY_OF_WEEK, i - 1);
                        System.out.println("createAlarmNotification");
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }
                }
            }
        }
    }
}
