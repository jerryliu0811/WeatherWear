package com.example.user.outfitdaily;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
// OpenWeatherMap api
public class OpenWeatherMapTask {
    double latitude ;
    double longitude ;
    String CityName ;
    String CountryName;
    String Temp, MinTemp, MaxTemp;
    String Humidity;
    String CityWeather = "";
    String dummyAppid = "bd82977b86bf27fb59a04b61b657fb6f";
    String queryWeather = "http://api.openweathermap.org/data/2.5/weather?";
    String queryDummyKey = "&appid=fc53ffb7bfae6cb9176fdc503fe1af35";

    OpenWeatherMapTask(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getCityName() { return CityName; }

    public String getTemp() { return Temp; }

    public String getMinTemp() { return MinTemp; }

    public String getMaxTemp() { return MaxTemp; }

    public String getHumidity() { return Humidity; }

    public String getCityWeather() { return CityWeather; }

    protected void execute() {
        String queryReturn = "";

        String query = null;
        try {

            String coordinate = "lat=" + latitude + "&lon=" + longitude;

            query = queryWeather + coordinate + queryDummyKey;

            queryReturn = sendQuery(query);

            CityWeather = "";
            CityWeather += ParseJSON(queryReturn);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            queryReturn = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            queryReturn = e.getMessage();
        }

    }

    private String sendQuery(String query) throws IOException {
        String result = "";

        URL searchURL = new URL(query);

        HttpURLConnection httpURLConnection = (HttpURLConnection)searchURL.openConnection();
        if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader,
                    8192);

            String line = null;
            while((line = bufferedReader.readLine()) != null){
                result += line;
            }

            bufferedReader.close();
        }

        return result;
    }

    private String ParseJSON(String json){
        String jsonResult = "";

        try {
            JSONObject JsonObject = new JSONObject(json);
            String cod = jsonHelperGetString(JsonObject, "cod");

            if(cod != null){
                if(cod.equals("200")){

                    //------------
                    CityName = jsonHelperGetString(JsonObject, "name");
                    //------------
                    jsonResult += CityName + " ";
                    JSONObject sys = jsonHelperGetJSONObject(JsonObject, "sys");
                    if(sys != null){

                        CountryName = jsonHelperGetString(sys, "country");

                        jsonResult += CountryName + "\n";

                    }
                    //jsonResult += "\n";

                    JSONObject coord = jsonHelperGetJSONObject(JsonObject, "coord");
                    if(coord != null){
                        String lon = jsonHelperGetString(coord, "lon");
                        String lat = jsonHelperGetString(coord, "lat");
                        //jsonResult += "lon: " + lon + "\n";
                        //jsonResult += "lat: " + lat + "\n";
                    }
                    //jsonResult += "\n";

                    JSONArray weather = jsonHelperGetJSONArray(JsonObject, "weather");
                    if(weather != null){
                        for(int i=0; i<weather.length(); i++){
                            JSONObject thisWeather = weather.getJSONObject(i);
                            //jsonResult += "weather " + i + ":\n";
                            //jsonResult += "id: " + jsonHelperGetString(thisWeather, "id") + "\n";
                            //jsonResult += jsonHelperGetString(thisWeather, "main") + "\n";
                            /*
                            if(Integer.valueOf(jsonHelperGetString(thisWeather, "id")) >= 700) {
                                jsonResult += jsonHelperGetString(thisWeather, "description");
                            }
                            else {
                                jsonResult += jsonHelperGetString(thisWeather, "main");
                            }
                            */
                            jsonResult += jsonHelperGetString(thisWeather, "main");
                            //jsonResult += "\n";
                        }
                    }

                    JSONObject main = jsonHelperGetJSONObject(JsonObject, "main");
                    if(main != null){

                        Temp = jsonHelperGetString(main, "temp");
                        Double DTemp = Double.parseDouble(Temp);
                        Long Itemp = Math.round(DTemp - 273);
                        Temp = String.valueOf(Itemp);

                        MinTemp = jsonHelperGetString(main, "temp_min");
                        Double DMinTemp = Double.parseDouble(MinTemp);
                        Long IMintemp = Math.round(DMinTemp - 273);
                        MinTemp = String.valueOf(IMintemp);

                        MaxTemp = jsonHelperGetString(main, "temp_max");
                        Double DMaxTemp = Double.parseDouble(MaxTemp);
                        Long IMaxtemp = Math.round(DMaxTemp - 273);
                        MaxTemp = String.valueOf(IMaxtemp);

                        Humidity = jsonHelperGetString(main, "humidity");

                        jsonResult += "\n" + Temp + " \u2103";
                        //jsonResult += "temp: " + Temp + "\n";
                        //jsonResult += "pressure: " + jsonHelperGetString(main, "pressure") + "\n";
                        //jsonResult += "humidity: " + Humidity + "\n";
                        //jsonResult += "temp_min: " + MinTemp + "\n";
                        //jsonResult += "temp_max: " + MaxTemp + "\n";
                        //jsonResult += "sea_level: " + jsonHelperGetString(main, "sea_level") + "\n";
                        //jsonResult += "grnd_level: " + jsonHelperGetString(main, "grnd_level") + "\n";
                        //jsonResult += "\n";
                    }

                    /*jsonResult += "visibility: " + jsonHelperGetString(JsonObject, "visibility") + "\n";
                    jsonResult += "\n";*/

                    /*JSONObject wind = jsonHelperGetJSONObject(JsonObject, "wind");
                    if(wind != null){
                        jsonResult += "wind:\n";
                        jsonResult += "speed: " + jsonHelperGetString(wind, "speed") + "\n";
                        jsonResult += "deg: " + jsonHelperGetString(wind, "deg") + "\n";
                        jsonResult += "\n";
                    }*/

                    //...incompleted

                }else if(cod.equals("404")){
                    String message = jsonHelperGetString(JsonObject, "message");
                    jsonResult += "cod 404: " + message;
                }
            }else{
                jsonResult += "cod == null\n";
            }

        } catch (JSONException e) {
            e.printStackTrace();
            jsonResult += e.getMessage();
        }

        return jsonResult;
    }

    private String jsonHelperGetString(JSONObject obj, String k){
        String v = null;
        try {
            v = obj.getString(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }

    private JSONObject jsonHelperGetJSONObject(JSONObject obj, String k){
        JSONObject o = null;

        try {
            o = obj.getJSONObject(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return o;
    }

    private JSONArray jsonHelperGetJSONArray(JSONObject obj, String k){
        JSONArray a = null;

        try {
            a = obj.getJSONArray(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return a;
    }
}
