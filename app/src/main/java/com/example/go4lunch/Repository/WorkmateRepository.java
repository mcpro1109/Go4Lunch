package com.example.go4lunch.Repository;

import android.content.Context;

import androidx.annotation.Nullable;

import com.example.go4lunch.Model.Workmate;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WorkmateRepository {

    private static volatile WorkmateRepository instance;

    private static final String COLLECTION_WORKMATE = "workmate";
    private static final String NAME_FIELD = "name";
    private static final String FIRSTNAME_FIELD = "firstname";
    private static final String AVATARURL_FIELD = "avatarurl";

    public static WorkmateRepository getInstance() {
        WorkmateRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (WorkmateRepository.class) {
            if (instance == null) {
                instance = new WorkmateRepository();
            }
        }
        return instance;
    }

    @Nullable
    public FirebaseUser getCurrentWorkmate() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    //get the collection reference
    private CollectionReference getWorkmateCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_WORKMATE);
    }

    public void createWorkmate() {
        FirebaseUser user = getCurrentWorkmate();

        if (user != null) {
            String avatarUrl = (user.getPhotoUrl() != null) ? user.getPhotoUrl().toString() : null;
            String workmateName = user.getDisplayName();
            String workmateFirstName= user.getDisplayName();
            String uid = user.getUid();

            Workmate workmateToCreate = new Workmate(uid, workmateName, workmateFirstName,avatarUrl);

            Task<DocumentSnapshot> workmateData = getWorkmateData();
            workmateData.addOnSuccessListener(documentSnapshot -> {
                getWorkmateCollection().document(uid).set(workmateToCreate);
            });
        }
    }
//get workmate data from firestore
    public Task<DocumentSnapshot> getWorkmateData() {
        String uid = this.getCurrentWorkmateID();
        if (uid != null) {
            return getWorkmateCollection().document(uid).get();
        } else {
            return null;
        }
    }

    private String getCurrentWorkmateID() {
        FirebaseUser user = getCurrentWorkmate();
        return (user != null) ? user.getUid() : null;
    }

    public Task<Void> UpdateWorkmateName(String workmateName) {
        String uid = this.getCurrentWorkmateID();
        if (uid != null) {
            return getWorkmateCollection().document(uid).update(NAME_FIELD, workmateName);
        } else {
            return null;
        }
    }

    public Task<Void> UpdateWorkmateFirstName(String workmateFirstName) {
        String uid = this.getCurrentWorkmateID();
        if (uid != null) {
            return getWorkmateCollection().document(uid).update(NAME_FIELD, workmateFirstName);
        } else {
            return null;
        }
    }

    public void deleteWorkmateFromFirestore(){
        String uid = this.getCurrentWorkmateID();
        if (uid!=null){
            this.getWorkmateCollection().document(uid).delete();
        }
    }
    public Task<Void> signOut(Context context){
        return AuthUI.getInstance().signOut(context);
    }

    public Task<Void> deleteWorkmate(Context context){
        return AuthUI.getInstance().delete(context);
    }
}
