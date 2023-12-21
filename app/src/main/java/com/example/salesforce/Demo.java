package com.example.salesforce;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Demo  extends Activity {
    private static final String NAMESPACE1 = "https://api.authorize.net/soap/v1/";
    private static final String URL1 ="https://apitest.authorize.net/soap/v1/Service.asmx?wsdl";
    private static final String SOAP_ACTION1 = "https://api.authorize.net/soap/v1/AuthenticateTest";
    private static final String METHOD_NAME1 = "AuthenticateTest";


    private static final String NAMESPACE = "http://aksha/app/";
    private static final String URL ="http://unnatisales.akshapp.com/mobb/service.asmx?wsdl";
    private static final String SOAP_ACTION = "http://aksha/app/CHANGEPASSOTP";
    private static final String METHOD_NAME = "CHANGEPASSOTP";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                    request.addProperty("userid","7007196660");
                    request.addProperty("oldpass","123456");
                    request.addProperty("newpass","12345");
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.implicitTypes = true;
                    envelope.setAddAdornments(false);
                    envelope.encodingStyle = SoapSerializationEnvelope.ENV;
                    envelope.setOutputSoapObject(request);
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                    try {
                        androidHttpTransport.call(SOAP_ACTION, envelope);

                        SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;


                        System.out.println("Response::"+resultsRequestSOAP.toString());



                    } catch (Exception e) {
                        System.out.println("Error"+e);
                    }                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

}
