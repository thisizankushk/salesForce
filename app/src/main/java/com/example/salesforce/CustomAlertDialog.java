package com.example.salesforce;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomAlertDialog extends Dialog {

    private String message;
    private String title;
    private String btYesText;
    private View.OnClickListener btYesListener=null;

    Button btYes;

    int icon;
    ImageView imageView;
//    TextView tv;
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    String color;


    public CustomAlertDialog(Context context) {
        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_dialog);


//        tv.setText(getTitle());
        TextView tvmessage = (TextView) findViewById(R.id.alertMsg);
        tvmessage.setText(getMessage());
        btYes = (Button) findViewById(R.id.alertbtn);
        btYes.setText(btYesText);
        btYes.setOnClickListener(btYesListener);
        imageView =(ImageView)findViewById(R.id.titalImage);
        imageView.setImageResource(getIcon());
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setIcon(int icon) {

        this.icon = icon;
    }

    public int getIcon() {
//        tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        imageView.setVisibility(View.VISIBLE);
        return icon;
    }

    public void setPositveButton(String yes, View.OnClickListener onClickListener) {
        dismiss();
        this.btYesText = yes;
        this.btYesListener = onClickListener;
    }


}
