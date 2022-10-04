package com.example.go4lunch.Viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.UUID;

public class HomeFragmentsViewModel extends ViewModel {

    private MutableLiveData<String> textLiveDataRestaurant = new MutableLiveData<>("text d'essai");

    public LiveData<String> getTextLiveData() {
        return textLiveDataRestaurant;
    }

    public void updateTextRestaurant() {
        textLiveDataRestaurant.setValue(UUID.randomUUID().toString());
    }

}
