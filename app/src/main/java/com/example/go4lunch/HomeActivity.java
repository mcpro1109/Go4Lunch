package com.example.go4lunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.go4lunch.Fragment.RestaurantListFragment;
import com.example.go4lunch.Fragment.RestaurantMapFragment;
import com.example.go4lunch.Fragment.WorkmateFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout menuLeft;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbarmenu);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        menuLeft = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);

        configureDrawerLayout();
        configureNavigationView();
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

}