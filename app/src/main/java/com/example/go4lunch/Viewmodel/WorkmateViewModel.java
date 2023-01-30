package com.example.go4lunch.Viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.EatingWorkmate;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.Repository.WorkmateRepository;
import com.example.go4lunch.utils.OnResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class WorkmateViewModel extends ViewModel {

    private WorkmateRepository workmateRepository = WorkmateRepository.getInstance();

    private MutableLiveData<ArrayList<Workmate>> workmateList = new MutableLiveData<>();

    public LiveData<ArrayList<Workmate>> getWorkmatesData() {
        return workmateList;
    }

    private MutableLiveData<Restaurant> restaurant = new MutableLiveData<>();

    public LiveData<Restaurant> getRestaurant() {
        return restaurant;
    }

    private MutableLiveData<ArrayList<EatingWorkmate>> eatingWorkmateList = new MutableLiveData<>();

    public LiveData<ArrayList<EatingWorkmate>> getEatingWorkmatesData() {
        return eatingWorkmateList;
    }

    public void loadWorkmates() {
        workmateRepository.loadWorkmates(new OnResult<ArrayList<Workmate>>() {
            @Override
            public void onSuccess(ArrayList<Workmate> data) {
                workmateList.setValue(data);
            }

            @Override
            public void onFailure() {
            }
        });
    }

    //todo
    public void eatingWorkmate(ArrayList<EatingWorkmate> eatingWorkmates, ArrayList<Workmate> workmates) {
        OnResult<Void> onResult = new OnResult<Void>() {
            @Override
            public void onSuccess(Void data) {
                  loadWorkmates();
              /*  for (Workmate workmate : workmates) {
                    for (EatingWorkmate eatingWorkmate : eatingWorkmates) {
                        if (workmate.getId().equals(eatingWorkmate.getWorkmate_id())) {

                        } else {
                        }
                    }
                }*/
            }
            @Override
            public void onFailure() {
            }
        };
        if (isEating(workmates)) {
            workmateRepository.addEating(getCurrentUser().getUid(), restaurant.getValue().getId(),onResult);

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

    @Nullable
    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public FirebaseUser getCurrentWorkmate() {
        return getCurrentUser();
    }

    public boolean isCurrentWorkmateLogin() {
        return (this.getCurrentWorkmate() != null);
    }
}