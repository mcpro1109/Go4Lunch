package com.example.go4lunch.Model;


import android.media.Image;

import java.sql.Time;

public class Restaurant {
    public String name;
    public int distance;
    public Image image;
    public String type;
    public String adresse;
    public String people;
    public Time hours;
    public int opinion;

    public Restaurant(String name, int distance, Image image, String type, String adresse, String people, Time hours, int opinion) {
        this.name = name;
        this.distance = distance;
        this.image = image;
        this.type = type;
        this.adresse = adresse;
        this.people = people;
        this.hours = hours;
        this.opinion = opinion;
    }


    public void name(Restaurant restaurant) {

    }
}
