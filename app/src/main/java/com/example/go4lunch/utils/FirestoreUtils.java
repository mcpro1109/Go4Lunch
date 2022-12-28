package com.example.go4lunch.utils;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FirestoreUtils {

    public static <T> ArrayList<T> getList(QuerySnapshot snapshot, Class<T> classs){
        ArrayList<T> result = new ArrayList<T>();
        for (DocumentSnapshot d : snapshot.getDocuments()) {
            result.add(d.toObject(classs));
        }
        return result;
    }
}
