package com.example.dea.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class DMU implements Serializable {
    private int DmuNo;
    private ArrayList<Double> inputArray=new ArrayList<>();
    private ArrayList<Double> outputArray =new ArrayList<>();
    private double productivity;
    private double DEA;

    public DMU(int dmuNo, ArrayList<Double> inputArray, ArrayList<Double> outputArray, double productivity,double DEA) {
        DmuNo = dmuNo;
        this.inputArray = inputArray;
        this.outputArray = outputArray;
        this.productivity=productivity;
        this.DEA=DEA;

    }

    public int getDmuNo() {
        return DmuNo;
    }

    public void setDmuNo(int dmuNo) {
        DmuNo = dmuNo;
    }

    public ArrayList<Double> getInputArray() {
        return inputArray;
    }

    public void setInputArray(ArrayList<Double> inputArray) {
        this.inputArray = inputArray;
    }

    public ArrayList<Double> getOutputArray() {
        return outputArray;
    }

    public void setOutputArray(ArrayList<Double> outputArray) {
        this.outputArray = outputArray;
    }

    public double getProductivity() {
        return productivity;
    }

    public void setProductivity(double productivity) {
        this.productivity = productivity;
    }

    public double getDEA() {
        return DEA;
    }

    public void setDEA(double DEA) {
        this.DEA = DEA;
    }


}
