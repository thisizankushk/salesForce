package com.example.salesforce.TA;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesforce.Database.Databaseutill;
import com.example.salesforce.Database.GetData;
import com.example.salesforce.R;
import com.example.salesforce.Web.ConnectionDetector;
import com.example.salesforce.Web.Webservicerequest;
import com.example.salesforce.adaptor.Myspinner;
import com.example.salesforce.adaptor.View_TA_Adapter;
import com.example.salesforce.snackbar.Snackbar;
import com.example.salesforce.snackbar.SnackbarManager;
import com.example.salesforce.snackbar.enums.SnackbarType;
import com.example.salesforce.snackbar.listeners.ActionClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import dmax.dialog.SpotsDialog;

public class TA_View extends Fragment {
    private View mView;


    EditText from_date,to_date;
    RecyclerView mListView;
    private ArrayList<Myspinner> ms = null;
    View_TA_Adapter mAdapter;
    String id_ta = "";
//    MCrypt mCrypt;
    Button search;
    TextView tv_total_sum;
    String all_sum;
    double total_sum=0.00;

    Databaseutill db;
    GetData get;
    Webservicerequest wsc = new Webservicerequest();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {

            mView = inflater.inflate(R.layout.ta_view, container, false);
            //   db = new Databaseutill(getActivity());
            //   get = new GetData(db, getActivity());
            getActivity().setTitle("TA View");
            db = Databaseutill
                    .getDBAdapterInstance(getContext());
            get = new GetData(db, getContext());

            init();
            function(from_date);
            function1(from_date);
            function(to_date);
            function1(to_date);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                //SID,VILLAGE_ID,FARMER_ID,DEMO_ACRES,CROP_ID,FERTILIZER,PHOTO,LATT,LONGG,CR_BY,CR_DATE,STATUS,statusl
                new ViewTA_Data_basedon_Date().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"",get.getEmpid().get(0),"");

            } else {
                new ViewTA_Data_basedon_Date().execute("",get.getEmpid().get(0),"");

            }



            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

