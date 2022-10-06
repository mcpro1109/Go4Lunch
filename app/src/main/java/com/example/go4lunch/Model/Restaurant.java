package com.example.go4lunch.Model;


import android.media.Image;

import androidx.annotation.Nullable;

import java.sql.Time;

public class Restaurant {
    public String name;
    public int distance;
    @Nullable
    public String imageURL;
    @Nullable
    public String type;
    public String adresse;
    public String people;
    public int opinion;

    public Restaurant(String name, int distance, @Nullable String imageURL, @Nullable String type, String adresse, String people, int opinion) {
        this.name = name;
        this.distance = distance;
        this.imageURL = imageURL;
        this.type = type;
        this.adresse = adresse;
        this.people = people;
        this.opinion = opinion;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public int getOpinion() {
        return opinion;
    }

    public void setOpinion(int opinion) {
        this.opinion = opinion;
    }
}
