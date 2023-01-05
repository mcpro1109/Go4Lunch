package com.example.go4lunch.Repository;

import com.example.go4lunch.Model.Like;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.utils.FirestoreUtils;
import com.example.go4lunch.utils.OnResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LikeRepository {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private static LikeRepository instance;
    private static final String COLLECTION_LIKE = "like_restaurant";

    public static LikeRepository getInstance() {
        if (instance == null) instance = new LikeRepository();
        return instance;
    }

    public void getLikes(Restaurant restaurant, OnResult<ArrayList<Like>> onResult) {

        firebaseFirestore
                .collection(COLLECTION_LIKE)
                .whereEqualTo("restaurant_id", restaurant.getId())
                .get()
                .addOnSuccessListener(eatingQuerySnapshot -> {

                    ArrayList<Like> likes = FirestoreUtils.getList(eatingQuerySnapshot, Like.class);

                    onResult.onSuccess(likes);
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    onResult.onFailure();
                });
    }

    private float computeRating(ArrayList<Like> list) {
        // TODO calcul
        return 5f;
    }

    public void addLike(String userId, String restaurantId, OnResult<Void> onResult) {
        Map<String, Object> obj = new HashMap<>();

        Boolean choice=true;

        obj.put("restaurant_id", restaurantId);
        obj.put("workmate_id", userId);
        obj.put("like",choice);

        firebaseFirestore
                .collection(COLLECTION_LIKE)
                .document(userId)
                .set(obj)
                .addOnFailureListener(e -> onResult.onFailure())
                .addOnSuccessListener(documentSnapshot -> onResult.onSuccess(null));
    }

    public void removeLike(String userId, OnResult<Void> onResult){
        firebaseFirestore.collection(COLLECTION_LIKE)
                .document(userId)
                .delete()
                .addOnFailureListener(e -> onResult.onFailure())
                .addOnSuccessListener(unused -> onResult.onSuccess(null));
    }
}
