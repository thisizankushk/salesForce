package com.example.salesforce.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.salesforce.R;



public class LeftSlideList extends BaseAdapter {


    String[] itemname = {"Home", "Quick Registration", "View Mason", "View Dealer"};
//    Integer[] itemimg = {R.drawable.home, R.drawable.icon_edit_white, R.drawable.view_and_create_white, R.drawable.view_dealer_white};


    Context mcontext;
    String itype;

    public LeftSlideList(Context applicationContext, String type) {
        // TODO Auto-generated constructor stub
        mcontext = applicationContext;
        itype = type;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return itemname.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub

        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View vi = convertView;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.list_row_transparent, null);

            TextView title = (TextView) vi.findViewById(R.id.title);
            title.setText(itemname[position]);
        }
        return vi;
    }


}
