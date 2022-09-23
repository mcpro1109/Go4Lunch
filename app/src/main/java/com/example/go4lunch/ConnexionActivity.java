package com.example.go4lunch;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

public class ConnexionActivity extends AppCompatActivity {
    ImageView loginButton;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(view -> {
            startSignInActivity();
        });


       /* loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(ConnexionActivity.this, HomeActivity.class);
            startActivityForResult(intent, 2);
        });*/


    }

    private void setupListeners() {
// Login Button
        loginButton.setOnClickListener(view -> {
            // startSignInActivity();
            AuthUI.getInstance().signOut(ConnexionActivity.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ConnexionActivity.this, "User Signed Out", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ConnexionActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    });
        });
    }


    private void startSignInActivity() {

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers;
        providers = Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build());
        // Launch the activity
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.Theme_Go4Lunch)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(com.firebase.ui.auth.R.drawable.common_google_signin_btn_icon_light_normal)
                        .build(),
                RC_SIGN_IN);

        Intent intent = new Intent(ConnexionActivity.this, HomeActivity.class);
        startActivityForResult(intent, 2);
        Toast.makeText(ConnexionActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
    }

}