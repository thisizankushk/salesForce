package com.example.salesforce.TA;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.salesforce.Database.Databaseutill;
import com.example.salesforce.Database.GetData;
import com.example.salesforce.Web.ConnectionDetector;
import com.example.salesforce.Web.Webservicerequest;
import com.example.salesforce.localclass.ScalingUtilities;
import com.example.salesforce.snackbar.SnackbarManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.commonsware.cwac.cam2.FlashMode;
import com.example.salesforce.R;
import com.example.salesforce.adaptor.Myspinner;
import com.example.salesforce.snackbar.Snackbar;
import com.example.salesforce.snackbar.enums.SnackbarType;
import com.example.salesforce.snackbar.listeners.ActionClickListener;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import dmax.dialog.SpotsDialog;

/**
 * Created by agent on 1/14/17.
 */

public class Daily_TA_Frg extends Fragment {
    private View mView;
    private static final int CHOOSER_PERMISSIONS_REQUEST_CODE = 7459;
    ImagePicker easyImage;
    ImageView addImage;

    EditText et_attandance_date, et_place_frm, et_place_visit, et_open_mtr, et_close_mtr, et_place_visit1, et_place_visit2, et_place_visit3, et_place_visit4;
    EditText et_local_convense, et_post_expense, et_print, et_misc, et_Remarks, et_toll_tax, et_fare_taxi;
    TextView txt_total_km, txt_km_done,ta_claim,et_total_da_claim;
    Button btn_ta;
    String file_upload_1 = "", file_upload_2 = "";
    String APP_MOD_DATE1;
    ArrayList<String> str = null;
    int value=0;
    double total_ta_claim=0.00,vehicle_allow=0.00,total_da_claim=0;
    LinearLayout linearLayout;
    Spinner travelto;
    Databaseutill db;
    GetData get;
    Bitmap edited2;

