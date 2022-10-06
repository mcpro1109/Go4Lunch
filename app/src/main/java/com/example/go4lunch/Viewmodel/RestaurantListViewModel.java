package com.example.go4lunch.Viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Repository.RestaurantRepository;
import com.example.go4lunch.utils.OnResult;

import java.util.ArrayList;

public class RestaurantListViewModel extends ViewModel {

    private RestaurantRepository restaurantRepository=RestaurantRepository.getInstance();
    private MutableLiveData<ArrayList<Restaurant>> restaurantList= new MutableLiveData<>();

    public LiveData<ArrayList<Restaurant>> getRestaurantData(){return restaurantList;}

}