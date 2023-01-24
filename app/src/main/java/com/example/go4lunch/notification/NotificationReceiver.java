package com.example.go4lunch.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.go4lunch.Model.Notification;
import com.example.go4lunch.Model.RestaurantDetails;
import com.example.go4lunch.Repository.RestaurantRepository;
import com.example.go4lunch.Repository.SettingsRepository;
import com.example.go4lunch.utils.OnResult;

public class NotificationReceiver extends BroadcastReceiver {

    RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
    SettingsRepository settingsRepository = SettingsRepository.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationService notificationService = new NotificationService(context);
        // notificationService.createNotification("essai");
        //TODO Regarder dans les sharedPreferences si on veut la notif ou non et l'afficher si ok
        settingsRepository.enabledNotification(new OnResult<Notification>() {

            @Override
            public void onSuccess(Notification data) {
                notificationService.onCancel();
            }

            @Override
            public void onFailure() {

            }
        });

        restaurantRepository.getEatingRestaurant(new OnResult<RestaurantDetails>() {
            @Override
            public void onSuccess(RestaurantDetails data) {
                notificationService.createNotification(data.getName());
            }

            @Override
            public void onFailure() {
            }
        });

    }

}
