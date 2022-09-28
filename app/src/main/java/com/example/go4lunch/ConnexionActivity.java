package com.example.go4lunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class ConnexionActivity extends AppCompatActivity {
    ImageView loginGoogleButton;
    LoginButton loginFacebookButton;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CallbackManager callbackManager;

    // Choose authentication providers
    List<AuthUI.IdpConfig>providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        loginGoogleButton = findViewById(R.id.loginButton);
        loginFacebookButton = findViewById(R.id.loginFacebookButton);


        loginGoogleButton.setOnClickListener(view -> {
            startSignInActivity();
        });


        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        startSignInActivity();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

       /* loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(ConnexionActivity.this, HomeActivity.class);
            startActivityForResult(intent, 2);
        });*/

    }


    private void startSignInActivity() {


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