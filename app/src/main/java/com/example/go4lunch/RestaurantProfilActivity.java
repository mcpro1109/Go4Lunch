package com.example.go4lunch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.Model.Restaurant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class RestaurantProfilActivity extends AppCompatActivity {

    private ImageView imageRestaurant;
    private TextView descriptionRestaurant;
    private FloatingActionButton addWorkmate;
    private Button phoneButton;
    private Button likeButton;
    private Button webButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profil);

        imageRestaurant = findViewById(R.id.imageRestaurant);
        descriptionRestaurant = findViewById(R.id.descriptionRestaurant);
        addWorkmate = findViewById(R.id.addworkmateRestaurant);
        phoneButton = findViewById(R.id.phoneButton);
        likeButton = findViewById(R.id.likeButton);
        webButton = findViewById(R.id.webButton);

        //insertion of the values restaurant
        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
        String resImage = restaurant.getImageURL();
        Glide.with(this)
                .load(resImage)
                .into(imageRestaurant);

        String description = restaurant.name + "\n" + restaurant.type + " " + restaurant.adresse;
        descriptionRestaurant.setText(description);


    }

    public static void navigate(FragmentActivity activity, Restaurant restaurant) {
        Intent intent = new Intent(activity, RestaurantProfilActivity.class);
        intent.putExtra("restaurant", (Serializable) restaurant);
        activity.startActivity(intent);
    }
}