package com.example.salesforce.Web;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;


import com.example.salesforce.Models.AddAttandancecls;
import com.example.salesforce.Models.AddVisitcls;
import com.example.salesforce.Models.Add_TA_Cls;
import com.example.salesforce.Models.Logincls;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;


public class Webservicerequest {

    public String URL = "http://unnatisales.akshapp.com/mobb/service.asmx";
    public static String SyncURL = "http://unnatisales.akshapp.com/mobb/";



    public String MobileWebservice(String Methodname, ArrayList<String> propertyArray) {
        String NAMESPACE = "http://aksha/app/";
        String SOAPAction = NAMESPACE + Methodname;
        SoapObject request = new SoapObject(NAMESPACE, Methodname);
        int icount = 0;
        HttpTransportSE androidhttptransport = new HttpTransportSE(URL, 9999999);
        try {
            for (icount = 0; icount < propertyArray.size(); icount += 2) {

                request.addProperty(propertyArray.get(icount), propertyArray.get(icount + 1));
            }
            request.addProperty("Connection", "Close");
        } catch (Exception e) {
            e.printStackTrace();
            request.addProperty(propertyArray.get(icount), "");
            request.addProperty("Connection", "Close");
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        String resultstring = null;

        try {
            androidhttptransport.debug = true;
            androidhttptransport.call(SOAPAction, envelope);
            Log.d("HTTP REQUEST ", androidhttptransport.requestDump);
            Log.d("HTTP RESPONSE", androidhttptransport.responseDump);
            Object results = (Object) envelope.getResponse();
            resultstring = results.toString();//{"tcl_id":"O5QXCx9uBwslRIPFdwxihA==","name":"jFY5D2XU14augUNz6EOpiSG0OzBzTcDVKpqsslWRqL4="

        } catch (Exception e) {

            e.printStackTrace();

        }


        return resultstring;
    }



    public String MobileWebservice(Context context, String Methodname, Logincls obj) {

        String NAMESPACE = "http://aksha/app/";
        String SOAPAction = NAMESPACE + Methodname;
        String METHOD_NAME = Methodname;

        SoapObject request = new SoapObject(NAMESPACE, Methodname);

        PropertyInfo pn = new PropertyInfo();
        pn.setName("lgn");
        pn.setValue(obj);
        pn.setType(obj.getClass());
        request.addProperty(pn);

        HttpTransportSE androidhttptransport = new HttpTransportSE(URL, 9999999);

        ArrayList<HeaderProperty> h = new ArrayList<HeaderProperty>();
        h.add(new HeaderProperty("Content-Type", "text/xml;charset=utf-8"));
        h.add(new HeaderProperty("SOAPAction", SOAPAction));


        System.setProperty("http.keepAlive", "false");
        try {
            androidhttptransport.getServiceConnection().setRequestProperty("Connection", "close");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.encodingStyle = SoapSerializationEnvelope.ENV;
        envelope.setOutputSoapObject(request);
        String resultstring = null;

        try {
            androidhttptransport.debug = true;
            androidhttptransport.call(SOAPAction, envelope, h);
            Log.d("HTTP REQUEST ", androidhttptransport.requestDump);
            Log.d("HTTP RESPONSE", androidhttptransport.responseDump);
            Object results = (Object) envelope.getResponse();
            androidhttptransport.reset();
            resultstring = results.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            androidhttptransport.getServiceConnection().disconnect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                Toast.makeText(context, "Connection Failed", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return resultstring;
    }



    public String Add_retailer_Visit(Context context, String Methodname, List<AddVisitcls> cls) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        mWakeLock.acquire();


        String NAMESPACE = "http://aksha/app/";
        String SOAPAction = NAMESPACE + Methodname;
        String METHOD_NAME = Methodname;

        SoapObject request = new SoapObject(NAMESPACE, Methodname);

        SoapObject logs = new SoapObject(NAMESPACE, "retailers");
        for (AddVisitcls frm : cls) {
            PropertyInfo pn = new PropertyInfo();
            pn.setName("Createretailer");
            pn.setValue(frm);
            pn.setType(frm.getClass());
            logs.addProperty(pn);
        }

        request.addSoapObject(logs);

        HttpTransportSE androidhttptransport = new HttpTransportSE(URL, 9999999);

        ArrayList<HeaderProperty> h = new ArrayList<HeaderProperty>();
        h.add(new HeaderProperty("Content-Type", "text/xml;charset=utf-8"));
        h.add(new HeaderProperty("SOAPAction", SOAPAction));

        System.setProperty("http.keepAlive", "false");
        try {
            androidhttptransport.getServiceConnection().setRequestProperty("Connection", "close");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.encodingStyle = SoapSerializationEnvelope.ENV;
        envelope.setOutputSoapObject(request);
        envelope.addMapping(NAMESPACE, "Createretailer", new AddVisitcls().getClass());
        String resultstring = null;


        try {
            androidhttptransport.debug = true;
            androidhttptransport.call(SOAPAction, envelope, h);
            Log.d("HTTP REQUEST ", androidhttptransport.requestDump);
            Log.d("HTTP RESPONSE", androidhttptransport.responseDump);
            Object results = (Object) envelope.getResponse();
            androidhttptransport.reset();
            resultstring = results.toString();
        } catch (Exception e) {//
            e.printStackTrace();
        }
//        System.gc();

        try {
            androidhttptransport.getServiceConnection().disconnect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                Toast.makeText(context, "Connection Failed", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        mWakeLock.release();
        return resultstring;

    }





    public String Add_Attandance(Context context, String Methodname, List<AddAttandancecls> cls) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        mWakeLock.acquire();


        String NAMESPACE = "http://aksha/app/";
        String SOAPAction = NAMESPACE + Methodname;
        String METHOD_NAME = Methodname;

        SoapObject request = new SoapObject(NAMESPACE, Methodname);

        SoapObject logs = new SoapObject(NAMESPACE, "attendance");
        for (AddAttandancecls frm : cls) {
            PropertyInfo pn = new PropertyInfo();
            pn.setName("Createattendance");
            pn.setValue(frm);
            pn.setType(frm.getClass());
            logs.addProperty(pn);
        }

        request.addSoapObject(logs);

        HttpTransportSE androidhttptransport = new HttpTransportSE(URL, 9999999);

        ArrayList<HeaderProperty> h = new ArrayList<HeaderProperty>();
        h.add(new HeaderProperty("Content-Type", "text/xml;charset=utf-8"));
        h.add(new HeaderProperty("SOAPAction", SOAPAction));

        System.setProperty("http.keepAlive", "false");
        try {
            androidhttptransport.getServiceConnection().setRequestProperty("Createattendance", "close");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.encodingStyle = SoapSerializationEnvelope.ENV;
        envelope.setOutputSoapObject(request);
        envelope.addMapping(NAMESPACE, "Createattendance", new AddVisitcls().getClass());
        String resultstring = null;


        try {
            androidhttptransport.debug = true;
            androidhttptransport.call(SOAPAction, envelope, h);
            Log.d("HTTP REQUEST ", androidhttptransport.requestDump);
            Log.d("HTTP RESPONSE", androidhttptransport.responseDump);
            Object results = (Object) envelope.getResponse();
            androidhttptransport.reset();
            resultstring = results.toString();
        } catch (Exception e) {//
            e.printStackTrace();
        }
//        System.gc();

        try {
            androidhttptransport.getServiceConnection().disconnect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                Toast.makeText(context, "Connection Failed", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        mWakeLock.release();
        return resultstring;
    }


    /*public String Get_ta_price(Context context, String Methodname, List<Ta_Price_cls> cls) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        mWakeLock.acquire();


        String NAMESPACE = "http://aksha/app/";
        String SOAPAction = NAMESPACE + Methodname;
        String METHOD_NAME = Methodname;

        SoapObject request = new SoapObject(NAMESPACE, Methodname);

        SoapObject logs = new SoapObject(NAMESPACE, "CreateTA");
        for (Ta_Price_cls frm : cls) {
            PropertyInfo pn = new PropertyInfo();
            pn.setName("CreateDailyTA");
            pn.setValue(frm);
            pn.setType(frm.getClass());
            logs.addProperty(pn);
        }

        request.addSoapObject(logs);

        HttpTransportSE androidhttptransport = new HttpTransportSE(URL, 9999999);

        ArrayList<HeaderProperty> h = new ArrayList<HeaderProperty>();
        h.add(new HeaderProperty("Content-Type", "text/xml;charset=utf-8"));
        h.add(new HeaderProperty("SOAPAction", SOAPAction));

        System.setProperty("http.keepAlive", "false");
        try {
            androidhttptransport.getServiceConnection().setRequestProperty("CreateDailyTA", "close");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.encodingStyle = SoapSerializationEnvelope.ENV;
        envelope.setOutputSoapObject(request);
        envelope.addMapping(NAMESPACE, "Createattendance", new AddVisitcls().getClass());
        String resultstring = null;


        try {
            androidhttptransport.debug = true;
            androidhttptransport.call(SOAPAction, envelope, h);
            Log.d("HTTP REQUEST ", androidhttptransport.requestDump);
            Log.d("HTTP RESPONSE", androidhttptransport.responseDump);
            Object results = (Object) envelope.getResponse();
            androidhttptransport.reset();
            resultstring = results.toString();
        } catch (Exception e) {//
            e.printStackTrace();
        }
//        System.gc();

        try {
            androidhttptransport.getServiceConnection().disconnect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                Toast.makeText(context, "Connection Failed", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        mWakeLock.release();
        return resultstring;
    }*/


    public String Add_TA(Context context, String Methodname, List<Add_TA_Cls> cls) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        mWakeLock.acquire();


        String NAMESPACE = "http://aksha/app/";
        String SOAPAction = NAMESPACE + Methodname;
        String METHOD_NAME = Methodname;

        SoapObject request = new SoapObject(NAMESPACE, Methodname);

        SoapObject logs = new SoapObject(NAMESPACE, "CreateTA");
        for (Add_TA_Cls frm : cls) {
            PropertyInfo pn = new PropertyInfo();
            pn.setName("CreateDailyTA");
            pn.setValue(frm);
            pn.setType(frm.getClass());
            logs.addProperty(pn);
        }

        request.addSoapObject(logs);

        HttpTransportSE androidhttptransport = new HttpTransportSE(URL, 9999999);

        ArrayList<HeaderProperty> h = new ArrayList<HeaderProperty>();
        h.add(new HeaderProperty("Content-Type", "text/xml;charset=utf-8"));
        h.add(new HeaderProperty("SOAPAction", SOAPAction));

        System.setProperty("http.keepAlive", "false");
        try {
            androidhttptransport.getServiceConnection().setRequestProperty("CreateDailyTA", "close");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.encodingStyle = SoapSerializationEnvelope.ENV;
        envelope.setOutputSoapObject(request);
        envelope.addMapping(NAMESPACE, "Createattendance", new AddVisitcls().getClass());
        String resultstring = null;


        try {
            androidhttptransport.debug = true;
            androidhttptransport.call(SOAPAction, envelope, h);
            Log.d("HTTP REQUEST ", androidhttptransport.requestDump);
            Log.d("HTTP RESPONSE", androidhttptransport.responseDump);
            Object results = (Object) envelope.getResponse();
            androidhttptransport.reset();
            resultstring = results.toString();
        } catch (Exception e) {//
            e.printStackTrace();
        }
//        System.gc();

        try {
            androidhttptransport.getServiceConnection().disconnect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                Toast.makeText(context, "Connection Failed", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        mWakeLock.release();
        return resultstring;
    }



    public String Encrypt(String text)
            throws Exception {
        String iv = "!QAZ2WSX#EDC4RFV";
        String key = "5TGB&YHN7UJM(IK<";
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(results);
    }


    public String Decrypt(String text) throws Exception {
        String iv = "!QAZ2WSX#EDC4RFV";
        String key = "5TGB&YHN7UJM(IK<";
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);


        BASE64Decoder decoder = new BASE64Decoder();
        byte[] results = cipher.doFinal(decoder.decodeBuffer(text));
        String datas = new String(results, "UTF-8");
        char[] schars = datas.toCharArray();
        StringBuilder news = new StringBuilder();
        int ccount = 0;
        for (int count = 0; count < schars.length; count++) {
            int val = CharToASCII(schars[count]);

            if (val != 0) {
                if (ccount > 1) {
                    ccount = 0;
                    news.append(schars[count]);
                } else {
                    news.append(schars[count]);
                    ccount = 0;
                }

            } else {
                ccount++;

            }
        }
        return news.toString();
    }

    public int CharToASCII(char character) {
        return (int) character;
    }

    public ArrayList<String> JSONEncoding(String result, ArrayList<String> listval) {
        ArrayList<String> al = new ArrayList<String>();

        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);

                for (int icount = 0; icount < listval.size(); icount++) {
                    String servicename = "";
                    if (listval.get(icount).toLowerCase().contains("date")) {
                        if (!listval.get(icount).toLowerCase().contains("status")) {
                            String[] val = Decrypt(row.getString(listval.get(icount))).split(" ");
                            servicename = val[0];
                        } else {
                            servicename = Decrypt(row.getString(listval.get(icount)));
                        }
                    } else {
                        servicename = row.getString(listval.get(icount));
                    }


                    al.add(servicename);


                }
            }
        } catch (Exception e) {
            String msg = e.getMessage().toString();
        }
        return al;
    }


    public String Image_upload(Bitmap bmp, String Methodname, String imgname)
    {

        String NAMESPACE="http://aksha/app/";
        String SOAPAction=NAMESPACE+Methodname;
        MarshalBase64 marshal = new MarshalBase64();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
        byte[] raw = out.toByteArray();
        SoapObject request = new SoapObject(NAMESPACE,Methodname);
        request.addProperty("fileName", imgname);
        request.addProperty("bytearray", raw);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        marshal.register(envelope);
        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        try
        {
            httpTransport.call(SOAPAction, envelope);
            Object response = envelope.getResponse();
            return response.toString();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return "0";
    }

}
