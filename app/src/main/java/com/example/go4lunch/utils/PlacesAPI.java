package com.example.go4lunch.utils;

import com.example.go4lunch.api.responses.RestaurantResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesAPI {
    /*
     * @Query() appends the parameter to the HTTP request.
     * In this case, the request made by retrofit looks like
     * BASEURL/api/place/autocomplete/json?types=address&key=YOUR-KEY&input=addressFromUser
     */
    @GET("api/place/nearbysearch/json")
    Call<RestaurantResponse> getNearbyPlaces(
            @Query("radius") int radius,
            @Query("location") String location,
            @Query("type") String type,
            @Query("key") String key
    );
}
