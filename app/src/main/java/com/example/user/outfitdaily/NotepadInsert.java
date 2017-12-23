package com.example.user.outfitdaily;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
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
import java.util.Calendar;
// insert diary
public class NotepadInsert extends Activity {
    private final static int CLOTH1 = 99 ;
    private final static int CLOTH2 = 100 ;
    private final static int CLOTH3 = 101 ;
    private final static int CLOTH4 = 102 ;
    private final static int CLOTH5 = 103 ;
    private final static int CLOTH6 = 104 ;

    Button insertBack;
    EditText insert_title;
    EditText insert_text;
    ImageView cloth1;
    ImageView cloth2;
    ImageView cloth3;
    ImageView cloth4;
    ImageView cloth5;
    ImageView cloth6;
    Button insertCancel;

    SQLiteDatabase db = null;
    final static String db_name = "db1.db";
    final static String table_name = "table02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notepad_insert);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        insert_title= (EditText)findViewById(R.id.insert_title);
        insert_text = (EditText)findViewById(R.id.insert_text);
        insertBack = (Button)findViewById(R.id.insert_back);
        cloth1 = (ImageView) findViewById(R.id.insert_hat);
        cloth2 = (ImageView) findViewById(R.id.insert_scarf);
        cloth3 = (ImageView) findViewById(R.id.insert_shirt);
        cloth4 = (ImageView) findViewById(R.id.insert_coat);
        cloth5 = (ImageView) findViewById(R.id.insert_pants);
        cloth6 = (ImageView) findViewById(R.id.insert_shoes);
        insertCancel = (Button)findViewById(R.id.insert_cancel);

        db = openOrCreateDatabase(db_name, MODE_PRIVATE, null);

