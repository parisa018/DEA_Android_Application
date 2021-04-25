package com.example.dea.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPreferenceManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public SharedPreferenceManager(Context context) {
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        editor=sharedPreferences.edit();
    }
    public int getDMUNo(){
        return sharedPreferences.getInt(Constants.dmuNo,0);
    }
    public void setDMUNo(int number){
        editor.putInt(Constants.dmuNo,number);
        editor.apply();
    }
    public int getInputNo(){
        return sharedPreferences.getInt(Constants.inputNo,0);
    }
    public void setInputNo(int number){
        editor.putInt(Constants.inputNo,number);
        editor.apply();
    }
    public int getOutputNo(){
        return sharedPreferences.getInt(Constants.outputNo,0);
    }
    public void setOutputNo(int number){
        editor.putInt(Constants.outputNo,number);
        editor.apply();
    }
    public void setGraphX(int number,String type){
        editor.putInt(Constants.graphXNo,number);
        editor.putString(Constants.graphXType,type);
        editor.apply();
    }
    public String getGraphXType(){
        return  sharedPreferences.getString(Constants.graphXType,"input");
    }
    public int getGraphXNo(){
        return sharedPreferences.getInt(Constants.graphXNo,1);
    }
    public void setGraphY(int number ,String type){
        editor.putInt(Constants.graphYNo,number);
        editor.putString(Constants.graphYType,type);
        editor.apply();
    }
    public String getGraphYType(){
        return sharedPreferences.getString(Constants.graphYType,"input");
    }
    public int getGraphYNo(){
        return sharedPreferences.getInt(Constants.graphYNo,1);
    }
    public void setGraphZ(int number ,String type){
        editor.putInt(Constants.graphZNo,number);
        editor.putString(Constants.graphZType,type);
        editor.apply();
    }
    public String getGraphZType(){
        return sharedPreferences.getString(Constants.graphZType,"input");
    }
    public int getGraphZNo(){
        return sharedPreferences.getInt(Constants.graphZNo,1);
    }

}
