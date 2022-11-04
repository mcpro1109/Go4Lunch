package com.example.go4lunch.Fragment;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.R;
import com.example.go4lunch.Viewmodel.HomeActivityViewModel;
import com.example.go4lunch.Viewmodel.RestaurantMapViewModel;
import com.example.go4lunch.adapter.CustomWindowInfoAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class RestaurantMapFragment extends Fragment implements GoogleMap.OnPoiClickListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private RestaurantMapViewModel restaurantMapViewModel;
    HomeActivityViewModel homeActivityViewModel;
    MapView mapView;
    private GoogleMap map;
    private PlacesClient PlacesClient;
    private boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Marker marker;
    private Restaurant restaurant;
    private ArrayList<Restaurant> restaurantArrayList;

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

        observeList();

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
        // enableMyLocation();

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
                .title("Paris");
        //remove all markets
        googleMap.clear();
        //animate to zoom at the opening
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paris, 18));
        //add marker on map
        googleMap.addMarker(markerOptions);

        //click on a POI to have the window of information
        map.setOnMapClickListener(RestaurantMapFragment.this);
        map.setOnMarkerClickListener(this);
        googleMap.setOnPoiClickListener(this);

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
    }

    private void observeList() {
        homeActivityViewModel.getRestaurantData().observe(getViewLifecycleOwner(), restaurants -> {
            map.clear();

            for(Restaurant restaurant : restaurants){
                map.addMarker(
                        new MarkerOptions()
                                .position(new LatLng(restaurant.getLatitude(), restaurant.getLongitude()))
                                .title(restaurant.getName())
                ).setTag(restaurant.getName());
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

        configureButton();
        observeView();

        getLocationPermission();

        homeActivityViewModel.start();
    }

    private void observeView() {

    }

    private void configureButton() {

    }

    @Override
    public void onPoiClick(@NonNull PointOfInterest pointOfInterest) {
        //ici pour avoir les infos bulles
       /* Log.d("essai_clic", "Clicked: " +
                pointOfInterest.name + "\nPlace ID:" + pointOfInterest.placeId +
                "\nLatitude:" + pointOfInterest.latLng.latitude +
                " Longitude:" + pointOfInterest.latLng.longitude
        );
        Toast.makeText(getActivity(), "click" + pointOfInterest.name, Toast.LENGTH_SHORT).show();*/

        map.clear();


        //window info restaurant
        CustomWindowInfoAdapter markerInfoWindowAdapter = new CustomWindowInfoAdapter(getContext());
        map.setInfoWindowAdapter(markerInfoWindowAdapter);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(pointOfInterest.latLng);

        // String infos= restaurant.getType() + "\n" + restaurant.getAddress()+ "\n" + restaurant.getOpinion();
        Marker restaurantClick = map.addMarker(new MarkerOptions()
                .position(pointOfInterest.latLng)
                //infos to receive here
                .title(pointOfInterest.name)
                .snippet("infos:" + pointOfInterest.latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );
        restaurantClick.showInfoWindow();


      /*  BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
        Marker marker = map.addMarker(new MarkerOptions()
                .position(pointOfInterest.latLng)
                .title("ici")
                .snippet("snipper")
                .icon(bitmapDescriptor));*/

    }

    public void setMarckerOnMap(GoogleMap googleMap, ArrayList<Restaurant> restaurantArrayList) {
        for (Restaurant restaurant : restaurantArrayList) {
            String infos = restaurant.getType() + "\n" + restaurant.getAddress() + "\n" + restaurant.getOpinion();

            Marker restaurantClick = map.addMarker(new MarkerOptions()
                    .position(new LatLng(restaurant.getLatitude(), restaurant.getLongitude()))
                    //infos to receive here
                    .title(restaurant.getName())
                    .snippet("infos:" + infos)
            );
            restaurantClick.showInfoWindow();
        }
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
        if(marker.getTag() != null){
            Restaurant restaurant = homeActivityViewModel.getRestaurantByTag(marker.getTag().toString());

            if(restaurant != null){

            }

            return restaurant != null;
        }
        return false;
    }
}