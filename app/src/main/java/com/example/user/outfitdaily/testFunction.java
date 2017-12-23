package com.example.user.outfitdaily;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2016/6/1.
 */
public class testFunction {
    public testFunction(){}
    protected JSONObject execute(JSONObject jsonObj){
        String queryReturn = "";
        String query = null;
        try {

            return sendQuery(jsonObj);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private JSONObject sendQuery(JSONObject jsonObj) throws IOException {

        String url = "http://people.cs.nctu.edu.tw/~chiyi0811/index.php";
        URL object=new URL(url);

        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json; charset=UTF-8");
        con.setRequestMethod("POST");

        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(jsonObj.toString());
        wr.flush();

//display what returns the POST request

        StringBuilder sb = new StringBuilder();
        int HttpResult = con.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
           //return sb.toString();

            try {
                JSONObject json = new JSONObject(sb.toString());
                return json;
            }catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //return con.getResponseMessage();
        }
        return null;
    }
}
