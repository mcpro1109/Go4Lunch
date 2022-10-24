package com.example.go4lunch.Viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Repository.RestaurantRepository;
import com.example.go4lunch.utils.LocationBuilder;
import com.example.go4lunch.utils.OnResult;

import java.util.ArrayList;

public class HomeFragmentsViewModel extends ViewModel {


    public void start()  {

        loadRestaurantsList();

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
        restaurantRepository.loadRestaurantList(LocationBuilder.create( 48.86325926951382, 2.354244777246452), new OnResult<ArrayList<Restaurant>>() {
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
