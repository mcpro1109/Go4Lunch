package com.example.go4lunch.Viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Repository.RestaurantRepository;
import com.example.go4lunch.utils.OnResult;

import java.util.ArrayList;
import java.util.UUID;

public class HomeFragmentsViewModel extends ViewModel {

    private MutableLiveData<String> textLiveDataRestaurant = new MutableLiveData<>("text d'essai");

    public LiveData<String> getTextLiveData() {
        return textLiveDataRestaurant;
    }

    public void updateTextRestaurant() {
        textLiveDataRestaurant.setValue(UUID.randomUUID().toString());
    }

    private RestaurantRepository restaurantRepository=RestaurantRepository.getInstance();
    private MutableLiveData<ArrayList<Restaurant>> restaurantList= new MutableLiveData<>();

    public LiveData<ArrayList<Restaurant>> getRestaurantData(){return restaurantList;}

    public void loadRestaurantsList(){
        restaurantRepository.loadRestaurantList(new OnResult<ArrayList<Restaurant>>() {
            @Override
            public void onSuccess(ArrayList<Restaurant> data) {
                restaurantList.setValue(data);
            }
            @Override
            public void onFailure() {

            }
        });
    }
}
