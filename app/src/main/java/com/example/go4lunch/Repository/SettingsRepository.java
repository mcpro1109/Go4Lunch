package com.example.go4lunch.Repository;

import com.example.go4lunch.Model.Notification;
import com.example.go4lunch.notification.NotificationService;
import com.example.go4lunch.utils.OnResult;

public class SettingsRepository {

    private static SettingsRepository instance;
    NotificationService notificationService;


    public static SettingsRepository getInstance() {
        if (instance == null) instance = new SettingsRepository();
        return instance;
    }


    public void enabledNotification(OnResult<Notification> notificationOnResult) {
        boolean isEnabled=true;
       if (!isEnabled){
           notificationService.onCancel();
           isEnabled=false;

       }

    }
}
