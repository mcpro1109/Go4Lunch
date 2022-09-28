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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout menuLeft;
    private NavigationView navigationView;

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;

    private FirebaseAuth.AuthStateListener authStateListener;
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbarmenu);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottonNavigationView);
        menuLeft = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.mapView);

        configureDrawerLayout();
        configureNavigationView();

        //connexion
        logUser();

        firebaseAuth.addAuthStateListener(authStateListener);
        firebaseAuth.removeAuthStateListener(authStateListener);

    }

    private void logUser() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // if the user is already authenticated then we will
                    // redirect our user to next screen which is our home screen.
                    // we are redirecting to new screen via an intent.
                    Intent i = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setTheme(R.style.Theme_Go4Lunch)
                                    .setAvailableProviders(providers)
                                    .setIsSmartLockEnabled(false,true)
                                    .setLogo(R.drawable.logo_food)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    private void configureNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    //a voir
    public void OnBackPressed() {
        //handle back click to close menu
        if (this.menuLeft.isDrawerOpen(GravityCompat.START)) {
            this.menuLeft.closeDrawer(GravityCompat.START);
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
        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
        Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


}