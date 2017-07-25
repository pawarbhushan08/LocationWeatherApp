package com.example.bhushan.locationweatherapp.Common;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bhushan on 7/25/2017.
 */
/*This class performs following operations
1. Making an API request to access the Weather API
2. Create a unix timestamp to get Current Date and time.
3. Making an API request to access image icon from the Weather API
*
* */
public class Common {

    public static String API_KEY ="0a9c8b845f566dd97bf703fcc664778b";
    public static String API_URL = "http://api.openweathermap.org/data/2.5/weather";

    @NonNull
    public static String apiRequest(String lat, String lng){
        StringBuilder sb = new StringBuilder(API_URL);//
        sb.append(String.format("?lat=%s&lon=%s&appid=%s",lat,lng,API_KEY));//append the remaining data to URL
        return sb.toString();


    }

    public static String unixTimeStampToDateTime(double unixTimeStamp){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long)unixTimeStamp*1000);//time is in milliseconds(Hence multiplied by 1000)
        return dateFormat.format(date);
    }


    public static String getDateNow(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getImage(String icon){
        return String.format("http://api.openweathermap.org/img/w/%s.png",icon);

    }

}
