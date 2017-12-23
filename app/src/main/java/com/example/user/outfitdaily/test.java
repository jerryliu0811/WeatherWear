package com.example.user.outfitdaily;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2016/5/31.
 */
public class test extends Activity{

    TextView sameStyle;
    TextView sameColor;
    TextView sameStyleNum;

    int temperature = 0;
    int styleNum = 0;
    String type = null;
    String color = null;
    String style = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.test);
        final Bundle extras = getIntent().getExtras();
        String value;

        temperature = extras.getInt("Temp");
        value = extras.getString("GGininder");
        type = extras.getString("Type");
        color = extras.getString("Color");
        style = extras.getString("Style");
        styleNum = extras.getInt("StyleNum");

        setContentView(R.layout.test);
        String[] NewColor;
        NewColor = color.split("色");

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("requestType", "recommend");
            jsonObj.put("WantClothType", type);
            jsonObj.put("WantClothColor", NewColor[0]);
            jsonObj.put("WantClothStyle", style);
            jsonObj.put("WantClothStyleNum", styleNum);

            //Toast.makeText(getApplicationContext(), type + " " + color + " " + style , Toast.LENGTH_LONG).show();

            new Async1().execute(jsonObj);
        }catch(JSONException e) {
            e.printStackTrace();
        }

    }
    public class Async extends AsyncTask<JSONObject, Void, JSONObject> {
        final protected JSONObject doInBackground(JSONObject... params) {
            testFunction result  = new testFunction();
            return result.execute(params[0]);
        }
    }
    public class Async1 extends Async{
        @Override
        protected void onPostExecute(JSONObject result) {

            String hat1,scarf1,shirt1,coat1,pants1,shoes1,hat2,scarf2,shirt2,coat2,pants2,shoes2,hat3,scarf3,shirt3,coat3,pants3,shoes3;

            hat1 = jsonHelperGetString(result, "hat1");
            scarf1 = jsonHelperGetString(result, "scarf1");
            shirt1 = jsonHelperGetString(result, "shirt1");
            coat1 = jsonHelperGetString(result, "coat1");
            pants1 = jsonHelperGetString(result, "pants1");
            shoes1 = jsonHelperGetString(result, "shoes1");

            hat2 = jsonHelperGetString(result, "hat2");
            scarf2 = jsonHelperGetString(result, "scarf2");
            shirt2 = jsonHelperGetString(result, "shirt2");
            coat2 = jsonHelperGetString(result, "coat2");
            pants2 = jsonHelperGetString(result, "pants2");
            shoes2 = jsonHelperGetString(result, "shoes2");

            hat3 = jsonHelperGetString(result, "hat3");
            scarf3 = jsonHelperGetString(result, "scarf3");
            shirt3 = jsonHelperGetString(result, "shirt3");
            coat3 = jsonHelperGetString(result, "coat3");
            pants3 = jsonHelperGetString(result, "pants3");
            shoes3 = jsonHelperGetString(result, "shoes3");

            if(coat1 == null) hat1 = "";
            if(shirt1 == null) shirt1 = "";
            if(pants1 == null) pants1 = "";
            if(shoes1 == null) shoes1 = "";
            if(hat1 == null) hat1 = "";
            if(scarf1 == null) scarf1 = "";

            if(coat2 == null) hat2 = "";
            if(shirt2 == null) shirt2 = "";
            if(pants2 == null) pants2 = "";
            if(shoes2 == null) shoes2 = "";
            if(hat2 == null) hat2 = "";
            if(scarf2 == null) scarf2 = "";

            if(coat3 == null) hat3 = "      ";
            if(shirt3 == null) shirt3 = "      ";
            if(pants3 == null) pants3 = "      ";
            if(shoes3 == null) shoes3 = "      ";
            if(hat3 == null) hat3 = "      ";
            if(scarf3 == null) scarf3 = "      ";

            sameStyle = (TextView)findViewById(R.id.sameStyle);
            String haha1 = hat1 + scarf1 + shirt1 + coat1 + pants1 + shoes1;
            if(!haha1.equals("")) sameStyle.setText(hat1 +" "+ scarf1 +"\n"+ shirt1 +" "+ coat1 +"\n"+ pants1 +" "+ shoes1);

            //sameStyle.setText(hat2 +" "+ scarf2 +"\n"+ shirt2 +" "+ coat2 +"\n"+ pants2 +" "+ shoes2);

            String haha2 = hat2 + scarf2 + shirt2 + coat2 + pants2 + shoes2;
            sameColor = (TextView)findViewById(R.id.sameColor);
            if(!haha2.equals("")) sameColor.setText(hat2 +" "+ scarf2 +"\n"+ shirt2 +" "+ coat2 +"\n"+ pants2 +" "+ shoes2);

            String haha3 = hat3 + scarf3 + shirt3 + coat3 + pants3 + shoes3;
            sameStyleNum = (TextView)findViewById(R.id.sameStyleNum);
            if(!haha3.equals("")) sameStyleNum.setText(hat3 +" "+ scarf3 +"\n"+ shirt3 +" "+ coat3 +"\n"+ pants3 +" "+ shoes3);
            /*test111.setText("\n"+ hat1 +"\n"+ scarf1 +"\n"+ shirt1 +"\n"+ coat1 +"\n"+ pants1 +"\n"+ shoes1 +
                    "\n"+ hat2 +"\n"+ scarf2 +"\n"+ shirt2 +"\n"+ coat2 +"\n"+ pants2 +"\n"+ shoes2 +
                    "\n"+ hat3 +"\n"+ scarf3 +"\n"+ shirt3 +"\n"+ coat3 +"\n"+ pants3 +"\n"+ shoes3);*/
            //test111.setText(result.toString());

        }
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

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent i = new Intent(test.this, Recommend.class);
        i.putExtra("Temp", temperature);
        startActivity(i);
        finish();
        //super.onBackPressed();
    }
}
