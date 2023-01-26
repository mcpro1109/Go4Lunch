package com.example.go4lunch.Repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.go4lunch.MyApp;
import com.example.go4lunch.notification.NotificationService;

public class SettingsRepository {

    private static SettingsRepository instance;
    SharedPreferences sharedPreferences= MyApp.app.getSharedPreferences("data", Context.MODE_PRIVATE);

    public static SettingsRepository getInstance() {
        if (instance == null) instance = new SettingsRepository();
        return instance;
    }


    public void enableNotification() {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("notification_enabled", true);
        editor.apply();
    }

    public void disableNotification(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("notification_enabled", false);
        editor.apply();
    }

    public boolean isNotificationEnabled() {
        return sharedPreferences.getBoolean("notification_enabled", true);
    }
}
