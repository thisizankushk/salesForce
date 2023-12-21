package com.example.salesforce.Retailer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesforce.R;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class RetailerVisit extends Fragment {
    View view;
    ArrayList<HashMap<String, String>> data;
    Button addVisit;
    RecyclerView mListView;
    TextView tvDatePicker;
    androidx.appcompat.widget.SearchView searchView;
    ProgressBar progressBar;

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addVisit = (Button) view.findViewById(R.id.add_visit);
        mListView = (RecyclerView) view.findViewById(R.id.retailer_visit);
        tvDatePicker = view.findViewById(R.id.datePicker);
        searchView = view.findViewById(R.id.searchView);
        progressBar = view.findViewById(R.id.progressBar);

        addVisit.setOnClickListener(v -> {
            AddVisitNew fragment = new AddVisitNew();
            Bundle bundle = new Bundle();
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.viewframe11, fragment, "dash").commit();
        });

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        if (c.getMonth() == 1) {
            c.setMonth(12);
            c.setYear(c.getYear() - 1);
        } else {
            c.setMonth(c.getMonth() - 1);
        }
        String previousMonth = df.format(c);

        tvDatePicker.setText(previousMonth + " - " + formattedDate);
        tvDatePicker.setOnClickListener(v -> DatePickerDialog());
        activateSearch();
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
    }

    private void DatePickerDialog() {
        // Creating a MaterialDatePicker builder for selecting a date range
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a date range");

        // Building the date picker dialog
        MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();
        datePicker.addOnPositiveButtonClickListener(selection -> {

            // Retrieving the selected start and end dates
            Long startDate = selection.first;
            Long endDate = selection.second;

            // Formatting the selected dates as strings
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            if (startDate != null && endDate != null) {
                String startDateString = sdf.format(new Date(startDate));
                String endDateString = sdf.format(new Date(endDate));

                // Creating the date range string
                String selectedDateRange = startDateString + " - " + endDateString;
                // Displaying the selected date range in the TextView
                tvDatePicker.setText(selectedDateRange);
            }
        });

        // Showing the date picker dialog
        datePicker.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
    }
}
