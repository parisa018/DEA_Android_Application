package com.example.dea.calculator;

import android.content.Context;
import android.util.Log;

import com.example.dea.entity.DMU;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.List;

public class OO_BCCModel {
    private Context context;
    private ArrayList<DMU> dmuArrayList;
    public OO_BCCModel(Context context,ArrayList<DMU> dmuArrayList) {
        this.context=context;
        this.dmuArrayList=dmuArrayList;
        getAllDataOptimized();
        calculateDEA();
    }
    public ArrayList<DMU> calculateOO_BCModel(){
        return dmuArrayList;
    }
    private void calculateDEA(){
        double maxProductivity=0;
        for(int i=0;i<dmuArrayList.size();i++){
            if(maxProductivity<dmuArrayList.get(i).getProductivity()){
                maxProductivity=dmuArrayList.get(i).getProductivity();
            }
        }
        for(int i=0;i<dmuArrayList.size();i++){
            dmuArrayList.get(i).setDEA(dmuArrayList.get(i).getProductivity()/maxProductivity);
        }
    }
    private void getAllDataOptimized(){
        for(int i=0;i<dmuArrayList.size();i++){
            optimize(dmuArrayList.get(i).getDmuNo()-1);
        }
    }

    /**
     *
     * REMEMBER that here in optimize , the result is the optimized points of each dmu
     *
     * the points are optimized wights of first the OUTPUTS and then the INPUTS
     *
     * @param dmuNo
     */
    private void optimize(int dmuNo){
        LinearObjectiveFunction function=new LinearObjectiveFunction(getFunction(dmuNo),0);
        List<LinearConstraint> constraints = new ArrayList();
        constraints=addConstraint(dmuNo);
        LinearConstraintSet constraintSet = new LinearConstraintSet(constraints);
        SimplexSolver linearOptimizer = new SimplexSolver();
        PointValuePair solution = linearOptimizer.optimize(function, constraintSet, GoalType.MINIMIZE);
        double solValue=1/(solution.getValue());
        double solutionValue = (int)(Math.round(solValue * 100000))/100000.0;
        Log.i("taggggggggggg","productivity is "+solValue);
        dmuArrayList.get(dmuNo).setProductivity(solutionValue);
    }
    private double[] getFunction(int dmuNo){
        ArrayList<Double> outputArrayList=dmuArrayList.get(dmuNo).getOutputArray();
        ArrayList<Double> inputArrayList=dmuArrayList.get(dmuNo).getInputArray();
        double[] outputVector=new double[(outputArrayList.size())+(inputArrayList.size())+1];
        for(int i=0;i<outputVector.length-1;i++){
            if(i<outputArrayList.size())
                outputVector[i]=0;
            else
                outputVector[i]=inputArrayList.get(i-outputArrayList.size());
        }
        outputVector[outputVector.length-1]=1;
        return outputVector;
    }
    private List<LinearConstraint> addConstraint(int dmuNo){
        List<LinearConstraint> constraints = new ArrayList();
        constraints.add(new LinearConstraint(getFirstConstraint(dmuNo), Relationship.EQ,1));

        int dmuSize=dmuArrayList.size();
        for(int i=0;i<dmuSize;i++){
            ArrayList<Double> inputArrayList=dmuArrayList.get(i).getInputArray();
            ArrayList<Double> outputArrayList=dmuArrayList.get(i).getOutputArray();
            int inputSize=inputArrayList.size();
            int outputSize=outputArrayList.size();
            double[] inputAndOutputVector=new double[(inputSize)+(outputSize)+1];
            double[] nonNegativityVector=new double[(inputSize)+(outputSize)+1];
            for(int j=0;j<inputAndOutputVector.length-1;j++){
                if(j<outputSize)
                    inputAndOutputVector[j]=(1)*(outputArrayList.get(j));
                else
                    inputAndOutputVector[j]=(-1)*(inputArrayList.get(j-outputSize));


                nonNegativityVector[j]=1;
                constraints.add(new LinearConstraint(nonNegativityVector,Relationship.GEQ,0));
                nonNegativityVector[j]=0;
            }
            inputAndOutputVector[inputAndOutputVector.length-1]=-1;
            constraints.add(new LinearConstraint(inputAndOutputVector,Relationship.LEQ,0));
        }
        return constraints;
    }
    private double[] getFirstConstraint(int dmuNo){
        ArrayList<Double> inputArrayList=dmuArrayList.get(dmuNo).getInputArray();
        ArrayList<Double> outputArrayList=dmuArrayList.get(dmuNo).getOutputArray();
        double[] inputVector=new double[(inputArrayList.size())+(outputArrayList.size())+1];
        for(int i=0;i<inputVector.length-1;i++){
            if(i<outputArrayList.size())
                inputVector[i]=1;
            else
                inputVector[i]=0;
        }
        inputVector[inputVector.length-1]=0;
        return inputVector;
    }
}
