package com.example.go4lunch.Repository;

import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.Model.EatingWorkmate;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Model.RestaurantDetails;
import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.api.API;
import com.example.go4lunch.api.responses.RestaurantResponse;
import com.example.go4lunch.api.responses.Result;
import com.example.go4lunch.api.responsesDetails.RestaurantResponseDetails;
import com.example.go4lunch.api.responsesDetails.ResultDetails;
import com.example.go4lunch.utils.FirestoreUtils;
import com.example.go4lunch.utils.OnResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantRepository {

    private static RestaurantRepository instance;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private static final String COLLECTION_NAME = "workmates";
    private static final String EATING_COLLECTION_NAME = "workmate_restaurant";
    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private String currentDate = formatter.format(new Date());

    public static RestaurantRepository getInstance() {
        if (instance == null) instance = new RestaurantRepository();
        return instance;
    }

    //load the data from Google
    public void loadRestaurantList(Location location, OnResult<ArrayList<Restaurant>> onResult) {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();

        API.getPlacesAPI()
                .getNearbyPlaces(
                        500,
                        location.getLatitude() + "," + location.getLongitude(),
                        "restaurant",
                        BuildConfig.PLACES_API_KEY
                )
                .enqueue(new Callback<RestaurantResponse>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {
                        if (response.isSuccessful()) {
                            List<Result> results = response.body().getResults();
                            for (Result result : results) {
                                restaurants.add(Restaurant.fromGoogleResponse(location, result));
                            }
                        } else {
                        }
                        onResult.onSuccess(restaurants);

                    }

                    @Override
                    public void onFailure(Call<RestaurantResponse> call, Throwable t) {
                        Log.e("CALL -> ", "ERROR " + t.getMessage());
                        t.printStackTrace();
                        onResult.onFailure();
                    }
                });
    }

    public void getDetails(String placeId, OnResult<RestaurantDetails> onResult) {
        API.getPlacesAPI()
                .getDetailsPlaces(
                        placeId,
                        "name,place_id,formatted_phone_number,current_opening_hours,website",
                        BuildConfig.PLACES_API_KEY
                )
                .enqueue(new Callback<RestaurantResponseDetails>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<RestaurantResponseDetails> call, Response<RestaurantResponseDetails> response) {
                        if (response.isSuccessful()) {
                            ResultDetails results = response.body().getResult();
                            onResult.onSuccess(RestaurantDetails.fromGoogleResponseDetails(results));
                           // Log.e("CALL -> ", "NAME " + results.getName());
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantResponseDetails> call, Throwable t) {
                        //   Log.e("CALL -> ", "ERROR " + t.getMessage());
                        t.printStackTrace();
                        onResult.onFailure();
                    }
                });
    }

    public void getEatingRestaurant(OnResult<RestaurantDetails> onResult){
        firebaseFirestore
                .collection(EATING_COLLECTION_NAME)
                .whereEqualTo("day", currentDate)
                .whereEqualTo("workmate_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(eatingQuerySnapshot -> {
                    // List of eating workmates
                    ArrayList<EatingWorkmate> eatingWorkmates = FirestoreUtils.getList(eatingQuerySnapshot, EatingWorkmate.class);
                    if (eatingWorkmates.isEmpty()) {
                        onResult.onFailure();

                    }else{
                        getDetails(eatingWorkmates.get(0).getRestaurant_id(), onResult);
                    }

                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    onResult.onFailure();
                });
    }
}