//                        total_sum=0.00;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                            //SID,VILLAGE_ID,FARMER_ID,DEMO_ACRES,CROP_ID,FERTILIZER,PHOTO,LATT,LONGG,CR_BY,CR_DATE,STATUS,statusl
                            new ViewTA_Data_basedon_Date().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,from_date.getText().toString(),get.getEmpid().get(0),to_date.getText().toString());

                        } else {
                            new ViewTA_Data_basedon_Date().execute(from_date.getText().toString(),get.getEmpid().get(0),to_date.getText().toString());

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mView;

    }


    private void init() {

        from_date = (EditText) mView.findViewById(R.id.from_date);
        tv_total_sum=(TextView)mView.findViewById(R.id.total_ta) ;
        to_date = (EditText) mView.findViewById(R.id.to_date);
        mListView = (RecyclerView) mView.findViewById(R.id.farmer_view_recyc);
        search=(Button)mView.findViewById(R.id.search);

    }

    class ViewTA_Data_basedon_Date extends AsyncTask<String, Void, String> {

        AlertDialog pd;
        ConnectionDetector cd;

        @Override
        public void onPreExecute() {

            try {
                pd = new SpotsDialog(getActivity());
                pd.show();

            } catch (Exception er) {
                er.printStackTrace();
            }
        }

        @Override
        public String doInBackground(String... params) {
            // TODO Auto-generated method stub

//            ArrayList<String> returnval = new ArrayList<String>();
            String resultdata = null;
            try {
                ArrayList<String> inputlist = new ArrayList<String>();
                inputlist.add("emp_id");
                inputlist.add(params[1]);
                inputlist.add("sd");
                inputlist.add(params[0]);
                inputlist.add("ed");
                inputlist.add(params[2]);


                resultdata = wsc.MobileWebservice("GetDailyTA", inputlist);


//                returnval.add(resultdata);
                pd.dismiss();
            } catch (Exception er) {
                er.printStackTrace();
            }
            return resultdata;
        }

        @Override
        public void onPostExecute(String result) {
            total_sum=0.00;

            try {

                if (!result.equals(null)) {

                    ArrayList<HashMap<String, String>> databy_date = new ArrayList<HashMap<String, String>>();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                HashMap<String, String> has_data = new HashMap<String, String>();
                                String TA_DATE = (jsonObject.getString("TA_DATE"));
                                String AMOUNT = (jsonObject.getString("AMOUNT"));
                                String SUBMITDATE = (jsonObject.getString("SUBMITDATE"));
                                total_sum= total_sum+Double.parseDouble(wsc.Decrypt(AMOUNT));
                                has_data.put("TA_DATE", wsc.Decrypt(TA_DATE));
                                has_data.put("AMOUNT", wsc.Decrypt(AMOUNT));
                                has_data.put("SUBMITDATE", wsc.Decrypt(SUBMITDATE));
                                databy_date.add(has_data);
                            } catch (Exception ex) {
                                ex.getStackTrace();
                            }
                        }


                        tv_total_sum.setText(""+total_sum);
                        mAdapter = new View_TA_Adapter(databy_date, getActivity(), TA_View.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        mListView.setLayoutManager(mLayoutManager);
                        mListView.setItemAnimator(new DefaultItemAnimator());
                        mListView.setAdapter(mAdapter);

                } else {
                    SnackbarManager.show(
                            Snackbar.with(getContext())
                                    .type(SnackbarType.MULTI_LINE)
                                    .text("Data not Found")
                                    .textColor(Color.WHITE)
                                    .color(Color.GRAY)
                                    .actionLabel("Cancel")
                                    .actionColor(Color.YELLOW)

                                    .actionListener(new ActionClickListener() {
                                        @Override
                                        public void onActionClicked(Snackbar snackbar) {

                                        }
                                    })
                    );
                }
                pd.dismiss();
            } catch (Exception er) {
                er.getStackTrace();

            }


        }
    }


    private void function(final EditText date) {

        date.setFocusable(false);
        InputMethodManager imm = (InputMethodManager)getActivity() .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(date.getWindowToken(), 0);

        date.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    Calendar c = Calendar.getInstance();
                    final Integer mYear = c.get(Calendar.YEAR);
                    Integer mMonth = c.get(Calendar.MONTH);
                    Integer mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker arg0,
                                                      int year, int monthOfYear,
                                                      int dayOfMonth) {


                                    try {

                                        //    date.setText(year + "-"
                                        //+ (monthOfYear + 1) + "-" + dayOfMonth);

                                        date.setText(year + "-" + (((monthOfYear+1)<10)?"0"+(monthOfYear+1):(monthOfYear + 1) )
                                                + "-" + (((dayOfMonth)<10)?"0"+dayOfMonth:dayOfMonth  ) );
                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }
                                }
                            }, mYear, mMonth, mDay);
                    dpd.show();
                }
                return false;
            }
        });
    }

    private void function1(final EditText date1) {

        date1.setFocusable(false);
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(date1.getWindowToken(), 0);

        date1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    Calendar c = Calendar.getInstance();
                    Integer mYear = c.get(Calendar.YEAR);
                    Integer mMonth = c.get(Calendar.MONTH);
                    Integer mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker arg0,
                                                      int year, int monthOfYear,
                                                      int dayOfMonth) {

                                    try {

                                        Calendar c = Calendar.getInstance();
                                        SimpleDateFormat df12 = new SimpleDateFormat("yyyy-MM-dd");
                                        String APP_MOD_DATE = df12.format(c.getTime());


                                        String trans_datee = String.valueOf(year + "-"
                                                + (monthOfYear + 1) + "-"
                                                + dayOfMonth);
                                        String sowing_datee = APP_MOD_DATE;
                                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");

                                        Date trans_dateeee = sd.parse(trans_datee);
                                        Date sowing_dateeee = sd.parse(sowing_datee);

                                        if (trans_dateeee.before(sowing_dateeee)||trans_dateeee.equals(sowing_dateeee) ) {
                                            //   date1.setText(year + "-"
                                            //    + (monthOfYear + 1) + "-" + dayOfMonth);
                                            date1.setText(    year + "-" + (((monthOfYear+1)<10)?"0"+(monthOfYear+1):(monthOfYear + 1) )
                                                    + "-" + (((dayOfMonth)<10)?"0"+dayOfMonth:dayOfMonth  ) );
                                          /*  try {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                                                    //SID,VILLAGE_ID,FARMER_ID,DEMO_ACRES,CROP_ID,FERTILIZER,PHOTO,LATT,LONGG,CR_BY,CR_DATE,STATUS,statusl
                                                    new ViewTA_Data_basedon_Date().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,auto_date.getText().toString());

                                                } else {
                                                    new ViewTA_Data_basedon_Date().execute(auto_date.getText().toString());

                                                }


                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }*/

                                        } else {


                                            SnackbarManager.show(Snackbar.with(getActivity()).type(SnackbarType.MULTI_LINE).text("Date always should be smaller than current date").textColor(Color.WHITE).color(Color.GRAY).actionLabel("Cancel").actionColor(Color.YELLOW).actionListener(new ActionClickListener() {
                                                        @Override
                                                        public void onActionClicked(Snackbar snackbar) {

                                                        }
                                                    })
                                            );

                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }
                                }
                            }, mYear, mMonth, mDay);
                    dpd.show();
                }
                return false;

            }
        });
    }
}
