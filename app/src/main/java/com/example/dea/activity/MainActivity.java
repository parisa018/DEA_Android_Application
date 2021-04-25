package com.example.dea.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.dea.R;
import com.example.dea.dialog.EnterGraphDataDialog;
import com.example.dea.entity.DMU;
import com.example.dea.util.Constants;
import com.example.dea.util.SharedPreferenceManager;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dea.adapter.SectionsPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<DMU> dmuArrayList=new ArrayList<>();
    private Context context;
    private SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getApplicationContext();
        sharedPreferenceManager=new SharedPreferenceManager(context);
        getArray();
        checkDialog();
    }
    private void checkDialog(){
        if(sharedPreferenceManager.getInputNo()+sharedPreferenceManager.getOutputNo()>2){
            setDialog();
        }
        else
            setTab();
    }
    private void setDialog(){
        EnterGraphDataDialog dialog=new EnterGraphDataDialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                setTab();
            }
        });

    }
    private void setTab(){
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(),dmuArrayList);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
    private void getArray(){
        dmuArrayList=(ArrayList<DMU>)getIntent().getBundleExtra(Constants.bundle).getSerializable(Constants.arrayListName);
    }
}