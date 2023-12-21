package com.example.salesforce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.salesforce.Database.Databaseutill;
import com.example.salesforce.Database.GetData;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;

public class ChangePassword extends AppCompatActivity {

    EditText et_currentpassword,et_newpassword,et_confirmpassword;
    Button btnsubmit;
    String login_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Databaseutill db=Databaseutill.getDBAdapterInstance(getApplicationContext());
        GetData get=new GetData(db, getApplicationContext());
        try {
             login_id=get.getLoginid().get(0);

        } catch (Exception er) {
            er.printStackTrace();
        }

        et_currentpassword = (EditText) findViewById(R.id.et_currentpassword);
        et_newpassword = (EditText) findViewById(R.id.et_newpassword);
        et_confirmpassword = (EditText) findViewById(R.id.et_confirmpassword);

        btnsubmit = (Button) findViewById(R.id.btnsubmit);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_currentpassword.getText().toString().isEmpty() || et_newpassword.getText().toString().isEmpty() || et_confirmpassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please Fill All Fields",Toast.LENGTH_LONG).show();
                    return;
                }
                else if (et_newpassword.getText().toString().length()<6){
                    Toast.makeText(getApplicationContext(),"Set Minimum 6 Characters New Password..!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if (!et_newpassword.getText().toString().equalsIgnoreCase(et_confirmpassword.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Confirm Your Password..!",Toast.LENGTH_LONG).show();
                    return;
                }

                else {
                    new CallWebService().execute();
                }
            }
        });
    }


    class CallWebService extends AsyncTask<String, Void, String> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            pd = new ProgressDialog(ChangePassword.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String URL ="http://unnatisales.akshapp.com/mobb/service.asmx?wsdl";
            String NAMESPACE = "http://aksha/app/";
            String METHOD_NAME = "CHANGEPASSOTP";
            String SOAPAction = NAMESPACE + METHOD_NAME;

            SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setName("userid");
            propertyInfo.setValue(login_id);
            propertyInfo.setType(String.class);

            soapObject.addProperty(propertyInfo);

            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("oldpass");
            propertyInfo1.setValue(et_currentpassword.getText().toString());
            propertyInfo1.setType(String.class);

            soapObject.addProperty(propertyInfo1);

            PropertyInfo propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("newpass");
            propertyInfo2.setValue(et_newpassword.getText().toString());
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
           // Toast.makeText(getApplicationContext(),"API Response"+s,Toast.LENGTH_LONG).show();
            pd.dismiss();
            if (s.equalsIgnoreCase("1")){
                File fdb = getDatabasePath("/data/data/"
                        + getApplicationContext()
                        .getPackageName()
                        + "/databases/DBName.sqlite");

                boolean val = fdb.delete();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else if (s.equalsIgnoreCase("0")){
                Toast.makeText(getApplicationContext(),"Invaild Request Response Code-"+s,Toast.LENGTH_LONG).show();
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
