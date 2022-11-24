package com.example.go4lunch.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.Period;
import java.util.List;

public class CurrentOpeningHours {

        @SerializedName("open_now")
        @Expose
        private boolean openNow;
        @SerializedName("periods")
        @Expose
        private List<java.time.Period> periods = null ;
        @SerializedName("weekday_text")
        @Expose
        private List<String> weekdayText = null;

        public boolean isOpenNow() {
            return openNow;
        }

        public void setOpenNow(boolean openNow) {
            this.openNow = openNow;
        }

        public List<java.time.Period> getPeriods() {
            return periods;
        }

        public void setPeriods(List<Period> periods) {
            this.periods = periods;
        }

        public List<String> getWeekdayText() {
            return weekdayText;
        }

        public void setWeekdayText(List<String> weekdayText) {
            this.weekdayText = weekdayText;
        }

}
