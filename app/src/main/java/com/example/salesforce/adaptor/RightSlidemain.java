package com.example.salesforce.adaptor;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.salesforce.R;


public class RightSlidemain extends BaseAdapter {

    //"R and D trial",

    String[] itemname = {"Logout", "Version Number","Change Password"};
//    Integer[] itemimg = {R.drawable.sync, R.drawable.view_profile, R.drawable.logout, R.drawable.version_number, R.drawable.change_password};
    Context mcontext;

    public RightSlidemain(Context applicationContext) {
        // TODO Auto-generated constructor stub

        mcontext = applicationContext;
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

        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vi = inflater.inflate(R.layout.list_row_transparent, null);

        TextView title = (TextView) vi.findViewById(R.id.title);
//        ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image);
        title.setText(itemname[position]);
//        thumb_image.setImageResource(itemimg[position]);

        return vi;
    }
}
