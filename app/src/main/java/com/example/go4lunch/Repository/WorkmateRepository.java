package com.example.go4lunch.Repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.go4lunch.Model.EatingWorkmate;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.utils.FirestoreUtils;
import com.example.go4lunch.utils.OnResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class WorkmateRepository {

    private static WorkmateRepository instance;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private static final String COLLECTION_NAME = "workmates";
    private static final String EATING_COLLECTION_NAME = "workmate_restaurant";

    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private String currentDate = formatter.format(new Date());

    public static WorkmateRepository getInstance() {
        if (instance == null) instance = new WorkmateRepository();
        return instance;
    }

    public void loadWorkmates(OnResult<ArrayList<Workmate>> onResult) {
        firebaseFirestore
                .collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    onResult.onSuccess(FirestoreUtils.getList(querySnapshot, Workmate.class));
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    onResult.onFailure();
                });
    }

    public void loadWorkmatesForRestaurant(Restaurant restaurant, OnResult<ArrayList<Workmate>> onResult) {
        firebaseFirestore
                .collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    // List of all workmates
                    ArrayList<Workmate> workmates = FirestoreUtils.getList(querySnapshot, Workmate.class);

                    firebaseFirestore
                            .collection(EATING_COLLECTION_NAME)
                            .whereEqualTo("day", currentDate)
                            .whereEqualTo("restaurant_id", restaurant.getId())
                            .get()
                            .addOnSuccessListener(eatingQuerySnapshot -> {
                                // List of eating workmates
                                ArrayList<EatingWorkmate> eatingWorkmates = FirestoreUtils.getList(eatingQuerySnapshot, EatingWorkmate.class);

                                // list of eating workmates ids
                                ArrayList<String> eatingWorkmatesIds = new ArrayList<>();
                                for (EatingWorkmate w : eatingWorkmates) {
                                    eatingWorkmatesIds.add(w.getWorkmate_id());
                                }

                                // Construct result array with filtered workmates
                                ArrayList<Workmate> result = new ArrayList<>();
                                for (Workmate w : workmates) {
                                    if (eatingWorkmatesIds.contains(w.getId())) {
                                        result.add(w);
                                    }
                                }

                                onResult.onSuccess(result);
                            })
                            .addOnFailureListener(e -> {
                                e.printStackTrace();
                                onResult.onFailure();
                            });
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    onResult.onFailure();
                });
    }

    public void addEating(String userId, String restaurantId, OnResult<Void> onResult) {
        Map<String, Object> obj = new HashMap<>();

        obj.put("workmate_id", userId);
        obj.put("restaurant_id", restaurantId);
        obj.put("day", currentDate);

        firebaseFirestore
                .collection(EATING_COLLECTION_NAME)
                .document(userId)
                .set(obj)
                .addOnFailureListener(e -> onResult.onFailure())
                .addOnSuccessListener(documentSnapshot -> onResult.onSuccess(null));
    }

    public void removeEating(String userId, OnResult<Void> onResult) {
        firebaseFirestore
                .collection(EATING_COLLECTION_NAME)
                .document(userId)
                .delete()
                .addOnFailureListener(e -> onResult.onFailure())
                .addOnSuccessListener(unused -> onResult.onSuccess(null));
    }
}
