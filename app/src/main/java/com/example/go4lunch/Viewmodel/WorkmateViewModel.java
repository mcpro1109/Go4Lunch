package com.example.go4lunch.Viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.WorkmateFragmentRecyclerViewAdapter;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class WorkmateViewModel extends ViewModel {
    private FirebaseFirestore db;
    private MutableLiveData<String> textLiveData = new MutableLiveData<>("texte");
    public static List<String> levelList = new ArrayList<String>();
    List<Workmate> workmate = new ArrayList<>();

    private WorkmateFragmentRecyclerViewAdapter workmateFragmentRecyclerViewAdapter;

    public LiveData<String> getTextLiveData() {
        return textLiveData;
    }

    public void updateText() {
        textLiveData.setValue(UUID.randomUUID().toString());
    }

    public void addDataToFirestore() {
        db = FirebaseFirestore.getInstance();
        Workmate workmate = new Workmate("1", "Dupond", "Alain", "url");


        db.collection("workmates").document("AD").set(workmate);

    }


    public void updateWorkmates() {
        // levelList.clear();
        db = FirebaseFirestore.getInstance();
        db.collection("workmates")
                .document("AD")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            Log.d("valeur", "DocumentSnapshot data: " + documentSnapshot.getData());
                            String name = documentSnapshot.getString("firstName");
                            levelList.add(name);

                        } else {
                            Log.d("valeur", "No such document");
                        }
                        // workmateFragmentRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("valeur", "get failed with ", task.getException());
                    }
                });
    }
}