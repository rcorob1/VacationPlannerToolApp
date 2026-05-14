package com.example.vacationplannertool;

import static android.content.Context.NOTIFICATION_SERVICE;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel("vptChannel", "Vacation Alert", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Alerts for scheduled vacations");
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "vptChannel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Vacation Alert!")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        if(notificationManager != null) {
            try {
                notificationManager.notify((int) (System.currentTimeMillis() + 5000), builder.build());
            } catch (SecurityException e) {
                Toast.makeText(context, "Notification permission not granted. Please enable it in settings.", Toast.LENGTH_LONG).show();
            }
        }


    }



}
