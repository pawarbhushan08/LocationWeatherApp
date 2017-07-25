package com.example.bhushan.locationweatherapp.Model;

/**
 * Created by Bhushan on 7/25/2017.
 */

public class Weather {

    private String description;
    private String icon;

    public Weather(String description, String icon) {
        this.description = description;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
