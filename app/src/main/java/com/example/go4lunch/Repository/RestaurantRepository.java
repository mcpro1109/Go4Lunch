package com.example.go4lunch.Repository;

import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.MyApp;
import com.example.go4lunch.api.API;
import com.example.go4lunch.api.responses.RestaurantResponse;
import com.example.go4lunch.api.responses.Result;
import com.example.go4lunch.utils.OnResult;
import com.google.android.libraries.places.api.model.OpeningHours;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

        Log.e("CALL -> ", "LOAD");

        API.getPlacesAPI()
                .getNearbyPlaces(
                        1500,
                        location.getLatitude() + "," + location.getLongitude(),
                        "restaurant",
                        "x"
                )
                .enqueue(new Callback<RestaurantResponse>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {
                        Log.e("CALL -> ", response.body().toString());

                       // Geocoder geocoder = new Geocoder(MyApp.app, Locale.getDefault());

                        if (response.isSuccessful()) {
                           List<Result> results = response.body().getResults();

                            for(Result result : results){
                              /*  String address = "adress";
                                try {
                                    address = geocoder.getFromLocation(result.getGeometry().getLocation().getLatitude(), result.getGeometry().getLocation().getLongitude(), 1).get(0).toString();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }*/

                                Restaurant restaurant = new Restaurant(
                                        result.getName(),
                                        (int) location.distanceTo(result.getGeometry().getLocation()),
                                        result.getPhotos().get(0).toString(),
                                        result.getTypes().get(0),
                                        //address,
                                        result.getVicinity(),
                                        result.getOpeningHours().isOpenNow(),
                                        2,
                                        result.getRating(),
                                        result.getPhoneNumber(),
                                        result.getWebsiteUri()
                                );

                                restaurants.add(restaurant);
                            }
                        } else {
                            Log.e("CALL2 -> ", "erreur");
                        }

                        onResult.onSuccess(restaurants);
                    }

                    @Override
                    public void onFailure (Call < RestaurantResponse > call, Throwable t){
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
    }
