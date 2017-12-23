package com.example.user.outfitdaily;

import android.content.Context;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.FileNotFoundException;
import java.util.Calendar;

import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import  android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.ContentValues;
import android.widget.EditText;

public class MainActivity extends Activity {

    Button closet;
    Button notepad;
    Button camera;
    Button newClothes;

    int rows_num = -525 ;
    int temp = 100;
    String[] allStyle = new String[]{
            "羽絨衣"  ,"西裝外套","尼龍外套","軍裝外套","長風衣","毛呢大衣","羽絨被心","牛仔外套","皮衣"    ,"連帽外套","運動外套",
            "短T恤"   ,"短襯衫"  ,"長襯衫"  ,"針織衫"  ,"運動衫","Polo衫"  ,"毛衣"    ,"衛衣"    ,"大學T"   ,"營服"    ,"工作服",
            "運動鞋"  ,"帆布鞋"  ,"皮鞋"    ,"樂福鞋"  ,"慢跑鞋","涼鞋"    ,"拖鞋"    ,"Slip on" ,"牛津鞋"  ,"籃球鞋"  ,
            "棒球帽"  ,"漁夫帽"  ,"寬邊帽"  ,"毛帽"    ,"頭帶"  ,"鴨舌帽"  ,"網帽"    ,"貝雷帽"  ,"五分割帽","紳士帽"  ,
            "針織圍巾","紡紗圍巾","毛呢圍巾",
            "牛仔褲"  ,"直筒褲"  ,"縮口褲"  ,"緊身褲"  ,"寬褲"  ,"海灘褲"  ,"運動褲"  ,"滑板褲"  ,"吊帶褲"  ,"棉褲"    ,"西裝褲"
    };
    int[] allWarmth = new int[]{4,2,1,2,2,4,4,2,4,1,1,
                                        1,1,2,2,1,1,4,4,2,1,2,
                                        7,7,7,7,7,7,7,7,7,7,
                                        1,1,1,2,1,1,1,2,1,1,
                                        2,2,2,
                                        1,1,1,1,1,1,1,2,2,2,2
    };
    int[] allStyleNum = new int[]{4,1,6,3,1,5,4,6,3,6,4,
                                          7,3,3,1,4,2,7,2,2,4,2,
                                          6,6,1,1,6,4,4,6,3,4,
                                          6,2,1,3,6,4,4,3,3,1,
                                          3,2,2,
                                          3,3,2,2,6,4,4,4,6,6,1
    };

    TextView weather;
    ImageView icon;
    TextView title;

    SQLiteDatabase db = null;
    final static String db_name = "db1.db";
    final static String table_name = "property";
    final static String create_table = "CREATE TABLE "+ table_name +"(_style TEXT PRIMARY KEY, _warmth INTEGER, _styleNum INTEGER)";

    // get GPS and weather information
    private class Async1 extends GetWeatherAsync {
        @Override
        protected void onPostExecute(String[] result) {
            //if(result==null) Toast.makeText(getApplicationContext(), "NULL", Toast.LENGTH_LONG).show();
            if(result!=null) {
                weather = (TextView)findViewById(R.id.showweather);
                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                temp = Integer.valueOf(result[1]);
                weather.setText(result[0]);
            }
            else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                // Setting Dialog Title
                alertDialog.setTitle("GPS is settings");
                // Setting Dialog Message
                alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
                // On pressing Settings button
                alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getApplicationContext().startActivity(intent);
                    }
                });
                // on pressing cancel button
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                // Showing Alert Message
                alertDialog.show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GPSTracker gps = new GPSTracker(getApplicationContext());

        // get GPS and weather information
        new Async1().execute(gps);

        db = openOrCreateDatabase(db_name, MODE_PRIVATE, null);
        try {
            db.execSQL(create_table);
            ContentValues values = new ContentValues();
            for(int i = 0; i < allStyleNum.length; i++){
                values.put("_style", allStyle[i]);
                values.put("_warmth", allWarmth[i]);
                values.put("_styleNum",allStyleNum[i]);
                db.insert(table_name, null, values);
                values.clear();
            }
            //Toast.makeText(getApplicationContext(), rows_num, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        title = (TextView)findViewById(R.id.textView5566);
        icon = (ImageView)findViewById(R.id.appLogo);
        closet = (Button)findViewById(R.id.gotocloset);
        notepad = (Button)findViewById(R.id.gotonotepad);
        camera = (Button)findViewById(R.id.gotocamera);
        newClothes = (Button)findViewById(R.id.gotonewclothes);

        closet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Closet.class);
                startActivity(i);
                finish();
            }
        });

        notepad.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NotepadMain.class);
                startActivity(i);
                finish();
            }
        });

        camera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(temp!=100){
                    //jump to recommend with temp variable
                    Intent i = new Intent(MainActivity.this, Recommend.class);
                    i.putExtra("Temp", temp);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(v.getContext(), "氣象資料取得中...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        newClothes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewClothes.class);
                startActivity(i);
                finish();
            }
        });

    }
}