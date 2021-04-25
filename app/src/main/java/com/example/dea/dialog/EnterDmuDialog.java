package com.example.dea.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dea.R;
import com.example.dea.util.SharedPreferenceManager;
import com.example.dea.util.Util;


public class EnterDmuDialog extends Dialog {
    private Context context;
    private EditText dmuEt,inputEt,outputEt;
    private Button submitBtn;
    private int dmuNo,inputNo,outputNo;
    private DialogListener listener;
    private ConstraintLayout parent;
    private SharedPreferenceManager sharedPreferenceManager;
    public EnterDmuDialog(Context _context,DialogListener _listener) {
        super(_context);
        context=_context;
        listener=_listener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_enter_dmu_dialog);
        sharedPreferenceManager=new SharedPreferenceManager(context);
        dmuEt=findViewById(R.id.dmu_number);
        inputEt=findViewById(R.id.input_number);
        outputEt=findViewById(R.id.output_number);
        submitBtn=findViewById(R.id.submit_btn);
        parent=findViewById(R.id.parent);
        parent.getLayoutParams().width= Util.getDeviceWidth()-100;
        submitInfo();

    }
    private void getEditTexts(){

        dmuNo=Integer.parseInt(dmuEt.getText().toString());

        inputNo=Integer.parseInt(inputEt.getText().toString());

        outputNo=Integer.parseInt(outputEt.getText().toString());

    }
    private void submitInfo(){
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditTexts();
                if(dmuNo>0 && inputNo>0 && outputNo>0) {
                    sharedPreferenceManager.setDMUNo(dmuNo);
                    sharedPreferenceManager.setInputNo(inputNo);
                    sharedPreferenceManager.setOutputNo(outputNo);
                    listener.setRecyclerInfo(dmuNo, inputNo, outputNo);
                    cancel();
                }
                else
                    Toast.makeText(context,"You entered invalid data. Please enter the data again",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public interface DialogListener{
        public void setRecyclerInfo(int dmu,int input,int output);
    }

}