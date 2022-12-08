package com.example.go4lunch.Repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.RestaurantProfileActivity;
import com.example.go4lunch.utils.OnResult;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;

public class WorkmateRepository {

    private static final String IS_EATING = "isEating";
    public boolean isEating = false;
    private MutableLiveData<Workmate> workmateEating = new MutableLiveData(new ArrayList());

    private static WorkmateRepository instance;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private static final String COLLECTION_NAME = "workmates";

    private CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static WorkmateRepository getInstance() {
        if (instance == null) instance = new WorkmateRepository();
        return instance;
    }

    @Nullable
    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
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

    public void addEating(OnResult<Void> onResult) {
        firebaseFirestore.collection("workmates")
                .add(workmateEating)
                .addOnSuccessListener(documentSnapshot -> {

                   if (documentSnapshot.get().isSuccessful()) {
                        isEating = true;
                    }
                });
        onResult.onSuccess(null);

    }

    public void removeEating(OnResult<Void> onResult) {
        firebaseFirestore.collection("workmates")
                .get()
                .addOnSuccessListener(documentSnapshot -> {

                    if (documentSnapshot.getDocuments().contains(IS_EATING)) {
                        isEating = false;
                    }
                });
        onResult.onSuccess(null);
    }

    public void createWorkmate() {
        FirebaseUser user = getCurrentUser();
        if (user != null) {
            String urlPicture = (user.getPhotoUrl() != null) ? user.getPhotoUrl().toString() : null;
            String userName = user.getDisplayName();
            String userFirstName = "name";
            String userId = user.getUid();
            Log.e("nameuser", userName);//ok

            Workmate workmateToCreate = new Workmate(userId, userName, userFirstName, false, workmateEating);
            Task<DocumentSnapshot> userData = getUserData();
            Log.e("nameuser", userData.toString());
            //if the user exist in firestore, we get the data
            userData.addOnSuccessListener(documentSnapshot -> {

               if (documentSnapshot.contains(IS_EATING)) {
                   // if (!userData.isSuccessful()) {
                    workmateToCreate.setFirstName(userFirstName);
                    workmateToCreate.setName(userName);
                    documentSnapshot.get(userId);
                }

            });
        }
    }

    private Task<DocumentSnapshot> getUserData() {
        String uId = this.getCurrentUserId();
        if (uId != null) {
            return this.getUsersCollection().document(uId).get();
        } else {
            return null;
        }
    }

    private String getCurrentUserId() {
        FirebaseUser user = getCurrentUser();
        return (user != null) ? user.getUid() : null;
    }
}
