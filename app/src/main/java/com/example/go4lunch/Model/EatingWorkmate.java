package com.example.go4lunch.Model;

import java.io.Serializable;

public class EatingWorkmate implements Serializable {
    String workmate_id;
    String restaurant_id;

    public EatingWorkmate(String workmate_id, String restaurant_id) {
        this.workmate_id = workmate_id;
        this.restaurant_id = restaurant_id;
    }
    public EatingWorkmate(){}

    public String getWorkmate_id() {
        return workmate_id;
    }

    public void setWorkmate_id(String workmate_id) {
        this.workmate_id = workmate_id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}
