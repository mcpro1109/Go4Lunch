package com.example.go4lunch.Fragment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.go4lunch.R;
import com.example.go4lunch.Viewmodel.RestaurantMapViewModel;
import com.example.go4lunch.Viewmodel.HomeFragmentsViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Collections;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class RestaurantMapFragment extends Fragment implements GoogleMap.OnPoiClickListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMapClickListener {

    private RestaurantMapViewModel restaurantMapViewModel;
    HomeFragmentsViewModel homeFragmentsViewModel;
    MapView mapView;
    private GoogleMap map;
    private PlacesClient PlacesClient;
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
        //async map
       /* supportMapFragment.getMapAsync(googleMap1 -> {
            OnMapReady(googleMap1);

        });*/

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
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
                .title(paris.latitude + " " + paris.longitude);
        //remove all markets
        googleMap.clear();
        //animate to zoom at the opening
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(paris, 18));
        //add marker on map
        googleMap.addMarker(markerOptions);


        //click on a POI to have the window of information
        this.map = googleMap;
        map.setOnMapClickListener(RestaurantMapFragment.this);
        googleMap.setOnPoiClickListener(this);


    }

  /*  public void OnMapReady( GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //position
        LatLng paris = new LatLng(48.866667, 2.333333);
        //add the zoom buttons
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        //user position
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

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
                .title(paris.latitude + " " + paris.longitude);
        //remove all markets
        googleMap.clear();
        //animate to zoom at the opening
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(paris, 18));
        //add marker on map
        googleMap.addMarker(markerOptions);


        //click on a POI
        this.map =googleMap;
        googleMap.setOnPoiClickListener(this);

    }*/

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
        homeFragmentsViewModel = new ViewModelProvider(getActivity()).get(HomeFragmentsViewModel.class);

        configureButton();
        observeView();

        List<Place.Field> placeField = Collections.singletonList(Place.Field.NAME);
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeField);

        //window information à voir nul object
       // map.setInfoWindowAdapter(new CustomWindowAdapter(getLayoutInflater()));
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

        map.clear();
        //blue marker
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
        Marker marker = map.addMarker(new MarkerOptions()
                .position(pointOfInterest.latLng)
                .title("ici")
                .snippet("snipper")
                .icon(bitmapDescriptor));



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

        //envoi à google Californie
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


    Marker myMarker;
    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        if (latLng.equals(myMarker))
        {
            map.setInfoWindowAdapter(new CustomWindowAdapter(getLayoutInflater()));
        }
    }


    //window information
    private static class CustomWindowAdapter implements GoogleMap.InfoWindowAdapter {
        LayoutInflater layout;

        public CustomWindowAdapter(LayoutInflater layoutInflater) {
            layout = layoutInflater;
        }

        @Nullable
        @Override
        public View getInfoContents(@NonNull Marker marker) {
            View view = layout.inflate(R.layout.custom_info_contents, null);
            TextView title = (TextView) view.findViewById(R.id.titleInfo);
            title.setText(marker.getTitle());
            TextView description = (TextView) view.findViewById(R.id.snippet);
            description.setText(marker.getSnippet());
            return view;

        }

        @Nullable
        @Override
        public View getInfoWindow(@NonNull Marker marker) {
            return null;
        }
    }
}