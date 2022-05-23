package com.example.simplemedicine.util.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.simplemedicine.R;
import com.example.simplemedicine.model.HourModel;
import com.example.simplemedicine.model.NotificationModel;
import com.example.simplemedicine.provider.room.dao.NotificationDao;
import com.example.simplemedicine.provider.room.repository.Repository;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "prueba")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("¡Hora de tomar la medicación!")
                .setContentText("Medicacion de prueba")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
    }
}
