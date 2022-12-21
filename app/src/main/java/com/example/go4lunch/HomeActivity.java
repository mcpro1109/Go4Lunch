package com.example.go4lunch;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Fragment.RestaurantListFragment;
import com.example.go4lunch.Fragment.RestaurantMapFragment;
import com.example.go4lunch.Fragment.WorkmateFragment;
import com.example.go4lunch.Viewmodel.HomeActivityViewModel;
import com.example.go4lunch.Viewmodel.WorkmateViewModel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceTypes;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout menuLeft;
    private NavigationView navigationView;
    private ImageView avatarLoginMenu;
    private TextView nameWorkmateLog;
    private TextView mailWorkmateLog;
    private HomeActivityViewModel homeActivityViewModel;

    private static int AUTOCOMPLETE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        toolbar = findViewById(R.id.toolbarmenu);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        menuLeft = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        avatarLoginMenu =navigationView.getHeaderView(0).findViewById(R.id.avatarMenu);
        nameWorkmateLog = navigationView.getHeaderView(0).findViewById(R.id.namePseudoMenu);
        mailWorkmateLog =  navigationView.getHeaderView(0).findViewById(R.id.mailPseudoMenu);

        setSupportActionBar(toolbar);
        homeActivityViewModel = new ViewModelProvider(this).get(HomeActivityViewModel.class);

        configureDrawerLayout();
        configureNavigationView();
        configureAvatarWorkmate();

        Window window = getWindow();
// Enable status bar translucency (requires API 19)
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// Disable status bar translucency (requires API 19)
        window.getAttributes().flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// Set a color (requires API 21)
        window.setStatusBarColor(Color.parseColor("#fb5607"));



    }


    private void configureNameWorkmate() {
        String name = TextUtils.isEmpty(homeActivityViewModel.getCurrentUser().getDisplayName()) ?
                getString(R.string.no_username_found) : homeActivityViewModel.getCurrentUser().getDisplayName();
        nameWorkmateLog.setText(name);

        String mail = TextUtils.isEmpty(homeActivityViewModel.getCurrentUser().getEmail()) ?
                getString(R.string.no_usermail_found) : homeActivityViewModel.getCurrentUser().getEmail();
        mailWorkmateLog.setText(mail);
    }

    private void configureAvatarWorkmate() {
        if (homeActivityViewModel.getCurrentUser() != null) {
            configureNameWorkmate();

            if (homeActivityViewModel.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                        .load(homeActivityViewModel.getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(avatarLoginMenu);
            } else {
                avatarLoginMenu.setImageResource(R.drawable.ic_baseline_people_24);
            }
        }
    }

    private void configureNavigationView() {
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.mapView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (menuLeft.isDrawerOpen(GravityCompat.START)) {
            menuLeft.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void configureDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, menuLeft,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_open);
        menuLeft.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //navigation in the bottomNavigation between the 3 fragments
            case R.id.mapView:
                RestaurantMapFragment restaurantMapFragment = RestaurantMapFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, restaurantMapFragment).commit();
                return true;

            case R.id.listView:
                RestaurantListFragment restaurantListFragment = RestaurantListFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, restaurantListFragment).commit();
                return true;

            case R.id.workmates:
                WorkmateFragment workmateFragment = WorkmateFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, workmateFragment).commit();
                return true;

                //navigation in the navigationView
            case R.id.yourLunchMenu:
                break;
            case R.id.settingsMenu:
                break;
            case R.id.logoutMenu:
                logout();
                return true;

        }
        this.menuLeft.closeDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    private void onSearchCalled() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
        Intent intent = new Autocomplete
                .IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("FR")
                .setLocationRestriction(RectangularBounds.newInstance(new LatLng(48.869717, 2.331679), new LatLng(48.866667, 2.333333)))
                .setTypesFilter(Collections.singletonList(PlaceTypes.RESTAURANT))
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_CODE);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, SplashActivity.class);
        Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onSearchCalled();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

            }else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(getApplicationContext(), status.getStatusMessage(),Toast.LENGTH_SHORT).show();
            }else if (resultCode == RESULT_CANCELED) {}
        }
    }


}