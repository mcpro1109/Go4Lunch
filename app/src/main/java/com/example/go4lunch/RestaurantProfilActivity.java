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

import com.bumptech.glide.Glide;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.api.responses.Photo;
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
        /*Photo resImage = restaurant.getImageURL();
        Glide.with(this)
                .load(resImage)
                .into(imageRestaurant);*/

        String nameRestaurant= restaurant.getName();
        String description =restaurant.getType() + " " + restaurant.getAddress();
        SpannableStringBuilder text=new SpannableStringBuilder(nameRestaurant + "\n" + description);
        text.setSpan(new StyleSpan(Typeface.BOLD), 0, nameRestaurant.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new RelativeSizeSpan(1.5f), 0, nameRestaurant.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        descriptionRestaurant.setText(text);

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=restaurant.getPhoneNumber();
                Intent callIntent=new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(number));
                startActivity(callIntent);
            }
        });

        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent= new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getWebsite()));
                startActivity(webIntent);
            }
        });

    }



    public static void navigate(FragmentActivity activity, Restaurant restaurant) {
        Intent intent = new Intent(activity, RestaurantProfilActivity.class);
        //intent.putExtra("restaurant", (Serializable) restaurant);
        activity.startActivity(intent);
    }
}