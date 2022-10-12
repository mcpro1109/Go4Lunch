package com.example.go4lunch.Viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Repository.RestaurantRepository;
import com.example.go4lunch.utils.OnResult;
import com.example.go4lunch.utils.PlaceAutoCompleteAPI;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class HomeFragmentsViewModel extends ViewModel {


String location="48.866667, 2.333333";


    public void start()  {

        Retrofit retrofit=new Retrofit.Builder()
               // .baseUrl("https://maps.googleapis.com/maps/api/place/autocomplete/json?types=food&name=48.866667%2C2.333333&radius=500&strictbounds=true&key=MAPS_API_KEY")
                .baseUrl("https://maps.googleapis.com/maps/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceAutoCompleteAPI service=retrofit.create(PlaceAutoCompleteAPI.class);

        service.loadRestaurants(location).enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                Restaurant restaurant=response.body();

            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                t.printStackTrace();
            }
        });
      /*  OkHttpClient client= new OkHttpClient().newBuilder().build();

        MediaType mediaType= MediaType.parse("text/plain");
        RequestBody body= RequestBody.create(mediaType, " ");
        Request request=new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/autocomplete/json?types=food&name=48.866667%2C2.333333&radius=500&strictbounds=true&key=MAPS_API_KEY")
                .method("GET", body)
                .build();
        try {
            Response response=client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private RestaurantRepository restaurantRepository=RestaurantRepository.getInstance();
    private MutableLiveData<ArrayList<Restaurant>> restaurantList= new MutableLiveData<>();

    public LiveData<ArrayList<Restaurant>> getRestaurantData(){return restaurantList;}

    public void loadRestaurantsList(){
        restaurantRepository.loadRestaurantList(new OnResult<ArrayList<Restaurant>>() {
            @Override
            public void onSuccess(ArrayList<Restaurant> data) {
                restaurantList.setValue(data);
            }
            @Override
            public void onFailure() {

            }
        });
    }
}
