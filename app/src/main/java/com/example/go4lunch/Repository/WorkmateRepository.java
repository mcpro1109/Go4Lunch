package com.example.go4lunch.Repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.utils.OnResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorkmateRepository {

    private static WorkmateRepository instance;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private static final String COLLECTION_NAME = "workmates";
    private static final String EATING_COLLECTION_NAME = "workmate_restaurant";

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

    //filter the current user wuth the workmatelist to add the user in the restaurant
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public void loadWorkmatesForRestaurant(Restaurant restaurant, OnResult<ArrayList<Workmate>> onResult) {
        firebaseFirestore
                .collection(COLLECTION_NAME)
                .whereEqualTo("id", user.getUid())
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    //TODO FILTERS
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

    public void addEating(String userId, String restaurantId, OnResult<Void> onResult) {
        Map<String, Object> obj = new HashMap<>();

        obj.put("workmate_id", userId);
        obj.put("restaurant_id", restaurantId);
        obj.put("day", "13/12/2022");

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

    /*public void createWorkmate() {
        FirebaseUser user = getCurrentUser();
        if (user != null) {
            String urlPicture = (user.getPhotoUrl() != null) ? user.getPhotoUrl().toString() : null;
            String userName = user.getDisplayName();
            String userFirstName = "name";
            String userId = user.getUid();
            Log.e("nameuser", userName);//ok

            Workmate workmateToCreate = new Workmate(userId, userName, userFirstName, false, workmateEating);



            /*Log.e("nameuser", userData.toString());
            //if the user exist in firestore, we get the data
           // firebaseFirestore.collection("workmates")

            userData.addOnSuccessListener(documentSnapshot -> {

               if (documentSnapshot.contains(IS_EATING)) {
                   // if (!userData.isSuccessful()) {
                    workmateToCreate.setFirstName(userFirstName);
                    workmateToCreate.setName(userName);
                    documentSnapshot.get(userId);
                }

            });
        }
    }*/
}
