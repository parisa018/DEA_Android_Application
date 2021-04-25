package com.example.dea.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dea.R;
import com.example.dea.entity.DMU;
import com.example.dea.util.Util;

import java.util.ArrayList;

public class RecyclerViewCardAdapter extends RecyclerView.Adapter<RecyclerViewCardAdapter.CardViewHolder> {
    public static String TAG="RecyclerViewAdapter";
    public Context mContext;
    private AdapterListener listener;
    private int dmuNo,inputNo,outputNo;

    public RecyclerViewCardAdapter(Context mContext, AdapterListener listener, int dmuNo) {
        this.mContext = mContext;
        this.listener = listener;
        this.dmuNo = dmuNo;
//        this.inputNo = inputNo;
//        this.outputNo = outputNo;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view0 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_recycler_view, viewGroup, false);
            CardViewHolder holder = new CardViewHolder(view0);
            return holder;
    }

    @Override
    public void onBindViewHolder(final CardViewHolder viewHolder, final int i) {
        viewHolder.dmuTv.setText(mContext.getString(R.string.dmu).concat(" ").concat(String.valueOf(i+1)));
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String inputText=viewHolder.inputEt.getText().toString();
                final String outputText=viewHolder.outputEt.getText().toString();
                if(!inputText.isEmpty() && !outputText.isEmpty()){
                    listener.submit(i+1,inputText,outputText,viewHolder.button,viewHolder.inputAnsTv,viewHolder.outputAnsTv,
                            viewHolder.inputEt,viewHolder.outputEt);
                }
                else
                    Toast.makeText(mContext,"fields cant be empty",Toast.LENGTH_LONG).show();
            }
        });


    }




    public interface AdapterListener{
        public void submit(int dmu,String inputText,String outputText,ImageView submitBtn,TextView inputAnsTv,TextView outputAnsTv,
                            EditText inputEt,EditText outputEt);
    }

    @Override
    public int getItemCount() {
        return dmuNo;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{

        TextView dmuTv,inputTv,outputTv,inputAnsTv,outputAnsTv;
        EditText inputEt,outputEt;
        LinearLayout frame;
        ImageView button;

        public CardViewHolder(View itemView) {
            super(itemView);
            dmuTv=itemView.findViewById(R.id.dmu_textView);
            inputTv=itemView.findViewById(R.id.input_tv);
            inputEt=itemView.findViewById(R.id.input_et);
            outputTv=itemView.findViewById(R.id.output_tv);
            outputEt=itemView.findViewById(R.id.output_et);
            button=itemView.findViewById(R.id.card_submit_btn);
            frame=itemView.findViewById(R.id.linear_layout);
            inputAnsTv=itemView.findViewById(R.id.input_ans_tv);
            outputAnsTv=itemView.findViewById(R.id.output_ans_tv);
            int x= Util.getDeviceHeight();
            frame.getLayoutParams().height=x/2;

        }
    }



}
