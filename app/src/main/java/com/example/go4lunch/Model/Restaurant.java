package com.example.go4lunch.Model;


import android.media.Image;

import androidx.annotation.Nullable;

import java.sql.Time;

public class Restaurant {
    public String name;
    public int distance;
    @Nullable
    public Image image;
    @Nullable
    public String type;
    public String adresse;
    public String people;
    public Time hours;
    public int opinion;

    public Restaurant(String name, int distance, @Nullable Image image, @Nullable String type, String adresse, String people, Time hours, int opinion) {
        this.name = name;
        this.distance = distance;
        this.image = image;
        this.type = type;
        this.adresse = adresse;
        this.people = people;
        this.hours = hours;
        this.opinion = opinion;
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
    public Image getImage() {
        return image;
    }

    public void setImage(@Nullable Image image) {
        this.image = image;
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

    public Time getHours() {
        return hours;
    }

    public void setHours(Time hours) {
        this.hours = hours;
    }

    public int getOpinion() {
        return opinion;
    }

    public void setOpinion(int opinion) {
        this.opinion = opinion;
    }
}
