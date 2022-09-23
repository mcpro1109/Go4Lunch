package com.example.go4lunch.Viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Restaurant;

import java.util.ArrayList;
import java.util.UUID;

public class WorkmateViewModel extends ViewModel {

    private MutableLiveData<String> textLiveData = new MutableLiveData<>("texte");

    public LiveData<String> getTextLiveData() {
        return textLiveData;
    }

    public void updateText() {
        textLiveData.setValue(UUID.randomUUID().toString());
    }
}