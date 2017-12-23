package com.example.user.outfitdaily;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
// Structure like the insert page, a update function version
public class NotepadUpdate extends Activity {

    Button update_confirm;
    EditText update_title;
    EditText update_text;
    String title,text;
    ImageView cloth1_iv;
    ImageView cloth2_iv;
    ImageView cloth3_iv;
    ImageView cloth4_iv;
    ImageView cloth5_iv;
    ImageView cloth6_iv;
    Button update_cancel;
    int s1=0;

    static String cloth1_path;
    static String cloth2_path;
    static String cloth3_path;
    static String cloth4_path;
    static String cloth5_path;
    static String cloth6_path;

    Cursor cursor;
    SQLiteDatabase db = null;
    final static String db_name = "db1.db";
    final static String table_name = "table02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notepad_update);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        update_title= (EditText)findViewById(R.id.update_title);
        update_text = (EditText)findViewById(R.id.update_text);
        update_confirm = (Button)findViewById(R.id.update_confirm);

        cloth1_iv = (ImageView)findViewById(R.id.update_hat);
        cloth2_iv = (ImageView)findViewById(R.id.update_scarf);
        cloth3_iv = (ImageView)findViewById(R.id.update_shirt);
        cloth4_iv = (ImageView)findViewById(R.id.update_coat);
        cloth5_iv = (ImageView)findViewById(R.id.update_pants);
        cloth6_iv = (ImageView)findViewById(R.id.update_shoes);

        update_cancel = (Button)findViewById(R.id.update_cancel);



        db = openOrCreateDatabase(db_name, MODE_PRIVATE, null);

