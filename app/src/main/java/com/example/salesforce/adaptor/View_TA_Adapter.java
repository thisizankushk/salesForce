package com.example.salesforce.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.salesforce.R;
import com.example.salesforce.TA.TA_View;

import java.util.ArrayList;
import java.util.HashMap;

public class View_TA_Adapter extends RecyclerView.Adapter<View_TA_Adapter.ViewHolder> {
    private ArrayList<HashMap<String, String>> farm_info_view;

    TA_View cntt;
    HashMap<String,String>   map;

    String Datee="",Ta_Date;


    public View_TA_Adapter(ArrayList<HashMap<String, String>> farm_info_view, Context context, TA_View cntt) {
        this.farm_info_view = farm_info_view;
        this.cntt = cntt;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ta_view_adapter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final  int position) {

        Datee=farm_info_view.get(position).get("TA_DATE");
        Ta_Date=Datee.substring(0,11);
        holder.ta_date.setText(""+Ta_Date);
        holder.ta_amount.setText(""+farm_info_view.get(position).get("AMOUNT"));
        holder.post_date.setText(""+farm_info_view.get(position).get("SUBMITDATE").substring(0,10));
    }


    public int getItemCount() {
        return farm_info_view.size();

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ta_date;
        public TextView ta_amount,post_date;


        public ViewHolder(View itemView) {

            super(itemView);
            ta_date = (TextView) itemView.findViewById(R.id.ta_date);
            ta_amount= (TextView) itemView.findViewById(R.id.ta_amount);
            post_date= (TextView) itemView.findViewById(R.id.post_date);
        }
    }
}

