package com.example.go4lunch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.api.responses.Result;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class RestaurantProfilActivity extends AppCompatActivity {

    private ImageView imageRestaurant;
    private TextView descriptionRestaurant;
    private FloatingActionButton addWorkmate;
    private Button phoneButton;
    private Button likeButton;
    private Button webButton;
    private Toolbar toolbarProfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profil);

        imageRestaurant = findViewById(R.id.imageProfilRestaurant);
        descriptionRestaurant = findViewById(R.id.descriptionRestaurant);
        addWorkmate = findViewById(R.id.addworkmateRestaurant);
        phoneButton = findViewById(R.id.phoneButton);
        likeButton = findViewById(R.id.likeButton);
        webButton = findViewById(R.id.webButton);
        toolbarProfil=findViewById(R.id.toolbarProfil);

        //back with toolbar
        setSupportActionBar(toolbarProfil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarProfil.setNavigationOnClickListener(view -> onBackPressed());

        //insertion of the values restaurant
        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");

        Glide
                .with(imageRestaurant)
                .load(restaurant.getImageURL(400))
                .apply(RequestOptions.centerCropTransform())
                .placeholder(R.drawable.logo_food)
                .into(imageRestaurant);

        String nameRestaurant = restaurant.getName();
        String description = restaurant.getType() + " " + restaurant.getAddress();
        SpannableStringBuilder text = new SpannableStringBuilder(nameRestaurant + "\n" + description);
        text.setSpan(new StyleSpan(Typeface.BOLD), 0, nameRestaurant.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new RelativeSizeSpan(1.5f), 0, nameRestaurant.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        descriptionRestaurant.setText(text);

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + 0134770565));
                startActivity(callIntent);*/
                String number = restaurant.getPhoneNumber();
                if (number!=null) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + restaurant.getPhoneNumber()));
                    startActivity(callIntent);
                }else{
                    Toast.makeText(RestaurantProfilActivity.this, "no phone", Toast.LENGTH_SHORT).show();
                }
            }
        });

        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(webIntent);*/
                if (restaurant.getWebsite()!=null) {
                   // OpenWebSite(restaurant.getWebsite());

                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getWebsite()));
                    startActivity(webIntent);
                }else{
                    Toast.makeText(RestaurantProfilActivity.this, "no website", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void OpenWebSite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public static void navigate(FragmentActivity activity, Restaurant restaurant) {
        Intent intent = new Intent(activity, RestaurantProfilActivity.class);
        intent.putExtra("restaurant", (Serializable) restaurant);
        activity.startActivity(intent);
    }
}