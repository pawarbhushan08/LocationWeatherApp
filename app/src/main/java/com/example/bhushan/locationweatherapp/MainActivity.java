package com.example.bhushan.locationweatherapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bhushan.locationweatherapp.Common.Common;
import com.example.bhushan.locationweatherapp.Model.OpenWeatherMap;
import com.example.bhushan.locationweatherapp.Service.Helper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView txtCity, txtLastUpdate, txtHumidity, txtDescription, txtTime, txtCelcius;
    ImageView imageView;

    LocationManager locationManager;
    String provider;
    static double lat, lng;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize views
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtLastUpdate = (TextView) findViewById(R.id.txtLastUpdate);
        txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtCelcius = (TextView) findViewById(R.id.txtCelcius);
        imageView = (ImageView) findViewById(R.id.imageView);

        //Receive the Coordinates
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        //Runtime Permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,

            }, 0);
        }

        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null)//Location returns null
            Log.e("TAG", "No Location");

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);//Removes all location updates for the specified pending intent
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
            }, 0);
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);//Register for location updates using the named provider, and a pending intent
    }


    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();//Current Latitude
        lng = location.getLongitude();//Current Longitude

        new GetWeather().execute(Common.apiRequest(String.valueOf(lat),String.valueOf(lng)));//AsyncTask call

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private class GetWeather extends AsyncTask<String,Void,String>{

        ProgressDialog pd = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait....");//Progress Dialogue Message
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String stream;
            String urlString = params[0];//Load the URL in String
            Helper http = new Helper();
            stream = http.getHTTPData(urlString);//Get the data from URL
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.contains("Error: City Not Found")){
                pd.dismiss();
                return;
            }
            Gson gson = new Gson();//GSON used to parse JSON to our class
            Type mType = new TypeToken<OpenWeatherMap>(){}.getType();
            openWeatherMap = gson.fromJson(s,mType);
            pd.dismiss();

            //Loading the received data on respective TextViews and Imageview
            txtCity.setText(String.format("%s,%s",openWeatherMap.getName(),openWeatherMap.getSys().getCountry()));
            txtLastUpdate.setText(String.format("Last Updated: %s", Common.getDateNow()));
            txtDescription.setText(String.format("Description: %s",openWeatherMap.getWeather().get(0).getDescription()));
            txtHumidity.setText(String.format("Humidity: %d%%",openWeatherMap.getMain().getHumidity()));
            txtTime.setText(String.format("Sunrise/Sunset: %s/%s", Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise()), Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
            txtCelcius.setText(String.format("Temperature: %.2f°C", openWeatherMap.getMain().getTemp()-273.15));//Converting Temperature from °K to °C
            Picasso.with(MainActivity.this).load(Common.getImage(openWeatherMap.getWeather().get(0).getIcon())).into(imageView);

        }
    }
}
