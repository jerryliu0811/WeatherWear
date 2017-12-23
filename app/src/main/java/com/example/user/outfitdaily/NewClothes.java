package com.example.user.outfitdaily;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
// insert new clothes
public class NewClothes extends Activity {

    SQLiteDatabase db = null;
    private final static int CAMERA_RESULT = 66 ;
    private Button SaveButton;
    //private Button LoadButton;
    private TextView mycloset;
    private Button ClearButton;
    private Button camera;
    private EditText InputName;
    ImageView iv;
    String iv_path;
    private String[] type = new String[] {"外套", "上衣","褲子","鞋子","帽子","圍巾"};//載入第一下拉選單
    private String[] coat = new String[]{"羽絨衣",	"西裝外套",	"尼龍外套",	"軍裝外套",	"長風衣",	"毛呢大衣",	"羽絨被心",	"牛仔外套",	"皮衣",	"連帽外套",	"運動外套"};//起始畫面時預先載入第二下拉選單
    private String[] material = new String[]{"黑色","白色",	"灰色",	"藍色",	"綠色",	"棕色",	"米色",	"紫色",	"黃色",	"紅色",	"橘色",	"粉色",	"其他",};//起始畫面時預先載入第三下拉選單
    //第一下拉選取後載入第二下拉選單

    private final static String _DBName = "db1.db";
    private final static String _TableName = "closet";
    final static String create_table = "CREATE TABLE "+_TableName+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, _category TEXT, _style TEXT, _color TEXT, _pic_path TEXT)";

    private String[][] type2 = new String[][]{{"羽絨衣",	"西裝外套",	"尼龍外套",	"軍裝外套",	"長風衣",	"毛呢大衣",	"羽絨被心",	"牛仔外套",	"皮衣",	"連帽外套",	"運動外套"},
            {"短T恤",	"短襯衫",	"長襯衫",	"針織衫",	"運動衫",	"Polo衫",	"毛衣",	"衛衣",	"大學T",	"營服",	"工作服"},
            {"牛仔褲",	"直筒褲",	"縮口褲",	"緊身褲",	"寬褲",	"海灘褲",	"運動褲",	"滑板褲",	"吊帶褲",	"棉褲",	"西裝褲"},
            {"運動鞋",	"帆布鞋",	"皮鞋",	"樂福鞋",	"慢跑鞋",	"涼鞋",	"拖鞋",	"Slip on",	"牛津鞋",	"籃球鞋"},
            {"棒球帽",	"漁夫帽",	"寬邊帽",	"毛帽",	"頭帶",	"鴨舌帽",	"網帽",	"貝雷帽",	"五分割帽",	"紳士帽"},
            {"針織圍巾",	"紡紗圍巾",	"毛呢圍巾"},

    };
    //第二下拉選取後載入第三下拉選單

    private Spinner sp;//第一個下拉選單
    private Spinner sp2;//第二個下拉選單
    private Spinner sp3;//第三個下拉選單
    private Context context;
    private ArrayList<String> writelist=new ArrayList<String>();
    ArrayAdapter<String> adapter ;

    ArrayAdapter<String> adapter2;

    ArrayAdapter<String> adapter3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newclothes);

        db = openOrCreateDatabase(_DBName, MODE_PRIVATE, null);
        try {
            db.execSQL(create_table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SaveButton = (Button) findViewById(R.id.savebutton);
        ClearButton= (Button) findViewById(R.id.clearbutton);
        camera = (Button) findViewById(R.id.new_clothes_camera);
        //initial();
        iv_path = null;
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ContentValues values = new ContentValues();

                try {
                    values.put("_category", sp.getSelectedItem().toString());
                    values.put("_style", sp2.getSelectedItem().toString());
                    values.put("_color", sp3.getSelectedItem().toString());
                    values.put("_pic_path", iv_path);
                    db.insert(_TableName, null, values);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(NewClothes.this, MainActivity.class);
                startActivity(i);
                finish();
            }

        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent();
                cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_RESULT);
            }
        });

        ClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewClothes.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        context = this;

        //程式剛啟始時載入第一個下拉選單
        adapter = new ArrayAdapter<String>(this,R.layout.myspinner, type);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp = (Spinner) findViewById(R.id.type);
        //設置下拉列表的風格
        adapter.setDropDownViewResource(R.layout.myspinner);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(selectListener);

        // according to the category of first spinner, show the corresponded style
        adapter2 = new ArrayAdapter<String>(this,R.layout.myspinner, coat);
        //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2 = (Spinner) findViewById(R.id.type2);
        sp2.setAdapter(adapter2);
        //sp2.setOnItemSelectedListener(selectListener2);

        // according to the style of first spinner, show the corresponded color
        adapter3 = new ArrayAdapter<String>(this,R.layout.myspinner, material);
        //adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3 = (Spinner) findViewById(R.id.type3);
        //設置下拉列表的風格
        adapter3.setDropDownViewResource(R.layout.myspinner);
        sp3.setAdapter(adapter3);


    }

    //第一個下拉類別的監看式
    private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener(){
        public void onItemSelected(AdapterView<?> parent, View v, int position,long id){
            //讀取第一個下拉選單是選擇第幾個
            int pos = sp.getSelectedItemPosition();
            //重新產生新的Adapter，用的是二維陣列type2[pos]
            adapter2 = new ArrayAdapter<String>(context,R.layout.myspinner, type2[pos]);
            //設置下拉列表的風格
            adapter2.setDropDownViewResource(R.layout.myspinner);
            //載入第二個下拉選單Spinner
            sp2.setAdapter(adapter2);
        }
        public void onNothingSelected(AdapterView<?> arg0){

        }
    };
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // after using camera
        if(requestCode==CAMERA_RESULT && intent!=null) {
            Bitmap bm = (Bitmap) intent.getExtras().get("data");
            iv = (ImageView) findViewById(R.id.new_clothes_iv);
            iv.setImageBitmap(bm);
            Uri uri = intent.getData();
            iv_path = uri.toString();
        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(NewClothes.this, MainActivity.class);
        startActivity(i);
        finish();
        //super.onBackPressed();
    }

    @Override
    protected void onDestroy() {//離開程式時關閉資料庫
        // TODO Auto-generated method stub
        super.onDestroy();
        db.close();
    }
}
