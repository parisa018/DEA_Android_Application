package com.example.dea.util;

import android.app.Activity;
import android.util.DisplayMetrics;

public class Util {
    private static int height;
    private static int width;
    public static void setDeviceWidth(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width=displayMetrics.widthPixels;
    }
    public static int getDeviceWidth() {
        return width;
    }
    public static void setDaviceHeight(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height=displayMetrics.heightPixels;

    }
    public static int getDeviceHeight(){
        return height;
    }
}
