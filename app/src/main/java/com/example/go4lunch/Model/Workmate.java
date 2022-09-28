package com.example.go4lunch.Model;

import android.media.Image;

public class Workmate {
    private int id;
    private String name;
    private String firstName;
    private Image avatar;

    public Workmate(int id, String name, String firstName, Image avatar) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }
}
