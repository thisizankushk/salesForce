package com.example.salesforce.Attendance;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesforce.Database.Databaseutill;
import com.example.salesforce.Database.GetData;
import com.example.salesforce.R;
import com.example.salesforce.adaptor.DateMapSComparator;
import com.example.salesforce.adaptor.SetterGetter;
import com.example.salesforce.adaptor.Show_Attendance_Adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import dmax.dialog.SpotsDialog;

public class View_Attendance_Fragment extends Fragment {
//    Spinner sp_select;
    RecyclerView mListView;
    Databaseutill db;
    GetData get;
    View vv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vv=inflater.inflate(R.layout.view_attendance,container,false);
        init();
        db = Databaseutill.getDBAdapterInstance(getActivity());
        get = new GetData(db, getActivity());
        new Random_list().execute();
        return vv;
    }
    public void init()
    {
        mListView = (RecyclerView)vv.findViewById(R.id.farmer_view_recyc);
//        sp_select=(Spinner)vv.findViewById(R.id.sp_select);
    }
    class Random_list extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>>
    {  	AlertDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try{
                pd=new SpotsDialog(getActivity());
                pd.show();
            }catch(Exception e){

            }
        }
        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... arg0) {

            ArrayList<HashMap<String, String>> returnvalue=new ArrayList<HashMap<String, String>>();

            try{
                returnvalue=get.getAttendanceField();

            }catch(Exception er)
            {
                er.getMessage();
            }
            return returnvalue;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            try{
                ArrayList<HashMap<String, String>> new_data=new ArrayList<>();
                if(result!=null){
                    if(result.size()>0){
                        ArrayList<SetterGetter> sett=new ArrayList<>();
                        try{
                            for (int icount1=0;icount1<result.size();icount1++) {
                                HashMap<String, String> val1 = new HashMap<String, String>();
                                HashMap<String, String> val=result.get(icount1);
                                for (int icount = 0; icount < val.size(); icount += 3){
                                    val1.put("1", String.valueOf(((((val.get("1")))))));
                                    val1.put("2", String.valueOf((((val.get("2"))))));
                                    val1.put("3", String.valueOf(((val.get("3")))));
                                  /*  val1.put("4", ((String.valueOf(val.get("4")))));
                                    val1.put("5", ((String.valueOf(val.get("5")))));*/
                                    new_data.add(val1);
                                }
                                //HashMap<String, String> val = new HashMap<String, String>();
                            }
                            Collections.sort(new_data,new DateMapSComparator("5"));
                            Show_Attendance_Adapter mAdapter = new Show_Attendance_Adapter(getActivity(),new_data);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            mListView.setLayoutManager(mLayoutManager);
                            mListView.setItemAnimator(new DefaultItemAnimator());
                            mListView.setAdapter(mAdapter);

                        }catch(Exception e){
                            e.getMessage();
                        }
                    }
                }
            }catch(Exception e){
                e.getMessage();
            }
            pd.dismiss();

        }
    }
}
