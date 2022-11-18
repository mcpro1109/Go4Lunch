package com.example.go4lunch.Fragment;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.R;
import com.example.go4lunch.RestaurantProfileActivity;
import com.example.go4lunch.Viewmodel.HomeActivityViewModel;
import com.example.go4lunch.Viewmodel.RestaurantMapViewModel;
import com.example.go4lunch.adapter.CustomWindowInfoAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

import pub.devrel.easypermissions.EasyPermissions;

public class RestaurantMapFragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private RestaurantMapViewModel restaurantMapViewModel;
    HomeActivityViewModel homeActivityViewModel;
    private GoogleMap map;
    private boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public static RestaurantMapFragment newInstance() {
        return new RestaurantMapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_restaurant_map, container, false);

        getMap();

        return result;
    }

    private void getMap() {
        //initialize map
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapRestaurants);
        supportMapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //position
        LatLng paris = new LatLng(48.866667, 2.333333);
        //add the zoom buttons
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        //user position
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        // position user a voir
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationClickListener(this);

        //different options
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        googleMapOptions.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false);

        MarkerOptions markerOptions = new MarkerOptions();
        //set position on marker
        markerOptions.position(paris)
                //set title of marker
                .title("Paris")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        //remove all markets
        googleMap.clear();
        //animate to zoom at the opening
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paris, 17));
        //add marker on map
        googleMap.addMarker(markerOptions);

        //click on a POI to have the window of information
        map.setOnMapClickListener(RestaurantMapFragment.this);
        map.setOnMarkerClickListener(this);

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.maps));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        //to have the infobulle
        observeList();
        map.setOnInfoWindowClickListener(this);
    }


    private void observeList() {
        homeActivityViewModel.getRestaurantData().observe(getViewLifecycleOwner(), restaurants -> {
            for (Restaurant restaurant : restaurants) {
                CustomWindowInfoAdapter markerInfoWindowAdapter = new CustomWindowInfoAdapter(getContext());

                map.setInfoWindowAdapter(markerInfoWindowAdapter);

                String infoRestaurant = restaurant.getAddress() + "\n" + "opinion: " + restaurant.getOpinion();
                Marker marker = map.addMarker(
                        new MarkerOptions()
                                .position(new LatLng(restaurant.getLatitude(), restaurant.getLongitude()))
                                .title(restaurant.getName())
                                .snippet(infoRestaurant)
                );
                marker.setTag(restaurant.getId());
                marker.showInfoWindow();
            }
        });
    }


    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            return;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantMapViewModel = new ViewModelProvider(this).get(RestaurantMapViewModel.class);
        homeActivityViewModel = new ViewModelProvider(getActivity()).get(HomeActivityViewModel.class);


        getLocationPermission();

        homeActivityViewModel.start();
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public boolean onMyLocationButtonClick() {

        LatLng paris = new LatLng(48.866667, 2.333333);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(paris, 18));
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        LatLng paris = new LatLng(48.866667, 2.333333);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        if (marker.getTag() != null) {
            Restaurant restaurant = homeActivityViewModel.getRestaurantByTag(marker.getTag().toString());
            if (restaurant != null) {

            }
            return false;
        }
        return false;
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        String title = marker.getTitle();
        Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT).show();
        if (marker.getTag() != null) {
            Restaurant restaurant = homeActivityViewModel.getRestaurantByTag(marker.getTag().toString());

            if (restaurant != null) {
                Intent intent = new Intent(RestaurantMapFragment.this.getActivity(), RestaurantProfileActivity.class);
                intent.putExtra("restaurant", (Serializable) restaurant);
                startActivity(intent);
            }
        }
    }
}