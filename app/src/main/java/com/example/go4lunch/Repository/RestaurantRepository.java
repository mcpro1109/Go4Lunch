package com.example.go4lunch.Repository;

import android.util.Log;

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.utils.OnResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RestaurantRepository {
    private static RestaurantRepository instance;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static RestaurantRepository getInstance() {
        if (instance == null) instance = new RestaurantRepository();
        return instance;
    }

    //load the data from Firestore
    public void loadRestaurantList(OnResult<ArrayList<Restaurant>> onResult) {
        firebaseFirestore
                .collection("restaurants")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    ArrayList<Restaurant> restaurants = new ArrayList<>();
                    for (DocumentSnapshot d : querySnapshot.getDocuments()) {
                        restaurants.add(d.toObject(Restaurant.class));
                       // Log.d("valeur", "DocumentSnapshot data: " + d.getData());
                    }
                    onResult.onSuccess(restaurants);
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    onResult.onFailure();
                });
    }
}
