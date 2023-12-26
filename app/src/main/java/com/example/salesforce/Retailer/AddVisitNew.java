package com.example.salesforce.Retailer;

import static android.content.Context.MODE_PRIVATE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.salesforce.Dashboard_dealer;
import com.example.salesforce.Database.Databaseutill;
import com.example.salesforce.Database.GetData;
import com.example.salesforce.Interfaces.ApiInterface;
import com.example.salesforce.Models.ReasonOfVisit;
import com.example.salesforce.Models.RetailerResponse;
import com.example.salesforce.R;
import com.example.salesforce.retrofit.ApiClient;
import com.example.salesforce.util.CommonFunctions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVisitNew extends Fragment {
    SearchView searchView;
    EditText retailerName, etDistrict;
    CardView imageCard;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    public static int request_code = 1000;
    public static final String IMAGE_DIRECTORY_NAME = "imageuploadtest";
    String mLatitude = "0.0", mLongitude = "0.0";
    String locationString;
    final static int COMPRESSED_RATIO = 70;
    final static int perPixelDataSize = 10;
    ImageView shop_image_view, cameraImage;
    Uri fileUri;
    Bitmap bitmap;
    TextView addressOnImage;
    boolean isImageUploaded = false;
    EditText retailer_mobile;
    Spinner reasonVisitSpinner;
    MaterialButton btnSubmit;
    CommonFunctions commonFunctions;
    List<ReasonOfVisit> reasons;
    List<String> reasonsAsStr;
    boolean isMobileValid = false;
    String reasonForVisit = "", district, storeID = "", mobileString = "", name = "", districtID = "", state;
    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
    ProgressBar progressBar;
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    String[] permissionNew = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frarment_add_visit_new, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkLocationStatus();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        activateSearch();
        if (checkPermissions()) {
            Log.e("AddVisitNew", "Granted");
        }
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(this::getLocation, 3000);
    }

    public void activateSearch() {
        searchView.setIconifiedByDefault(true);
        searchView.setActivated(true);
        searchView.setQueryHint("Search");
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.clearFocus();
    }

    private void init(View view) {
        searchView = view.findViewById(R.id.searchView);
        retailerName = view.findViewById(R.id.retailer_name);
        retailer_mobile = view.findViewById(R.id.retailer_mobile);
        retailer_mobile = view.findViewById(R.id.retailer_mobile);
        reasonVisitSpinner = view.findViewById(R.id.reasonForVisit);
        etDistrict = view.findViewById(R.id.district);
        imageCard = view.findViewById(R.id.image_card_view);
        shop_image_view = view.findViewById(R.id.shop_image_view);
        cameraImage = view.findViewById(R.id.cameraImage);
        addressOnImage = view.findViewById(R.id.addressOnImage);
        btnSubmit = view.findViewById(R.id.btnkisanbill);
        progressBar = view.findViewById(R.id.progressBar);
        commonFunctions = new CommonFunctions(requireActivity());

        reasons = new ArrayList<>();
        reasonsAsStr = new ArrayList<>();
        imageCard.setOnClickListener(view1 -> {
            check_camera();
            takePhotoFromCamera();
        });

        btnSubmit.setOnClickListener(view12 -> submitData());
        retailer_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 10) {
                    progressBar.setVisibility(View.VISIBLE);
                    getRetailer(editable.toString());
                } else {
                    etDistrict.setText("");
                    retailerName.setText("");
                    isMobileValid = false;
                }
            }
        });

        reasonVisitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reasonForVisit = reasons.get(i).getSid();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        new GetVisitReason().execute();
        new Handler().postDelayed(this::setReasonSpinner, 1000);
    }

    private void takePhotoFromCamera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, request_code);
    }

    private Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("TAG", "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (1 == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }


    private void check_camera() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (getFromPref()) {
                showSettingsAlert();
            } else if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA)) {
                    showAlert();
                } else {
                    // No explanation needed, we can request the permission.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        }
    }

    private void showAlert() {
        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(requireActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Needs Camera Access");
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE, "DON'T ALLOW", (dialog, which) -> {
            dialog.dismiss();
            requireActivity().finish();
        });
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "ALLOW", (dialog, which) -> {
            dialog.dismiss();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        });
        alertDialog.show();
    }

    private void showSettingsAlert() {

        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(requireActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Needs Camera Access");
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE, "DON'T ALLOW", (dialog, which) -> dialog.dismiss());
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "SETTINGS", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private boolean getFromPref() {
        SharedPreferences myPrefs = requireActivity().getSharedPreferences(CAMERA_PREF, MODE_PRIVATE);
        return (myPrefs.getBoolean(AddVisitNew.ALLOW_KEY, false));
    }


    public void checkLocationStatus() {
        LocationManager manager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?").setCancelable(false).setPositiveButton("Yes", (dialog, id) -> startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))).setNegativeButton("No", (dialog, id) -> {
            dialog.cancel();
            goToDashboard();
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void goToDashboard() {
        Dashboard_dealer dashFragment = new Dashboard_dealer();
        Bundle bundle = requireActivity().getIntent().getExtras();
        dashFragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.viewframe11, dashFragment, "dash").commit();
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            for (String p : permissionNew) {
                result = ContextCompat.checkSelfPermission(requireActivity(), p);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p);
                }
            }
        } else {
            for (String p : permissions) {
                result = ContextCompat.checkSelfPermission(requireActivity(), p);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p);
                }
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 200);
            return false;
        }
        return true;
    }

    private void getLocation() {
        try {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ContextCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ContextCompat#requestPermissions for more details.
                return;
            }
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    mLatitude = latitude + " ";
                    mLongitude = longitude + " ";
                    // Use latitude and longitude as needed
                    // For example, you can print them or pass to another function
                    // For simplicity, we are just printing them here
                    System.out.println("Latitude: " + latitude);
                    System.out.println("Longitude: " + longitude);
                    locationString = getLocationFromCoordinates(requireActivity(), latitude, longitude);
                }
            });
            progressBar.setVisibility(View.GONE);
            imageCard.setVisibility(View.VISIBLE);
        } catch (Exception q) {
            q.printStackTrace();
            imageCard.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == request_code) {
                try {
                    bitmap = getResizedBitmapLessThanMaxSize(MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), fileUri));
                } catch (Exception q) {
                    q.printStackTrace();
                }
                shop_image_view.setImageBitmap(bitmap);
                shop_image_view.setPadding(0, 0, 0, 0);
                String timeStamp = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime());
                addressOnImage.setText(locationString + "\n" + timeStamp);
                cameraImage.setVisibility(View.GONE);
                isImageUploaded = true;
            }
        }
    }

    private Bitmap getResizedBitmapLessThanMaxSize(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        height = (int) Math.sqrt((double) (90 * 1024 * COMPRESSED_RATIO) / perPixelDataSize / bitmapRatio);
        width = (int) (height * bitmapRatio);
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static String getLocationFromCoordinates(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        StringBuilder addressString = new StringBuilder("Could not determine address");

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                addressString = new StringBuilder();

                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressString.append(address.getAddressLine(i));

                    if (i < address.getMaxAddressLineIndex()) {
                        addressString.append(", ");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressString.toString();
    }

    private void submitData() {
        if (!mobileString.equals("") && isMobileValid) {
            if (!name.equals("")) {
                if (!district.equals("")) {
                    if (!reasonForVisit.equals("")) {
                        if (commonFunctions.isMobileValid(retailer_mobile.getText().toString())) {
                            if (isImageUploaded) {
                                new CreateRetailerVisit().execute();
                            } else {
                                Toast.makeText(requireActivity(), "Please Upload Shop Image", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(requireActivity(), "Please Fill Reason for Visit", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "Please Fill District", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireActivity(), "Please Fill Retailer Name", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireActivity(), "Please Fill Valid Retailer Mobile No.", Toast.LENGTH_SHORT).show();
        }

    }

    class CreateRetailerVisit extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(requireContext());
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String URL = "http://unnatisales.akshapp.com/mobb/service.asmx";
            String NAMESPACE = "http://aksha/app/";
            String METHOD_NAME = "Send_retailers_visit";
            String SOAPAction = NAMESPACE + METHOD_NAME;

            SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
            String timeStamp2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            Databaseutill db = Databaseutill.getDBAdapterInstance(requireActivity());
            GetData get = new GetData(db, requireActivity());


            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setName("id");
            propertyInfo.setValue("");
            propertyInfo.setType(String.class);
            soapObject.addProperty(propertyInfo);

            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("store_id");
            propertyInfo1.setValue(storeID);
            propertyInfo1.setType(String.class);
            soapObject.addProperty(propertyInfo1);


            PropertyInfo propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("store_name");
            propertyInfo2.setValue(name);
            propertyInfo2.setType(String.class);
            soapObject.addProperty(propertyInfo2);

            PropertyInfo propertyInfo3 = new PropertyInfo();
            propertyInfo3.setName("name");
            propertyInfo3.setValue(name);
            propertyInfo3.setType(String.class);
            soapObject.addProperty(propertyInfo3);

            PropertyInfo propertyInfo4 = new PropertyInfo();
            propertyInfo4.setName("mobile_number");
            propertyInfo4.setValue(mobileString);
            propertyInfo4.setType(String.class);
            soapObject.addProperty(propertyInfo4);


            PropertyInfo propertyInfo5 = new PropertyInfo();
            propertyInfo5.setName("state");
            propertyInfo5.setValue(state);
            propertyInfo5.setType(String.class);
            soapObject.addProperty(propertyInfo5);

            PropertyInfo propertyInfo6 = new PropertyInfo();
            propertyInfo6.setName("district");
            propertyInfo6.setValue(district);
            propertyInfo6.setType(String.class);
            soapObject.addProperty(propertyInfo6);

            PropertyInfo propertyInfo7 = new PropertyInfo();
            propertyInfo7.setName("reasonofvisit");
            propertyInfo7.setValue(reasonForVisit);
            propertyInfo7.setType(String.class);
            soapObject.addProperty(propertyInfo7);

            PropertyInfo propertyInfo8 = new PropertyInfo();
            propertyInfo8.setName("createdate");
            propertyInfo8.setValue(timeStamp2);
            propertyInfo8.setType(String.class);
            soapObject.addProperty(propertyInfo8);

            PropertyInfo propertyInfo9 = new PropertyInfo();
            propertyInfo9.setName("activitydate");
            propertyInfo9.setValue(timeStamp2);
            propertyInfo9.setType(String.class);
            soapObject.addProperty(propertyInfo9);

            PropertyInfo propertyInfo10 = new PropertyInfo();
            propertyInfo10.setName("remarks");
            propertyInfo10.setValue("");
            propertyInfo10.setType(String.class);
            soapObject.addProperty(propertyInfo10);


            PropertyInfo propertyInfo11 = new PropertyInfo();
            propertyInfo11.setName("Createdby");
            propertyInfo11.setValue(get.getEmpid().get(0));
            propertyInfo11.setType(String.class);
            soapObject.addProperty(propertyInfo11);

            PropertyInfo propertyInfo12 = new PropertyInfo();
            propertyInfo12.setName("bytearray");
            propertyInfo12.setValue(convertToByteArray(getBitmapFromView(imageCard)));
            propertyInfo12.setType(String.class);
            soapObject.addProperty(propertyInfo12);

            PropertyInfo propertyInfo13 = new PropertyInfo();
            propertyInfo13.setName("fileName");
            propertyInfo13.setValue(timeStamp);
            propertyInfo13.setType(String.class);
            soapObject.addProperty(propertyInfo13);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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
            // Toast.makeText(getApplicationContext(),"Response"+s,Toast.LENGTH_LONG).show();
            if (s != null) {
                if (s.equalsIgnoreCase("0")) {
                    pd.dismiss();
                    Toast.makeText(requireContext(), "Invalid Request", Toast.LENGTH_LONG).show();
                } else if (s.equalsIgnoreCase("1")) {
                    pd.dismiss();
                    AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
                    alert.setTitle("Success");
                    alert.setMessage("Visit Added Successfully");
                    alert.setPositiveButton("OK", (dialogInterface, i) -> goToDashboard());
                    alert.show();
                }
            } else {
                pd.dismiss();
                Toast.makeText(requireContext(), "Something Went Wrong, Please Try Again", Toast.LENGTH_LONG).show();
                goToDashboard();
            }
        }

    }

    private void getRetailer(String mobile) {
        ApiInterface service = ApiClient.getClientUstore().create(ApiInterface.class);
        Call<RetailerResponse> call = service.getRetailer(mobile);

        call.enqueue(new Callback<RetailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<RetailerResponse> call, @NonNull Response<RetailerResponse> response) {
                if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                    if (response.body() != null) {
                        retailerName.setText(response.body().getData().getStoreName());
                        etDistrict.setText(response.body().getData().getStoreDist());
                        district = response.body().getData().getStoreDist();
                        storeID = response.body().getData().getStoreId().toString();
                        mobileString = response.body().getData().getStoreMob();
                        name = response.body().getData().getStoreName();
                        districtID = response.body().getData().getStoreDistId().toString();
                        state = response.body().getData().getStoreState();
                        progressBar.setVisibility(View.GONE);
                        isMobileValid = true;
                    } else {
                        mobileNotValid();
                    }
                } else {
                    mobileNotValid();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RetailerResponse> call, @NonNull Throwable t) {
                mobileNotValid();
            }
        });

    }

    private void mobileNotValid() {
        retailerName.setText("");
        etDistrict.setText("");
        isMobileValid = false;
        progressBar.setVisibility(View.GONE);

        AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
        alert.setTitle("Mobile Invalid");
        alert.setMessage("Please Enter Valid Retailer Mobile No. Retailer Must Have a Registered Account on uStore.");
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    private void setReasonSpinner() {
        for (int i = 0; i <= reasons.size() - 1; i++) {
            reasonsAsStr.add(reasons.get(i).getReason());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, reasonsAsStr);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        reasonVisitSpinner.setAdapter(spinnerArrayAdapter);
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        return returnedBitmap;
    }

    public static String convertToByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmp.recycle();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(byteArray);
        } else {
            return "";
        }
    }

    public class GetVisitReason extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(requireContext());
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String URL = "http://unnatisales.akshapp.com/mobb/service.asmx";
            String NAMESPACE = "http://aksha/app/";
            String METHOD_NAME = "Getreasonofvisit";
            String SOAPAction = NAMESPACE + METHOD_NAME;

            SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setAddAdornments(false);
            envelope.encodingStyle = SoapSerializationEnvelope.ENV;
            envelope.setOutputSoapObject(soapObject);

            String resultstring = null;

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL);

            try {
                httpTransportSE.call(SOAPAction, envelope);
                Object results = envelope.getResponse();
                resultstring = results.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultstring;
        }

        @Override
        protected void onPostExecute(String s) {
            // Toast.makeText(getApplicationContext(),"Response"+s,Toast.LENGTH_LONG).show();
            if (s != null) {
                try {
                    JSONArray jsonArr = new JSONArray(s);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        ReasonOfVisit mItem = new ReasonOfVisit();
                        mItem.setReason(jsonObj.getString("reason"));
                        mItem.setSid(jsonObj.getString("sid"));
                        reasons.add(mItem);
                    }
                } catch (Exception w) {
                    w.printStackTrace();
                }
            }
            pd.dismiss();
        }
    }


}
