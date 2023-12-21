package com.example.salesforce.Retailer;

import static android.content.Context.MODE_PRIVATE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.salesforce.Dashboard_dealer;
import com.example.salesforce.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddVisitNew extends Fragment {
    SearchView searchView;
    EditText retailerName, district;
    CardView ImageCard;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    public static int request_code = 1000;
    public static final String IMAGE_DIRECTORY_NAME = "imageuploadtest";
    private LocationManager manager;
    String mLatitude = "0.0", mLongitude = "0.0";
    String locationString;
    final static int COMPRESSED_RATIO = 70;
    final static int perPixelDataSize = 10;
    ImageView shop_image_view;
    Uri fileUri;
    Bitmap bitmap;
    TextView addressOnImage;
    boolean isImageUploaded = false;
    EditText retailer_mobile,reasonVisit;
    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION};


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
        getLocation();
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
        reasonVisit = view.findViewById(R.id.reasonForVisit);
        district = view.findViewById(R.id.district);
        ImageCard = view.findViewById(R.id.image_card_view);
        shop_image_view = view.findViewById(R.id.shop_image_view);
        addressOnImage = view.findViewById(R.id.addressOnImage);

        ImageCard.setOnClickListener(view1 -> {
            check_camera();
            takePhotoFromCamera();
        });
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
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "SETTINGS", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.show();
    }

    private boolean getFromPref() {
        SharedPreferences myPrefs = requireActivity().getSharedPreferences(CAMERA_PREF, MODE_PRIVATE);
        return (myPrefs.getBoolean(AddVisitNew.ALLOW_KEY, false));
    }


    public void checkLocationStatus() {
        manager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
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
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(requireActivity(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 200);
            return false;
        }
        return true;
    }

    private void getLocation() {
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
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    mLatitude = latitude + "";
                    mLongitude = longitude + "";
                    // Use latitude and longitude as needed
                    // For example, you can print them or pass to another function
                    // For simplicity, we are just printing them here
                    System.out.println("Latitude: " + latitude);
                    System.out.println("Longitude: " + longitude);
                    locationString = getLocationFromCoordinates(requireActivity(), latitude, longitude);

                    Toast.makeText(requireActivity(), "lat long" + latitude + longitude, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                goToDashboard();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
                    shop_image_view.setImageBitmap(bitmap);
                    shop_image_view.setPadding(0, 0, 0, 0);
                    String timeStamp = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime());
                    addressOnImage.setText(locationString + "\n" + timeStamp);
                    isImageUploaded = true;
                } catch (Exception er) {
                    er.printStackTrace();
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private Bitmap getResizedBitmapLessThanMaxSize(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        height = (int) Math.sqrt(90 * 1024 * COMPRESSED_RATIO / perPixelDataSize / bitmapRatio);
        width = (int) (height * bitmapRatio);
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static String getLocationFromCoordinates(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String addressString = "Could not determine address";

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                addressString = "";

                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressString += address.getAddressLine(i);

                    if (i < address.getMaxAddressLineIndex()) {
                        addressString += ", ";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressString;
    }

    private void submitData(){
        String mobileNo,name,district_,reason;
        mobileNo = retailer_mobile.getText().toString();
        name = retailerName.getText().toString();
        district_ = district.getText().toString();
        reason = reasonVisit.getText().toString();

    }


}
