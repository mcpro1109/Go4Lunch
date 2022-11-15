package com.example.go4lunch.Viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.RestaurantDetails;
import com.example.go4lunch.Repository.RestaurantRepository;
import com.example.go4lunch.utils.OnResult;

import java.util.Objects;

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
                restaurantDetail.setValue(data);

            }

            @Override
            public void onFailure() {

            }
        });
    }

    public void updateRestaurantDetail() {
        //  getDetailsWithPlaceId(restaurantDetail.getValue().getId());
        if (restaurantDetail.getValue() != null) {
            getDetailsWithPlaceId(restaurantDetail.getValue().getId());
        }
        //restaurantDetail.getValue();

    }
}