        // select picture
        cloth1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, CLOTH1);
            }
        });
        cloth2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, CLOTH2);
            }
        });
        cloth3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, CLOTH3);
            }
        });
        cloth4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, CLOTH4);
            }
        });
        cloth5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, CLOTH5);
            }
        });
        cloth6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, CLOTH6);
            }
        });

        insertCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NotepadInsert.this,NotepadMain.class);
                startActivity(i);
                finish();
            }
        });

        insertBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //如標題無輸入則將現在時間設為標題
                if(insert_title.getText().toString().equals("")){

                    Calendar calendar = Calendar.getInstance();
                    String now = ""+calendar.get(Calendar.YEAR)
                            +"/"+(calendar.get(Calendar.MONTH)+1)
                            +"/"+calendar.get(Calendar.DATE)
                            +" "+calendar.get(Calendar.HOUR_OF_DAY)
                            +":"+calendar.get(Calendar.MINUTE);

                    insert_title.setText(now);
                }
                //如內文無輸入則將"無輸入"設為內文
                if(insert_text.getText().toString().equals("")){
                    insert_text.setText("無輸入");
                }

                ContentValues cv = new ContentValues();
                try {
                    cv.put("title", insert_title.getText().toString());
                    cv.put("text", insert_text.getText().toString());
                    cv.put("cloth1_path", (String) cloth1.getTag());
                    cv.put("cloth2_path", (String) cloth2.getTag());
                    cv.put("cloth3_path", (String) cloth3.getTag());
                    cv.put("cloth4_path", (String) cloth4.getTag());
                    cv.put("cloth5_path", (String) cloth5.getTag());
                    cv.put("cloth6_path", (String) cloth6.getTag());

                    db.insert(table_name, null, cv);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                Intent i = new Intent(NotepadInsert.this,NotepadMain.class);
                startActivity(i);

                finish();
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
        getMenuInflater().inflate(R.menu.insert, menu);

        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CLOTH1  && data != null) {
            Uri uri = data.getData();
            cloth1.setTag(uri.toString());

            // put picture into ImageView with scaling
            ViewTreeObserver vto = cloth1.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    cloth1.getViewTreeObserver().removeOnPreDrawListener(this);
                    int finalHeight = cloth1.getMeasuredHeight();
                    Bitmap bitmap = null;
                    try {
                        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                        float screenWidth = dm.widthPixels;
                        int iconWidth = (int) (screenWidth * 0.35);
                        Uri uri = Uri.parse(cloth1.getTag().toString());
                        bitmap = BitmapUtils.decodeSampledBitmapFromFd(getPath(uri), iconWidth, finalHeight);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    cloth1.setImageBitmap(bitmap);
                    return true;
                }
            });
        }
        if (requestCode == CLOTH2  && data != null) {

            Uri uri = data.getData();
            cloth2.setTag(uri.toString());

            ViewTreeObserver vto = cloth2.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    cloth2.getViewTreeObserver().removeOnPreDrawListener(this);
                    int finalHeight = cloth2.getMeasuredHeight();
                    Bitmap bitmap = null;
                    try {
                        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                        float screenWidth = dm.widthPixels;
                        int iconWidth = (int) (screenWidth * 0.35);
                        Uri uri = Uri.parse(cloth2.getTag().toString());
                        bitmap = BitmapUtils.decodeSampledBitmapFromFd(getPath(uri), iconWidth, finalHeight);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    cloth2.setImageBitmap(bitmap);
                    return true;
                }
            });
        }
        if (requestCode == CLOTH3  && data != null) {
            Uri uri = data.getData();
            cloth3.setTag(uri.toString());

            ViewTreeObserver vto = cloth3.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    cloth3.getViewTreeObserver().removeOnPreDrawListener(this);
                    int finalHeight = cloth3.getMeasuredHeight();
                    Bitmap bitmap = null;
                    try {
                        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                        float screenWidth = dm.widthPixels;
                        int iconWidth = (int) (screenWidth * 0.35);
                        Uri uri = Uri.parse(cloth3.getTag().toString());
                        bitmap = BitmapUtils.decodeSampledBitmapFromFd(getPath(uri), iconWidth, finalHeight);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    cloth3.setImageBitmap(bitmap);
                    return true;
                }
            });
        }
        if (requestCode == CLOTH4  && data != null) {
            Uri uri = data.getData();
            cloth4.setTag(uri.toString());

            ViewTreeObserver vto = cloth4.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    cloth4.getViewTreeObserver().removeOnPreDrawListener(this);
                    int finalHeight = cloth4.getMeasuredHeight();
                    Bitmap bitmap = null;
                    try {
                        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                        float screenWidth = dm.widthPixels;
                        int iconWidth = (int) (screenWidth * 0.35);
                        Uri uri = Uri.parse(cloth4.getTag().toString());
                        bitmap = BitmapUtils.decodeSampledBitmapFromFd(getPath(uri), iconWidth, finalHeight);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    cloth4.setImageBitmap(bitmap);
                    return true;
                }
            });
        }
        if (requestCode == CLOTH5  && data != null) {
            Uri uri = data.getData();
            cloth5.setTag(uri.toString());

            ViewTreeObserver vto = cloth5.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    cloth5.getViewTreeObserver().removeOnPreDrawListener(this);
                    int finalHeight = cloth5.getMeasuredHeight();
                    Bitmap bitmap = null;
                    try {
                        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                        float screenWidth = dm.widthPixels;
                        int iconWidth = (int) (screenWidth * 0.35);
                        Uri uri = Uri.parse(cloth5.getTag().toString());
                        bitmap = BitmapUtils.decodeSampledBitmapFromFd(getPath(uri), iconWidth, finalHeight);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    cloth5.setImageBitmap(bitmap);
                    return true;
                }
            });
        }
        if (requestCode == CLOTH6  && data != null) {
            Uri uri = data.getData();
            cloth6.setTag(uri.toString());

            ViewTreeObserver vto = cloth6.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    cloth6.getViewTreeObserver().removeOnPreDrawListener(this);
                    int finalHeight = cloth6.getMeasuredHeight();
                    Bitmap bitmap = null;
                    try {
                        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                        float screenWidth = dm.widthPixels;
                        int iconWidth = (int) (screenWidth * 0.35);
                        Uri uri = Uri.parse(cloth6.getTag().toString());
                        bitmap = BitmapUtils.decodeSampledBitmapFromFd(getPath(uri), iconWidth, finalHeight);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    cloth6.setImageBitmap(bitmap);
                    return true;
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){ //根據所選的Item執行
            case R.id.clear://清除
                insert_title.setText("");
                insert_text.setText("");
                break;
            case R.id.back://返回主程式
                Intent i = new Intent(NotepadInsert.this,NotepadMain.class);
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
        Intent i = new Intent(NotepadInsert.this, NotepadMain.class);
        startActivity(i);
        finish();
        //super.onBackPressed();
    }
    // uri to absolute path
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null); Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}