    ImageView open_mtr_image,close_mtr_img;
    ImageView doc_image1,doc_image2,doc_image3,doc_image4,doc_image5,doc_image6,doc_image7;
    LinearLayout ll1,ll2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {


            mView = inflater.inflate(R.layout.daily_ta, container, false);
            init();

            ConnectionDetector cd = new ConnectionDetector(
                    getContext());
            if (!cd.isConnectingToInternet()) {
                SnackbarManager.show(
                        Snackbar.with(getContext())
                                .type(SnackbarType.MULTI_LINE)
                                .text("Please check internet connection")
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
            } else {
                new Get_price().execute();
            }
            str = new ArrayList<String>();
            str.clear();

            db = Databaseutill
                    .getDBAdapterInstance(getContext());
            get = new GetData(db, getContext());

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df121 = new SimpleDateFormat("HH:mm:ss");
            APP_MOD_DATE1 = df121.format(c.getTime());
            function(et_attandance_date);
            function1(et_attandance_date);
            getActivity().setTitle("Daily TA");

            open_mtr_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (arePermissionsGranted(necessaryPermissions)) {
                        easyImage.Companion.with(getActivity())
                                .compress(1024)
                                .maxResultSize(1080, 1920)
                                .start(101);
                    } else {
                        requestPermissionsCompat(necessaryPermissions, CHOOSER_PERMISSIONS_REQUEST_CODE);
                    }
                }
            });

            close_mtr_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (arePermissionsGranted(necessaryPermissions)) {
                        easyImage.Companion.with(getActivity())
                                .compress(1024)
                                .maxResultSize(1080, 1920)
                                .start(102);
                    } else {
                        requestPermissionsCompat(necessaryPermissions, CHOOSER_PERMISSIONS_REQUEST_CODE);
                    }
                }
            });

            doc_image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (arePermissionsGranted(necessaryPermissions)) {
                        easyImage.Companion.with(getActivity())
                                .compress(1024)
                                .maxResultSize(1080, 1920)
                                .start(103);
                    } else {
                        requestPermissionsCompat(necessaryPermissions, CHOOSER_PERMISSIONS_REQUEST_CODE);
                    }
                }
            });

            doc_image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (arePermissionsGranted(necessaryPermissions)) {
                        easyImage.Companion.with(getActivity())
                                .compress(1024)
                                .maxResultSize(1080, 1920)
                                .start(104);
                    } else {
                        requestPermissionsCompat(necessaryPermissions, CHOOSER_PERMISSIONS_REQUEST_CODE);
                    }
                }
            });

            doc_image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (arePermissionsGranted(necessaryPermissions)) {
                        easyImage.Companion.with(getActivity())
                                .compress(1024)
                                .maxResultSize(1080, 1920)
                                .start(105);
                    } else {
                        requestPermissionsCompat(necessaryPermissions, CHOOSER_PERMISSIONS_REQUEST_CODE);
                    }
                }
            });

            doc_image4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (arePermissionsGranted(necessaryPermissions)) {
                        easyImage.Companion.with(getActivity())
                                .compress(1024)
                                .maxResultSize(1080, 1920)
                                .start(106);
                    } else {
                        requestPermissionsCompat(necessaryPermissions, CHOOSER_PERMISSIONS_REQUEST_CODE);
                    }
                }
            });

            doc_image5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (arePermissionsGranted(necessaryPermissions)) {
                        easyImage.Companion.with(getActivity())
                                .compress(1024)
                                .maxResultSize(1080, 1920)
                                .start(107);
                    } else {
                        requestPermissionsCompat(necessaryPermissions, CHOOSER_PERMISSIONS_REQUEST_CODE);
                    }
                }
            });

            doc_image6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (arePermissionsGranted(necessaryPermissions)) {
                        easyImage.Companion.with(getActivity())
                                .compress(1024)
                                .maxResultSize(1080, 1920)
                                .start(108);
                    } else {
                        requestPermissionsCompat(necessaryPermissions, CHOOSER_PERMISSIONS_REQUEST_CODE);
                    }
                }
            });

            doc_image7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (arePermissionsGranted(necessaryPermissions)) {
                        easyImage.Companion.with(getActivity())
                                .compress(1024)
                                .maxResultSize(1080, 1920)
                                .start(109);
                    } else {
                        requestPermissionsCompat(necessaryPermissions, CHOOSER_PERMISSIONS_REQUEST_CODE);
                    }
                }
            });


            btn_ta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (!et_attandance_date.getText().toString().equalsIgnoreCase("")) {
                            if (travelto.getSelectedItemPosition()!=0) {
                                if (!et_place_frm.getText().toString().equalsIgnoreCase("")) {
                                    if (!et_place_visit.getText().toString().equalsIgnoreCase("") || !et_place_visit1.getText().toString().equalsIgnoreCase("") || !et_place_visit2.getText().toString().equalsIgnoreCase("") || !et_place_visit2.getText().toString().equalsIgnoreCase("") || !!et_place_visit3.getText().toString().equalsIgnoreCase("") || !et_place_visit4.getText().toString().equalsIgnoreCase("")) {
                                        if (!et_open_mtr.getText().toString().equalsIgnoreCase("")) {
                                            if (!et_close_mtr.getText().toString().equalsIgnoreCase("")) {
                                                if (file_upload_1.equalsIgnoreCase("") || file_upload_1.equalsIgnoreCase(null)) {
                                                    SnackbarManager.show(
                                                           Snackbar.with(getContext())
                                                                   .type(SnackbarType.MULTI_LINE)
                                                                   .text("Please Upload Opening Meter Image")
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
                                                else  if (file_upload_2.equalsIgnoreCase("") || file_upload_2.equalsIgnoreCase(null)){
                                                    SnackbarManager.show(
                                                       Snackbar.with(getContext())
                                                               .type(SnackbarType.MULTI_LINE)
                                                               .text("Please Upload Closing Meter Image")
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

                                                    String travelledTo = null;

                                                       if (travelto.getSelectedItemPosition() == 1)
                                                           travelledTo = "Local";
                                                       else if (travelto.getSelectedItemPosition() == 2)
                                                           travelledTo = "Local Other Vehicle";
                                                       else if (travelto.getSelectedItemPosition() == 3 )
                                                           travelledTo = "Outsatation with night";
                                                       else if( travelto.getSelectedItemPosition() == 4)
                                                           travelledTo = "Outsatation without night";

                                                       String toll_tax,fare_taxi,mics,local_convence,post_expence,print;

                                                       if(et_toll_tax.getText().toString().equals(""))
                                                           toll_tax="0";
                                                       else
                                                           toll_tax = et_toll_tax.getText().toString();

                                                       if(et_fare_taxi.getText().toString().equals(""))
                                                           fare_taxi="0";
                                                       else
                                                           fare_taxi = et_fare_taxi.getText().toString();

                                                    if(et_misc.getText().toString().equals(""))
                                                        mics="0";
                                                    else
                                                        mics = et_misc.getText().toString();

                                                    if(et_local_convense.getText().toString().equals(""))
                                                        local_convence="0";
                                                    else
                                                        local_convence = et_local_convense.getText().toString();

                                                    if(et_post_expense.getText().toString().equals(""))
                                                        post_expence="0";
                                                    else
                                                        post_expence = et_post_expense.getText().toString();

                                                    if(et_print.getText().toString().equals(""))
                                                        print="0";
                                                    else
                                                        print = et_print.getText().toString();


                                                    new Save().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,et_attandance_date.getText().toString(),
                                                            et_place_frm.getText().toString(),travelledTo,
                                                            et_place_visit.getText().toString(), et_place_visit1.getText().toString(),
                                                            et_place_visit2.getText().toString(),et_place_visit3.getText().toString(),
                                                            et_place_visit4.getText().toString(), et_open_mtr.getText().toString(),
                                                            et_close_mtr.getText().toString(),String.valueOf(total_ta_claim),
                                                            String.valueOf(total_da_claim),et_Remarks.getText().toString(),
                                                            toll_tax,fare_taxi,
                                                            mics,local_convence,
                                                            post_expence,print);

                                                   }
                                               } else {
                                                   SnackbarManager.show(
                                                           Snackbar.with(getContext())
                                                                   .type(SnackbarType.MULTI_LINE)
                                                                   .text("Please enter close km")
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
                                           } else {
                                               SnackbarManager.show(
                                                       Snackbar.with(getContext())
                                                               .type(SnackbarType.MULTI_LINE)
                                                               .text("Please enter open mtr")
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
                                       } else {
                                           SnackbarManager.show(
                                                   Snackbar.with(getContext())
                                                           .type(SnackbarType.MULTI_LINE)
                                                           .text("Please enter atleast one place visited")
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
                                   } else {
                                       SnackbarManager.show(
                                               Snackbar.with(getContext())
                                                       .type(SnackbarType.MULTI_LINE)
                                                       .text("Please enter place from")
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
                                   else {
                                   SnackbarManager.show(
                                           Snackbar.with(getContext())
                                                   .type(SnackbarType.MULTI_LINE)
                                                   .text("Please select Travel To")
                                                   .textColor(Color.WHITE)
                                                   .color(Color.GRAY)
                                                   .actionLabel("Cancel")
                                                   .actionColor(Color.YELLOW)

                                                   .actionListener(new ActionClickListener() {
                                                       @Override
                                                       public void onActionClicked(Snackbar snackbar) {

                                                       }
                                                   })
                                   ); }
                        }

                        else {
                            SnackbarManager.show(
                                    Snackbar.with(getContext())
                                            .type(SnackbarType.MULTI_LINE)
                                            .text("Please select date")
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
                    }catch (Exception ex) {
                        ex.getStackTrace();
                    }
                }
            });





            et_open_mtr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {

                        int Opening = Integer.parseInt(et_open_mtr.getText().toString());
                        int closing = Integer.parseInt(et_close_mtr.getText().toString());
                        value = closing - Opening;
                        if(value>0) {
                            txt_km_done.setText(String.valueOf(value));
                            if(value>=150){
                                et_total_da_claim.setText("DA : 200");
                                total_da_claim = 200;
                            }else {
                                et_total_da_claim.setText("DA : 0");
                                total_da_claim = 0;
                            }
                        }
                        else
                            txt_km_done.setText("0");


                        update_total_ta_claim_value();


                    }
                    catch(Exception ex){
                        ex.getStackTrace();
                        txt_km_done.setText("0");

                    }
                }
            });


            et_close_mtr.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    try {
                        int Opening = Integer.parseInt(et_open_mtr.getText().toString());
                        int closing = Integer.parseInt(et_close_mtr.getText().toString());
                        value = closing - Opening;
                        if(value>0) {
                            txt_km_done.setText(String.valueOf(value));
                            if (value >= 150) {
                                et_total_da_claim.setText("DA : 200");
                                total_da_claim = 200;
                            }
                            else {
                                et_total_da_claim.setText("DA : 0");
                                total_da_claim =0;
                            }
                        }else
                            txt_km_done.setText("0");
                        update_total_ta_claim_value();
                    }
                    catch(Exception ex){
                        ex.getStackTrace();
                        txt_km_done.setText("0");

                    }

                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {

                }
            });



            et_fare_taxi.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    update_total_ta_claim_value();
                }
            });


            et_toll_tax.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    update_total_ta_claim_value();
                }
            });



            et_misc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    update_total_ta_claim_value();
                }
            });

            et_local_convense.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    update_total_ta_claim_value();
                }
            });


            et_post_expense.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    update_total_ta_claim_value();
                }
            });

            et_print.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    update_total_ta_claim_value();
                }
            });



        }
        catch (Exception ex){
            ex.getStackTrace();
        }
        return mView;

    }

    private void update_total_ta_claim_value() {
        try {
            total_ta_claim = 0.00;
            if (!et_toll_tax.getText().toString().trim().isEmpty())
                total_ta_claim = total_ta_claim + Double.parseDouble(et_toll_tax.getText().toString());
            if (!et_post_expense.getText().toString().trim().isEmpty())
                total_ta_claim = total_ta_claim + Double.parseDouble(et_post_expense.getText().toString());
            if (!et_local_convense.getText().toString().trim().isEmpty())
                total_ta_claim = total_ta_claim + Double.parseDouble(et_local_convense.getText().toString());
            if (!et_fare_taxi.getText().toString().trim().isEmpty())
                total_ta_claim = total_ta_claim + Double.parseDouble(et_fare_taxi.getText().toString());
            if (!et_misc.getText().toString().trim().isEmpty())
                total_ta_claim = total_ta_claim + Double.parseDouble(et_misc.getText().toString());
            if (!et_print.getText().toString().trim().isEmpty())
                total_ta_claim = total_ta_claim + Double.parseDouble(et_print.getText().toString());

            if(value>0)
            total_ta_claim += (value * vehicle_allow);

            if(total_ta_claim>=0.00) {
                total_ta_claim= Math.round(total_ta_claim * 1000d) / 1000d;
                ta_claim.setText("Total Ta:" + " " + total_ta_claim);
            }
            else
                ta_claim.setText("Total Ta:" +" 0.00");

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    private static final FlashMode[] FLASH_MODES={
            FlashMode.ALWAYS,
            FlashMode.AUTO
    };



    private void init() {
        try {

            et_attandance_date=(EditText)mView.findViewById(R.id.et_attandance_date);
            travelto=(Spinner)mView.findViewById(R.id.spinner_dailyta_travelto);
            et_place_frm=(EditText)mView.findViewById(R.id.et_place_frm);
//            et_place_fr=(EditText)mView.findViewById(R.id.et_place_fr);
            et_open_mtr=(EditText)mView.findViewById(R.id.et_open_mtr);
            et_total_da_claim=(TextView)mView.findViewById(R.id.et_total_da_claim);
            et_close_mtr=(EditText)mView.findViewById(R.id.et_close_mtr);
            et_local_convense=(EditText)mView.findViewById(R.id.et_local_convense);
            et_post_expense=(EditText)mView.findViewById(R.id.et_post_expense);
            et_print=(EditText)mView.findViewById(R.id.et_print);
            et_misc=(EditText)mView.findViewById(R.id.et_misc);
            et_Remarks=(EditText)mView.findViewById(R.id.et_Remarks);
            ta_claim=(TextView)mView.findViewById(R.id.et_total_ta_claim);
            btn_ta=(Button)mView.findViewById(R.id.btn_ta);
            txt_total_km=(TextView)mView.findViewById(R.id.txt_total_km);
            txt_km_done=(TextView)mView.findViewById(R.id.txt_km_done);
            et_toll_tax=(EditText)mView.findViewById(R.id.et_toll_tax);
            et_fare_taxi=(EditText) mView.findViewById(R.id.et_fare_taxi);
            et_place_visit=(EditText)mView.findViewById(R.id.et_place_visit);
            et_place_visit1=(EditText)mView.findViewById(R.id.et_place_visit1);
            et_place_visit2=(EditText)mView.findViewById(R.id.et_place_visit2);
            et_place_visit3=(EditText)mView.findViewById(R.id.et_place_visit3);
            et_place_visit4=(EditText)mView.findViewById(R.id.et_place_visit4);
            linearLayout = (LinearLayout)mView.findViewById(R.id.liner_view);

            open_mtr_image = (ImageView)mView.findViewById(R.id.open_mtr_image);
            close_mtr_img = (ImageView)mView.findViewById(R.id.close_mtr_img);
            doc_image1 = (ImageView)mView.findViewById(R.id.doc_image1);
            doc_image2 = (ImageView)mView.findViewById(R.id.doc_image2);
            doc_image3 = (ImageView)mView.findViewById(R.id.doc_image3);
            doc_image4 = (ImageView)mView.findViewById(R.id.doc_image4);
            doc_image5 = (ImageView)mView.findViewById(R.id.doc_image5);
            doc_image6 = (ImageView)mView.findViewById(R.id.doc_image6);
            doc_image7 = (ImageView)mView.findViewById(R.id.doc_image7);

            ll1 = (LinearLayout) mView.findViewById(R.id.ll1);
            ll2 = (LinearLayout) mView.findViewById(R.id.ll2);

//            add_dynamic_image_view();

            ArrayList<Myspinner> ms = new ArrayList<Myspinner>();
            ms.add(new Myspinner("Travelled To", "0"));
            ms.add(new Myspinner("Local(Vehicle)", "1"));
            ms.add(new Myspinner("Local(Other Vehicle)", "2"));
            ms.add(new Myspinner("Outstation(With Night)", "3"));
            ms.add(new Myspinner("Outstation(Without Night)", "4"));

            ArrayAdapter<Myspinner> arr_adap = new ArrayAdapter<>(getActivity(),R.layout.spinner_drop_down_travelto, ms);
            travelto.setAdapter(arr_adap);

        } catch (Exception er) {
            er.printStackTrace();
        }
    }


    class Save extends AsyncTask<String, Void, String> {

        AlertDialog pd;
        @Override
        public void onPreExecute() {

            try {
                pd = new SpotsDialog(getActivity());
                pd.show();

            } catch (Exception er) {
                er.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
// TODO Auto-generated method stub
            String resultdata="";


            String[] doc = {"", "", "", "","","",""};


            for(int i=0;i<str.size();i++)
            {
                doc[i] = str.get(i);
            }


            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


            resultdata = get.Add_TA(get.getEmpid().get(0),params[0],date,params[1],params[2],params[8],params[9],
                    params[13],params[14],params[15],params[16],params[17],params[18],params[12],params[3],
                    params[4],params[5],params[6],params[11],params[10],file_upload_1,file_upload_2,doc[0],doc[1],
                    doc[2],doc[3],doc[4],doc[5],doc[6]);

            return resultdata;
        }

        @Override
        protected void onPostExecute(String result) {
// TODO Auto-generated method stub
//            super.onPostExecute(result);
//            pd.dismiss();

            try {
                if(result.equalsIgnoreCase("1")  ) {
                    SnackbarManager.show(
                            Snackbar.with(getContext())
                                    .type(SnackbarType.MULTI_LINE)
                                    .text("TA successfully ")
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
                    et_attandance_date.setText("");
                    //   et_daily_da.setText("");
                    et_place_frm.setText("");
                    et_place_visit.setText("");
                    et_open_mtr.setText("");
                    et_close_mtr.setText("");
                    et_fare_taxi.setText("");
                    et_toll_tax.setText("");
                    // et_petrol_pur.setText("");
                    et_local_convense.setText("");
                    //et_hotel.setText("");
                    travelto.setSelection(0);
                    ta_claim.setText("");
                    et_post_expense.setText("");
                    et_print.setText("");
                    et_misc.setText("");
                    et_Remarks.setText("");
                    et_place_visit1.setText("");
                    et_place_visit2.setText("");
                    et_place_visit3.setText("");
                    et_place_visit4.setText("");
                    str.clear();
                    et_total_da_claim.setText("DA : 0");
                    ll1.setVisibility(View.GONE);
                    ll2.setVisibility(View.GONE);
                    open_mtr_image.setImageResource(R.drawable.open_meter);
                    close_mtr_img.setImageResource(R.drawable.closed_meter);
                    doc_image1.setImageResource(R.drawable.other_document);
                    str.clear();
                    file_upload_1="";
                    file_upload_2="";
                    value=0;
                    str.clear();
                    update_total_ta_claim_value();

                }
                else if(result.equalsIgnoreCase("2")){
                    SnackbarManager.show(
                            Snackbar.with(getContext())
                                    .type(SnackbarType.MULTI_LINE)
                                    .text("Ta already submitted on this date")
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
//                    WebUtils.showSnackbar(getActivity(),"Ta already submitted on this date");
                }

                else
                {
                    SnackbarManager.show(
                            Snackbar.with(getContext())
                                    .type(SnackbarType.MULTI_LINE)
                                    .text("Data Not Found ")
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
//                    WebUtils.showSnackbar(getActivity(),"Data Not Found  ");
                }
                pd.dismiss();
            }catch(Exception er){
                er.printStackTrace();

            }

        }

    }


    class Get_price extends AsyncTask<String, Void, String> {

        AlertDialog pd;
        @Override
        public void onPreExecute() {

            try {
                pd = new SpotsDialog(getActivity());
                pd.show();

            } catch (Exception er) {
                er.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
// TODO Auto-generated method stub
//            String resultdata="";
            ArrayList<String> inputlist = new ArrayList<String>();
            String price="0";
            inputlist.add("emp_id");
            inputlist.add(get.getEmpid().get(0));
            Webservicerequest wsc = new Webservicerequest();

            String resultdata = wsc.MobileWebservice("GetTAPrice", inputlist);
            try {
                JSONArray jsonarray = new JSONArray(resultdata);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    price = wsc.Decrypt(jsonobject.getString("Price"));

//                    String url = jsonobject.getString("url");
                }
            }catch(Exception er){
            er.printStackTrace();
                price="0";

        }

//            resultdata = get.Get_Ta_Price(get.getEmpid().get(0));

            return price;
        }

        @Override
        protected void onPostExecute(String result) {
// TODO Auto-generated method stub
//            super.onPostExecute(result);
//            pd.dismiss();

            try {

                vehicle_allow = Double.parseDouble(result);
                pd.dismiss();
            }catch(Exception er){
                er.printStackTrace();

            }

        }

    }


    class upload_image extends AsyncTask<Integer, Void, String> {

        AlertDialog pd;
        @Override
        public void onPreExecute() {

            try {
                pd = new SpotsDialog(getActivity());
                pd.show();

            } catch (Exception er) {
                er.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Integer... params) {

            Calendar c = Calendar.getInstance();
            String Image = String.valueOf(c.getTimeInMillis());

            Webservicerequest wsc = new Webservicerequest();
//            String resultdata = "1";
            String resultdata = wsc.Image_upload(edited2,"FileUpload_Image",Image);
            if(resultdata.equals("1")){
                switch (params[0]){
                    case 101:
                        file_upload_1 = Image+".jpg";
                        break;
                    case 102:
                        file_upload_2=Image+".jpg";
                        break;
                    case 103:
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                doc_image1.setClickable(false);
                            }
                        });
                        str.add(Image+".jpg");
                        break;
                    case 104:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                doc_image2.setClickable(false);
                            }
                        });
                        str.add(Image+".jpg");
                        break;
                    case 105:
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                doc_image3.setClickable(false);
                            }
                        });
                        str.add(Image+".jpg");
                        break;
                    case 106:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                doc_image4.setClickable(false);
                            }
                        });
                        str.add(Image+".jpg");
                        break;
                    case 107:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                doc_image5.setClickable(false);
                            }
                        });
                        str.add(Image+".jpg");
                        break;
                    case 108:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                doc_image6.setClickable(false);
                            }
                        });
                        str.add(Image+".jpg");
                        break;
                    case 109:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                doc_image7.setClickable(false);
                            }
                        });
                        str.add(Image+".jpg");
                        break;
                }
                if(str.size()==1) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ll1.setVisibility(View.VISIBLE);
                        }
                    });

                }
                if(str.size()==4){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ll2.setVisibility(View.VISIBLE);
                        }
                    });

                }
