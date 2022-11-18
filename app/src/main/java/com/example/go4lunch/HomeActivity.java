package com.example.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Fragment.RestaurantListFragment;
import com.example.go4lunch.Fragment.RestaurantMapFragment;
import com.example.go4lunch.Fragment.WorkmateFragment;
import com.example.go4lunch.Viewmodel.WorkmateViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout menuLeft;
    private NavigationView navigationView;
    private ImageView avatarLoginMenu;
    private TextView nameWorkmateLog;
    private TextView mailWorkmateLog;
    private WorkmateViewModel workmateViewModel;
    private static int AUTOCOMPLETE_CODE = 1;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbarmenu);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        menuLeft = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        avatarLoginMenu = findViewById(R.id.avatarMenu);
        nameWorkmateLog = findViewById(R.id.namePseudoMenu);
        mailWorkmateLog = findViewById(R.id.mailPseudoMenu);

        setSupportActionBar(toolbar);

        configureDrawerLayout();
        configureNavigationView();
        //configureAvatarWorkmate();
        // configureSearchBar();
    }

    private void configureSearchBar() {


    }

    private void configureNameWorkmate() {
        String name = TextUtils.isEmpty(workmateViewModel.getCurrentUser().getDisplayName()) ?
                getString(R.string.no_username_found) : workmateViewModel.getCurrentUser().getDisplayName();
        nameWorkmateLog.setText(name);

        String mail = TextUtils.isEmpty(workmateViewModel.getCurrentUser().getEmail()) ?
                getString(R.string.no_usermail_found) : workmateViewModel.getCurrentUser().getEmail();
        mailWorkmateLog.setText(mail);
    }

    // à voir null objet
    private void configureAvatarWorkmate() {
      /*  if (workmateViewModel.isCurrentWorkmateLogin()) {
            FirebaseUser firebaseUser = workmateViewModel.getCurrentUser();
            configureNameWorkmate(firebaseUser);
            if (firebaseUser.getPhotoUrl() != null) {
                Glide.with(this)
                        .load("https://ui-avatars.com/api/?name=" + firebaseUser.getPhotoUrl() + "&background=random")
                        .apply(RequestOptions.circleCropTransform())
                        .into(avatarLoginMenu);
            } else {
                Glide.with(this)
                        .load("https://ui-avatars.com/api/?name=Ma&background=random")
                        .apply(RequestOptions.circleCropTransform())
                        .into(avatarLoginMenu);
            }
        }*/
        if (workmateViewModel.getCurrentWorkmate() != null) {
            configureNameWorkmate();

            if (workmateViewModel.getCurrentWorkmate().getPhotoUrl() != null) {
                Glide.with(this)
                        .load("https://ui-avatars.com/api/?name=" + workmateViewModel.getCurrentWorkmate().getPhotoUrl() + "&background=random")
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