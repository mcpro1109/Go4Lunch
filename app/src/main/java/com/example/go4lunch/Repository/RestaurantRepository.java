package com.example.go4lunch.Repository;

import static android.content.ContentValues.TAG;

import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Model.RestaurantDetails;
import com.example.go4lunch.api.API;
import com.example.go4lunch.api.responses.RestaurantResponse;
import com.example.go4lunch.api.responses.Result;
import com.example.go4lunch.api.responsesDetails.RestaurantResponseDetails;
import com.example.go4lunch.api.responsesDetails.ResultDetails;
import com.example.go4lunch.utils.OnResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantRepository {

    private static RestaurantRepository instance;



    public static RestaurantRepository getInstance() {
        if (instance == null) instance = new RestaurantRepository();
        return instance;
    }

    //load the data from Google
    public void loadRestaurantList(Location location, OnResult<ArrayList<Restaurant>> onResult) {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();

      //  Log.e("CALL -> ", "LOAD");

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
                      //  Log.e("CALL -> ", response.body().toString());
                      //  Log.e("CALL URL -> ", call.request().url().toString());

                        if (response.isSuccessful()) {
                            List<Result> results = response.body().getResults();


                            for (Result result : results) {
                                restaurants.add(Restaurant.fromGoogleResponse(location, result));
                            }
                        } else {
                          //  Log.e("CALL2 -> ", "erreur");
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

        /*firebaseFirestore
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
                });*/
    }

    public void getDetails(String placeId, OnResult<RestaurantDetails> onResult){
        API.getPlacesAPI()
                .getDetailsPlaces(
                        placeId,
                        "formatted_phone_number,current_opening_hours,website",
                        BuildConfig.PLACES_API_KEY
                )
                .enqueue(new Callback<RestaurantResponseDetails>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<RestaurantResponseDetails> call, Response<RestaurantResponseDetails> response) {
                        Log.e("CALLDetails -> ", response.body().toString());
                        Log.e("CALL URLDetails -> ", call.request().url().toString());

                        if (response.isSuccessful()) {
                            Log.e("CALL success -> ", "success");

                            ResultDetails results = response.body().getResult();

                            onResult.onSuccess(RestaurantDetails.fromGoogleResponseDetails(results));
                        } else {
                          //  Log.e("CALL2 -> ", "erreur");
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
}
