package com.example.go4lunch.Model;

import android.media.Image;

import androidx.annotation.NonNull;

import java.util.Map;

public class Workmate {

    private Long id;
    private String name;
    private String firstName;


    public Workmate(){}

    public Workmate(Long id, String name, String firstName) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
