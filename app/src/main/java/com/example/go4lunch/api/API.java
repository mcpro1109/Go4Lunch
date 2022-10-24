package com.example.go4lunch.api;

import com.example.go4lunch.utils.PlacesAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    public static retrofit2.Retrofit getInstance() {
        Retrofit retrofit;
        return  retrofit = new Retrofit.Builder()
                // .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/json
                //  ?keyword=cruise
                //  &location=-33.8670522%2C151.1957362
                //  &radius=1500
                //  &type=restaurant
                //  &key=YOUR_API_KEY")
                .baseUrl("https://maps.googleapis.com/maps/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static PlacesAPI getPlacesAPI() {
     return getInstance().create(PlacesAPI.class);
    }
}
