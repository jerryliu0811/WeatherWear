package com.example.user.outfitdaily;

import android.graphics.Path;

/**
 * Created by user on 2016/6/6.
 */
public class ClothData {
    String[] Style1Array;
    String[] ColorArray;
    String[] PathArray;
    int[] StyleNumArray;
    public ClothData(int length){
        Style1Array = new String[length];
        ColorArray = new String[length];
        PathArray = new String[length];
        StyleNumArray = new int[length];
    }
}
