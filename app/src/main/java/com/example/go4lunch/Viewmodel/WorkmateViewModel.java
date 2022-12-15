package com.example.go4lunch.Viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.Repository.WorkmateRepository;
import com.example.go4lunch.utils.OnResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class WorkmateViewModel extends ViewModel {

    private WorkmateRepository workmateRepository = WorkmateRepository.getInstance();

    private MutableLiveData<ArrayList<Workmate>> workmateList = new MutableLiveData<>();
    public LiveData<ArrayList<Workmate>> getWorkmatesData() {return workmateList;}

    public void loadWorkmates() {
        workmateRepository.loadWorkmates(new OnResult<ArrayList<Workmate>>() {
            @Override
            public void onSuccess(ArrayList<Workmate> data) {
                workmateList.setValue(data);
            }

            @Override
            public void onFailure() {}
        });
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