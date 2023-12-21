package com.example.salesforce.Retailer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.salesforce.CustomAlertDialog;
import com.example.salesforce.Database.Databaseutill;
import com.example.salesforce.Database.GetData;
import com.example.salesforce.GPS.GPSTracker;
import com.example.salesforce.R;
import com.example.salesforce.Web.ConnectionDetector;
import com.example.salesforce.Web.Webservicerequest;
import com.example.salesforce.adaptor.CustomAdapter;
import com.example.salesforce.adaptor.Myspinner;
import com.example.salesforce.adaptor.Search;
import com.example.salesforce.adaptor.SearchAdaptor;
import com.example.salesforce.snackbar.Snackbar;
import com.example.salesforce.snackbar.SnackbarManager;
import com.example.salesforce.snackbar.enums.SnackbarType;
import com.example.salesforce.snackbar.listeners.ActionClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AddVisit extends Fragment {
    View view;
    LinearLayout linearLayout;
    TextView storeName,retailer_name,retailer_mob,retailer_dist,retailer_state,visit_date;
    Spinner visit;
    EditText remark;
    Button submit;
    AutoCompleteTextView et_Search;
    ImageView search;
    CustomAlertDialog m1Alert;
    ArrayList<HashMap<String, String>> returnvalue = null;
    Databaseutill db;
    GetData get;
    ArrayList<Myspinner> visitReson = null;
    String id_visit = "0";
    SearchAdaptor adapter;
    List<Search> mList;
//    ArrayList<String> frequentList = new ArrayList<>();
    ArrayList<String> details = new ArrayList<>();
    private Search selectedPerson;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            getActivity().setTitle("New Visit");
            view = inflater.inflate(R.layout.new_retailer_visit, container, false);
            linearLayout = (LinearLayout)view.findViewById(R.id.linear_layout);
            storeName = (TextView) view.findViewById(R.id.store_name_text);
            retailer_name = (TextView) view.findViewById(R.id.retailer_name_text);
            retailer_mob = (TextView) view.findViewById(R.id.retailer_mob_text);
            retailer_dist = (TextView) view.findViewById(R.id.retailer_dist_text);
            retailer_state = (TextView) view.findViewById(R.id.retailer_state_text);
            visit_date = (TextView) view.findViewById(R.id.visit_date_text);
            et_Search = (AutoCompleteTextView) view.findViewById(R.id.et_search);
            search = (ImageView)view.findViewById(R.id.img_srch);

            linearLayout.setVisibility(View.GONE);
            visit = (Spinner)view.findViewById(R.id.spinner);
            new Visit().execute();
            remark = (EditText)view.findViewById(R.id.reasonEditText);
            submit = (Button)view.findViewById(R.id.submit);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String store_id = storeName.getTag().toString();
                    String store_name = storeName.getText().toString().replace(": ","");;
                    String name = retailer_name.getText().toString().replace(": ","");
                    String mobile_number = retailer_mob.getText().toString().replace(": ","");;
                    String state = retailer_state.getText().toString().replace(": ","");;
                    String district = retailer_dist.getText().toString().replace(": ","");;
                    String  resionofvisit = id_visit;
//                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String date = df.format(Calendar.getInstance().getTime());
                    String crteatedate = date;
                    String activitydatedate = visit_date.getText().toString().replace(": ","");
                    /*try {
                        Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(activitydatedate);
                        activitydatedate = df.format(date1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/

                    ConnectionDetector cd = new ConnectionDetector(
                            getContext());

                    String remarks = remark.getText().toString();
                    String Createby= get.getEmpid().get(0);
                    if(resionofvisit.equals("0")){
                        Toast.makeText(getContext(),"Please select Reason of Visit",Toast.LENGTH_LONG).show();
                    }else if(activitydatedate.contains("Select")){
                        Toast.makeText(getContext(),"Please enter date of visit",Toast.LENGTH_LONG).show();
                    }else if(remarks.equals("")){
                        Toast.makeText(getContext(),"Write a remark",Toast.LENGTH_LONG).show();
                    }else if (cd.isConnectingToInternet()){
                        new Async_AddVisit().execute(store_id,store_name,name,mobile_number,state,district,
                                resionofvisit,crteatedate,activitydatedate,remarks,Createby);
                    }else {
                        SnackbarManager.show(
                                Snackbar.with(getContext())
                                        .type(SnackbarType.MULTI_LINE)
                                        .text("There is no Network")
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
//                        new AsyncLoginLocal().execute(store_id,store_name,name,mobile_number,state,district,
//                                resionofvisit,crteatedate,activitydatedate,remarks,Createby);
                    }
                }
            });

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = et_Search.getText().toString();
                    if(number.contains(",")){
                        String mob[] = number.split(",");
                        number = mob[0];
                    }
                    if(number.length()==10){
                        details = get.getDetails(number);
                        if(!details.isEmpty()) {
                            linearLayout.setVisibility(View.VISIBLE);
                            storeName.setText(": " + details.get(1));
                            storeName.setTag(details.get(0));
                            retailer_name.setText(": " + details.get(2));
                            retailer_mob.setText(": " + details.get(3));
                            retailer_dist.setText(": " + details.get(5));
                            retailer_state.setText(": " + details.get(4));
                            et_Search.setText("");

                        }else {
                            linearLayout.setVisibility(View.GONE);
                            SnackbarManager.show(
                                    Snackbar.with(getContext())
                                            .type(SnackbarType.MULTI_LINE)
                                            .text("There is no retailer with is number")
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
                    }else {
                        linearLayout.setVisibility(View.GONE);
                        SnackbarManager.show(
                                Snackbar.with(getContext())
                                        .type(SnackbarType.MULTI_LINE)
                                        .text("please enter a valid number")
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
                }
            });

            visit_date.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {

                    if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                        final Calendar c = Calendar.getInstance();
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
                                            SimpleDateFormat df12 = new SimpleDateFormat("yyyy-MM-dd");
                                            String date = df12.format(c.getTime());
//                                            visit_date.setText(date);
//                                        visit_date.setText(year + "-"
//                                        + (monthOfYear + 1) + "-" + dayOfMonth);
                                        visit_date.setText(": "+dayOfMonth + "-"
                                                + (monthOfYear + 1) + "-" + year);


                                        } catch (Exception e) {
                                            e.getMessage();

                                        }
                                    }
                                }, mYear, mMonth, mDay);
                        dpd.getDatePicker().setMinDate(new Date().getTime());
                        dpd.getDatePicker().setMaxDate(new Date().getTime());
                        dpd.show();
                    }
                    return false;
                }
            });



            et_Search.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // TODO Auto-generated method stub

                    if (et_Search.getText().toString().trim().equals("")) {

                    }

//                    if (et_Search.getText().length() > 9) {
//                        frequent.performClick();
//                    }
//                    frequentList = null;
                    if(et_Search.getText().length() > 3){
                        mList = get.getNumber(et_Search.getText().toString());
                        adapter = new SearchAdaptor(getContext(), R.layout.new_retailer_visit, R.id.lbl_name, mList);
                        et_Search.setAdapter(adapter);

//                        et_Search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
//                                //this is the way to find selected object/item
//                                selectedPerson = (Search) adapterView.getItemAtPosition(pos);
//                            }
//                        });






//                        frequentList = get.getNumber(et_Search.getText().toString());
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),
//                                android.R.layout.simple_spinner_item,
//                                frequentList); //selected item will look like a spinner set from XML
//                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        et_Search.setAdapter(spinnerArrayAdapter);
//                        frequent.performClick();
                    }else {
                        et_Search.setAdapter(null);
                        et_Search.showDropDown();
                    }
                    if(et_Search.getText().length() >=10){
                        String number = et_Search.getText().toString();
                        if(number.contains(",")){
                            String mob[] = number.split(",");
                            number = mob[0];
                        }
                        if(number.length()==10){
                            details = get.getDetails(number);
                            if(!details.isEmpty()) {
                                linearLayout.setVisibility(View.VISIBLE);
                                storeName.setText(": " + details.get(1));
                                storeName.setTag(details.get(0));
                                retailer_name.setText(": " + details.get(2));
                                retailer_mob.setText(": " + details.get(3));
                                retailer_dist.setText(": " + details.get(5));
                                retailer_state.setText(": " + details.get(4));
                                et_Search.setText("");

                            }else {
                                linearLayout.setVisibility(View.GONE);
                                SnackbarManager.show(
                                        Snackbar.with(getContext())
                                                .type(SnackbarType.MULTI_LINE)
                                                .text("There is no retailer with is number")
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
                        }else {
                            linearLayout.setVisibility(View.GONE);
                            SnackbarManager.show(
                                    Snackbar.with(getContext())
                                            .type(SnackbarType.MULTI_LINE)
                                            .text("please enter a valid number")
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
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub
                }
            });




        } catch (Exception e) {
            e.getMessage();
        }
        return view;
    }



    class Visit extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {
        AlertDialog pd;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            try {
                pd = new SpotsDialog(getActivity());
                pd.show();
            } catch (Exception e) {

            }
        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... arg0) {
            db = new Databaseutill(getActivity());
            get = new GetData(db, getActivity());

            returnvalue = new ArrayList<HashMap<String, String>>();


            try {

                returnvalue = get.get_visit_reason();

            } catch (Exception er) {
                er.printStackTrace();
            }


            return returnvalue;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            try {
                ArrayList<HashMap<String, String>> arr_data = result;
                visitReson = new ArrayList<Myspinner>();
                visitReson.add(new Myspinner("Select Visit", "0"));
                for (int i = 0; i < arr_data.size(); i++) {
                    HashMap<String, String> hash = arr_data.get(i);
                    visitReson.add(new Myspinner(hash.get("2"), hash.get("1")));

                }


//                ArrayAdapter<Myspinner> arr_adap = new ArrayAdapter<Myspinner>(getActivity(), android.R.layout.simple_spinner_item, visitReson);
//                visit.setAdapter(arr_adap);

                CustomAdapter customAdapter=new CustomAdapter(getContext(),visitReson);
                visit.setAdapter(customAdapter);


                visit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                        CustomAdapter customAdapter1 = (CustomAdapter) visit.getAdapter();
//                        Myspinner my = (Myspinner) parent.getItemAtPosition(position);
//                        id_visit = my.getValue();
                        id_visit = visitReson.get(position).getValue();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
            pd.dismiss();

        }
    }


    class Async_AddVisit extends AsyncTask<String, Void, String> {
        AlertDialog pb;
        Databaseutill db;
        GetData get;
        String resultdata;
        GPSTracker gp;
        String Lng;
        String Lat;
//        String urlstr;
//        ConnectionDetector cd = new ConnectionDetector(LoginActivity.this);

        @Override
        public void onPreExecute() {

            try {

                db = Databaseutill
                        .getDBAdapterInstance(getContext());
                get = new GetData(db, getContext());
                pb = new SpotsDialog(getContext());
                pb.show();
                gp=new GPSTracker(getContext());
                Lat=String.valueOf(gp.getLatitude());
                Lng=String.valueOf(gp.getLongitude());
            } catch (Exception er) {
                er.printStackTrace();
            }
        }

        @Override
        public String doInBackground(String... params) {
            // TODO Auto-generated method stub

            try {
//                if (cd.isConnectingToInternet()) {
                Webservicerequest wsc = new Webservicerequest();
                resultdata = get.Visit_Add(params[0],params[1],params[2],params[3],params[4],params[5],
                        params[6],params[7],params[8],params[9],params[10],"1");

                String id = get.Lastid();
                int i = Integer.parseInt(id)+1;

                get.insertLocalRetailer(String.valueOf(i),params[0],params[1],params[2],params[3],params[4],params[5],
                        params[6],params[7],params[8],params[9],params[10],"1");

                if (resultdata == null) {
                    return null;
                }


            } catch (Exception ex) {

                ex.printStackTrace();
                //		return null;
            }
            return resultdata;
        }

        @Override
        public void onPostExecute(String result) {
                try {
                    if(result.equals("1")){
                        linearLayout.setVisibility(View.GONE);
                        visit_date.setText(": Select Date");
                        remark.setText("");
                        new Visit().execute();

                        m1Alert = new CustomAlertDialog(getContext());
                        m1Alert.setIcon(R.drawable.success);
                        m1Alert.setMessage("Retailer visit submitted successfully");
                        m1Alert.setPositveButton("Go Home", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                RetailerVisit fragment = new RetailerVisit();
                                Bundle bundle = new Bundle();
                                fragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.viewframe11, fragment, "dash")
                                        .commit();
                                m1Alert.dismiss();
                            }
                        });
                        m1Alert.show();
                    }
                    else {

                        m1Alert = new CustomAlertDialog(getContext());
                        m1Alert.setIcon(R.drawable.error_msg);
                        m1Alert.setMessage("Something went Wrong, Please try again");
                        m1Alert.setPositveButton("Go Home", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                            tv_credit_points.setText("0");
//                            et_quantity_sold.setText("");
                                m1Alert.dismiss();
                            }
                        });
                        m1Alert.show();
                    }
                } catch (Exception er) {
                    er.printStackTrace();
                }
                pb.dismiss();
        }

    }

    class AsyncLoginLocal extends AsyncTask<String, Void, String> {
        ProgressDialog pd;
        String resultdata = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            pd = new ProgressDialog(getContext());
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {

//            resultdata = get.insertLocalRetailer(params[0],params[1],params[2],params[3],params[4],params[5],
//                    params[6],params[7],params[8],params[9],params[10],"0");

            return resultdata;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            try {
                super.onPostExecute(result);
                if (result.equals("1")) {
                    SnackbarManager.show(
                            Snackbar.with(getContext())
                                    .type(SnackbarType.MULTI_LINE)
                                    .text("Visit added successfully")
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
                else {
                    SnackbarManager.show(
                            Snackbar.with(getContext())
                                    .type(SnackbarType.MULTI_LINE)
                                    .text("Please try again something went wrong")
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

            } catch (Exception er) {
                er.printStackTrace();
            }

            pd.dismiss();
        }

    }
}
