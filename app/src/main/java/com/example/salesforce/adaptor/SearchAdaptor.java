package com.example.salesforce.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.salesforce.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdaptor extends ArrayAdapter<Search> {

    Context context;
    int resource, textViewResourceId;
    List<Search> items, tempItems, suggestions;

    public SearchAdaptor(Context context, int resource, int textViewResourceId, List<Search> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<Search>(items); // this makes the difference.
        suggestions = new ArrayList<Search>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.raw_layout, parent, false);
        }
        Search people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            TextView lblName1 = (TextView) view.findViewById(R.id.lbl_name1);
            if (lblName != null)
                lblName1.setText(people.getName());
            lblName.setText(people.getNumber());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((Search) resultValue).getNumber();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Search people : tempItems) {
                    if (people.getNumber().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Search> filterList = (ArrayList<Search>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Search people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}