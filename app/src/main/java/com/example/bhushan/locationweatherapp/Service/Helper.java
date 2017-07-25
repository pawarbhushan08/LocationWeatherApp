package com.example.bhushan.locationweatherapp.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Bhushan on 7/25/2017.
 */

/*This class retrives the data from URL by making a HTTP request
*
* */

public class Helper {

    static String stream = null;

    public Helper() {
    }

    public String getHTTPData(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            if(httpURLConnection.getResponseCode() == 200)//HTTP_OK = 200
            {
                BufferedReader r = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line =r.readLine())!= null)
                    sb.append(line);
                stream = sb.toString();
                httpURLConnection.disconnect();

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stream;
    }

}
