package com.example.salesforce.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.salesforce.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Show_Attendance_Adapter extends  RecyclerView.Adapter<Show_Attendance_Adapter.ViewHolder>{
    ArrayList<HashMap<String, String>> field_Info_day;
    private Context context;
    String finalString="";
    public Show_Attendance_Adapter(Context context, ArrayList<HashMap<String, String>> field_Info_day) {
        this.context = context;
        this.field_Info_day=field_Info_day;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_attendance, parent, false);
        return new ViewHolder(v);
    }
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> map = field_Info_day.get(position);
        //holder.txt_present.setText("present -" + map.get("3"));
        String aa=map.get("3");
        if(aa.equalsIgnoreCase("0")){
            holder.txt_present.setText("Status- Absent");
        }
        else{
            holder.txt_present.setText("Status- Present");
        }
       // holder.txt_type.setText("Type -" + map.get("4"));
       // holder.txt_village.setText("Village name -" + map.get("5"));


        String date_new = map.get("2");
        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = (Date) formatter.parse(date_new);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
            finalString = newFormat.format(date);
        } catch (Exception e) {
            e.getMessage();
        }
        try {
            holder.txt_date.setText("Date - " + finalString);
            holder.show_farm_Info.setText(String.valueOf(position + 1));
            holder.itemView.setTag(map);
        } catch (Exception e) {
            e.getMessage();
        }
    }
    public int getItemCount() {
        return field_Info_day.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_present;

        private TextView txt_date;

        Button show_farm_Info;
        public ViewHolder(View convertView) {
            super(convertView);
            txt_present= (TextView)convertView.findViewById(R.id.txt_farmer_n);
            txt_date= (TextView)convertView.findViewById(R.id.txt_date);
            show_farm_Info=(Button)convertView.findViewById(R.id.fab_button);

        }

    }
}

