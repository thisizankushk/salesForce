package com.example.salesforce.util;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class CommonFunctions {
    Context context;

    public CommonFunctions(Context context) {
        this.context = context;
    }

    public boolean isMobileValid(@NonNull String mobile) {
        boolean isMobileValid;
        int firstChar;
        if (mobile.isEmpty()) {
            isMobileValid = false;
            showToast("Mobile cannot be empty");

        } else if (mobile.length() < 10) {
            isMobileValid = false;
            showToast("mobile number must be of 10 digits");
        } else {
            firstChar = mobile.charAt(0);
            if (firstChar == '6' || firstChar == '7' || firstChar == '8' || firstChar == '9') {
                isMobileValid = true;
            } else {
                isMobileValid = false;
                showToast("Mobile number should start with 6 or 7 or 8 or 9");
            }
        }
        return isMobileValid;
    }
    private void showToast(String message) {
        Toast.makeText(context, " " + message, Toast.LENGTH_SHORT).show();
    }

}
