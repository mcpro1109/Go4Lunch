package com.example.go4lunch.utils;

import android.view.Menu;

public interface ContactRestaurant {

    void phoneCall();
    void websiteOpen();
    void like();

    boolean onPrepareOptionMenu(Menu menu);
}
