package com.example.go4lunch.Model;

public class Notification {
    Boolean isEnabled;

    public Notification(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
