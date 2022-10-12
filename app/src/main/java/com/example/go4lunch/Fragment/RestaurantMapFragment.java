package com.example.go4lunch.Fragment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.go4lunch.R;
import com.example.go4lunch.Viewmodel.RestaurantMapViewModel;
import com.example.go4lunch.Viewmodel.HomeFragmentsViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RestaurantMapFragment extends Fragment implements GoogleMap.OnPoiClickListener {

    private RestaurantMapViewModel restaurantMapViewModel;
    HomeFragmentsViewModel homeFragmentsViewModel;
    MapView mapView;
    private GoogleMap googleMap;
    private PlacesClient PlacesClient;
    private boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

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

        //async map
        supportMapFragment.getMapAsync(googleMap1 -> {
            OnMapReady(googleMap1);
        });

    }


    public void OnMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //position
        LatLng paris = new LatLng(48.866667, 2.333333);
        //add the zoom buttons
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        //user position
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        // position user a voir
        //googleMap.setMyLocationEnabled(true);
        // googleMap.setOnMyLocationButtonClickListener((GoogleMap.OnMyLocationButtonClickListener) this);
        // googleMap.setOnMyLocationClickListener((GoogleMap.OnMyLocationClickListener) this);

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
                .title(paris.latitude + " " + paris.longitude);
        //remove all markets
        googleMap.clear();
        //animate to zoom at the opening
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(paris, 18));
        //add marker on map
        googleMap.addMarker(markerOptions);


        //click on a POI
        this.googleMap=googleMap;
        googleMap.setOnPoiClickListener(this);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantMapViewModel = new ViewModelProvider(this).get(RestaurantMapViewModel.class);
        homeFragmentsViewModel = new ViewModelProvider(getActivity()).get(HomeFragmentsViewModel.class);

        configureButton();
        observeView();

        List<Place.Field> placeField= Collections.singletonList(Place.Field.NAME);
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeField);


        getLocationPermission();
    }

    private void observeView() {

    }

    private void configureButton() {

    }

    @Override
    public void onPoiClick(@NonNull PointOfInterest pointOfInterest) {
        //ici pour avoir les infos bulles
        Log.d("essai_clic", "Clicked: " +
                pointOfInterest.name + "\nPlace ID:" + pointOfInterest.placeId +
                "\nLatitude:" + pointOfInterest.latLng.latitude +
                " Longitude:" + pointOfInterest.latLng.longitude
        );
        Toast.makeText(getActivity(), "click" + pointOfInterest.name, Toast.LENGTH_SHORT).show();


    /*
     //infobulle à voir
     googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoContents(@NonNull Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,
                        (FrameLayout) mapView.findViewById(R.id.mapRestaurants), false);

                TextView title = infoWindow.findViewById(R.id.title);
                title.setText(marker.getTitle());

                TextView snippet = infoWindow.findViewById(R.id.snippet);
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }

            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                return null;
            }
        });*/
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
}