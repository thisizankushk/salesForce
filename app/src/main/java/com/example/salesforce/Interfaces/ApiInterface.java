package com.example.salesforce.Interfaces;



import com.example.salesforce.Models.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("index.php?route=api/retailer/")
    Call<List<Response>> postResponse(@Query("district_id") String district_id,@Query("user_id") String user_id);
}
