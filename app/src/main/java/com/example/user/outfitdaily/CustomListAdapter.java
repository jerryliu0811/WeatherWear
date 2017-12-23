package com.example.user.outfitdaily;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] imgid;
    private final String[] itemcolor;
    private final String[] itemcategory;

    public CustomListAdapter(Activity context, String[] itemname, String[] imgid, String[] itemcolor, String[] itemcategory) {
        super(context, R.layout.mylist, itemname);

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.itemcolor=itemcolor;
        this.itemcategory=itemcategory;
    }

    public View getView(final int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        extratxt.setText(itemcolor[position]);
        String tmp = imgid[position];
        Uri uri=null;
        if(tmp != null && !tmp.isEmpty()) {
            uri = Uri.parse(tmp);
        }

        // set default icon
        if(itemcategory[position].equals("帽子")){
            int id = context.getResources().getIdentifier("com.example.user.outfitdaily:drawable/hats_converted", null, null);
            imageView.setImageResource(id);
            //imageView.setImageDrawable(ShowMyCloset.getAppContext().getResources().getDrawable( R.drawable.hats_converted ));
        }
        if(itemcategory[position].equals("圍巾")){
            int id = context.getResources().getIdentifier("com.example.user.outfitdaily:drawable/scarfs_converted", null, null);
            imageView.setImageResource(id);
            //imageView.setImageDrawable(ShowMyCloset.getAppContext().getResources().getDrawable( R.drawable.scarfs_converted ));
        }
        if(itemcategory[position].equals("上衣")){
            int id = context.getResources().getIdentifier("com.example.user.outfitdaily:drawable/shirts_converted", null, null);
            imageView.setImageResource(id);
            //imageView.setImageDrawable(ShowMyCloset.getAppContext().getResources().getDrawable( R.drawable.shirts_converted ));
        }
        if(itemcategory[position].equals("褲子")){
            int id = context.getResources().getIdentifier("com.example.user.outfitdaily:drawable/pant_converted", null, null);
            imageView.setImageResource(id);
            //imageView.setImageDrawable(ShowMyCloset.getAppContext().getResources().getDrawable( R.drawable.pant_converted ));
        }
        if(itemcategory[position].equals("外套")){
            int id = context.getResources().getIdentifier("com.example.user.outfitdaily:drawable/coats_converted", null, null);
            imageView.setImageResource(id);
            //imageView.setImageDrawable(ShowMyCloset.getAppContext().getResources().getDrawable( R.drawable.coats_converted ));

        }
        if(itemcategory[position].equals("鞋子")){
            int id = context.getResources().getIdentifier("com.example.user.outfitdaily:drawable/shoe_converted", null, null);
            imageView.setImageResource(id);
            //imageView.setImageDrawable(ShowMyCloset.getAppContext().getResources().getDrawable( R.drawable.shoe_converted ));
        }


        // put picture into ImageView with scaling
        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                int finalHeight = imageView.getMeasuredHeight();

                String tmp = imgid[position];
                Uri uri=null;
                if(tmp != null && !tmp.isEmpty()) {
                    uri = Uri.parse(tmp);
                }

                Bitmap bitmap = null;
                if (tmp != null && !tmp.isEmpty()) {
                    try {
                        //Context context = ShowMyCloset.getAppContext();
                        DisplayMetrics dm = context.getResources().getDisplayMetrics();

                        float screenWidth = dm.widthPixels;
                        int iconWidth = (int) (screenWidth * 0.4);
                        bitmap = BitmapUtils.decodeSampledBitmapFromFd(getPath(uri), iconWidth, finalHeight);

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);
                }

                return true;
            }
        });

        return rowView;
    };

    // uri to absolute path
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, uri, projection, null, null, null); Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


}