package com.example.salesforce.Attendance;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.salesforce.CustomAlertDialog;
import com.example.salesforce.Database.Databaseutill;
import com.example.salesforce.Database.GetData;
import com.example.salesforce.GPS.GPSTracker;
import com.example.salesforce.R;
import com.example.salesforce.Web.ConnectionDetector;
import com.example.salesforce.snackbar.Snackbar;
import com.example.salesforce.snackbar.SnackbarManager;
import com.example.salesforce.snackbar.enums.SnackbarType;
import com.example.salesforce.snackbar.listeners.ActionClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dmax.dialog.SpotsDialog;

public class AttendanceSubmussion extends Fragment {
    View view;
    ImageView date_minus,date_plus;
    TextView present,leave,rdate;
    Button submit;
    int i = 1;
    int j = 0;
    String createdate;
    EditText reason;
    String status = "1";
    CustomAlertDialog m1Alert;
    Databaseutill db;
    GetData get;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {

            view = inflater.inflate(R.layout.attendance_layout, container, false);
            present = (TextView)view.findViewById(R.id.textpresent);
            leave = (TextView)view.findViewById(R.id.textleave);
            rdate = (TextView)view.findViewById(R.id.date);
            submit = (Button)view.findViewById(R.id.submit_attandence_button);
            reason = (EditText)view.findViewById(R.id.reasonEditText);

            db = Databaseutill
                    .getDBAdapterInstance(getContext());
            get = new GetData(db, getContext());

            SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String date = df.format(Calendar.getInstance().getTime());
            createdate = date;
            rdate.setText(date);
            Calendar cal = Calendar.getInstance();
            present();

            date_minus = (ImageView)view.findViewById(R.id.date_minus);
            date_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(j==0){
                        SnackbarManager.show(
                                Snackbar.with(getContext())
                                        .type(SnackbarType.MULTI_LINE)
                                        .text("You can not change the past but you can change the future.")
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
                    }else {
                        j--;
                        if(j==0) {
                            date_minus.setImageDrawable(getResources().getDrawable(R.drawable.left_blak_arrow));
                            present();
                        }
                        cal.add(Calendar.DATE, -1);
                        rdate.setText(df.format(new Date(cal.getTimeInMillis())));
                    }
                }
            });

            date_plus = (ImageView)view.findViewById(R.id.date_plus);
            date_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    j++;
                    date_minus.setImageDrawable(getResources().getDrawable(R.drawable.left_arrow_blue));
                    leave();
                    cal.add(Calendar.DATE, +1);
                    rdate.setText(df.format(new Date(cal.getTimeInMillis())));
                }
            });



            present.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(j>0){
                        SnackbarManager.show(
                                Snackbar.with(getContext())
                                        .type(SnackbarType.MULTI_LINE)
                                        .text("You cannot escape the responsibility of tomorrow by evading it today.")
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
                    }else {
                        present();
                    }
                }
            });

            leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    leave();
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectionDetector cd = new ConnectionDetector(
                            getContext());
                    if (status.equals("0") && reason.getText().length() == 0) {
                        SnackbarManager.show(
                                Snackbar.with(getContext())
                                        .type(SnackbarType.MULTI_LINE)
                                        .text("Please write a remark")
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
                    } else if (cd.isConnectingToInternet()) {
                        new Async_Add().execute(createdate, rdate.getText().toString(), reason.getText().toString(), get.getEmpid().get(0), status);
                    } else {
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
                    }
                }
            });
        }catch (Exception e) {
            e.getMessage();
        }
        return view;
    }

    public void present(){
        present.setTextColor(Color.parseColor("#FFFFFF"));
        present.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.greentext_background));
        leave.setTextColor(Color.parseColor("#00517A"));
        leave.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.broun_border));
        status = "1";
    }

    public void leave(){
        leave.setTextColor(Color.parseColor("#FFFFFF"));
        leave.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.red_text_background));
        present.setTextColor(Color.parseColor("#00517A"));
        present.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.broun_border));
        status = "0";
    }




    class Async_Add extends AsyncTask<String, Void, String> {
        AlertDialog pb;
//        Databaseutill db;
//        GetData get;
        String resultdata;
        GPSTracker gp;
        String Lng;
        String Lat;
//        String urlstr;
//        ConnectionDetector cd = new ConnectionDetector(LoginActivity.this);

        @Override
        public void onPreExecute() {

            try {

//                db = Databaseutill
//                        .getDBAdapterInstance(getContext());
//                get = new GetData(db, getContext());
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
                resultdata = get.Attandance_Add(params[0],params[1],params[2],params[3],params[4]);
                String id = get.getLastAttendanceId();
                int i = Integer.parseInt(id)+1;

                DateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
                Date date = (Date) formatter.parse(params[1]);
                SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
                String finalString = newFormat.format(date);

                if(resultdata.equals("1"))
                    get.addAttendance(String.valueOf(i),finalString,params[4]);

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

                    reason.setText("");
                    m1Alert = new CustomAlertDialog(getContext());
                    m1Alert.setIcon(R.drawable.success);
                    m1Alert.setMessage("Your Attendance\nIs Summited Successfully!");
                    m1Alert.setPositveButton("Go Home", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            m1Alert.dismiss();
                        }
                    });
                    m1Alert.show();





//                    SnackbarManager.show(
//                            Snackbar.with(getContext())
//                                    .type(SnackbarType.MULTI_LINE)
//                                    .text("Attendance submitted successfully")
//                                    .textColor(Color.WHITE)
//                                    .color(Color.GRAY)
//                                    .actionLabel("Cancel")
//                                    .actionColor(Color.YELLOW)
//                                    .actionListener(new ActionClickListener() {
//                                        @Override
//                                        public void onActionClicked(Snackbar snackbar) {
//
//                                        }
//                                    })
//                    );
                }else if(result.equals("2")){
                    reason.setText("");

                    m1Alert = new CustomAlertDialog(getContext());
                    m1Alert.setIcon(R.drawable.error_msg);
                    m1Alert.setMessage("Attendance alreay submitted");
                    m1Alert.setPositveButton("Go Home", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            tv_credit_points.setText("0");
//                            et_quantity_sold.setText("");
                            m1Alert.dismiss();
                        }
                    });
                    m1Alert.show();



//                    SnackbarManager.show(
//                            Snackbar.with(getContext())
//                                    .type(SnackbarType.MULTI_LINE)
//                                    .text("Attendance alreay submitted")
//                                    .textColor(Color.WHITE)
//                                    .color(Color.GRAY)
//                                    .actionLabel("Cancel")
//                                    .actionColor(Color.YELLOW)
//                                    .actionListener(new ActionClickListener() {
//                                        @Override
//                                        public void onActionClicked(Snackbar snackbar) {
//
//                                        }
//                                    })
//                    );
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


                    /*SnackbarManager.show(
                            Snackbar.with(getContext())
                                    .type(SnackbarType.MULTI_LINE)
                                    .text("Something went Wrong, Please try again")
                                    .textColor(Color.WHITE)

                                    .color(Color.GRAY)
                                    .actionLabel("Cancel")
                                    .actionColor(Color.YELLOW)

                                    .actionListener(new ActionClickListener() {
                                        @Override
                                        public void onActionClicked(Snackbar snackbar) {

                                        }
                                    })
                    );*/


                }


            } catch (Exception er) {
                er.printStackTrace();
            }
            pb.dismiss();
        }

    }
}
