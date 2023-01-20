package com.example.go4lunch.Model;


import androidx.annotation.Nullable;

import com.example.go4lunch.api.responses.CurrentOpeningHours;
import com.example.go4lunch.api.responsesDetails.ResultDetails;

import java.io.Serializable;

public class RestaurantDetails implements Serializable {

    private String placeId;
    @Nullable
    private CurrentOpeningHours hoursOpen;
    @Nullable
    private String phoneNumber;
    @Nullable
    private String website;
    private String name;

    public RestaurantDetails(
            String placeId,
            @Nullable CurrentOpeningHours hoursOpen,
            @Nullable String phoneNumber,
            @Nullable String website,
            String name) {
        this.placeId = placeId;
        this.hoursOpen = hoursOpen;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Nullable
    public CurrentOpeningHours getHoursOpen() {
        return hoursOpen;
    }

    public void setHoursOpen(CurrentOpeningHours hoursOpen) {
        this.hoursOpen = hoursOpen;
    }

    @Nullable
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
                result.getWebsite(),
                result.getName()
        );
    }
}
