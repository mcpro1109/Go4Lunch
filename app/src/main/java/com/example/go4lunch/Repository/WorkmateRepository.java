package com.example.go4lunch.Repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.go4lunch.Model.EatingWorkmate;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Model.Workmate;
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

    //private Date currentDate= Calendar.getInstance().getTime();
    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private String currentDate = formatter.format(new Date());

    public static WorkmateRepository getInstance() {
        if (instance == null) instance = new WorkmateRepository();
        return instance;
    }

    //load the data from Firestore
    public void loadWorkmates(OnResult<ArrayList<Workmate>> onResult) {
        firebaseFirestore
                .collection(COLLECTION_NAME)
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

    //filter the current user with the workmatelist to add the user in the restaurant
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public void loadWorkmatesForRestaurant(Restaurant restaurant, OnResult<ArrayList<Workmate>> onResult) {
        firebaseFirestore
                .collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    ArrayList<Workmate> workmates = new ArrayList<>();

                    for (DocumentSnapshot d : querySnapshot.getDocuments()) {
                        workmates.add(d.toObject(Workmate.class));
                    }
                    firebaseFirestore
                            .collection(EATING_COLLECTION_NAME)
                            .whereEqualTo("day", currentDate)
                            .whereEqualTo("restaurant_id", restaurant.getId())
                            .get()
                            .addOnSuccessListener(eatingQuerySnapshot -> {
                                //TODO FILTERS
                                ArrayList<EatingWorkmate> eatingWorkmates = new ArrayList<>();

                                for (DocumentSnapshot d : eatingQuerySnapshot.getDocuments()) {
                                    eatingWorkmates.add(d.toObject(EatingWorkmate.class));
                                }
                                //filtrer si idworkmate est dans eatingworkmate

                                for (EatingWorkmate w : eatingWorkmates) {
                                    if (w.getWorkmate_id().equals(user.getUid())) {//oui
                                        Log.e("id", w.getWorkmate_id());
                                        Log.e("id2", user.getUid());

                                         onResult.onSuccess(workmates);
                                    }
                                }
                                onResult.onSuccess(workmates);
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
                .addOnFailureListener(e -> {
                    onResult.onFailure();
                })
                .addOnSuccessListener(documentSnapshot -> {
                    onResult.onSuccess(null);
                });
    }

    public void removeEating(String userId, OnResult<Void> onResult) {

        firebaseFirestore
                .collection(EATING_COLLECTION_NAME)
                .document(userId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("message", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("message", "Error deleting document", e);
                    }
                });
    }
}
