package com.example.go4lunch.Viewmodel;

import android.content.res.ColorStateList;
import android.os.Handler;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.RestaurantDetails;
import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.R;
import com.example.go4lunch.Repository.RestaurantRepository;
import com.example.go4lunch.Repository.WorkmateRepository;
import com.example.go4lunch.RestaurantProfileActivity;
import com.example.go4lunch.utils.OnResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

public class RestaurantProfileActivityViewModel extends ViewModel {

    private RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
    private WorkmateRepository workmateRepository=WorkmateRepository.getInstance();

    private MutableLiveData<RestaurantDetails> restaurantDetail = new MutableLiveData<>();
    public LiveData<RestaurantDetails> getRestaurantData() {
        return restaurantDetail;
    }

    private MutableLiveData<ArrayList<Workmate>> workmate = new MutableLiveData<>(new ArrayList<>());
    public LiveData<ArrayList<Workmate>> getWorkmateData() {return workmate;}
    private MutableLiveData<Workmate> workmateEating = new MutableLiveData(new ArrayList());

    public LiveData<Integer> fabBackground= Transformations.map(workmate, new Function<ArrayList<Workmate>, Integer>() {
        @Override
        public Integer apply(ArrayList<Workmate> input) {
            return R.color.red;
        }
    });


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

    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void toggleEat() {
        OnResult<Void> onResult=new OnResult<Void>() {
            @Override
            public void onSuccess(Void data) {
                //mettre à jour liste des workmates qui mangent
                loadWorkmates();

            }

            @Override
            public void onFailure() {

            }
        };
       if (isEating()){
           workmateRepository.removeEating(onResult);

       }else{
           workmateRepository.addEating(onResult);

       }
    }

    private boolean isEating(){

        boolean result=false;
        for (Workmate w: workmate.getValue()){
            if (getCurrentUser().getDisplayName().equals(w.getName())){
                result=true;
            }
        }

        return result;
    }

    public void loadWorkmates(){
        //maj livedata avec les workmates qui mangent
        workmateRepository.createWorkmate();

    }

   /* private void isWorkmateEatHere() {

        if (!isWorkmateEatHere) {
            addWorkmateFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(RestaurantProfileActivity.this, R.color.green)));
            if (restaurantProfileActivityViewModel.getCurrentUser() != null) {
                configureName();
            }

            isWorkmateEatHere = true;
        } else {
            addWorkmateFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(RestaurantProfileActivity.this, R.color.blue)));
            isWorkmateEatHere = false;
        }
    }*/
}
