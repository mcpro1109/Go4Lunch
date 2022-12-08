package com.example.go4lunch.Model;

import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;
import java.util.ArrayList;

public class Workmate implements Serializable {

    private String id;
    private String name;
    private String firstName;
    private Boolean isEating;
    private MutableLiveData<Boolean> workmateEating = new MutableLiveData(new ArrayList());

    public Workmate() {
    }

    public Workmate(String id, String name, String firstName, Boolean isEating, MutableLiveData workmateEating) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.isEating=isEating;
        this.workmateEating=workmateEating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean getEating() {
        return isEating;
    }

    public void setEating(Boolean eating) {
        isEating = eating;
    }
}
