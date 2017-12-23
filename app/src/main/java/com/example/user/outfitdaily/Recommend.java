package com.example.user.outfitdaily;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by user on 2016/6/5.
 */
public class Recommend extends Activity{

    int temperature = 0;


    TextView coattext;
    TextView shirttext;
    TextView pantstext;
    TextView shoestext;
    TextView hattext;
    TextView scarftext;

    ImageView coatPic;
    ImageView shirtPic;
    ImageView pantsPic;
    ImageView shoesPic;
    ImageView hatPic;
    ImageView scarfPic;

    Button nextSetButton;
    Button nextStyleButton;

    SQLiteDatabase db = null;
    final static String db_name = "db1.db";
    final static String table_name = "closet";
    final static String table_name2 = "property";
    Cursor cursor;

    int setIndex = 0;
    int styleIndex = 0;
    int[] styleListArray = new int[] {1,2,4};


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend);
        final Bundle extras = getIntent().getExtras();

        Toast.makeText(Recommend.this, "點擊圖片瀏覽線上穿搭喔", Toast.LENGTH_SHORT).show();

        coattext = (TextView)findViewById(R.id.coattext);
        shirttext = (TextView)findViewById(R.id.shirttext);
        pantstext = (TextView)findViewById(R.id.pantstext);
        shoestext = (TextView)findViewById(R.id.shoestext);
        hattext = (TextView)findViewById(R.id.hattext);
        scarftext = (TextView)findViewById(R.id.scarftext);

        coatPic = (ImageView) findViewById(R.id.coat);
        shirtPic = (ImageView) findViewById(R.id.shirt);
        pantsPic = (ImageView) findViewById(R.id.pants);
        shoesPic = (ImageView) findViewById(R.id.shoes);
        hatPic = (ImageView) findViewById(R.id.hat);
        scarfPic = (ImageView) findViewById(R.id.scarf);

        nextSetButton = (Button)findViewById(R.id.nextSet);
        nextStyleButton = (Button)findViewById(R.id.nextStyle);

        temperature = extras.getInt("Temp");

        db = openOrCreateDatabase(db_name, MODE_PRIVATE, null);

        if(temperature > 31){
            /**以全套風格一樣直接吹到陣列(都1 2 3 就好)
                        不能6個單品同色 且 亮色系只能有一個*/
            //hat scarf shirt pants coat shoes
            /**外套 = null
                             上衣->保暖1
                             庫子->保暖1
                             鞋子->保暖1
                             帽子 = null
                             圍巾 = null*/
            //clothUseOrNotArray[0~5] coat shirt pants shoes hat scarf
            // 0 is not use
            //styleNum[124] -> 風格124
            int[] clothUseOrNotArray = new int[]{0,1,1,1,0,0};
            ClothData[] resultArray;
            resultArray = searchWarmthStyleNum(1,1,clothUseOrNotArray);

            recommendSet(resultArray, 0);
        }
        else if(temperature > 28){
            /**'''以全套風格一樣直接吹到陣列(都1 2 3 就好)'''
                        * **/
            /**外套 = null
                             上衣->保暖1
                             庫子->保暖1
                             鞋子->保暖1
                             帽子->保暖1
                             圍巾 = null*/

            int[] clothUseOrNotArray = new int[]{0,1,1,1,1,0};
            ClothData[] resultArray;
            resultArray = searchWarmthStyleNum(1,1,clothUseOrNotArray);

            recommendSet(resultArray, 0);

        }
        else if(temperature > 25){
            /**'''以全套風格一樣直接吹到陣列(都1 2 3 就好)'''
             * **/
            /**外套->保暖1
                             上衣->保暖1
                             庫子->保暖1
                             鞋子->保暖1
                             帽子 = GG
                             圍巾 = null*/

            int[] clothUseOrNotArray = new int[]{1,1,1,1,1,0};
            ClothData[] resultArray;
            resultArray = searchWarmthStyleNum(1,1,clothUseOrNotArray);

            recommendSet(resultArray, 0);

        }
        else if(temperature > 17){
            /**''''保暖1的單品數量 > 保暖2的單品數量'''
             '              ''然後保暖1的單品數量不能為5'''
             * ''以全套風格一樣直接吹到陣列(都1 2 3 就好)''**/
            /**外套->保暖2
                             上衣->保暖2
                             庫子->保暖2
                             鞋子->保暖2
                             帽子->保暖2
                             圍巾 = null*/

            int[] clothUseOrNotArray = new int[]{1,1,1,1,1,0};
            ClothData[] resultArray;
            resultArray = searchWarmthStyleNum(2,1,clothUseOrNotArray);

            recommendSet(resultArray, 0);

        }
        else{
            /**'''以全套風格一樣直接吹到陣列(都1 2 3 就好)'''
             * **/
            /**外套->保暖3
                             上衣->保暖3
                             庫子->保暖3
                             鞋子->保暖3
                             帽子->保暖3
                             圍巾->保暖3*/

            int[] clothUseOrNotArray = new int[]{1,1,1,1,1,1};
            ClothData[] resultArray;
            resultArray = searchWarmthStyleNum(3,1,clothUseOrNotArray);

            recommendSet(resultArray, 0);

        }
        nextSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(temperature > 31){

                    int[] clothUseOrNotArray = new int[]{0,1,1,1,0,0};
                    ClothData[] resultArray;
                    resultArray = searchWarmthStyleNum(1,styleListArray[styleIndex],clothUseOrNotArray);

                    setIndex++;
                    setIndex %= resultArray[0].Style1Array.length;

                    recommendSet(resultArray, setIndex);
                }
                else if(temperature > 28){

                    int[] clothUseOrNotArray = new int[]{0,1,1,1,1,0};
                    ClothData[] resultArray;
                    resultArray = searchWarmthStyleNum(1,styleListArray[styleIndex],clothUseOrNotArray);

                    setIndex++;
                    setIndex %= resultArray[0].Style1Array.length;

                    recommendSet(resultArray, setIndex);
                }
                else if(temperature > 25){

                    int[] clothUseOrNotArray = new int[]{1,1,1,1,0,0};
                    ClothData[] resultArray;
                    resultArray = searchWarmthStyleNum(1,styleListArray[styleIndex],clothUseOrNotArray);

                    setIndex++;
                    setIndex %= resultArray[0].Style1Array.length;

                    recommendSet(resultArray, setIndex);
                }
                else if(temperature > 17){

                    int[] clothUseOrNotArray = new int[]{1,1,1,1,1,0};
                    ClothData[] resultArray;
                    resultArray = searchWarmthStyleNum(2,styleListArray[styleIndex],clothUseOrNotArray);

                    setIndex++;
                    setIndex %= resultArray[0].Style1Array.length;

                    recommendSet(resultArray, setIndex);
                }
                else{

                    int[] clothUseOrNotArray = new int[]{1,1,1,1,1,1};
                    ClothData[] resultArray;
                    resultArray = searchWarmthStyleNum(3,styleListArray[styleIndex],clothUseOrNotArray);

                    setIndex++;
                    setIndex %= resultArray[0].Style1Array.length;

                    recommendSet(resultArray, setIndex);
                }
            }
        });
        nextStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(temperature > 31){

                    int[] clothUseOrNotArray = new int[]{0,1,1,1,0,0};
                    ClothData[] resultArray;

                    styleIndex++;
                    styleIndex %= 3;

                    resultArray = searchWarmthStyleNum(1,styleListArray[styleIndex],clothUseOrNotArray);

                    recommendSet(resultArray, 0);
                }
                else if(temperature > 28){

                    int[] clothUseOrNotArray = new int[]{0,1,1,1,1,0};
                    ClothData[] resultArray;

                    styleIndex++;
                    styleIndex %= 3;

                    resultArray = searchWarmthStyleNum(1,styleListArray[styleIndex],clothUseOrNotArray);

                    recommendSet(resultArray, 0);
                }
                else if(temperature > 25){

                    int[] clothUseOrNotArray = new int[]{1,1,1,1,0,0};
                    ClothData[] resultArray;

                    styleIndex++;
                    styleIndex %= 3;

                    resultArray = searchWarmthStyleNum(1,styleListArray[styleIndex],clothUseOrNotArray);

                    recommendSet(resultArray, 0);
                }
                else if(temperature > 17){

                    int[] clothUseOrNotArray = new int[]{1,1,1,1,1,0};
                    ClothData[] resultArray;

                    styleIndex++;
                    styleIndex %= 3;

                    resultArray = searchWarmthStyleNum(2,styleListArray[styleIndex],clothUseOrNotArray);

                    recommendSet(resultArray, 0);
                }
                else{

                    int[] clothUseOrNotArray = new int[]{1,1,1,1,1,1};
                    ClothData[] resultArray;

                    styleIndex++;
                    styleIndex %= 3;

                    resultArray = searchWarmthStyleNum(3,styleListArray[styleIndex],clothUseOrNotArray);

                    recommendSet(resultArray, 0);
                }
            }
        });
    }

    public ClothData searchDatabase(String category, int warmth, int styleNum){
        //String[][] result = null;
        ClothData result = null;
        try {
            //風格表{{1,3,5,7},{2,3,6,7},{4,5,6,7}}
            String[] style = new String[5];
            style[1] = " AND (_styleNum = 1 OR _styleNum = 3 OR _styleNum = 5 OR _styleNum = 7)";
            style[2] = " AND (_styleNum = 2 OR _styleNum = 3 OR _styleNum = 6 OR _styleNum = 7)";
            style[4] = " AND (_styleNum = 4 OR _styleNum = 5 OR _styleNum = 6 OR _styleNum = 7)";

            cursor = db.rawQuery("SELECT closet._category , closet._style , closet._color , closet._pic_path , property._styleNum FROM closet " +
                    "INNER JOIN property " +
                    "ON closet._style = property._style " +
                    "WHERE closet._category = '"+ category +"' AND property._warmth = " + warmth + style[styleNum], null);
            int rows_num = cursor.getCount();
            if(rows_num != 0) {
                //result = new String[4][rows_num];
                result = new ClothData(rows_num);
                cursor.moveToFirst();   //將指標移至第一筆資料
                for(int i=0; i<rows_num; i++) {
                    result.Style1Array[i] = cursor.getString(1);
                    result.ColorArray[i] = cursor.getString(2);
                    result.PathArray[i] = cursor.getString(3);
                    result.StyleNumArray[i] = cursor.getInt(4);

                    cursor.moveToNext();//將指標移至下一筆資料
                }
            }
            //text.setText(Integer.toString(cursor.getColumnCount()));
            return result;
        }
        catch(SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ClothData[] searchWarmthStyleNum(int warmth,int styleNum, int[] clothUseOrNotArray){
        ClothData coatArray = null;
        ClothData shirtArray = null;
        ClothData pantsArray = null;
        ClothData shoesArray = null;
        ClothData hatArray = null;
        ClothData scarfArray = null;

        if(clothUseOrNotArray[0] != 0) coatArray = searchDatabase("外套",warmth,styleNum);
        if(clothUseOrNotArray[1] != 0) shirtArray = searchDatabase("上衣",warmth,styleNum);
        if(clothUseOrNotArray[2] != 0) pantsArray = searchDatabase("褲子",warmth,styleNum);
        if(clothUseOrNotArray[3] != 0) shoesArray = searchDatabase("鞋子",7,styleNum);
        if(clothUseOrNotArray[4] != 0) hatArray = searchDatabase("帽子",warmth,styleNum);
        if(clothUseOrNotArray[5] != 0) scarfArray = searchDatabase("圍巾",warmth,styleNum);

        int coatLength =  1;
        int shirtLength =  1;
        int pantsLength =  1;
        int shoesLength =  1;
        int hatLength =  1;
        int scarfLength =  1;

        if(coatArray != null) coatLength =  coatArray.Style1Array.length;
        if(shirtArray != null) shirtLength =  shirtArray.Style1Array.length;
        if(pantsArray != null) pantsLength =  pantsArray.Style1Array.length;
        if(shoesArray != null) shoesLength =  shoesArray.Style1Array.length;
        if(hatArray != null) hatLength =  hatArray.Style1Array.length;
        if(scarfArray != null) scarfLength =  scarfArray.Style1Array.length;

        int totalLength = coatLength*shirtLength*pantsLength*shoesLength*hatLength*scarfLength;

        // result[category]
        ClothData[] resultArray = new ClothData[6];
        for(int j = 0;j < 6; j++){
            resultArray[j] = new ClothData(totalLength);
        }

        int index = 0;
        for(int i1 = 0; i1 < coatLength ; i1++){
            for(int i2 = 0; i2 < shirtLength ; i2++){
                for(int i3 = 0; i3 < pantsLength ; i3++){
                    for(int i4 = 0; i4 < shoesLength ; i4++){
                        for(int i5 = 0; i5 < hatLength ; i5++){
                            for(int i6 = 0; i6 < scarfLength ; i6++){
                                // 0 coat
                                if(coatArray != null) {
                                    resultArray[0].Style1Array[index] = coatArray.Style1Array[i1];
                                    resultArray[0].ColorArray[index] = coatArray.ColorArray[i1];
                                    resultArray[0].PathArray[index] = coatArray.PathArray[i1];
                                    resultArray[0].StyleNumArray[index] = coatArray.StyleNumArray[i1];
                                }else {
                                    resultArray[0].Style1Array[index] = null;
                                    resultArray[0].ColorArray[index] = null;
                                    resultArray[0].PathArray[index] = null;
                                    resultArray[0].StyleNumArray[index] = -1;
                                }
                                if(shirtArray != null) {
                                    // 1 shirt
                                    resultArray[1].Style1Array[index] = shirtArray.Style1Array[i2];
                                    resultArray[1].ColorArray[index] = shirtArray.ColorArray[i2];
                                    resultArray[1].PathArray[index] = shirtArray.PathArray[i2];
                                    resultArray[1].StyleNumArray[index] = shirtArray.StyleNumArray[i2];
                                }else {
                                    resultArray[1].Style1Array[index] = null;
                                    resultArray[1].ColorArray[index] = null;
                                    resultArray[1].PathArray[index] = null;
                                    resultArray[1].StyleNumArray[index] = -1;
                                }
                                if(pantsArray != null) {
                                    // 2 pants
                                    resultArray[2].Style1Array[index] = pantsArray.Style1Array[i3];
                                    resultArray[2].ColorArray[index] = pantsArray.ColorArray[i3];
                                    resultArray[2].PathArray[index] = pantsArray.PathArray[i3];
                                    resultArray[2].StyleNumArray[index] = pantsArray.StyleNumArray[i3];
                                }else {
                                    resultArray[2].Style1Array[index] = null;
                                    resultArray[2].ColorArray[index] = null;
                                    resultArray[2].PathArray[index] = null;
                                    resultArray[2].StyleNumArray[index] = -1;
                                }
                                if(shoesArray != null) {
                                    // 3 shoes
                                    resultArray[3].Style1Array[index] = shoesArray.Style1Array[i4];
                                    resultArray[3].ColorArray[index] = shoesArray.ColorArray[i4];
                                    resultArray[3].PathArray[index] = shoesArray.PathArray[i4];
                                    resultArray[3].StyleNumArray[index] = shoesArray.StyleNumArray[i4];
                                }else {
                                    resultArray[3].Style1Array[index] = null;
                                    resultArray[3].ColorArray[index] = null;
                                    resultArray[3].PathArray[index] = null;
                                    resultArray[3].StyleNumArray[index] = -1;
                                }
                                if(hatArray != null) {
                                    // 4 hat
                                    resultArray[4].Style1Array[index] = hatArray.Style1Array[i5];
                                    resultArray[4].ColorArray[index] = hatArray.ColorArray[i5];
                                    resultArray[4].PathArray[index] = hatArray.PathArray[i5];
                                    resultArray[4].StyleNumArray[index] = hatArray.StyleNumArray[i5];
                                }else {
                                    resultArray[4].Style1Array[index] = null;
                                    resultArray[4].ColorArray[index] = null;
                                    resultArray[4].PathArray[index] = null;
                                    resultArray[4].StyleNumArray[index] = -1;
                                }
                                if(scarfArray != null) {
                                    // 5 scarf
                                    resultArray[5].Style1Array[index] = scarfArray.Style1Array[i6];
                                    resultArray[5].ColorArray[index] = scarfArray.ColorArray[i6];
                                    resultArray[5].PathArray[index] = scarfArray.PathArray[i6];
                                    resultArray[5].StyleNumArray[index] = scarfArray.StyleNumArray[i6];
                                }else {
                                    resultArray[5].Style1Array[index] = null;
                                    resultArray[5].ColorArray[index] = null;
                                    resultArray[5].PathArray[index] = null;
                                    resultArray[5].StyleNumArray[index] = -1;
                                }
                                index++;
                            }
                        }
                    }
                }
            }
        }
        return  resultArray;
    }

    public void setImageViewUsePath(final int category,String path,final String color5566,final String style5566,final int styleNum5566){
        ImageView imageView = null;


        if(category == 0) imageView = coatPic;
        if(category == 1) imageView = shirtPic;
        if(category == 2) imageView = pantsPic;
        if(category == 3) imageView = shoesPic;
        if(category == 4) imageView = hatPic;
        if(category == 5) imageView = scarfPic;

        int finalHeight = imageView.getMeasuredHeight();

        String tmp = path;
        Uri uri=null;
        if(tmp != null && !tmp.isEmpty()) {
            uri = Uri.parse(tmp);
        }

        Bitmap bitmap = null;
        if (tmp != null && !tmp.isEmpty()) {
            try {
                DisplayMetrics dm = Recommend.this.getResources().getDisplayMetrics();

                float screenWidth = dm.widthPixels;
                int iconWidth = (int) (screenWidth * 0.4);
                bitmap = BitmapUtils.decodeSampledBitmapFromFd(getPath(uri), iconWidth, finalHeight);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String type5566 = null;
                    if(category == 0){ type5566 = "coat";}
                    if(category == 1){ type5566 = "shirt";}
                    if(category == 2){ type5566 = "pants";}
                    if(category == 3){ type5566 = "shoes";}
                    if(category == 4){ type5566 = "hat";}
                    if(category == 5){ type5566 = "scarf";}

                    Intent i = new Intent(Recommend.this, test.class);
                    i.putExtra("GGininder", "HTTPrequest");
                    i.putExtra("Temp", temperature);
                    i.putExtra("Type", type5566);
                    i.putExtra("Color", color5566);
                    i.putExtra("Style", style5566);
                    i.putExtra("StyleNum", styleNum5566);
                    startActivity(i);
                    finish();
                }
            });
        }
    }

    // uri to absolute path
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(Recommend.this, uri, projection, null, null, null); Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void recommendSet(ClothData[] resultArray, int index){
        String ShowCoat,ShowShirt,ShowPants,ShowShoes,ShowHat,ShowScarf;
        ShowCoat = resultArray[0].ColorArray[index] + resultArray[0].Style1Array[index];
        if(ShowCoat.compareTo("nullnull") == 0) {
            ShowCoat = "缺少單品喔";
            int id = Recommend.this.getResources().getIdentifier("com.example.user.outfitdaily:drawable/coats_converted", null, null);
            coatPic.setImageResource(id);
        }
        else {
            if(resultArray[0].PathArray[index] == null){
                int id = Recommend.this.getResources().getIdentifier("com.example.user.outfitdaily:drawable/coats_converted", null, null);
                coatPic.setImageResource(id);
            }
            else {
                setImageViewUsePath(0, resultArray[0].PathArray[index],resultArray[0].ColorArray[index],resultArray[0].Style1Array[index],resultArray[0].StyleNumArray[index]);
            }
        }
        ShowShirt = resultArray[1].ColorArray[index] + resultArray[1].Style1Array[index];
        if(ShowShirt.compareTo("nullnull") == 0) {
            ShowShirt = "缺少單品喔";
            int id = Recommend.this.getResources().getIdentifier("com.example.user.outfitdaily:drawable/shirts_converted", null, null);
            shirtPic.setImageResource(id);
        }
        else {
            if(resultArray[1].PathArray[index] == null) {
                int id = Recommend.this.getResources().getIdentifier("com.example.user.outfitdaily:drawable/shirts_converted", null, null);
                shirtPic.setImageResource(id);
            }
            else{
                setImageViewUsePath(1,resultArray[1].PathArray[index],resultArray[1].ColorArray[index],resultArray[1].Style1Array[index],resultArray[1].StyleNumArray[index]);
            }
        }
        ShowPants = resultArray[2].ColorArray[index] + resultArray[2].Style1Array[index];
        if(ShowPants.compareTo("nullnull") == 0) {
            ShowPants = "缺少單品喔";
            int id = Recommend.this.getResources().getIdentifier("com.example.user.outfitdaily:drawable/pant_converted", null, null);
            pantsPic.setImageResource(id);
        }
        else {
            if(resultArray[2].PathArray[index] == null){
                int id = Recommend.this.getResources().getIdentifier("com.example.user.outfitdaily:drawable/pant_converted", null, null);
                pantsPic.setImageResource(id);
            }
            else{
                setImageViewUsePath(2,resultArray[2].PathArray[index],resultArray[2].ColorArray[index],resultArray[2].Style1Array[index],resultArray[2].StyleNumArray[index]);
            }
        }
        ShowShoes = resultArray[3].ColorArray[index] + resultArray[3].Style1Array[index];
        if(ShowShoes.compareTo("nullnull") == 0) {
            ShowShoes = "缺少單品喔";
            int id = Recommend.this.getResources().getIdentifier("com.example.user.outfitdaily:drawable/shoe_converted", null, null);
            shoesPic.setImageResource(id);
        }
        else {
            if(resultArray[3].PathArray[index] == null){
                int id = Recommend.this.getResources().getIdentifier("com.example.user.outfitdaily:drawable/shoe_converted", null, null);
                shoesPic.setImageResource(id);
            }
            else {
                setImageViewUsePath(3,resultArray[3].PathArray[index],resultArray[3].ColorArray[index],resultArray[3].Style1Array[index],resultArray[3].StyleNumArray[index]);
            }
        }
        ShowHat = resultArray[4].ColorArray[index] + resultArray[4].Style1Array[index];
        if(ShowHat.compareTo("nullnull") == 0) {
            ShowHat = "缺少單品喔";
            int id = Recommend.this.getResources().getIdentifier("com.example.user.outfitdaily:drawable/hats_converted", null, null);
            hatPic.setImageResource(id);
        }
        else {
            if(resultArray[4].PathArray[index]== null){
                int id = Recommend.this.getResources().getIdentifier("com.example.user.outfitdaily:drawable/hats_converted", null, null);
                hatPic.setImageResource(id);
            }
            else{
                setImageViewUsePath(4,resultArray[4].PathArray[index],resultArray[4].ColorArray[index],resultArray[4].Style1Array[index],resultArray[4].StyleNumArray[index]);
            }
        }
        ShowScarf = resultArray[5].ColorArray[index] + resultArray[5].Style1Array[index];
        if(ShowScarf.compareTo("nullnull") == 0) {
            ShowScarf = "缺少單品喔";
            int id = Recommend.this.getResources().getIdentifier("com.example.user.outfitdaily:drawable/scarfs_converted", null, null);
            scarfPic.setImageResource(id);
        }
        else {
            if(resultArray[5].PathArray[index] == null){
                int id = Recommend.this.getResources().getIdentifier("com.example.user.outfitdaily:drawable/scarfs_converted", null, null);
                scarfPic.setImageResource(id);
            }
            else{
                setImageViewUsePath(5,resultArray[5].PathArray[index],resultArray[5].ColorArray[index],resultArray[5].Style1Array[index],resultArray[5].StyleNumArray[index]);
            }
        }

        coattext.setText(ShowCoat);
        shirttext.setText(ShowShirt);
        pantstext.setText(ShowPants);
        shoestext.setText(ShowShoes);
        hattext.setText(ShowHat);
        scarftext.setText(ShowScarf);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent i = new Intent(Recommend.this, MainActivity.class);
        startActivity(i);
        finish();
        //super.onBackPressed();
    }
}
