package com.example.go4lunch.Repository;


import android.content.Context;

import com.example.go4lunch.Model.Workmate;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class WorkmateManager {

    private static volatile WorkmateManager instance;
    private WorkmateRepository workmateRepository;

    private WorkmateManager(){
        workmateRepository=WorkmateRepository.getInstance();
    }

    public static WorkmateManager getInstance(){
        WorkmateManager result=instance;
        if (result!=null){
            return result;
        }
        synchronized (WorkmateRepository.class){
            if (instance==null){
                instance=new WorkmateManager();
            }
        }return instance;
    }


    public FirebaseUser getCurrentWorkmate(){
        return workmateRepository.getCurrentWorkmate();
    }

    public Boolean isCurrentWorkmateIsLogged(){
        return (this.getCurrentWorkmate() !=null);
    }

    public void createWorkmate(){
        workmateRepository.createWorkmate();
    }

    public Task<Workmate> getWorkmateData(){
        return workmateRepository.getWorkmateData().continueWith(task -> task.getResult().toObject(Workmate.class));
    }

    public Task<Void> updateWorkmateName(String workmateName){
        return workmateRepository.UpdateWorkmateName(workmateName);
    }

    public Task<Void> updateWorkmateFirstName(String workmateFirstName){
        return workmateRepository.UpdateWorkmateFirstName(workmateFirstName);
    }

    public Task<Void> deleteWorkmate(Context context){
        return workmateRepository.deleteWorkmate(context).addOnCompleteListener(task ->{
            workmateRepository.deleteWorkmateFromFirestore();
        });
    }
}
