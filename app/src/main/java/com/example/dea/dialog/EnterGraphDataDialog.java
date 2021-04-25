package com.example.dea.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.dea.R;
import com.example.dea.util.SharedPreferenceManager;

public class EnterGraphDataDialog extends Dialog {
    private Context context;
    private SharedPreferenceManager sharedPreferenceManager;
    private RadioGroup xRG,yRG,zRG;
    private Button submit;
    private String xType,yType,zType;
    private int xNumber,yNumber,zNumber;
    private EditText xET,yET,zET;
    public EnterGraphDataDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.graph_options);
        sharedPreferenceManager=new SharedPreferenceManager(context);
        setView();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               checkEditors();
            }
        });
    }
    private void checkEditors(){
        String sXET=xET.getText().toString();
        String sYET=yET.getText().toString();
        String sZET=zET.getText().toString();
        int XNo=Integer.parseInt(sXET);
        int YNo=Integer.parseInt(sYET);
        int ZNo=Integer.parseInt(sZET);
        if(!sXET.isEmpty() && !sYET.isEmpty() && !sZET.isEmpty()){
            if((xRG.getCheckedRadioButtonId()==(R.id.x_input_radio) && XNo<=sharedPreferenceManager.getInputNo())
                    || xRG.getCheckedRadioButtonId()==(R.id.x_output_radio) && XNo<=sharedPreferenceManager.getOutputNo()) {
                sharedPreferenceManager.setGraphX(XNo, xType);
                dismiss();
            }
            else if((yRG.getCheckedRadioButtonId()==(R.id.y_input_radio) && YNo<= sharedPreferenceManager.getInputNo())
                    || yRG.getCheckedRadioButtonId()==(R.id.y_output_radio) && YNo<=sharedPreferenceManager.getOutputNo()){
                sharedPreferenceManager.setGraphY(YNo,yType);
                dismiss();
            }
            else if((zRG.getCheckedRadioButtonId()==(R.id.z_input_radio) && ZNo<= sharedPreferenceManager.getInputNo())
                    || zRG.getCheckedRadioButtonId()==(R.id.z_output_radio) && ZNo<=sharedPreferenceManager.getOutputNo()) {
                sharedPreferenceManager.setGraphZ(ZNo, zType);
                dismiss();
            }
            else
                Toast.makeText(context,"incorrect entry",Toast.LENGTH_SHORT).show();

        }
        else
            Toast.makeText(context,"no filed can't be empty",Toast.LENGTH_SHORT).show();
    }

    private void setView(){
        xRG=findViewById(R.id.x_radio_group);
        yRG=findViewById(R.id.y_radio_group);
        zRG=findViewById(R.id.z_radio_group);
        submit=findViewById(R.id.graph_options_submit);
        xET=findViewById(R.id.x_edit_text);
        yET=findViewById(R.id.y_edit_text);
        zET=findViewById(R.id.z_edit_text);
    }
}
