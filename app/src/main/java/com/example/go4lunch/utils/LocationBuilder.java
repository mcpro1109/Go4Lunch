package com.example.go4lunch.utils;

import android.location.Location;

public class LocationBuilder {
    public static Location create(double latitude, double longitude) {
        final Location location = new Location("location");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }
}
