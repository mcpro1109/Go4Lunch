
package com.example.go4lunch.api.responsesDetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Close {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("day")
    @Expose
    private int day;
    @SerializedName("time")
    @Expose
    private String time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
