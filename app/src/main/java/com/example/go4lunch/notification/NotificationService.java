package com.example.go4lunch.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.os.Build;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
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
        String title="Time to go to lunch ";
        SpannableStringBuilder titleShow=new SpannableStringBuilder(title);
        titleShow.setSpan(new StyleSpan(Typeface.BOLD), 0, titleShow.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setSmallIcon(R.drawable.ic_baseline_people_24)
                .setContentTitle(titleShow)
                .setContentText("you eat at : " +restaurantName)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);


        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);

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
}
