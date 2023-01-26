package com.example.go4lunch.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.go4lunch.Model.RestaurantDetails;
import com.example.go4lunch.Repository.RestaurantRepository;
import com.example.go4lunch.Repository.SettingsRepository;
import com.example.go4lunch.utils.OnResult;

public class NotificationReceiver extends BroadcastReceiver {

    RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
    SettingsRepository settingsRepository = SettingsRepository.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {

        //notification if workmate is eating
        if (!settingsRepository.isNotificationEnabled()) return;
        restaurantRepository.getEatingRestaurant(new OnResult<RestaurantDetails>() {
            @Override
            public void onSuccess(RestaurantDetails data) {
                NotificationService notificationService = new NotificationService(context);
                notificationService.createNotification(data.getName());
            }

            @Override
            public void onFailure() {
            }
        });

    }

}
