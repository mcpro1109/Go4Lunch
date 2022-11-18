package com.example.go4lunch.Model;


import android.location.Location;

import androidx.annotation.Nullable;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.api.responses.Result;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private String id;
    private String name;
    private int distance;
    @Nullable
    private String imageReference;
    @Nullable
    private String type;
    private String address;
    private int people;
    private double opinion;
    private Double latitude;
    private Double longitude;

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Restaurant(String id,
                      String name,
                      int distance,
                      @Nullable String imageReference,
                      @Nullable String type,
                      String address,
                      int people,
                      double opinion,
                      double latitude,
                      double longitude
    ) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.imageReference = imageReference;
        this.type = type;
        this.address = address;
        this.people = people;
        this.latitude = latitude;
        this.longitude = longitude;
        this.opinion = opinion;
    }

    public void setImageReference(@Nullable String imageReference) {
        this.imageReference = imageReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Nullable
    public String getType() {
        return type;
    }

    public void setType(@Nullable String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public double getOpinion() {
        return opinion;
    }

    public void setOpinion(double opinion) {
        this.opinion = opinion;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getImageURL(int width) {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + width + "&photoreference=" +
                imageReference +
                "&key=" +
                BuildConfig.PLACES_API_KEY;
    }

    public static Restaurant fromGoogleResponse(Location queryCenter, Result result) {
        Location androidLocation = new Location("");
        androidLocation.setLatitude(result.getGeometry().getLocation().getLat());
        androidLocation.setLongitude(result.getGeometry().getLocation().getLng());

        return new Restaurant(
                result.getPlaceId(),
                result.getName(),
                (int) queryCenter.distanceTo(androidLocation),
                result.getPhotos().get(0).getPhotoReference(),
                result.getTypes().get(0),
                //address,
                result.getVicinity(),
                2,
                result.getRating(),
                result.getGeometry().getLocation().getLat(),
                result.getGeometry().getLocation().getLng()
        );
    }
}
