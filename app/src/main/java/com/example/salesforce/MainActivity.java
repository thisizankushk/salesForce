package com.example.salesforce;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.legacy.app.ActionBarDrawerToggle;
import androidx.viewpager.widget.ViewPager;

import com.example.salesforce.Database.Databaseutill;
import com.example.salesforce.Database.GetData;
import com.example.salesforce.Web.Controller;
import com.example.salesforce.adaptor.LeftSlideList;
import com.example.salesforce.adaptor.RightSlidemain;

import java.io.File;


public class MainActivity extends AppCompatActivity implements
        ActionBar.TabListener, ViewPager.OnPageChangeListener {


    Object tag;
    //***************** Tabs Initialize ***********************************
//	private ViewPager viewPager;
    ActionBar actionBar;
    public FrameLayout viewframe11;

    boolean doubleBackToExitPressedOnce = false;
    private String[] tabs = {"Home"};
//***************** Tabs Initializing Ends ***************************

    //******************  Left Drawer Initialize *************************
    private DrawerLayout mDrawerLayout;


    private ListView mDrawerList, list_right, list_left;

    //******************  Left Drawer Ends ************************
    private ActionBarDrawerToggle mDrawerToggle;
    Databaseutill db;
    GetData get;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
            setContentView(R.layout.activity_main);

            db = new Databaseutill(getApplicationContext());
            get = new GetData(db, getApplicationContext());

            String user_id;
            String district = get.getEmpid().get(25);
            try {
                user_id = get.getLastUser_id().get(0);
            }catch (Exception er) {
                user_id="";
            }
            getResponseFromServer(district,user_id);


            actionBar = this.getActionBar();




        } catch (Exception e) {
            e.printStackTrace();
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawerblue, R.string.drawer_open,
                R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View view) {


                invalidateOptionsMenu();

            }
            @Override
            public void onDrawerOpened(View drawerView) {
                if (getActionBar() != null)

                invalidateOptionsMenu();
            }

        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);

        }
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        list_right = (ListView) findViewById(R.id.right_drawer);
        mDrawerList.setAdapter(new LeftSlideList(getApplicationContext(), ""));
        list_right.setAdapter(new RightSlidemain(getApplicationContext()));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                onPageSelected(position);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                if (position == 0) {
                    try {
                        Dashboard_dealer dashfragment = new Dashboard_dealer();
                        Bundle bundle = getIntent().getExtras();
                        dashfragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.viewframe11, dashfragment, "dash").commit();
                        mDrawerLayout.closeDrawer(Gravity.LEFT);

                    } catch (Exception e) {
                        e.getMessage();
                    }
                } else if (position == 1) {
//

                } else if (position == 2) {

                } else if (position == 3) {

                }


                mDrawerLayout.closeDrawer(Gravity.LEFT);

            }
        });


        list_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {

                if (arg2 == 0) {
                    try {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setMessage("Do you want to logout ?");
                        builder1.setTitle("Are you sure?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();

                                            File fdb = getDatabasePath("/data/data/"
                                                    + getApplicationContext()
                                                    .getPackageName()
                                                    + "/databases/DBName.sqlite");

                                            boolean val = fdb.delete();
                                            Intent intent = new Intent(getApplicationContext(),
                                                    LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        mDrawerLayout.closeDrawer(Gravity.RIGHT);
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


                if (arg2 == 1) {
                    try {
                        try {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                            builder1.setTitle("App Version");
                            builder1.setMessage("Version no.: " + getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            mDrawerLayout.closeDrawer(Gravity.RIGHT);
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        } catch (Exception e) {

                        }

                    } catch (Exception e) {
                        e.getMessage();
                    }

                }


                if (arg2 == 2) {
                   Intent intent = new Intent(getApplicationContext(),ChangePassword.class);
                   startActivity(intent);

                }
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }

        });

        viewframe11 = (FrameLayout) findViewById(R.id.viewframe11);
        Dashboard_dealer dashfragment = new Dashboard_dealer();
        Bundle bundle = getIntent().getExtras();
        dashfragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.viewframe11, dashfragment, "dash").commit();


    }


//*********** Left Drawer ************************

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_rightsettings:
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.action_bar, menu);
        Dashboard_dealer dashfragment = new Dashboard_dealer();
        Bundle bundle = getIntent().getExtras();
        dashfragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.viewframe11, dashfragment, "dash").commit();
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int position) {
        actionBar.setSelectedNavigationItem(position);
    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Dashboard_dealer dashfragment = new Dashboard_dealer();
        Bundle bundle = getIntent().getExtras();
        dashfragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.viewframe11, dashfragment, "dash").commit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        // TODO Auto-generated method stub
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        //	mDrawerLayout.closeDrawer(Gravity.RIGHT);
        //	 viewPager.setCurrentItem(tab.getPosition());
        tag = tab.getTag();

    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        // TODO Auto-generated method stub

    }

    private void getResponseFromServer(String district,String user_id) {
        Controller controller = new Controller();
        controller.start(this,district,user_id);
    }

}

