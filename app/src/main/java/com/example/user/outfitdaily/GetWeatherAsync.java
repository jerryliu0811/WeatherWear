package com.example.user.outfitdaily;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

// async class to get weather and GPS information
public class GetWeatherAsync extends AsyncTask <GPSTracker, Void, String[]>{

    final protected String[] doInBackground(GPSTracker... params) {
        String CityWeather="";
        String Temp = "";
        GPSTracker gps = params[0];
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            OpenWeatherMapTask WeatherObject = new OpenWeatherMapTask(latitude, longitude);
            WeatherObject.execute();
            CityWeather = WeatherObject.getCityWeather();
            Temp = WeatherObject.getTemp();
            String Weather[] = new String[2];
            Weather[0] = CityWeather;
            Weather[1] = Temp;
            return Weather;
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            //return "NoGPS";
            return  null;
        }
    }

}