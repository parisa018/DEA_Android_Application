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

public class IO_BCCModel {
    private Context context;
    private ArrayList<DMU> dmuArrayList;
    public IO_BCCModel(Context context,ArrayList<DMU> dmuArrayList) {
        this.context=context;
        this.dmuArrayList=dmuArrayList;
        getAllDataOptimized();
        calculateDEA();
    }
    public ArrayList<DMU> calculateIO_BCCModel(){
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
        // add coefficients and constant of the objective function
        LinearObjectiveFunction function=new LinearObjectiveFunction(getObjectiveFunctionCoefficients(dmuNo),0);
        //make the constraints
        List<LinearConstraint> constraints = new ArrayList();
        constraints=getConstraint(dmuNo);
        LinearConstraintSet constraintSet = new LinearConstraintSet(constraints);
        // call simplex solver
        SimplexSolver linearOptimizer = new SimplexSolver();
        /** optimize the problem and get the results by entering the function , constraints
         *      , goalType (weather it is a maximization or minimization)
         */
        PointValuePair solution = linearOptimizer.optimize(function, constraintSet, GoalType.MAXIMIZE);
        //get  numerical value of the answers
        double solValue=solution.getValue();
        //round the answers
        double solutionValue = (int)(Math.round(solValue * 100000))/100000.0;
        dmuArrayList.get(dmuNo).setProductivity(solutionValue);
    }
    private double[] getObjectiveFunctionCoefficients(int dmuNo){
        //get all the outputs of a dmu
        ArrayList<Double> outputArrayList=dmuArrayList.get(dmuNo).getOutputArray();
        //get all the inputs of a dmu
        ArrayList<Double> inputArrayList=dmuArrayList.get(dmuNo).getInputArray();
        double[] outputVector=new double[(outputArrayList.size())+(inputArrayList.size())+1];
        //add the coefficients
        for(int i=0;i<outputVector.length-1;i++){
            if(i<outputArrayList.size())
                outputVector[i]=outputArrayList.get(i);
            else
                outputVector[i]=0;
        }
        //add coefficient of U0
        outputVector[outputVector.length-1]=1;
        return outputVector;
    }
    private double[] getFirstConstraint(int dmuNo){
        //get all the inputs of a dmu
        ArrayList<Double> inputArrayList=dmuArrayList.get(dmuNo).getInputArray();
        //get all the outputs of a dmu
        ArrayList<Double> outputArrayList=dmuArrayList.get(dmuNo).getOutputArray();
        //make an array for coefficients of the first constraint
        double[] inputVector=new double[(inputArrayList.size())+(outputArrayList.size())+1];
        for(int i=0;i<inputVector.length-1;i++){
            if(i<outputArrayList.size())
                inputVector[i]=0;
            else
                inputVector[i]=inputArrayList.get(i-outputArrayList.size());
        }
        //add coefficient of U0
        inputVector[inputVector.length-1]=0;
        return inputVector;
    }
    private List<LinearConstraint> getConstraint(int dmuNo){
        List<LinearConstraint> constraints = new ArrayList();
        constraints.add(new LinearConstraint(getFirstConstraint(dmuNo), Relationship.EQ,1));
        int dmuSize=dmuArrayList.size();
        for(int i=0;i<dmuSize;i++){
            //get all the inputs of a dmu
            ArrayList<Double> inputArrayList=dmuArrayList.get(i).getInputArray();
            //get all the outputs of a dmu
            ArrayList<Double> outputArrayList=dmuArrayList.get(i).getOutputArray();
            int inputSize=inputArrayList.size();
            int outputSize=outputArrayList.size();
            // Ur>= 0    &&    Vi>=0
            double[] nonNegativityVector=new double[(inputSize)+(outputSize)+1];
            //make other constraints based on all dmu 's inputs and outputs
            double[] inputAndOutputVector=new double[(inputSize)+(outputSize)+1];
            for(int j=0;j<inputAndOutputVector.length-1;j++){
                if(j<outputSize)
                    inputAndOutputVector[j]=(1)*(outputArrayList.get(j));
                else
                    inputAndOutputVector[j]=(-1)*(inputArrayList.get(j-outputSize));

                nonNegativityVector[j]=1;
                constraints.add(new LinearConstraint(nonNegativityVector,Relationship.GEQ,0));
                nonNegativityVector[j]=0;
            }
            //add coefficient of U0
            inputAndOutputVector[inputAndOutputVector.length-1]=1;
            constraints.add(new LinearConstraint(inputAndOutputVector,Relationship.LEQ,0));
        }
        return constraints;
    }

}
