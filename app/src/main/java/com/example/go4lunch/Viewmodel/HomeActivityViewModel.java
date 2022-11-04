package com.example.go4lunch.Viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Repository.RestaurantRepository;
import com.example.go4lunch.utils.LocationBuilder;
import com.example.go4lunch.utils.OnResult;

import java.util.ArrayList;

public class HomeActivityViewModel extends ViewModel {

    private RestaurantRepository restaurantRepository=RestaurantRepository.getInstance();
    private MutableLiveData<ArrayList<Restaurant>> restaurantList= new MutableLiveData<>(new ArrayList<>());

    public LiveData<ArrayList<Restaurant>> getRestaurantData(){ return restaurantList; }

    public void start()  {
        loadRestaurantsList();
    }

    public void loadRestaurantsList(){
        if(restaurantList.getValue().isEmpty()){
            restaurantRepository
                    .loadRestaurantList(LocationBuilder.create( 48.86325926951382, 2.354244777246452), new OnResult<ArrayList<Restaurant>>() {
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

    public Restaurant getRestaurantByTag(String tag) {
        for(Restaurant restaurant : getRestaurantData().getValue()){
            if(restaurant.getName().equals(tag)) return restaurant;
        }
        return null;
    }
}
