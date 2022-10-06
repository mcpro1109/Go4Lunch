package com.example.go4lunch.Viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.utils.OnResult;
import com.example.go4lunch.Repository.WorkmateRepository;

import java.util.ArrayList;

public class WorkmateViewModel extends ViewModel {
    private WorkmateRepository workmateRepository = WorkmateRepository.getInstance();
    private MutableLiveData<ArrayList<Workmate>> workmateList = new MutableLiveData<>();

    public LiveData<ArrayList<Workmate>> getWorkmatesData() {
        return workmateList;
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
}