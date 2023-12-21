package com.example.salesforce;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class ForgotPassword  extends AppCompatActivity {
EditText et_mobile,etotp,etnewpassword;
Button btngetotp,btnsubmit;
LinearLayout hidelayout;
String str_otp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        et_mobile = (EditText)  findViewById(R.id.et_mobile);
        etotp = (EditText)  findViewById(R.id.etotp);
        etnewpassword = (EditText)  findViewById(R.id.etnewpassword);
        btngetotp = (Button) findViewById(R.id.btngetotp);
        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        hidelayout = (LinearLayout) findViewById(R.id.hidelayout);

        btngetotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_mobile.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Enter Mobile Number",Toast.LENGTH_LONG).show();
                    return;
                }else if (et_mobile.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),"Please Enter Vaild Mobile Number",Toast.LENGTH_LONG).show();
                    return;
                }else {
                    hidelayout.setVisibility(View.VISIBLE);
                    new CallWebService().execute();
                }
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etotp.getText().toString().isEmpty() || etotp.getText().toString().length()<4){
                    Toast.makeText(getApplicationContext(),"Please Enter OTP",Toast.LENGTH_LONG).show();
                    return;
                }

                else if (etnewpassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Enter New Password",Toast.LENGTH_LONG).show();
                    return;
                }
                else if (etnewpassword.getText().toString().length()<6){
                    Toast.makeText(getApplicationContext(),"Set Minimum 6 Characters New Password..!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if (!etotp.getText().toString().equalsIgnoreCase(str_otp)){
                    Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_LONG).show();
                    return;
                }
                else if (etotp.getText().toString().equalsIgnoreCase(str_otp)){
                    new CallWebService1().execute();
                }

            }
        });
    }


    class CallWebService extends AsyncTask<String, Void, String> {
        ProgressDialog pd;


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            pd = new ProgressDialog(ForgotPassword.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {


            String result = "";
            String URL ="http://unnatisales.akshapp.com/mobb/service.asmx";
            String NAMESPACE = "http://aksha/app/";
            String METHOD_NAME = "SENDOTP";
            String SOAPAction = NAMESPACE + METHOD_NAME;

            SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setName("userid");
            propertyInfo.setValue(et_mobile.getText().toString());
            propertyInfo.setType(String.class);

            soapObject.addProperty(propertyInfo);

            SoapSerializationEnvelope envelope =  new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setAddAdornments(false);
            envelope.encodingStyle = SoapSerializationEnvelope.ENV;
            envelope.setOutputSoapObject(soapObject);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL);

            try {
                httpTransportSE.call(SOAPAction, envelope);
                SoapPrimitive soapPrimitive = (SoapPrimitive)envelope.getResponse();
                result = soapPrimitive.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
          //  Toast.makeText(getApplicationContext(),"Response"+s,Toast.LENGTH_LONG).show();

            if (s.equalsIgnoreCase("0")){
                Toast.makeText(getApplicationContext(),"Invalid Request",Toast.LENGTH_LONG).show();
            }
            else if (s.length()==4){
                str_otp = ""+s;
                Log.e("str_otp",str_otp);
                Toast.makeText(getApplicationContext(),"You will receive one time password",Toast.LENGTH_LONG).show();
            }
        }

    }
    class CallWebService1 extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            pd = new ProgressDialog(ForgotPassword.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();

        }


        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String URL ="http://unnatisales.akshapp.com/mobb/service.asmx";
            String NAMESPACE = "http://aksha/app/";
            String METHOD_NAME = "ForgotPassword";
            String SOAPAction = NAMESPACE + METHOD_NAME;

            SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setName("mobile");
            propertyInfo.setValue(et_mobile.getText().toString());
            propertyInfo.setType(String.class);

            soapObject.addProperty(propertyInfo);

            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("newpass");
            propertyInfo1.setValue(etnewpassword.getText().toString());
            propertyInfo1.setType(String.class);

            soapObject.addProperty(propertyInfo1);

            PropertyInfo propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("otp");
            propertyInfo2.setValue(etotp.getText().toString());
            propertyInfo2.setType(String.class);

            soapObject.addProperty(propertyInfo2);

            SoapSerializationEnvelope envelope =  new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setAddAdornments(false);
            envelope.encodingStyle = SoapSerializationEnvelope.ENV;
            envelope.setOutputSoapObject(soapObject);

            String resultstring = null;

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL);

            try {
                httpTransportSE.call(SOAPAction, envelope);
                Object results = (Object) envelope.getResponse();
                resultstring = results.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultstring;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
           // Toast.makeText(getApplicationContext(),"Response"+s,Toast.LENGTH_LONG).show();

            if (s.equalsIgnoreCase("0")){
                Toast.makeText(getApplicationContext(),"Invalid Request",Toast.LENGTH_LONG).show();
            }
            else if (s.equalsIgnoreCase("1")){
                Toast.makeText(getApplicationContext(),"Your password is updated ..Login Now",Toast.LENGTH_LONG).show();
                finish();
            }

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
