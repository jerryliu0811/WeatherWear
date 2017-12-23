package com.example.user.outfitdaily;

import android.content.Context;
import android.database.sqlite.SQLiteException;
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
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
// the page for every clothes categories
public class ShowMyCloset extends Activity{
    private static Context context;
    public static Context getAppContext() {
        return ShowMyCloset.context;
    }


    SQLiteDatabase db = null;
    final static String db_name = "db1.db";
    final static String table_name = "closet";
    final static String create_table = "CREATE TABLE "+table_name+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, _category TEXT, _style TEXT, _color TEXT, _pic_path TEXT)";
    Cursor cursor;

    TextView myAwesomeTextView;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showmycloset);
        ShowMyCloset.context = getApplicationContext();
        db = openOrCreateDatabase(db_name, MODE_PRIVATE, null);
        try {
            db.execSQL(create_table);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Bundle extras = getIntent().getExtras();
        String value=null;
        if (extras != null) {
            value = extras.getString("ChosenCategory");
            myAwesomeTextView = (TextView)findViewById(R.id.textView1);
            if(value.equals("hat")) value = "帽子";
            if(value.equals("scarf")) value = "圍巾";
            if(value.equals("shirt")) value = "上衣";
            if(value.equals("pants")) value = "褲子";
            if(value.equals("coat")) value = "外套";
            if(value.equals("shoes")) value = "鞋子";
            myAwesomeTextView.setText(value);
        }

        try {
            cursor = db.rawQuery("SELECT * FROM closet WHERE _category='" + value + "'", null);
        }
        catch(SQLiteException e) {
            e.printStackTrace();
        }
        String[] itemname = new String[cursor.getCount()];
        String[] imgid = new String[cursor.getCount()];
        String[] itemcolor = new String[cursor.getCount()];
        String[] itemcategory = new String[cursor.getCount()];
        String t = null;
        int rows_num = cursor.getCount();//取得資料表列數
        if(rows_num != 0) {
            cursor.moveToFirst();   //將指標移至第一筆資料
            for(int i=0; i<rows_num; i++) {
                itemname[i] = cursor.getString(2);
                imgid[i] = cursor.getString(4);
                itemcolor[i] = cursor.getString(3);
                itemcategory[i] = cursor.getString(1);
                cursor.moveToNext();//將指標移至下一筆資料
            }
        }
        list=(ListView)findViewById(R.id.list);

        CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid, itemcolor, itemcategory);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
                                    final long arg3) {
                // TODO Auto-generated method stub

                new AlertDialog.Builder(ShowMyCloset.this)
                        .setItems(new String[]{"刪除", "取消"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (which == 0) {
                                    cursor.moveToPosition(arg2);
                                    //取得此筆資料的第0個欄位, 即 id
                                    cursor.getInt(0);
                                    db.delete(table_name, "_id=" + cursor.getInt(0), null);

                                    Intent i = new Intent(ShowMyCloset.this, ShowMyCloset.class);

                                    try {
                                        i.putExtra("ChosenCategory", extras.getString("ChosenCategory"));
                                    } catch(NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(i);

                                    finish();
                                }
                            }

                        }).show();

            }
        });
    }

    private Cursor getAll() {
        // TODO Auto-generated method stub
        Cursor cursor = db.rawQuery("SELECT * FROM closet", null);

        return cursor;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ShowMyCloset.this, Closet.class);
        startActivity(i);
        finish();
        //super.onBackPressed();
    }
}
