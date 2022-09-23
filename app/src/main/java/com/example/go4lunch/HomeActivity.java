package com.example.go4lunch;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.go4lunch.Fragment.RestaurantListFragment;
import com.example.go4lunch.Fragment.RestaurantMapFragment;
import com.example.go4lunch.Fragment.WorkmateFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;
    private FirebaseAuth.AuthStateListener authStateListener;
    List<AuthUI.IdpConfig> providers = Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottonNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.mapView);

        firebaseAuth = FirebaseAuth.getInstance();
        createRequest();
     /*   firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // if the user is already authenticated then we will
                    // redirect our user to next screen which is our home screen.
                    // we are redirecting to new screen via an intent.
                    Intent i = new Intent(HomeActivity.this, ConnexionActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setTheme(R.style.Theme_Go4Lunch)
                                    .setAvailableProviders(providers)
                                    .setIsSmartLockEnabled(false, true)
                                    .setLogo(com.firebase.ui.auth.R.drawable.common_google_signin_btn_icon_light_normal)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };*/

    }

    private void createRequest() {
        googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //navigationbar choice between the 3 fragments
        switch (item.getItemId()) {
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
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}