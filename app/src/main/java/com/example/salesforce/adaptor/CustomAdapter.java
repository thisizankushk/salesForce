package com.example.salesforce.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.salesforce.R;

import java.util.ArrayList;

public class CustomAdapter  extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    ArrayList<Myspinner> visitReson;


    public CustomAdapter(Context context, ArrayList<Myspinner> visitReson) {
        this.context = context;
        this.visitReson = visitReson;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return visitReson.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(visitReson.get(i).getSpinnerText());
        return view;
    }
}