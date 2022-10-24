package com.example.go4lunch.Model;


import android.media.Image;

import androidx.annotation.Nullable;

import com.example.go4lunch.api.responses.OpeningHours;
import com.example.go4lunch.api.responses.Photo;
import com.example.go4lunch.api.responses.RestaurantResponse;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;

public class Restaurant implements Serializable {
    private String name;
    private int distance;
    @Nullable
    private String imageURL;
    @Nullable
    private String type;
    private String address;
    private int people;
    private Boolean hoursOpen;
    private double opinion;
    private String phoneNumber;
    private String website;


    public Restaurant(String name, int distance,
                      @Nullable String imageURL, @Nullable String type,
                      String address, Boolean hoursOpen,
                      int people, double opinion,
                      String phoneNumber, String website) {
        this.name = name;
        this.distance = distance;
        this.imageURL = imageURL;
        this.type = type;
        this.address = address;
        this.hoursOpen=hoursOpen;
        this.people = people;
        this.opinion = opinion;
        this.phoneNumber=phoneNumber;
        this.website=website;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setImageURL(@Nullable String imageURL) {
        this.imageURL = imageURL;
    }

    public Boolean getHoursOpen() {
        return hoursOpen;
    }

    public void setHoursOpen(Boolean hoursOpen) {
        this.hoursOpen = hoursOpen;
    }

    public Restaurant(){}

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
    public String getImageURL() {
        return imageURL;
    }

    public void setImage(@Nullable String imageURL) {
        this.imageURL = imageURL;
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

    private ArrayList<RestaurantResponse> restaurantResponseArrayList;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
