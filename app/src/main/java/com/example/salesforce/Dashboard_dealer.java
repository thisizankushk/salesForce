package com.example.salesforce;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.salesforce.Attendance.Attendance_Fragment_Tab;
import com.example.salesforce.Database.Databaseutill;
import com.example.salesforce.Database.GetData;
import com.example.salesforce.Retailer.RetailerVisit;
import com.example.salesforce.TA.Daily_TA_Tab;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Dashboard_dealer extends Fragment {
    View rootView;
    ImageView retailer_visit,attandance,daily_ta;
    TextView present,leave,visit;

    Databaseutill db;
    GetData get;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            db = Databaseutill.getDBAdapterInstance(getContext());
            get = new GetData(db, getContext());
            getActivity().setTitle("Hi, "+get.getEmpid().get(2));


            rootView = inflater.inflate(R.layout.freagment_main, container,
                    false);

            retailer_visit= (ImageView)rootView.findViewById(R.id.retailer_visit);
            attandance= (ImageView)rootView.findViewById(R.id.attandance);
            daily_ta= (ImageView)rootView.findViewById(R.id.daily_ta);

            SimpleDateFormat df = new SimpleDateFormat("MM dd yyyy");
            String date = df.format(Calendar.getInstance().getTime());
            date = "-"+date.substring(0,2)+"-";

            String visit_count = get.Visit(date);
            String attand = get.attandence_count(date,"1");
            String absent = get.attandence_count(date,"0");


            present= (TextView) rootView.findViewById(R.id.textView_present);
            leave= (TextView) rootView.findViewById(R.id.textView_leave);
            visit= (TextView) rootView.findViewById(R.id.textView_visit);

            present.setText(attand);
            leave.setText(absent);
            visit.setText(visit_count);

            retailer_visit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RetailerVisit fragment = new RetailerVisit();
                    Bundle bundle = new Bundle();
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.viewframe11, fragment, "dash")
                            .commit();
                }
            });


            attandance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Attendance_Fragment_Tab fragment = new Attendance_Fragment_Tab();
                    Bundle bundle = new Bundle();
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.viewframe11, fragment, "dash")
                            .commit();
                }
            });

            daily_ta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Daily_TA_Tab fragment = new Daily_TA_Tab();
                    Bundle bundle = new Bundle();
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.viewframe11, fragment, "dash")
                            .commit();
                }
            });


        } catch (Exception e) {
            e.getMessage();
        }

        return rootView;
    }


}
