package com.example.go4lunch.Viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Repository.RestaurantRepository;
import com.example.go4lunch.Repository.SettingsRepository;
import com.example.go4lunch.utils.LocationBuilder;
import com.example.go4lunch.utils.OnResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class SettingsActivityViewModel extends ViewModel {

    private SettingsRepository settingsRepository = SettingsRepository.getInstance();
    private MutableLiveData<Boolean> notificationEnabled = new MutableLiveData<>(settingsRepository.isNotificationEnabled());

    public LiveData<Boolean> getNotificationData() {
        return notificationEnabled;
    }


    public void setNotificationEnabled(boolean isChecked) {
        if (isChecked){
            settingsRepository.enableNotification();
        }else{
            settingsRepository.disableNotification();
        }
        notificationEnabled.setValue(isChecked);
    }
}
