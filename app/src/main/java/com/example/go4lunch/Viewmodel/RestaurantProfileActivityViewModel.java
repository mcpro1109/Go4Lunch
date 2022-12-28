package com.example.go4lunch.Viewmodel;

import android.os.Handler;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.EatingWorkmate;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Model.RestaurantDetails;
import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.R;
import com.example.go4lunch.Repository.RestaurantRepository;
import com.example.go4lunch.Repository.WorkmateRepository;
import com.example.go4lunch.utils.OnResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RestaurantProfileActivityViewModel extends ViewModel {

    private RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
    private WorkmateRepository workmateRepository=WorkmateRepository.getInstance();

    private MutableLiveData<Restaurant> restaurant = new MutableLiveData<>();
    public LiveData<Restaurant> getRestaurant() {
        return restaurant;
    }

    private MutableLiveData<RestaurantDetails> restaurantDetail = new MutableLiveData<>();
    public LiveData<RestaurantDetails> getRestaurantData() {
        return restaurantDetail;
    }

    private MutableLiveData<ArrayList<Workmate>> workmates = new MutableLiveData<>(new ArrayList<>());
    public LiveData<ArrayList<Workmate>> getWorkmateData() {return workmates;}

    public LiveData<Integer> fabBackground = Transformations.map(workmates, input -> {
        if(isEating(input)){
            return R.color.green;
        }
        else {
            return R.color.red;
        }
    });

    public void getDetailsWithPlaceId(String placeId) {
        restaurantRepository.getDetails(placeId, new OnResult<RestaurantDetails>() {
            @Override
            public void onSuccess(RestaurantDetails data) {
                final Handler handler = new Handler();
                handler.postDelayed(() -> restaurantDetail.setValue(data), 3000);
            }
            @Override
            public void onFailure() {
                restaurantDetail.postValue(null);
            }
        });
    }

    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void toggleEat() {
        OnResult<Void> onResult = new OnResult<Void>() {
            @Override
            public void onSuccess(Void data) {
                reloadWorkmates();
            }
            @Override
            public void onFailure() {}
        };

       if (isEating(workmates.getValue())){
           workmateRepository.removeEating(getCurrentUser().getUid(), onResult);
       } else {
           workmateRepository.addEating(getCurrentUser().getUid(), restaurant.getValue().getId(), onResult);
       }
    }

    private boolean isEating(ArrayList<Workmate> workmates){
        boolean result = false;
        for (Workmate w: workmates){
           if (getCurrentUser().getUid().equals(w.getId())){
                result = true;
            }
        }
        return result;
    }

    public void reloadWorkmates(){
        workmateRepository.loadWorkmatesForRestaurant(restaurant.getValue(), new OnResult<ArrayList<Workmate>>() {
            @Override
            public void onSuccess(ArrayList<Workmate> data) {
                workmates.postValue(data);
            }

            @Override
            public void onFailure() {}
        });
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant.setValue(restaurant);
        getDetailsWithPlaceId(restaurant.getId());
        reloadWorkmates();
    }
}
