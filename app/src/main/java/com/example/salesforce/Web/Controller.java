package com.example.salesforce.Web;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.salesforce.Database.Databaseutill;
import com.example.salesforce.Database.GetData;
import com.example.salesforce.Interfaces.ApiInterface;
import com.example.salesforce.Models.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller implements Callback<List<Response>> {

    Databaseutill db;
    GetData get;

    static final String BASE_URL = "https://unnatiagro.in/stores/";
//    String district_id = "180";
//    String user_id = "5916";

    public void start(Context context,String district_id,String user_id ) {

        db = Databaseutill.getDBAdapterInstance(context);
        get = new GetData(db, context);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterface gerritAPI = retrofit.create(ApiInterface.class);

        Call<List<Response>> call = gerritAPI.postResponse(district_id,user_id);
        call.enqueue(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
        if (!response.toString().equalsIgnoreCase("")) {
            List<Response> changesList = response.body();
            Log.d("TAG", "onResponse: "+changesList);


            int count = 0;
            changesList.forEach(change -> get.insRetailer(change.getStoreId(),change.getStoreName(),change.getName(),
                    change.getMobileNumber(),change.getState(),change.getDistrict(),change.getUserId()));
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Response>> call, Throwable t) {
        t.printStackTrace();
    }
}