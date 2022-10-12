package com.example.go4lunch.utils;

import com.example.go4lunch.Model.Restaurant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceAutoCompleteAPI {
    /*
     * @Query() appends the parameter to the HTTP request.
     * In this case, the request made by retrofit looks like
     * BASEURL/api/place/autocomplete/json?types=address&key=YOUR-KEY&input=addressFromUser
     * */
    @GET("api/place/autocomplete/json?types=address&key=YOUR-KEY")
    Call<Restaurant> loadRestaurants(@Query("location") String location);
}
