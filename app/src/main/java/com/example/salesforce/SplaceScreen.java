package com.example.salesforce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplaceScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generate method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splacescreen);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                SplaceScreen.this.finish();
//                new checkVersionUpdate().execute();
            }
        }, 2000);


    }
}
