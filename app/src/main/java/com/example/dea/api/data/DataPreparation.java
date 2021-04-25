package com.example.dea.api.data;

import android.content.Context;
import android.util.Log;

import com.example.dea.api.javaScript.WebClient;
import com.example.dea.entity.DMU;
import com.example.dea.util.SharedPreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class DataPreparation {
    private Context context;
    private SharedPreferenceManager sharedPreferenceManager;
    private ArrayList<DMU> dmuArrayList;
    public DataPreparation(Context context,ArrayList<DMU> dmuArrayList) {
        this.context=context;
        this.dmuArrayList=dmuArrayList;
        sharedPreferenceManager=new SharedPreferenceManager(context);
    }
    public String checkDataType(WebClient client, boolean needPoint0){
        if(sharedPreferenceManager.getInputNo()==1 && sharedPreferenceManager.getOutputNo()==1){
            set2DData(client,needPoint0);
            return "http://www.happymarkstutoring.com/html/2dchart.html";
        }
        else {
            set3DData(client);
            return "http://www.happymarkstutoring.com";
        }
    }
    private void set2DData(WebClient client, boolean needPoint0){
        double[] xMarkerArray=get2DDMUsData(context,"input",false);
        client.setJsonArray2(make2DJson(xMarkerArray),"x_marker");
        double[] yMarkerArray=get2DDMUsData(context,"output",false);
        client.setJsonArray2(make2DJson(yMarkerArray),"y_marker");


        double[] xLineArray=addPoint0(get2DDMUsData(context,"input",true),needPoint0);
        client.setJsonArray2(make2DJson(xLineArray),"x_line");
        double[] yLineArray=addPoint0(get2DDMUsData(context,"output",true),needPoint0);
        client.setJsonArray2(make2DJson(yLineArray),"y_line");
    }
    private double[] addPoint0(double[] array,boolean needPoint0){
        if(needPoint0) {
            double[] temp = Arrays.copyOf(array, array.length + 1);
            temp[0] = 0;
            System.arraycopy(array, 0, temp, 1, array.length);
            return temp;
        }
        else
            return array;
    }
    private String make2DJson(double[] x){
        JSONArray jsonArray=new JSONArray();
        JSONObject points=new JSONObject();
        try {
            for(int i=0;i<x.length;i++){
                JSONObject point=new JSONObject();
                point.put("x",x[i]);
                jsonArray.put(point);
            }
            points.put("points",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return points.toString();
    }



    private void set3DData(WebClient client){
        //x=input1
        double[] xArray=getAllDMUsData(context,sharedPreferenceManager.getGraphXType(),sharedPreferenceManager.getGraphXNo());
        double maxInput1=findLargestData(xArray);
        client.setxLength(findAxisLength(maxInput1));
        //y=input2
        double[] yArray=getAllDMUsData(context,sharedPreferenceManager.getGraphYType(),sharedPreferenceManager.getGraphYNo());
        double maxInput2=findLargestData(yArray);
        client.setyLength(findAxisLength(maxInput2));
        //z=output
        double[] zArray=getAllDMUsData(context,sharedPreferenceManager.getGraphZType(),sharedPreferenceManager.getGraphZNo());
        double maxOutput=findLargestData(zArray);
        client.setzLength(findAxisLength(maxOutput));
        client.setJsonArray(make3DJson(xArray,yArray,zArray));
    }
    private String make3DJson(double[] x,double[] y,double[] z){
        JSONArray jsonArray=new JSONArray();
        JSONObject points=new JSONObject();
        try {
            for(int i=0;i<x.length;i++){
                JSONObject point=new JSONObject();
                point.put("x",x[i]);
                point.put("y",y[i]);
                point.put("z",z[i]);
                jsonArray.put(point);
            }
            points.put("points",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return points.toString();
    }

    private double[] get2DDMUsData(Context context, String type, boolean optimized){
        int dmuTotalNo=new SharedPreferenceManager(context).getDMUNo();
        double[] data=new double[dmuTotalNo];
        for(int i=0;i<dmuTotalNo;i++){
            if(type.equals("input")) {
                if(optimized){
                    if(dmuArrayList.get(i).getDEA()==1)
                        data[i] = dmuArrayList.get(i).getInputArray().get(0);
                }
                else {
                    if(dmuArrayList.get(i).getDEA()<1)
                        data[i] = dmuArrayList.get(i).getInputArray().get(0);
                }
            }
            else if(type.equals("output")) {
                if(optimized) {
                    if (dmuArrayList.get(i).getDEA() == 1)
                        data[i] = dmuArrayList.get(i).getOutputArray().get(0);
                }
                else {
                    if(dmuArrayList.get(i).getDEA()<1)
                        data[i] = dmuArrayList.get(i).getOutputArray().get(0);
                }
            }
        }
        return data;
    }

    private double[] getAllDMUsData(Context context,String type,int typeNumber){
        int dmuTotalNo=new SharedPreferenceManager(context).getDMUNo();
        double[] data=new double[dmuTotalNo];
        for(int i=0;i<dmuTotalNo;i++){
            if(type.equals("input"))
                data[i]=dmuArrayList.get(i).getInputArray().get(typeNumber-1);
            else if(type.equals("output"))
                data[i]=dmuArrayList.get(i).getOutputArray().get(typeNumber-1);
        }
        return data;
    }
    private int findAxisLength(double max){
        int thisMax=(int)max;
        thisMax=thisMax/10;
        return ((thisMax)*10)+10;
    }
    private double findLargestData(double[] data){
        double max=0;
        for(int i=0;i<data.length;i++) {
            if(max<data[i])
                max=data[i];
        }
        return max;
    }
}
