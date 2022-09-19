package com.example.go4lunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.go4lunch.Fragment.RestaurantListFragment;
import com.example.go4lunch.Fragment.RestaurantMapFragment;
import com.example.go4lunch.Fragment.WorkmateFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WelcomeScreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    RestaurantListFragment restaurantListFragment;
    RestaurantMapFragment restaurantMapFragment;
    WorkmateFragment workmateFragment;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView=findViewById(R.id.bottonNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.mapView);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //navigationbar choice between the 3 fragments
        switch (item.getItemId()) {
            case R.id.mapView:
                RestaurantMapFragment restaurantMapFragment=new RestaurantMapFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, restaurantMapFragment).commit();

                return true;
            case R.id.listView:
                RestaurantListFragment restaurantListFragment= new RestaurantListFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, restaurantListFragment).commit();

                return true;
            case R.id.workmates:
                //Log.d("essai", "fragment work");
                WorkmateFragment workmateFragment= new WorkmateFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, workmateFragment).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}