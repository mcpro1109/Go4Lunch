package com.example.go4lunch.Model;

import android.media.Image;

import androidx.annotation.NonNull;

import java.util.Map;

public class Workmate {

    private String id;
    private String name;
    private String firstName;
    private String avatarUrl;

    public Workmate(String id, String name, String firstName, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.avatarUrl = avatarUrl;
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

    public String getAvatar() {
        return avatarUrl;
    }

    public void setAvatar(Image avatar) {
        this.avatarUrl = avatarUrl;
    }

    @NonNull
    public static Workmate[] getAllWorkmates(){
        return new Workmate[]{
                new Workmate("1", "Dupond", "Alain", "url"),
                new Workmate("2", "Durand", "Elise", "url"),
                new Workmate("3", "Martin", "Paul", "url"),

        };
    }

    public Object[] workmates(){
        return new Workmate[]{
                new Workmate("1", "Dupond", "Alain", "url"),
                new Workmate("2", "Durand", "Elise", "url"),
                new Workmate("3", "Martin", "Paul", "url"),

        };
    }
}
