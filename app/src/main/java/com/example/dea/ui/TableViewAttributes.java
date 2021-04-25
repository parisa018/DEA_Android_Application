package com.example.dea.ui;

import android.content.Context;

import com.example.dea.R;
import com.example.dea.entity.Cell;
import com.example.dea.entity.DMU;
import com.example.dea.util.SharedPreferenceManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TableViewAttributes {
    private Context context;
    private ArrayList<DMU> dmuArrayList;
    public TableViewAttributes(Context context, ArrayList<DMU> dmuArrayList) {
        this.context=context;
        this.dmuArrayList=dmuArrayList;
    }
    public List<Cell.RowHeader> getRowHeader(Context context){
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager(context);
        int dmuNo=sharedPreferenceManager.getDMUNo();
        List<Cell.RowHeader> list=new ArrayList<>();
        for(int i=0;i<dmuNo;i++){
            list.add(new Cell.RowHeader(context.getString(R.string.dmu).concat(" ").concat(String.valueOf(i+1))));
        }
        return  list;
    }
    public List<Cell.ColumnHeader> getColumnHeader(Context context){
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager(context);
        int input=sharedPreferenceManager.getInputNo();
        int output=sharedPreferenceManager.getOutputNo();
        List<Cell.ColumnHeader> list=new ArrayList<>();
        for(int i=0;i<input+output;i++){
            String column;
            if(i+1<=sharedPreferenceManager.getInputNo())
                column=context.getString(R.string.input1).concat(" ").concat(String.valueOf(i+1));
            else
                column=context.getString(R.string.output1).concat(" ").concat(String.valueOf(i+1-input));
            list.add(new Cell.ColumnHeader(column));
        }
        list.add(new Cell.ColumnHeader(context.getString(R.string.productivity)));
        list.add(new Cell.ColumnHeader(context.getString(R.string.DEA)));
        return list;
    }
    public List<List<Cell>> getCell(Context context){
        List<List<Cell>> cellList=new ArrayList<>();
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager(context);
        int dmuNo=sharedPreferenceManager.getDMUNo();
        int input=sharedPreferenceManager.getInputNo();
        int output=sharedPreferenceManager.getOutputNo();
        for(int i=0;i<dmuNo;i++){
            List<Cell> cellRowList=new ArrayList<>();
            for(int j=0;j<input;j++){
                cellRowList.add(new Cell(String.valueOf(dmuArrayList.get(i).getInputArray().get(j))));
            }
            for(int j=0;j<output;j++){
                cellRowList.add(new Cell(String.valueOf(dmuArrayList.get(i).getOutputArray().get(j))));
            }
            cellRowList.add(new Cell(String.valueOf(dmuArrayList.get(i).getProductivity())));
            cellRowList.add(new Cell(String.valueOf(dmuArrayList.get(i).getDEA())));
            cellList.add(cellRowList);

        }
        return cellList;
    }
}