//                    add_dynamic_image_view();
            }
            return resultdata;
        }

        @Override
        protected void onPostExecute(String result) {
// TODO Auto-generated method stub
//            super.onPostExecute(result);
//            pd.dismiss();

            try {

//                vehicle_allow = Double.parseDouble(result);
                pd.dismiss();
            }catch(Exception er){
                er.printStackTrace();

            }

        }

    }

    private void function(final EditText date) {

        date.setFocusable(false);
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(date.getWindowToken(), 0);

        date.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    Calendar c = Calendar.getInstance();
                    final Integer mYear = c.get(Calendar.YEAR);
                    Integer mMonth = c.get(Calendar.MONTH);
                    Integer mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker arg0,
                                                      int year, int monthOfYear,
                                                      int dayOfMonth) {


                                    try {

                                        date.setText(year + "-"
                                                + (monthOfYear + 1) + "-" + dayOfMonth+" "+ APP_MOD_DATE1);


                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }
                                }
                            }, mYear, mMonth, mDay);
//                    dpd.getDatePicker().setMinDate(new Date().getTime());
//                    dpd.getDatePicker().setMaxDate(new Date().getTime());
                    dpd.show();
                }
                return false;
            }
        });
    }





    private void function1(final EditText date1) {

        date1.setFocusable(false);
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(date1.getWindowToken(), 0);

        date1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    Calendar c = Calendar.getInstance();
                    Integer mYear = c.get(Calendar.YEAR);
                    Integer mMonth = c.get(Calendar.MONTH);
                    Integer mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker arg0,
                                                      int year, int monthOfYear,
                                                      int dayOfMonth ) {

                                    try {

                                        Calendar c = Calendar.getInstance();
                                        String posted_Date = dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
                                        String dateNew = ""+c.getTime().getDate()+"-"+(c.getTime().getMonth()+1)+"-"+c.get(Calendar.YEAR);

                                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                        Date startDate = format.parse(posted_Date);
                                        Date endDate   = format.parse(dateNew);

                                        long duration  = endDate.getTime() - startDate.getTime();
                                        Log.e("Difference",posted_Date+"\n"+dateNew+"\n"+ TimeUnit.MILLISECONDS.toDays(duration));
                                        if(TimeUnit.MILLISECONDS.toDays(duration)<=18  && TimeUnit.MILLISECONDS.toDays(duration)>=0 )
                                        //   if(c.getTime().getDate()-dayOfMonth>=0&&c.getTime().getDate()-dayOfMonth<=2 &&monthOfYear==c.getTime().getMonth()  && String.valueOf(year).equalsIgnoreCase(String.valueOf(c.get(Calendar.YEAR))))
                                        {
                                            SimpleDateFormat df121 = new SimpleDateFormat("HH:mm:ss");
                                            String APP_MOD_DATE1 = df121.format(c.getTime());
                                            date1.setText(year + "-" + (((monthOfYear+1)<10)?"0"+(monthOfYear+1):(monthOfYear + 1) )+ "-" + (((dayOfMonth)<10)?"0"+dayOfMonth:dayOfMonth  ) );
                                            //    Log.e("gdggh","fair"+dayOfMonth+"\n"+c.getTime());
                                        }
                                        else
                                        {
                                            SnackbarManager.show(Snackbar.with(getActivity()).type(SnackbarType.MULTI_LINE).text("TA could not be set on this Date").textColor(Color.WHITE).color(Color.GRAY).actionLabel("Cancel").actionColor(Color.YELLOW).actionListener(new ActionClickListener() {
                                                        @Override
                                                        public void onActionClicked(Snackbar snackbar) {

                                                        }
                                                    })
                                            );
                                            //   Log.e("gdggh","fraud"+c.get(Calendar.YEAR)+"\n"+c.getTime().toString());
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }
                                }
                            }, mYear, mMonth, mDay);
                    dpd.getDatePicker().setMinDate(new Date().getTime()-(5*86400*1000));//day*sec*1000
                    dpd.getDatePicker().setMaxDate(new Date().getTime());//day*sec*1000
                    dpd.show();
                }
                return false;
            }
        });
    }

    public void add_dynamic_image_view(){
        LinearLayout lp = new LinearLayout(getContext());
        lp.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300));
        lp.setOrientation(LinearLayout.HORIZONTAL);
        lp.setWeightSum(3);
        for(int i=1;i<=3;i++) {
            ImageView imgView = new ImageView(getContext());
            imgView.setId(i);
            imgView.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            imgView.setImageResource(R.drawable.attendance);
            lp.addView(imgView);
            imgView.setOnClickListener(new ImageClickLIstener(i));
        }
        linearLayout.addView(lp);
    }

    class ImageClickLIstener implements View.OnClickListener
    {
        int position;
        ImageClickLIstener(int position)
        {
            this.position= position;
        }
        @Override
        public void onClick(View v) {
            int iv_id = v.getId();
            addImage= (ImageView)v;
            Log.e("Position",""+position);
            if(position%3 == 0){
                add_dynamic_image_view();
            }
            String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (arePermissionsGranted(necessaryPermissions)) {
                easyImage.Companion.with(getActivity())
                        .compress(1024)
                        .maxResultSize(1080, 1920)
                        .start();
            } else {
                requestPermissionsCompat(necessaryPermissions, CHOOSER_PERMISSIONS_REQUEST_CODE);
            }
        }
    }


    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String fileUri = easyImage.Companion.getFilePath(data);
            switch(requestCode) {
                case 101:
                    open_mtr_image.setImageURI(Uri.parse(fileUri));
                    break;

                case 102:
                    close_mtr_img.setImageURI(Uri.parse(fileUri));
                    break;

                case 103:
                    doc_image1.setImageURI(Uri.parse(fileUri));
                    break;
                case 104:
                    doc_image2.setImageURI(Uri.parse(fileUri));
                    break;
                case 105:
                    doc_image3.setImageURI(Uri.parse(fileUri));
                    break;
                case 106:
                    doc_image4.setImageURI(Uri.parse(fileUri));
                    break;
                case 107:
                    doc_image5.setImageURI(Uri.parse(fileUri));
                    break;
                case 108:
                    doc_image6.setImageURI(Uri.parse(fileUri));
                    break;
                case 109:
                    doc_image7.setImageURI(Uri.parse(fileUri));
                    break;
            }
            File file = easyImage.Companion.getFile(data);
            String imagePath="1";

            if(file!=null) {
                Uri tempUri = Uri.parse(String.valueOf(file.getAbsolutePath())); //getImageUri(getActivity(), imageBitmap,"11");
                imagePath = tempUri.getPath();//getRealPathFromURI(tempUri);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                Bitmap photo2 = BitmapFactory.decodeFile(imagePath);
                edited2 = ScalingUtilities.createScaledBitmap(photo2, 1024, 816, ScalingUtilities.ScalingLogic.FIT);
                edited2.compress(Bitmap.CompressFormat.JPEG, 100, bos);

                new upload_image().execute(requestCode);
            }
        }
    }


    private boolean arePermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED)
                return false;

        }
        return true;
    }

    private void requestPermissionsCompat(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
    }

}