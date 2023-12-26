package com.example.salesforce.Retailer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesforce.Database.Databaseutill;
import com.example.salesforce.Database.GetData;
import com.example.salesforce.Models.RetailerVisitModel;
import com.example.salesforce.R;
import com.example.salesforce.adaptor.RetailerVisitAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class RetailerVisit extends Fragment {
    View view;
    Button addVisit;
    RecyclerView mListView;
    TextView tvDatePicker;
    androidx.appcompat.widget.SearchView searchView;
    ProgressBar progressBar;
    ArrayList<RetailerVisitModel> visits;
    RetailerVisitAdapter retailerAdapter;
    String searchedText = "", startDate = "", endDate = "";
    MaterialButton btnAddVisit;

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addVisit =  view.findViewById(R.id.add_visit);
        btnAddVisit = view.findViewById(R.id.btnAddVisit);
        mListView =  view.findViewById(R.id.retailer_visit);
        tvDatePicker = view.findViewById(R.id.datePicker);
        searchView = view.findViewById(R.id.searchView);
        progressBar = view.findViewById(R.id.progressBar);

        btnAddVisit.setOnClickListener(v -> {
            AddVisitNew fragment = new AddVisitNew();
            Bundle bundle = new Bundle();
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.viewframe11, fragment, "dash").commit();
        });

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        endDate = df.format(c);
        if (c.getMonth() == 1) {
            c.setMonth(12);
            c.setYear(c.getYear() - 1);
        } else {
            c.setMonth(c.getMonth() - 1);
        }
        startDate = df.format(c);

        tvDatePicker.setText(startDate + " - " + endDate);
        tvDatePicker.setOnClickListener(v -> DatePickerDialog());
        activateSearch();
        new GetVisitList().execute();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        requireActivity().setTitle("Retailer Visit");
        view = inflater.inflate(R.layout.retailer_view, container, false);
        return view;
    }

    public void activateSearch() {
        searchView.setIconifiedByDefault(true);
        searchView.setActivated(true);
        searchView.setQueryHint("Search");
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchedText = newText;
                startDate = "";
                endDate = "";
                new GetVisitList().execute();
                return false;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void DatePickerDialog() {
        // Creating a MaterialDatePicker builder for selecting a date range
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a date range");

        // Building the date picker dialog
        MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();
        datePicker.addOnPositiveButtonClickListener(selection -> {

            // Retrieving the selected start and end dates
            Long startDateL = selection.first;
            Long endDateL = selection.second;

            // Formatting the selected dates as strings
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            if (startDateL != null && endDateL != null) {
                String startDateString = sdf.format(new Date(startDateL));
                String endDateString = sdf.format(new Date(endDateL));
                startDate = startDateString;
                endDate = endDateString;
                searchedText = "";
                // Creating the date range string
                // Displaying the selected date range in the TextView
                tvDatePicker.setText(startDateString + " - " + endDateString);
                new GetVisitList().execute();
            }
        });
        // Showing the date picker dialog
        datePicker.show(requireActivity().getSupportFragmentManager(), "DATE_PICKER");
    }

    public class GetVisitList extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(requireContext());
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String URL = "http://unnatisales.akshapp.com/mobb/service.asmx";
            String NAMESPACE = "http://aksha/app/";
            String METHOD_NAME = "Getretailersvisit";
            String SOAPAction = NAMESPACE + METHOD_NAME;

            SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);

            Databaseutill db = Databaseutill.getDBAdapterInstance(requireActivity());
            GetData get = new GetData(db, requireActivity());

            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setName("emp_id");
            propertyInfo.setValue(get.getEmpid().get(0));
            propertyInfo.setType(String.class);
            soapObject.addProperty(propertyInfo);

            PropertyInfo propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("startdate");
            propertyInfo2.setValue(startDate);
            propertyInfo2.setType(String.class);
            soapObject.addProperty(propertyInfo2);

            PropertyInfo propertyInfo3 = new PropertyInfo();
            propertyInfo3.setName("enddate");
            propertyInfo3.setValue(endDate);
            propertyInfo3.setType(String.class);
            soapObject.addProperty(propertyInfo3);

            PropertyInfo propertyInfo4 = new PropertyInfo();
            propertyInfo4.setName("search");
            propertyInfo4.setValue(searchedText);
            propertyInfo4.setType(String.class);
            soapObject.addProperty(propertyInfo4);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setAddAdornments(false);
            envelope.encodingStyle = SoapSerializationEnvelope.ENV;
            envelope.setOutputSoapObject(soapObject);

            String resultstring = null;

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL);

            try {
                httpTransportSE.call(SOAPAction, envelope);
                Object results =  envelope.getResponse();
                resultstring = results.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultstring;
        }

        @Override
        protected void onPostExecute(String s) {
            // Toast.makeText(getApplicationContext(),"Response"+s,Toast.LENGTH_LONG).show();
            if (s != null) {
                try {
                    visits = new ArrayList<>();
                    JSONArray jsonArr = new JSONArray(s);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        RetailerVisitModel mItem = new RetailerVisitModel();
                        mItem.setStoreName(jsonObj.getString("store_name"));
                        mItem.setDistrict(jsonObj.getString("district"));
                        mItem.setMobileNumber(jsonObj.getString("mobile_number"));
                        mItem.setCreatedate(jsonObj.getString("createdate"));
                        mItem.setReasonofvisit(jsonObj.getString("reasonvisitname"));
                        visits.add(mItem);
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
                    retailerAdapter = new RetailerVisitAdapter(visits, requireActivity());
                    mListView.setHasFixedSize(true);
                    mListView.setLayoutManager(layoutManager);
                    mListView.setAdapter(retailerAdapter);
                    mListView.setAdapter(mListView.getAdapter());

                } catch (Exception w) {
                    w.printStackTrace();
                }
                if (s.equalsIgnoreCase("0")) {
                    Toast.makeText(requireContext(), "Invalid Request", Toast.LENGTH_LONG).show();
                } else if (s.equalsIgnoreCase("1")) {
                    Toast.makeText(requireContext(), "Details Submitted", Toast.LENGTH_LONG).show();
                }
            }
            pd.dismiss();
        }
    }

}
