package com.example.go4lunch.notification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.go4lunch.HomeActivity;
import com.example.go4lunch.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class NotificationService extends FirebaseMessagingService {

    public final int NOTIFICATION_ID=123;
    private final String NOTIFICATION_TAG="FirebaseOC";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification()!=null){
            //get message from Firebase
            RemoteMessage.Notification notification= remoteMessage.getNotification();
            sendVisualNotification(notification);
            Log.e("essai_notif", notification.getBody());
        }
    }

    private void sendVisualNotification(RemoteMessage.Notification notification) {
        //create an Intent to show notif when click
        Intent intent=new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_ONE_SHOT);

       //create a channel
        String channelID= getString(R.string.default_notification_channel_ID);

        //notification object
        NotificationCompat.Builder notificationBuilder= new NotificationCompat.Builder(this,channelID)
                .setSmallIcon(R.drawable.ic_baseline_people_24)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            CharSequence channelName= "Firebase messages";
            int importance=NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel= new NotificationChannel(channelID,channelName,importance);
            notificationManager.createNotificationChannel((channel));
        }
        //show notifications
        notificationManager.notify(NOTIFICATION_TAG,NOTIFICATION_ID, notificationBuilder.build());

    }


}
