package com.example.go4lunch.Viewmodel;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Like;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Model.RestaurantDetails;
import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.R;
import com.example.go4lunch.Repository.LikeRepository;
import com.example.go4lunch.Repository.RestaurantRepository;
import com.example.go4lunch.Repository.WorkmateRepository;
import com.example.go4lunch.utils.CombinedLiveData2;
import com.example.go4lunch.utils.OnResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RestaurantProfileActivityViewModel extends ViewModel {

    private RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
    private WorkmateRepository workmateRepository = WorkmateRepository.getInstance();
    private LikeRepository likeRepository = LikeRepository.getInstance();

    private MutableLiveData<ArrayList<Workmate>> workmates = new MutableLiveData<>(new ArrayList<>());

    public LiveData<ArrayList<Workmate>> getWorkmateData() {
        return workmates;
    }

    private MutableLiveData<Restaurant> restaurant = new MutableLiveData<>();

    public LiveData<Restaurant> getRestaurant() {
        return restaurant;
    }

    private MutableLiveData<RestaurantDetails> restaurantDetail = new MutableLiveData<>();

    public LiveData<RestaurantDetails> getRestaurantData() {
        return restaurantDetail;
    }

    private MutableLiveData<ArrayList<Like>> likes = new MutableLiveData<>(new ArrayList<>());

    private CombinedLiveData2<ArrayList<Like>, ArrayList<Workmate>> combine = new CombinedLiveData2(likes, workmates);

    private LiveData<Float> rating = Transformations.map(combine, input -> {
        //calcul de la note
        Float resultRating = (float) (input.first.size() / input.second.size());
        Float result=resultRating.floatValue();
        return result;
    });

    public LiveData<Float> getRating() {
        return rating;
    }

    public LiveData<Integer> fabBackground = Transformations.map(workmates, input -> {
        if (isEating(input)) {
            return R.color.green;
        } else {
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
            public void onFailure() {
            }
        };

        if (isEating(workmates.getValue())) {
            workmateRepository.removeEating(getCurrentUser().getUid(), onResult);
        } else {
            workmateRepository.addEating(getCurrentUser().getUid(), restaurant.getValue().getId(), onResult);
        }
    }

    public boolean isEating(ArrayList<Workmate> workmates) {
        boolean result = false;
        for (Workmate w : workmates) {
            if (getCurrentUser().getUid().equals(w.getId())) {
                result = true;
            }
        }
        return result;
    }

    public void reloadWorkmates() {
        workmateRepository.loadWorkmatesForRestaurant(restaurant.getValue(), new OnResult<ArrayList<Workmate>>() {
            @Override
            public void onSuccess(ArrayList<Workmate> data) {
                workmates.postValue(data);
            }

            @Override
            public void onFailure() {
            }
        });
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant.setValue(restaurant);
        getDetailsWithPlaceId(restaurant.getId());
        getLikes(restaurant);
        reloadWorkmates();
    }

    public LiveData<Integer> likeBackground = Transformations.map(likes, input -> {
        if (isLike(input)) {
            return R.color.green;
        } else {
            return com.google.android.libraries.places.R.color.quantum_grey;
        }
    });

    private boolean isLike(ArrayList<Like> input) {
        return !input.isEmpty();
    }

    public void getLikes(Restaurant restaurant) {
        likeRepository.getLikes(getCurrentUser().getUid(), restaurant.getId(), new OnResult<ArrayList<Like>>() {
            @Override
            public void onSuccess(ArrayList<Like> data) {
                likes.postValue(data);
            }

            @Override
            public void onFailure() {
            }
        });
    }

    public void toggleLike() {
        //TODO
        OnResult<Void> onResult = new OnResult<Void>() {
            @Override
            public void onSuccess(Void data) {
                getRating();
                getLikes(restaurant.getValue());
            }

            @Override
            public void onFailure() {
            }
        };
        if (isLike(likes.getValue())) {
            likeRepository.removeLike(getCurrentUser().getUid(),restaurant.getValue().getId(), onResult);

        } else {
            likeRepository.addLike(getCurrentUser().getUid(), restaurant.getValue().getId(), onResult);
        }
    }
}
