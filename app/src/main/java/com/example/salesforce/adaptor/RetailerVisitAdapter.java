package com.example.salesforce.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesforce.Models.RetailerVisitModel;
import com.example.salesforce.R;

import java.util.ArrayList;

public class RetailerVisitAdapter extends RecyclerView.Adapter<RetailerVisitAdapter.ViewHolder> {
    ArrayList<RetailerVisitModel> visits;
    Context context;

    public RetailerVisitAdapter(ArrayList<RetailerVisitModel> visits, Context context) {
        this.visits = visits;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.retailer_visit_item, parent, false);
        return new ViewHolder(listItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(visits.get(position).getStoreName());
        holder.subTitle.setText("+91 " + visits.get(position).getMobileNumber());
        holder.createDate.setText(visits.get(position).getCreatedate());
        holder.reason.setText(visits.get(position).getReasonofvisit());
        holder.grey_line.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return visits.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subTitle, createDate, reason;
        View grey_line;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.subTitle = itemView.findViewById(R.id.subTitle);
            this.createDate = itemView.findViewById(R.id.createDate);
            this.grey_line = itemView.findViewById(R.id.grey_line);
            this.reason = itemView.findViewById(R.id.reason);
        }
    }
}
