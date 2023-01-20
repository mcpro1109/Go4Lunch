package com.example.go4lunch.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.go4lunch.HomeActivity;
import com.example.go4lunch.Model.EatingWorkmate;
import com.example.go4lunch.MyApp;
import com.example.go4lunch.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class NotificationService  {

    private Context context;
    public final int NOTIFICATION_ID = 123;
    public final String NOTIFICATION_CHANNEL_ID = "123";
    private final String NOTIFICATION_TAG = "FirebaseOC";

    public NotificationService(Context context) {
        this.context = context;
    }

    public void createNotification(String restaurantName) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //notification object
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setSmallIcon(R.drawable.ic_baseline_people_24)
                .setContentTitle("Time to go to lunch to " + restaurantName)
                .setContentText("you eat at ")
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);


        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, notificationBuilder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "Firebase messages";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManagerCompat.createNotificationChannel((channel));
        }
        //show notifications
        notificationManagerCompat.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());

    }

    public void onCancel(){
        NotificationManager notificationManager = (NotificationManager) MyApp.app.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

  /*  private void sendNotificationAtLunch(ArrayList<EatingWorkmate> eatingWorkmates, RemoteMessage remoteMessage) {
        Calendar calendar = Calendar.getInstance();
        // calendar.add(Calendar.MINUTE, -5);
        Date until = calendar.getTime();

        for (EatingWorkmate ew : eatingWorkmates) {
            remoteMessage.getNotification().getBody();
        }
    }

    private void sendVisualNotification(RemoteMessage.Notification notification) {
        //create an Intent to show notif when click
        Intent intent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //create a channel
        String channelID = getString(R.string.default_notification_channel_ID);

        //notification object
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.drawable.ic_baseline_people_24)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Firebase messages";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelID, channelName, importance);
            notificationManager.createNotificationChannel((channel));
        }
        //show notifications
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());

    }*/


}
