package com.example.dea.api.javaScript;

import android.content.Context;
import android.webkit.JavascriptInterface;


public class WebClient {
    private Context context;
    private int xLength;
    private int yLength;
    private int zLength;
    private String jsonObject;
    private String xMarkerJsonObj;
    private String yMarkerJsonObj;
    private String xLineJsonObj;
    private String yLineJsonObj;
    public WebClient(Context _context) {
        context=_context;
    }
    @JavascriptInterface
    public int getxLength() {
        return xLength;
    }
    public void setxLength(int xLength) {
        this.xLength = xLength;
    }
    @JavascriptInterface
    public int getyLength() {
        return yLength;
    }
    public void setyLength(int yLength) {
        this.yLength = yLength;
    }
    @JavascriptInterface
    public int getzLength() {
        return zLength;
    }

    public void setzLength(int zLength) {
        this.zLength = zLength;
    }
    @JavascriptInterface
    public String getJsonArray() {
        return jsonObject;
    }

    public void setJsonArray(String jsonObject) {
        this.jsonObject = jsonObject;
    }
    @JavascriptInterface
    public String getJsonArray2(String type) {
        if(type.equals("x_marker"))
            return xMarkerJsonObj;
        else if(type.equals("y_marker"))
            return yMarkerJsonObj;
        else if(type.equals("x_line"))
            return xLineJsonObj;
        else
            return yLineJsonObj;
    }

    public void setJsonArray2(String jsonObject,String type) {
        if(type.equals("x_marker")) {
            xMarkerJsonObj = jsonObject;
            return;
        }
        else if(type.equals("y_marker")){
            yMarkerJsonObj=jsonObject;
            return;
        }
        else if(type.equals("x_line")) {
            xLineJsonObj=jsonObject;
            return ;
        }
        else
            {yLineJsonObj=jsonObject;
            return;}
    }
}
