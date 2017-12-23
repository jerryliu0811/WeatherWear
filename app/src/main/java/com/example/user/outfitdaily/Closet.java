package com.example.user.outfitdaily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.FileNotFoundException;
import java.util.Calendar;

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
// the closet enter page
public class Closet extends Activity{
    ImageView hat;
    ImageView scarf;
    ImageView shirt;
    ImageView coat;
    ImageView pants;
    ImageView shoes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet);

        hat = (ImageView)findViewById(R.id.hat);
        hat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShowMyCloset.class);
                i.putExtra("ChosenCategory", "hat");
                startActivity(i);
                finish();
            }
        });

        scarf = (ImageView)findViewById(R.id.scarf);
        scarf.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShowMyCloset.class);
                i.putExtra("ChosenCategory", "scarf");
                startActivity(i);
                finish();
            }
        });

        shirt = (ImageView)findViewById(R.id.shirt);
        shirt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShowMyCloset.class);
                i.putExtra("ChosenCategory", "shirt");
                startActivity(i);
                finish();
            }
        });

        coat = (ImageView)findViewById(R.id.coat);
        coat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShowMyCloset.class);
                i.putExtra("ChosenCategory", "coat");
                startActivity(i);
                finish();
            }
        });

        pants = (ImageView)findViewById(R.id.pants);
        pants.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShowMyCloset.class);
                i.putExtra("ChosenCategory", "pants");
                startActivity(i);
                finish();
            }
        });

        shoes = (ImageView)findViewById(R.id.shoes);
        shoes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShowMyCloset.class);
                i.putExtra("ChosenCategory", "shoes");
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent i = new Intent(Closet.this, MainActivity.class);
        startActivity(i);
        finish();
        //super.onBackPressed();
    }
}
