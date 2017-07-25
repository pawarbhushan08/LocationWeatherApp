package com.example.bhushan.locationweatherapp.Model;

import java.util.List;

/**
 * Created by Bhushan on 7/25/2017.
 */
/*This class represents the required JSON objects and Get the data from it.*/

public class OpenWeatherMap {

    private Main main;
    private Sys sys;
    private List<Weather> weather;

    private String name;

    public OpenWeatherMap(){}

    public OpenWeatherMap(Main main, Sys sys, List<Weather> weather, String base, double dt, double id, String name, int cod) {
        this.main = main;
        this.sys = sys;
        this.weather = weather;
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
