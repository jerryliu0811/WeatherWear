package com.example.user.outfitdaily;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

// the diary main page
public class NotepadMain extends Activity {

    Button insert;
    ListView listview;
    TextView test111;

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    SharePhotoContent PhotoContent;

    ClipboardManager notepadClipboard;
    Bitmap bm;

    SQLiteDatabase db = null;
    final static String db_name = "db1.db";
    final static String table_name = "table02";
    final static String create_table = "CREATE TABLE "+table_name+"(_id INTEGER PRIMARY KEY, title TEXT, text TEXT, cloth1_path TEXT, cloth2_path TEXT, cloth3_path TEXT, cloth4_path TEXT, cloth5_path TEXT, cloth6_path TEXT)";
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notepad_main);

        // initialize Facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onCancel() {
                Intent i = new Intent(NotepadMain.this, NotepadMain.class);
                startActivity(i);
                finish();
            }
            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
                Intent i = new Intent(NotepadMain.this, NotepadMain.class);
                startActivity(i);
                finish();
            }
            @Override
            public void onSuccess(Sharer.Result result) {
                Intent i = new Intent(NotepadMain.this, NotepadMain.class);
                startActivity(i);
                finish();
            }
        });

        insert = (Button)findViewById(R.id.insert);
        listview=(ListView)findViewById(R.id.listView1);

        db = openOrCreateDatabase(db_name, MODE_PRIVATE, null);
        try {
            db.execSQL(create_table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor = getAll();
        UpdataAdapter(cursor);//將資料顯示在ListView

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
                                    final long arg3) {
                // TODO Auto-generated method stub

                new AlertDialog.Builder(NotepadMain.this)
                        .setItems(new String[]{"檢視","刪除","分享雲端","分享FB"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(which==1){
                                    cursor.moveToPosition(arg2);
                                    //取得此筆資料的第0個欄位, 即 id
                                    //cursor.getInt(0);
                                    db.delete(table_name, "_id="+cursor.getInt(0), null);

                                    cursor = getAll();
                                    UpdataAdapter(cursor);
                                }
                                else if(which==0){
                                    cursor.moveToPosition(arg2);

                                    Intent i = new Intent(NotepadMain.this,NotepadUpdate.class);

                                    Bundle b = new Bundle();
                                    b.putInt("key1",cursor.getInt(0) );//將ID放入key1
                                    i.putExtras(b);

                                    startActivity(i);
                                    finish();
                                }
                                else if(which==2){
                                    cursor.moveToPosition(arg2);
                                    //取得此筆資料的第0個欄位, 即 id
                                    //cursor.getInt(0);
                                    Cursor cursor2 = null;
                                    try {
                                        cursor2 = db.rawQuery("SELECT * FROM table02 WHERE _id="+cursor.getInt(0), null);
                                        cursor2.moveToFirst();
                                        String item1 = cursor2.getString(3);
                                        String item2 = cursor2.getString(4);
                                        String item3 = cursor2.getString(5);
                                        String item4 = cursor2.getString(6);
                                        String item5 = cursor2.getString(7);
                                        String item6 = cursor2.getString(8);

                                        //String itemCategory1 = null,itemCategory2 = null,itemCategory3 = null,itemCategory4 = null,itemCategory5 = null,itemCategory6 = null;
                                        String itemStyle1 = null,itemStyle2 = null,itemStyle3 = null,itemStyle4 = null,itemStyle5 = null,itemStyle6 = null;
                                        String itemColor1 = null,itemColor2 = null,itemColor3 = null,itemColor4 = null,itemColor5 = null,itemColor6 = null;

                                        Cursor cursor3 = null;
                                        int rows_num;
                                        cursor3 = db.rawQuery("SELECT * FROM closet WHERE _pic_path='"+item1 + "'", null);
                                        rows_num = cursor3.getCount();
                                        if(rows_num != 0) {
                                            cursor3.moveToFirst();
                                            //itemCategory1 = cursor3.getString(1);
                                            itemStyle1 = cursor3.getString(2);
                                            itemColor1 = cursor3.getString(3);
                                        }
                                        cursor3 = db.rawQuery("SELECT * FROM closet WHERE _pic_path='"+item2 + "'", null);
                                        rows_num = cursor3.getCount();
                                        if(rows_num != 0) {
                                            cursor3.moveToFirst();
                                            //itemCategory2 = cursor3.getString(1);
                                            itemStyle2 = cursor3.getString(2);
                                            itemColor2 = cursor3.getString(3);
                                        }

                                        cursor3 = db.rawQuery("SELECT * FROM closet WHERE _pic_path='"+item3 + "'", null);
                                        rows_num = cursor3.getCount();
                                        if(rows_num != 0) {
                                            cursor3.moveToFirst();
                                            //itemCategory3 = cursor3.getString(1);
                                            itemStyle3 = cursor3.getString(2);
                                            itemColor3 = cursor3.getString(3);
                                        }

                                        cursor3 = db.rawQuery("SELECT * FROM closet WHERE _pic_path='"+item4 + "'", null);
                                        rows_num = cursor3.getCount();
                                        if(rows_num != 0) {
                                            cursor3.moveToFirst();
                                            //itemCategory4 = cursor3.getString(1);
                                            itemStyle4 = cursor3.getString(2);
                                            itemColor4 = cursor3.getString(3);
                                        }

                                        cursor3 = db.rawQuery("SELECT * FROM closet WHERE _pic_path='"+item5 + "'", null);
                                        rows_num = cursor3.getCount();
                                        if(rows_num != 0) {
                                            cursor3.moveToFirst();
                                            //itemCategory5 = cursor3.getString(1);
                                            itemStyle5 = cursor3.getString(2);
                                            itemColor5 = cursor3.getString(3);
                                        }

                                        cursor3 = db.rawQuery("SELECT * FROM closet WHERE _pic_path='"+item6 + "'", null);
                                        rows_num = cursor3.getCount();
                                        if(rows_num != 0) {
                                            cursor3.moveToFirst();
                                            //itemCategory6 = cursor3.getString(1);
                                            itemStyle6 = cursor3.getString(2);
                                            itemColor6 = cursor3.getString(3);
                                        }

                                        try {
                                            //String[] shareMyCloth = tmp.split("@");
                                            if(itemColor1 == null){
                                                itemColor1 = "";
                                                itemStyle1 = "";
                                            }
                                            if(itemColor2 == null){
                                                itemColor2 = "";
                                                itemStyle2 = "";
                                            }
                                            if(itemColor3 == null){
                                                itemColor3 = "";
                                                itemStyle3 = "";
                                            }
                                            if(itemColor4 == null){
                                                itemColor4 = "";
                                                itemStyle4 = "";
                                            }
                                            if(itemColor5 == null){
                                                itemColor5 = "";
                                                itemStyle5 = "";
                                            }
                                            if(itemColor6 == null){
                                                itemColor6 = "";
                                                itemStyle6 = "";
                                            }

                                            if((itemColor1 == "") && (itemColor2 == "") && (itemColor3 == "") &&
                                                    (itemColor4 == "") &&(itemColor5 == "") && (itemColor6 == "")){
                                                Toast.makeText(NotepadMain.this, "請先選擇穿搭再上傳喔", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                JSONObject jsonObj = new JSONObject();
                                                jsonObj.put("requestType", "share");
                                                jsonObj.put("hat", itemStyle1);    jsonObj.put("hatColor" , itemColor1);
                                                jsonObj.put("scarf", itemStyle2);  jsonObj.put("scarfColor" , itemColor2);
                                                jsonObj.put("shirt", itemStyle3);  jsonObj.put("shirtColor" , itemColor3);
                                                jsonObj.put("coat", itemStyle4);   jsonObj.put("coatColor" , itemColor4);
                                                jsonObj.put("pants", itemStyle5);  jsonObj.put("pantsColor" , itemColor5);
                                                jsonObj.put("shoes", itemStyle6);  jsonObj.put("shoesColor" , itemColor6);

                                                new Async1().execute(jsonObj);
                                            }

                                        }catch(JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    catch(SQLiteException e) {
                                        e.printStackTrace();
                                    }

                                }
                                else if(which==3) {
                                    cursor.moveToPosition(arg2);
                                    //取得此筆資料的第0個欄位, 即 id
                                    int c=cursor.getInt(0);

                                    String titleCopy = cursor.getString(1);
                                    String textCopy = cursor.getString(2);
                                    ClipData clip = ClipData.newPlainText("text", "穿搭主題" + "\n" + titleCopy + "\n" + "穿搭靈感" + "\n" + textCopy + " <3 (^^^) " );
                                    ClipboardManager notepadClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                    notepadClipboard.setPrimaryClip(clip);


                                    Intent cameraIntent = new Intent();
                                    cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(cameraIntent, 1);
                                }

                            }

                        }).show();

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i = new Intent(NotepadMain.this, NotepadInsert.class);
                startActivity(i);
                finish();
            }
        });
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

            //接資料的地方
            Toast.makeText(NotepadMain.this, "分享雲端成功!!", Toast.LENGTH_SHORT).show();

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

    private void UpdataAdapter(final Cursor cursor) {
        // TODO Auto-generated method stub
        if(cursor!=null && cursor.getCount()>=0){
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_2,
                    cursor,
                    new String[]{"title","text"},
                    new int[] {android.R.id.text1,android.R.id.text2}) {

                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    text1.setTextSize(25);

                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                    text2.setTextSize(20);

                    // change the show content
                    try{
                        String title = cursor.getString(1);
                        char[] charArray = title.toCharArray();
                        String newtitle = "";
                        if(title.length() > 10){
                            for(int i=0; i < 10; i++){
                                if(String.valueOf(charArray[i]).equals("\n")) {
                                    newtitle += "...";
                                    text1.setText(newtitle);
                                    break;
                                }
                                newtitle += charArray[i];
                            }
                            newtitle += "...";
                            text1.setText(newtitle);
                        }

                        String text = cursor.getString(2);
                        char[] charArray2 = text.toCharArray();
                        String newtext = "";
                        if(text.length() > 10){
                            for(int i=0; i < 10; i++){
                                if(String.valueOf(charArray2[i]).equals("\n")) {
                                    newtext += "...";
                                    text2.setText(newtext);
                                    break;
                                }
                                newtext += charArray2[i];
                            }
                            newtext += "...";
                            text2.setText(newtext);
                        }


                    }catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    return view;
                };
            };
            listview.setAdapter(adapter);
        }

    }

    private Cursor getAll() {
        // TODO Auto-generated method stub
        Cursor cursor = db.rawQuery("SELECT * FROM table02",null);

        return cursor;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {//離開程式時關閉資料庫
        // TODO Auto-generated method stub
        super.onDestroy();
        db.close();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent i = new Intent(NotepadMain.this, MainActivity.class);
        startActivity(i);
        finish();
        //super.onBackPressed();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        callbackManager.onActivityResult(requestCode, resultCode, intent);
        // camera's callback
        if(intent!=null) {
            setContentView(R.layout.share);
            ImageView iv = (ImageView) findViewById(R.id.iv);
            bm = (Bitmap) intent.getExtras().get("data");
            iv.setImageBitmap(bm);

            // Facebook share button clicked
            Button fbShareButton = (Button)findViewById(R.id.fb_share_button);
            fbShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Clipboard done
                    Toast.makeText(NotepadMain.this, "長按以貼上穿搭靈感!", Toast.LENGTH_LONG).show();

                    if (ShareDialog.canShow(ShareLinkContent.class)) {

                        SharePhoto photo = new SharePhoto.Builder()
                                .setBitmap(bm)
                                .build();
                        PhotoContent = new SharePhotoContent.Builder()
                                .addPhoto(photo)
                                .build();

                        shareDialog.show(PhotoContent);
                    }
                }
            });
            Button cancelButton = (Button)findViewById(R.id.cancel_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(NotepadMain.this, NotepadMain.class);
                    startActivity(i);
                    finish();
                }

            });
        }
    }

}
