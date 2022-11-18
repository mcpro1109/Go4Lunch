package com.example.go4lunch.Repository;

import androidx.annotation.Nullable;

import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.utils.OnResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class WorkmateRepository {

    private static WorkmateRepository instance;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static WorkmateRepository getInstance() {
        if (instance == null) instance = new WorkmateRepository();
        return instance;
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

    //load the data from Firestore
    public void loadWorkmates(OnResult<ArrayList<Workmate>> onResult) {
        firebaseFirestore
                .collection("workmates")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    ArrayList<Workmate> workmates = new ArrayList<>();
                    for (DocumentSnapshot d : querySnapshot.getDocuments()) {
                        workmates.add(d.toObject(Workmate.class));
                    }
                    onResult.onSuccess(workmates);
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    onResult.onFailure();
                });
    }
}
