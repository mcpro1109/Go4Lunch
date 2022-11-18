package com.example.go4lunch.Viewmodel;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.RestaurantDetails;
import com.example.go4lunch.Repository.RestaurantRepository;
import com.example.go4lunch.utils.OnResult;

public class RestaurantProfileActivityViewModel extends ViewModel {

    private RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();

    private MutableLiveData<RestaurantDetails> restaurantDetail = new MutableLiveData<>();

    public LiveData<RestaurantDetails> getRestaurantData() {
        return restaurantDetail;
    }

    public void getDetailsWithPlaceId(String placeId) {
        restaurantRepository.getDetails(placeId, new OnResult<RestaurantDetails>() {
            @Override
            public void onSuccess(RestaurantDetails data) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        restaurantDetail.setValue(data);
                    }
                }, 3000);
            }

            @Override
            public void onFailure() {
                restaurantDetail.postValue(null);
            }
        });
    }
}
