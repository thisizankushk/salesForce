package com.example.salesforce.TA;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;

import com.example.salesforce.Dashboard_dealer;
import com.example.salesforce.R;

public class Daily_TA_Tab extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view_root=inflater.inflate(R.layout.view_tab,container , false);
        try{
            getActivity().setTitle("");
            FragmentTabHost mTabHost=(FragmentTabHost)view_root.findViewById(android.R.id.tabhost);
            mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

            mTabHost.addTab(
                    mTabHost.newTabSpec("0").setIndicator(gettabtxt("Home",R.drawable.home)),
                    Dashboard_dealer.class, getActivity().getIntent().getExtras());

            mTabHost.addTab(
                    mTabHost.newTabSpec("1").setIndicator(gettabtxt("Daily TA",R.drawable.add_blue)),
                    Daily_TA_Frg.class, getActivity().getIntent().getExtras());
            mTabHost.addTab(
                    mTabHost.newTabSpec("2").setIndicator(gettabtxt("View",R.drawable.list_blak)),
                    TA_View.class, getActivity().getIntent().getExtras());



            for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
            {
//                mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#6DCB99"));//#5F3C3A
                mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height=alt_tabs(getActivity());
            }

            mTabHost.setCurrentTab(1);
            TextView textView1 = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(R.id.txttitle);
            textView1.setTextColor(Color.parseColor("#00517A"));
            mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

                @Override
                public void onTabChanged(String tabId) {
                    // TODO Auto-generated method stub
                    View view = mTabHost.getCurrentView();
                    for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
                    {
                        mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height=alt_tabs(getActivity());
                    }
                    if(tabId.equals("0")) {
                        Dashboard_dealer dashfragment = new Dashboard_dealer();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.viewframe11, dashfragment, "dash").commit();
                    }
                    if(tabId.equals("2")){
                        TextView textView1 = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(R.id.txttitle);
                        textView1.setTextColor(Color.parseColor("#737373"));
                        ImageView ImageView1=(ImageView)mTabHost.getTabWidget().getChildAt(1).findViewById(R.id.imageView2);
                        ImageView1.setImageResource(R.drawable.add_black);

                        TextView textView2 = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(R.id.txttitle);
                        textView2.setTextColor(Color.parseColor("#00517A"));
                        ImageView ImageView2=(ImageView)mTabHost.getTabWidget().getChildAt(2).findViewById(R.id.imageView2);
                        ImageView2.setImageResource(R.drawable.list_blue);
                    }
                    if(tabId.equals("1")){
                        TextView textView2 = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(R.id.txttitle);
                        textView2.setTextColor(Color.parseColor("#737373"));
                        ImageView ImageView2=(ImageView)mTabHost.getTabWidget().getChildAt(2).findViewById(R.id.imageView2);
                        ImageView2.setImageResource(R.drawable.list_blak);

                        ImageView ImageView1=(ImageView)mTabHost.getTabWidget().getChildAt(1).findViewById(R.id.imageView2);
                        ImageView1.setImageResource(R.drawable.add_blue);
                        TextView textView1 = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(R.id.txttitle);
                        textView1.setTextColor(Color.parseColor("#00517A"));
                    }
//                    getActivity().finish();
                }
            });
        }catch(Exception er){
            er.getMessage();
        }
        return view_root;
    }

    public int alt_tabs(Context cont)
    {
        int alt;
        int dx, dy;
        DisplayMetrics metrics = cont.getResources().getDisplayMetrics();
        dx = metrics.widthPixels;
        dy = metrics.heightPixels;
        if (dx < dy) alt = dy/15;
        else alt = dy/10;
        return alt;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        try{
            for(	Fragment fragment : getChildFragmentManager().getFragments()){
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }catch(Exception er)
        {
            er.getMessage();
        }

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private View gettabtxt(String stringtxt, int icon) {
        LayoutInflater inflater1 = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v1 = (View) inflater1.inflate(R.layout.dynamictext, null);
        TextView tv1=(TextView)v1.findViewById(R.id.txttitle);
        ImageView ImageView1=(ImageView) v1.findViewById(R.id.imageView2);
        ImageView1.setImageResource(icon);
        tv1.setText(stringtxt);

        return v1;
    }

}