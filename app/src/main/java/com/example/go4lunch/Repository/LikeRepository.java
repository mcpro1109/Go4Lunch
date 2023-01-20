package com.example.go4lunch.Repository;

import com.example.go4lunch.Model.Like;
import com.example.go4lunch.utils.FirestoreUtils;
import com.example.go4lunch.utils.OnResult;
import com.google.firebase.firestore.DocumentSnapshot;
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

    public void getLikes(String userId, String restaurantId, OnResult<ArrayList<Like>> onResult) {

        firebaseFirestore
                .collection(COLLECTION_LIKE)
                .whereEqualTo("workmate_id", userId)
                .whereEqualTo("restaurant_id", restaurantId)
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

    public void addLike(String userId, String restaurantId, OnResult<Void> onResult) {
        Map<String, Object> obj = new HashMap<>();


        obj.put("workmate_id", userId);
        obj.put("restaurant_id", restaurantId);

        firebaseFirestore
                .collection(COLLECTION_LIKE)
                // .document(userId)
                .add(obj)
                .addOnFailureListener(e -> onResult.onFailure())
                .addOnSuccessListener(documentSnapshot -> onResult.onSuccess(null));
    }

    public void removeLike(String userId, String restaurantId, OnResult<Void> onResult) {

        firebaseFirestore.collection(COLLECTION_LIKE)
                .whereEqualTo("workmate_id", userId)
                .whereEqualTo("restaurant_id", restaurantId)
                .get()
                .addOnSuccessListener(eatingQuerySnapshot -> {
                    for (DocumentSnapshot document : eatingQuerySnapshot.getDocuments()) {
                        document.getReference().delete();
                    }

                    onResult.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    onResult.onFailure();
                });


    }
}
