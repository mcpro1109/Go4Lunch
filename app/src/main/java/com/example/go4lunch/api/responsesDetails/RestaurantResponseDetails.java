
package com.example.go4lunch.api.responsesDetails;

import java.util.List;

import com.example.go4lunch.api.responses.Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RestaurantResponseDetails {

    @SerializedName("html_attributions")
    @Expose
    private List<Object> htmlAttributions = null;

    @SerializedName("result")
    @Expose
    private ResultDetails resultDetails;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public ResultDetails getResult() {
        return resultDetails;
    }

    public void setResult(ResultDetails resultDetails) {
        this.resultDetails = resultDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
