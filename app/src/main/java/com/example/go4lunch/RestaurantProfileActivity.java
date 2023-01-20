package com.example.go4lunch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Viewmodel.RestaurantProfileActivityViewModel;
import com.example.go4lunch.adapter.ProfileRestaurantRecyclerViewAdapter;
import com.example.go4lunch.utils.ContactRestaurant;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RestaurantProfileActivity extends AppCompatActivity implements ContactRestaurant {

    public static void navigate(FragmentActivity activity, Restaurant restaurant) {
        Intent intent = new Intent(activity, RestaurantProfileActivity.class);
        intent.putExtra("restaurant", restaurant);
        activity.startActivity(intent);
    }

    private ImageView imageRestaurant;
    private TextView descriptionRestaurant;
    private FloatingActionButton addWorkmateFab;
    private Toolbar toolbarProfile;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private RecyclerView recyclerView;
    private AppBarLayout appBarLayout;
    private boolean appBarExpanded = true;
    private Menu collapseMenu;

    private RestaurantProfileActivityViewModel restaurantProfileActivityViewModel;
    private ProfileRestaurantRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profil);

        imageRestaurant = findViewById(R.id.imageProfilRestaurant);
        descriptionRestaurant = findViewById(R.id.descriptionRestaurant);
        addWorkmateFab = findViewById(R.id.addworkmateRestaurant);
        toolbarProfile = findViewById(R.id.toolbarProfil);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        recyclerView = findViewById(R.id.restaurantProfileRecyclerview);
        appBarLayout = findViewById(R.id.app_bar_layout);

        //back with toolbar
        setSupportActionBar(toolbarProfile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarProfile.setNavigationOnClickListener(view -> onBackPressed());

        configureRecyclerview();

        restaurantProfileActivityViewModel = new ViewModelProvider(this).get(RestaurantProfileActivityViewModel.class);
        restaurantProfileActivityViewModel.setRestaurant((Restaurant) getIntent().getSerializableExtra("restaurant"));
        restaurantProfileActivityViewModel.getRestaurant().observe(this, restaurant -> {
            updateRestaurantInformation(restaurant);
            collapsingToolbarLayout.setTitle(restaurant.getName());
        });

        observeDetails();
        appBarMenuShow();
        configureWorkmateEating();

        addWorkmateFab.setOnClickListener(view -> restaurantProfileActivityViewModel.toggleEat());
    }

    @SuppressLint("ResourceAsColor")
    private void configureWorkmateEating() {
        restaurantProfileActivityViewModel.getWorkmateData().observe(this, workmate -> {
            adapter.update(workmate);
        });

        restaurantProfileActivityViewModel.fabBackground.observe(this, color -> {
            addWorkmateFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(RestaurantProfileActivity.this, color)));
        });

        restaurantProfileActivityViewModel.likeBackground.observe(this, color -> {
            adapter.updateHeader(ContextCompat.getColor(RestaurantProfileActivity.this, color));
        });
    }

    private void updateRestaurantInformation(Restaurant restaurant) {
        Glide
                .with(imageRestaurant)
                .load(restaurant.getImageURL(400))
                .apply(RequestOptions.centerCropTransform())
                .placeholder(R.drawable.logo_food)
                .into(imageRestaurant);

        String description = restaurant.getType() + " " + restaurant.getAddress();
        descriptionRestaurant.setText(description);
    }

    private void configureRecyclerview() {
        adapter = new ProfileRestaurantRecyclerViewAdapter(this, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    public void observeDetails() {
        restaurantProfileActivityViewModel.getRestaurantData().observe(this, restaurantDetails -> {
            if (restaurantDetails != null) {
                adapter.updateWithDetails(restaurantDetails);
            }
        });
    }

    @Override
    public void phoneCall() {
        String number = restaurantProfileActivityViewModel.getRestaurantData().getValue().getPhoneNumber();
        if (number != null) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + number));
            startActivity(callIntent);
        } else {
            Toast.makeText(RestaurantProfileActivity.this, "no phone", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void websiteOpen() {
        String website = restaurantProfileActivityViewModel.getRestaurantData().getValue().getWebsite();
        if (website != null) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
            startActivity(webIntent);
        } else {
            Toast.makeText(RestaurantProfileActivity.this, "no website", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void like() {
        restaurantProfileActivityViewModel.toggleLike();
    }

    private void appBarMenuShow() {
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            //  Vertical offset == 0 indicates appBar is fully expanded.
            if (Math.abs(verticalOffset) > 600) {
                appBarExpanded = false;
                invalidateOptionsMenu();
            } else {
                appBarExpanded = true;
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onPrepareOptionMenu(Menu menu) {
        if (collapseMenu != null && (!appBarExpanded || collapseMenu.size() != 1)) {
            collapseMenu
                    .add("Add")
                    .setIcon(R.drawable.ic_baseline_add_24)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        return super.onPrepareOptionsMenu(collapseMenu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        collapseMenu = menu;
        onPrepareOptionMenu(menu);
        return true;
    }
}