package com.example.go4lunch;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConnexionActivity extends AppCompatActivity {
    Button loginButton;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(ConnexionActivity.this, WelcomeScreen.class);
            startActivityForResult(intent, 2);
        });

        firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //if user is signed in, we call a helper method to save the user details to Firebase
                if (user != null) {
                    // User is signed in
                    // you could place other firebase code
                    //logic to save the user details to Firebase
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }

        };
        // setupListeners();
    }

   /* private void setupListeners() {
// Login Button
        loginButton.setOnClickListener(view -> {
            startSignInActivity();
        });

    }*/

    private void startSignInActivity() {

        // Choose authentication providers
       /* List<AuthUI.IdpConfig> providers;
        providers = new AuthUI.IdpConfig.GoogleBuilder().build();

        // Launch the activity
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.Theme_Go4Lunch)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.ic_baseline_hourglass_top_24)
                        .build(),
                RC_SIGN_IN);*/
    }
}