package com.example.dea.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dea.R;
import com.example.dea.adapter.RecyclerViewCardAdapter;
import com.example.dea.dialog.EnterDmuDialog;
import com.example.dea.entity.DMU;
import com.example.dea.util.Constants;
import com.example.dea.util.SharedPreferenceManager;
import com.example.dea.util.Util;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EnterData extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewCardAdapter adapter;
    private EnterDmuDialog dialog;
    private Context context;
    private SharedPreferenceManager sharedPreferenceManager;
    private ArrayList<DMU> dmuArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);
        context=getApplicationContext();
        Util.setDaviceHeight(this);
        Util.setDeviceWidth(this);
        sharedPreferenceManager=new SharedPreferenceManager(context);
        showDialog();
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setVisibility(View.INVISIBLE);


    }
    private void showDialog(){
        if(dialog!=null)
            dialog=null;
        dialog=new EnterDmuDialog(EnterData.this, new EnterDmuDialog.DialogListener() {
            @Override
            public void setRecyclerInfo(int dmu, int input, int output) {
                setRecyclerView(dmu);
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
    private void setRecyclerView(final int dmuNo){
        recyclerView.setVisibility(View.VISIBLE);
         adapter=new RecyclerViewCardAdapter(context, new RecyclerViewCardAdapter.AdapterListener() {
            @Override
            public void submit(int dmu, String inputText, String outputText, ImageView submitBtn,
                               TextView inputAnsTv, TextView outputAnsTv, EditText inputEt,EditText outputEt) {
                ArrayList<Double> arrayList0=getNumbersOfText(inputText);
                ArrayList<Double> arrayList1=getNumbersOfText(outputText);
                if(arrayList0.size()!=sharedPreferenceManager.getInputNo()
                || (arrayList1.size()!=sharedPreferenceManager.getOutputNo()) ) {
                    Toast.makeText(context,"please enter the exact number of inputs or outputs that you have entered",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    dmuArrayList.add(new DMU(dmu,arrayList0,arrayList1,0,0));
                    Toast.makeText(context,"you set the data for DMU No."+dmu,Toast.LENGTH_SHORT).show();
                    submitBtn.setClickable(false);
                    submitBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_diable));
                    inputEt.setVisibility(View.GONE);
                    outputEt.setVisibility(View.GONE);
                    inputAnsTv.setText(inputEt.getText());
                    outputAnsTv.setText(outputEt.getText());
                    inputAnsTv.setVisibility(View.VISIBLE);
                    outputAnsTv.setVisibility(View.VISIBLE);
                }
                if(dmuArrayList.size()==dmuNo){
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(Constants.arrayListName,dmuArrayList);
                    startActivity(new Intent(EnterData.this,MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            .putExtra(Constants.bundle,bundle));
                }
            }
        }, dmuNo);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(context,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    private ArrayList<Double> getNumbersOfText(String text){
        int i=0;
        String number="";
        ArrayList<Double> numberArray= new ArrayList<>();
        while(i<text.length()){
            if((text.charAt(i)>=48 && text.charAt(i)<=57)||(text.charAt(i)==46)){
                number+=text.charAt(i);
            }
            else{
                if(!number.isEmpty()) {
                    numberArray.add(Double.valueOf(number));
                    number = "";
                }
            }
            i++;
        }
        if(!number.isEmpty()){
            numberArray.add(Double.valueOf(number));
            number="";
        }
        return numberArray;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog!=null){
            dialog.cancel();
            dialog=null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(dialog!=null){
            dialog.cancel();
            dialog=null;
        }
    }
}