        Bundle b2 = this.getIntent().getExtras();
        if(b2 != null){
            s1 = b2.getInt("key1");//抓取來自b2的key1

            cursor = db.rawQuery("SELECT * FROM table02 WHERE _id = '" + s1 + "'", null);
            cursor.moveToFirst();
            title = cursor.getString(1);//查詢table01的第一欄位資料,即title
            text = cursor.getString(2);//查詢table01的第二欄位資料,即text
            cloth1_path = cursor.getString(3);
            cloth2_path = cursor.getString(4);
            cloth3_path = cursor.getString(5);
            cloth4_path = cursor.getString(6);
            cloth5_path = cursor.getString(7);
            cloth6_path = cursor.getString(8);

            update_title.setText(title);
            update_text.setText(text);

            if(cloth1_path!=null) {
                ViewTreeObserver vto = cloth1_iv.getViewTreeObserver();
                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        cloth1_iv.getViewTreeObserver().removeOnPreDrawListener(this);
                        int finalHeight = cloth1_iv.getMeasuredHeight();
                        Bitmap bitmap = null;
                            try {
                                DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                                float screenWidth = dm.widthPixels;
                                int iconWidth = (int) (screenWidth * 0.35);
                                Uri uri = Uri.parse(cloth1_path);
                                bitmap = BitmapUtils.decodeSampledBitmapFromFd(getPath(uri), iconWidth, finalHeight);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        cloth1_iv.setImageBitmap(bitmap);
                        return true;
                    }
                });
            }
            if(cloth2_path!=null) {
                ViewTreeObserver vto = cloth2_iv.getViewTreeObserver();
                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        cloth2_iv.getViewTreeObserver().removeOnPreDrawListener(this);
                        int finalHeight = cloth2_iv.getMeasuredHeight();
                        Bitmap bitmap = null;
                        try {
                            DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                            float screenWidth = dm.widthPixels;
                            int iconWidth = (int) (screenWidth * 0.35);
                            Uri uri = Uri.parse(cloth2_path);
                            bitmap = BitmapUtils.decodeSampledBitmapFromFd(getPath(uri), iconWidth, finalHeight);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cloth2_iv.setImageBitmap(bitmap);
                        return true;
                    }
                });
            }
            if(cloth3_path!=null) {
                ViewTreeObserver vto = cloth3_iv.getViewTreeObserver();
                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        cloth3_iv.getViewTreeObserver().removeOnPreDrawListener(this);
                        int finalHeight = cloth3_iv.getMeasuredHeight();
                        Bitmap bitmap = null;
                        try {
                            DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                            float screenWidth = dm.widthPixels;
                            int iconWidth = (int) (screenWidth * 0.35);
                            Uri uri = Uri.parse(cloth3_path);
                            bitmap = BitmapUtils.decodeSampledBitmapFromFd(getPath(uri), iconWidth, finalHeight);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cloth3_iv.setImageBitmap(bitmap);
                        return true;
                    }
                });
            }
            if(cloth4_path!=null) {
                ViewTreeObserver vto = cloth4_iv.getViewTreeObserver();
                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        cloth4_iv.getViewTreeObserver().removeOnPreDrawListener(this);
                        int finalHeight = cloth4_iv.getMeasuredHeight();
                        Bitmap bitmap = null;
                        try {
                            DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                            float screenWidth = dm.widthPixels;
                            int iconWidth = (int) (screenWidth * 0.35);
                            Uri uri = Uri.parse(cloth4_path);
                            bitmap = BitmapUtils.decodeSampledBitmapFromFd(getPath(uri), iconWidth, finalHeight);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cloth4_iv.setImageBitmap(bitmap);
                        return true;
                    }
                });
            }
            if(cloth5_path!=null) {
                ViewTreeObserver vto = cloth5_iv.getViewTreeObserver();
                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        cloth5_iv.getViewTreeObserver().removeOnPreDrawListener(this);
                        int finalHeight = cloth5_iv.getMeasuredHeight();
                        Bitmap bitmap = null;
                        try {
                            DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                            float screenWidth = dm.widthPixels;
                            int iconWidth = (int) (screenWidth * 0.35);
                            Uri uri = Uri.parse(cloth5_path);
                            bitmap = BitmapUtils.decodeSampledBitmapFromFd(getPath(uri), iconWidth, finalHeight);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cloth5_iv.setImageBitmap(bitmap);
                        return true;
                    }
                });
            }
            if(cloth6_path!=null) {
                ViewTreeObserver vto = cloth6_iv.getViewTreeObserver();
                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        cloth6_iv.getViewTreeObserver().removeOnPreDrawListener(this);
                        int finalHeight = cloth6_iv.getMeasuredHeight();
                        Bitmap bitmap = null;
                        try {
                            DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                            float screenWidth = dm.widthPixels;
                            int iconWidth = (int) (screenWidth * 0.35);
                            Uri uri = Uri.parse(cloth6_path);
                            bitmap = BitmapUtils.decodeSampledBitmapFromFd(getPath(uri), iconWidth, finalHeight);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cloth6_iv.setImageBitmap(bitmap);
                        return true;
                    }
                });
            }
        }

        update_cancel.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent i = new Intent(NotepadUpdate.this, NotepadMain.class);
                                                 startActivity(i);
                                                 finish();
                                             }
                                         }
        );

        update_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (s1 != 0) {
                    ContentValues cv = new ContentValues();
                    cv.put("title", update_title.getText().toString());
                    cv.put("text", update_text.getText().toString());
                    db.update("table02", cv, "_id=" + s1, null);

                    Intent i = new Intent(NotepadUpdate.this, NotepadMain.class);
                    startActivity(i);

                    finish();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.delete, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){ //根據所選的Item執行
            case R.id.clear://清除
                update_title.setText("");
                update_text.setText("");
                break;
            case R.id.back://返回主程式
                Intent i = new Intent(NotepadUpdate.this,NotepadMain.class);
                startActivity(i);

                finish();
                break;
            default:

                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(NotepadUpdate.this, NotepadMain.class);
        startActivity(i);
        finish();
        //super.onBackPressed();
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null); Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}