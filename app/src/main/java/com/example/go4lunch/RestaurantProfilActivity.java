package com.example.go4lunch;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Model.RestaurantDetails;
import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.Viewmodel.RestaurantMapViewModel;
import com.example.go4lunch.Viewmodel.RestaurantProfileActivityViewModel;
import com.example.go4lunch.adapter.ProfilRestaurantRecyclerViewAdapter;
import com.example.go4lunch.api.responses.Result;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantProfilActivity extends AppCompatActivity {

    private ImageView imageRestaurant;
    private TextView descriptionRestaurant;
    private FloatingActionButton addWorkmate;
    private Button phoneButton;
    private Button likeButton;
    private Button webButton;
    private Toolbar toolbarProfil;
    private RestaurantProfileActivityViewModel restaurantProfileActivityViewModel;
    private RecyclerView recyclerView;

    private ArrayList<Workmate> workmate=new ArrayList<>();

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
        toolbarProfil = findViewById(R.id.toolbarProfil);
        recyclerView=findViewById(R.id.restaurantProfileRecyclerview);

        //back with toolbar
        setSupportActionBar(toolbarProfil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarProfil.setNavigationOnClickListener(view -> onBackPressed());


        ProfilRestaurantRecyclerViewAdapter adapter=new ProfilRestaurantRecyclerViewAdapter(this,workmate );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        //insertion of the values restaurant
        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
        RestaurantDetails restaurantDetails = (RestaurantDetails) getIntent().getSerializableExtra("restaurantDetails");//null ici à voir

        restaurantProfileActivityViewModel = new ViewModelProvider(this).get(RestaurantProfileActivityViewModel.class);
        restaurantProfileActivityViewModel.updateRestaurantDetail();
         ObserveDetails();

        //ici à continuer
        //  String phone=restaurantDetails.getPhoneNumber();
        // restaurantProfileActivityViewModel.getDetailsWithPlaceId(restaurantDetails.getId());
        //  Log.e("essai id", restaurantDetails.getWebsite());

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

       /* phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + 0134770565));
                startActivity(callIntent);*/
                // String number = restaurant.getPhoneNumber();
                // Toast.makeText(RestaurantProfilActivity.this, restaurantDetails.getPhoneNumber(), Toast.LENGTH_SHORT).show();
               /* if (number!=null) {
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
               /* if (restaurant.getWebsite()!=null) {
                   // OpenWebSite(restaurant.getWebsite());

                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getWebsite()));
                    startActivity(webIntent);
                }else{
                    Toast.makeText(RestaurantProfilActivity.this, "no website", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

    }

    public void OpenWebSite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public static void navigate(FragmentActivity activity, Restaurant restaurant, RestaurantDetails restaurantDetails) {
        Intent intent = new Intent(activity, RestaurantProfilActivity.class);
        intent.putExtra("restaurant", (Serializable) restaurant);
        intent.putExtra("restaurantDetails", (Serializable) restaurantDetails);
        activity.startActivity(intent);
    }

    public void ObserveDetails() {
        restaurantProfileActivityViewModel.getRestaurantData().observe(this, restaurantDetails -> {

                    if (restaurantDetails.getPhoneNumber() != null) {
                        String number = restaurantDetails.getPhoneNumber();
                        phoneButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(RestaurantProfilActivity.this, number, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(RestaurantProfilActivity.this, "no phone", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}