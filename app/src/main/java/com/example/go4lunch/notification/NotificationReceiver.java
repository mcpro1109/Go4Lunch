package com.example.go4lunch.notification;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationService notificationService = new NotificationService(context);

        //TODO Regarder dans les sharedPreferences si on veut la notif ou non et l'afficher si ok
        //TODO Regarder si on manger dans un restaurant aujourd'hui et si oui l'afficher

        notificationService.createNotification();

    }

}
