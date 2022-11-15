package com.example.go4lunch.Model;


import android.location.Location;

import androidx.annotation.Nullable;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.api.responses.CurrentOpeningHours;
import com.example.go4lunch.api.responses.RestaurantResponse;
import com.example.go4lunch.api.responses.Result;
import com.example.go4lunch.api.responsesDetails.ResultDetails;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantDetails implements Serializable {

    private String id;
    private CurrentOpeningHours hoursOpen;
    private String phoneNumber;
    private String website;

    public RestaurantDetails(
            String id,
            CurrentOpeningHours hoursOpen,
            String phoneNumber,
            String website) {
        this.id = id;
        this.hoursOpen = hoursOpen;
        this.phoneNumber = phoneNumber;
        this.website = website;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CurrentOpeningHours getHoursOpen() {
        return hoursOpen;
    }

    public void setHoursOpen(CurrentOpeningHours hoursOpen) {
        this.hoursOpen = hoursOpen;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public static RestaurantDetails fromGoogleResponseDetails(ResultDetails result) {

        return new RestaurantDetails(
                result.getPlaceId(),
                result.getCurrentOpeningHours(),
                result.getFormattedPhoneNumber(),
                result.getWebsite()
        );
    }
}
