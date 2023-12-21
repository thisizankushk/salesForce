package com.example.salesforce;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.salesforce.Database.Databaseutill;
import com.example.salesforce.Database.GetData;
import com.example.salesforce.GPS.GPSTracker;
import com.example.salesforce.Web.ConnectionDetector;
import com.example.salesforce.Web.Webservicerequest;
import com.example.salesforce.snackbar.Snackbar;
import com.example.salesforce.snackbar.SnackbarManager;
import com.example.salesforce.snackbar.enums.SnackbarType;
import com.example.salesforce.snackbar.listeners.ActionClickListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import dmax.dialog.SpotsDialog;


public class LoginActivity extends Activity {



//    select * from retailervisit order by cast(ID as int) desc LIMIT 3

    Button login;
    EditText user, pass;
    TextView forgot_password,new_user;
    String versionName;
//    Databaseutill db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        user = (EditText) findViewById(R.id.editTextPhone);
        pass = (EditText) findViewById(R.id.editTextNumberPassword);
        login = (Button) findViewById(R.id.button);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        new_user = (TextView) findViewById(R.id.new_user);

        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(),0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Databaseutill db=Databaseutill.getDBAdapterInstance(getApplicationContext());
        GetData get=new GetData(db, getApplicationContext());
        try {
            String login_id=get.getLoginid().get(0);
            String pass1=get.getPassword().get(0);
            user.setText(login_id);
            user.setTag(login_id);
            user.setEnabled(false);
            pass.setText(pass1);
            pass.setTag(pass1);
            pass.setEnabled(false);


        } catch (Exception er) {
            er.printStackTrace();
        }

forgot_password.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(),ForgotPassword.class);
        startActivity(intent);
    }
});

        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
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
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (String.valueOf(user.getText()).length() != 0
                        && String.valueOf(pass.getText()).length() != 0) {

                    Databaseutill db = Databaseutill.getDBAdapterInstance(getApplicationContext());
                    if(db.tb_exist("mas_emp")){
                        new AsyncLoginLocal().execute(
                                String.valueOf(user.getTag()),
                                String.valueOf(pass.getTag()));
                    } else {
                        ConnectionDetector cd = new ConnectionDetector(
                                getApplicationContext());
                        if (!cd.isConnectingToInternet()) {
                            SnackbarManager.show(
                                    Snackbar.with(LoginActivity.this)
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
                            new AsyncLogin().execute(
                                    String.valueOf(user.getText()),
                                    String.valueOf(pass.getText()));
                        }
                    }
                }

                else {
                    SnackbarManager.show(
                            Snackbar.with(LoginActivity.this)
                                    .type(SnackbarType.MULTI_LINE)
                                    .text("Please enter username and password")
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
    }


    class AsyncLoginLocal extends AsyncTask<String, Void, ArrayList<String>> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {

            ArrayList<String> pass = new GetData(
                    Databaseutill.getDBAdapterInstance(getApplicationContext()),
                    getApplicationContext()).getEmpid();

            return pass;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            // TODO Auto-generated method stub
            try {
                super.onPostExecute(result);
                if (result.size() > 0) {
                    String username=String.valueOf(result.get(1));
                    String password = String.valueOf(result.get(5));

                    if (String.valueOf(pass.getText()).trim().equals(password)
                            && String.valueOf(user.getText()).trim()
                            .equals(username))
                    {
                        Webservicerequest wsc = new Webservicerequest();

                        Intent intenti = new Intent(getApplicationContext(),MainActivity.class);
                        intenti.putExtra("empid", result.get(0));
                        intenti.putExtra("districtId",result.get(21));
                        startActivity(intenti);



                        finish();

                    } else {

                        SnackbarManager.show(
                                Snackbar.with(LoginActivity.this)
                                        .type(SnackbarType.MULTI_LINE)
                                        .text("Enter a Valid Username/Password")
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

                    File fdb = getDatabasePath("/data/data/"
                            + LoginActivity.this.getApplicationContext()
                            .getPackageName()
                            + "/databases/DBName.sqlite");
                    fdb.delete();
/*					AlertDialogManager alert = new AlertDialogManager();

					alert.showAlertDialog(LoginActivity.this, "Login",
							"Check internet connection", false);
*/
                }

            } catch (Exception er) {
                er.printStackTrace();
            }

            pd.dismiss();
        }

    }


    public void deleteRecursive(File fileDir)
    {
        if(fileDir.isDirectory())

            for (File child: fileDir.listFiles())
                deleteRecursive(child);

        fileDir.delete();
    }

    class AsyncLogin extends AsyncTask<String, Void, ArrayList<String>> {

        Databaseutill db;
        String selectionarg = "EMP_ID,LOGIN_ID,PASSWORD,districtId";
        //ProgressDialog pb;[{"EMP_ID":"zhxD0qoCbno954YmfCUA+Q==","LOGIN_ID":"fmpMzBUf0mf1YiVcjZ7nGQ==","PASSWORD":"Dn3Nunt74N6dEQY53WTF5rQAtoxNKf0iGb5ZQHJXNks=","districtId":"AsRqC9r0puFWVYkVE4egJg=="}]
        AlertDialog pb;
        GetData get;
        String resultdata;
        GPSTracker gp;
        String Lng;
        String Lat;
        String urlstr;
        ConnectionDetector cd = new ConnectionDetector(LoginActivity.this);

        @Override
        public void onPreExecute() {

            try {

                db = Databaseutill
                        .getDBAdapterInstance(getApplicationContext());
                get = new GetData(db, getApplicationContext());

            pb = new SpotsDialog(LoginActivity.this);
            pb.show();
            gp=new GPSTracker(LoginActivity.this);
            Lat=String.valueOf(gp.getLatitude());
            Lng=String.valueOf(gp.getLongitude());

                File fdb = getDatabasePath("/data/data/"
                        + getApplicationContext().getPackageName()
                        + "/databases");
                fdb.delete();

                deleteRecursive(fdb);


            } catch (Exception er) {
                er.printStackTrace();
            }
        }

        @Override
        public ArrayList<String> doInBackground(String... params) {
            // TODO Auto-generated method stub

            ArrayList<String> returnval = new ArrayList<String>();
            try {
//                if (cd.isConnectingToInternet()) {
                Webservicerequest wsc = new Webservicerequest();
                resultdata = get.Logincls1(params[0],wsc.Encrypt(params[1]), Lat, Lng,versionName);
                if (resultdata == null) {
                    return null;
                }
                ArrayList<String> listvalue = new ArrayList<String>();
                if (resultdata != null) {
                    String[] colval = selectionarg.split(",");

                    for (int strCount = 0; strCount < colval.length; strCount++) {
                        listvalue.add(colval[strCount]);
                    }

                    returnval = wsc.JSONEncoding(resultdata, listvalue);
                    if (returnval.size() == 0) {
                        return returnval;
                    }
                    //	returnval.add(params[0]);//userid means loginid
                    //	returnval.add(params[1]);//password

                    if (returnval.size() < 4) {
                        return null;
                    }
                    // ////////////////////////////////////////////////synk///////////////////

                    try{
                        File destDir = new File("/data/data/"
                                + LoginActivity.this.getPackageName()
                                + "/databases");
                        if(!destDir.exists()){
                            destDir.mkdir();}
                        File destDir1 = new File("/data/data/"
                                + LoginActivity.this.getPackageName()
                                + "/databases/"+"DBNameS.sqlite");
                        if(!destDir1.exists()){
                            destDir1.createNewFile();
                        }


                        Webservicerequest localWebservicerequest = new Webservicerequest();
                        ArrayList<String> localArrayList1 = new ArrayList<String>();
                        localArrayList1.add("empid");
                        localArrayList1.add((returnval.get(0)).trim());
                        String str2 = localWebservicerequest.MobileWebservice("sync", localArrayList1);


                        File fdb_result=getApplicationContext().getDatabasePath( "/data/data/"
                                + LoginActivity.this.getPackageName()
                                + "/databases/");
                        fdb_result.mkdir();
                        File fdb=	getApplicationContext().getDatabasePath( "/data/data/"
                                + LoginActivity.this.getPackageName()
                                + "/databases/DBNameG.zip");
                        if(fdb.exists())
                        {
                            fdb.delete();
                        }
                        fdb.createNewFile();
                        urlstr =Webservicerequest.SyncURL + str2;
//							String urlstr_temp=localWebservicerequest.URL;
//							urlstr=urlstr_temp.substring(0,urlstr_temp.lastIndexOf("/"))+("/")+str2;

                        //String  urlstr="http://savannah.akshapp.com/mobapp/"+str2;
                        // String  urlstr="http://savannah.akshapp.com/mobappt/"+str2;
                        int count;
                        URL url = new URL(urlstr);
                        HttpURLConnection conection = (HttpURLConnection)url.openConnection();

                        conection.connect();
                        conection.setInstanceFollowRedirects(true);

                        int lenghtOfFile = conection.getContentLength();

                        InputStream input = new BufferedInputStream(conection.getInputStream(), 8192);
                        FileOutputStream fos=new FileOutputStream(fdb);

                        byte data[] = new byte[1024];
                        long total = 0;
                        while ((count = input.read(data)) != -1) {
                            total += count;

                            fos.write(data, 0, count);
                        }

                        fos.flush();

                        fos.close();
                        input.close();
                        ArrayList<String> localArrayList_deleted = new ArrayList<String>();
                        localArrayList_deleted.add("fname");
                        localArrayList_deleted.add(str2);
                        unzipFile( "/data/data/"
                                + LoginActivity.this.getPackageName()
                                + "/databases/DBNameG.zip","/data/data/"
                                + LoginActivity.this.getPackageName()

                                + "/databases/");

                    }catch(Exception e){
                        e.printStackTrace();
                        return null;
                    }
                }


//                }


            } catch (Exception ex) {

                ex.printStackTrace();
                //		return null;
            }
            return returnval;
        }

        @Override
        public void onPostExecute(ArrayList<String> result) {
            if (result != null && result.size() > 0) {
                try {
                    if (result.size() >= 4) {
                            Intent intenti = new Intent(
                                    getApplicationContext(), MainActivity.class);
                            intenti.putExtra("empid", result.get(0));
                            intenti.putExtra("districtId",result.get(3));
                            startActivity(intenti);

                            finish();
                    } else {

                        SnackbarManager.show(
                                Snackbar.with(LoginActivity.this)
                                        .type(SnackbarType.MULTI_LINE)
                                        .text("Please enter a valid username and password")
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


                } catch (Exception er) {
                    er.printStackTrace();
                }
            } else {
                SnackbarManager.show(
                        Snackbar.with(LoginActivity.this) // context
                                .type(SnackbarType.MULTI_LINE)
                                .text("Please enter a valid username and password")
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

            pb.dismiss();
        }

    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {

            out.write(buffer, 0, read);
        }
    }

    public void unzipFile(String filePath, String extpath) {

        FileInputStream fis = null;
        ZipInputStream zipIs = null;
        ZipEntry zEntry = null;
        try {
            fis = new FileInputStream(filePath);
            zipIs = new ZipInputStream(new BufferedInputStream(fis));

            while ((zEntry = zipIs.getNextEntry()) != null) {
                try {
                    byte[] tmp = new byte[4 * 1024];
                    FileOutputStream fos = null;
                    String opFilePath = extpath + zEntry.getName();

                    File destDir = new File("/data/data/"
                            + LoginActivity.this.getPackageName() + "/databases/files");
                    destDir.mkdir();
                    File destDir1 = new File(opFilePath);
                    destDir1.createNewFile();
                    System.out.println("Extracting file to " + opFilePath);
                    fos = new FileOutputStream(destDir1);
                    int size = 0;
                    while ((size = zipIs.read(tmp)) != -1) {
                        fos.write(tmp, 0, size);
                    }
                    fos.flush();
                    fos.close();

                    // move file
                    InputStream in = null;
                    OutputStream out = null;

                    in = new FileInputStream(opFilePath);
                    out = new FileOutputStream("/data/data/"
                            + LoginActivity.this.getPackageName() + "/databases/"
                            + "DBName.sqlite");
                    copyFile(in, out);
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                    File fd = new File(opFilePath);
                    fd.delete();
                    File fd1 = new File(filePath);
                    fd1.delete();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            zipIs.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}