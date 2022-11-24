package com.example.go4lunch.utils;

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.api.responses.RestaurantResponse;
import com.example.go4lunch.api.responsesDetails.RestaurantResponseDetails;

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

    @GET("api/place/details/json")
    Call<RestaurantResponseDetails> getDetailsPlaces(
            @Query("place_id") String place_id,
            @Query("fields") String fields,
            @Query("key") String key
    );

    @GET("api/place/autocomplete/json")
    Call<Restaurant>getSearchRestaurant(
            @Query("input") String input,
            @Query("radius") int radius,
            @Query("key") String key
    );
}
