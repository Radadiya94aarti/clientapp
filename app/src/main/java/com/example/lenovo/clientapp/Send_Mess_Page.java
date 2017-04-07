package com.example.lenovo.clientapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by lenovo on 03-04-2017.
 */

public class Send_Mess_Page extends AppCompatActivity {

    Handler handler;
    Runnable workRunnable;
    EditText number,mess_info;
    String numbers;
    int counter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mess_form);



        mess_info = (EditText) findViewById(R.id.textMess);
        number = (EditText) findViewById(R.id.phoneNumber);
        Button mSend = (Button) findViewById(R.id.send_btn);
        Button mCancel = (Button)findViewById(R.id.cancel_btn);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_send);
        TextView message = (TextView)toolbar.findViewById(R.id.title_send);
        TextView to = (TextView)findViewById(R.id.to_text);
        TextView here = (TextView)findViewById(R.id.here_text);

        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "font/Roboto-Light.ttf");

        mess_info.setTypeface(typeface);
        number.setTypeface(typeface);
        mSend.setTypeface(typeface);
        mCancel.setTypeface(typeface);
        message.setTypeface(typeface);
        to.setTypeface(typeface);
        here.setTypeface(typeface);


//        String user_name = getIntent().getStringExtra("name").toString();
        final ArrayList<String> abc = getIntent().getStringArrayListExtra("array_list");


        numbers="";
        for (int i=0;i<User_viewdetails_adapter.values.size();i++)
        {
            numbers+=User_viewdetails_adapter.values.get(i)+";";
        }

        Toast.makeText(this, ""+User_viewdetails_adapter.values, Toast.LENGTH_SHORT).show();
        number.setText(numbers);
//        mess_info.setText(user_name);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), User_viewdetails_adapter.values.size()+"", Toast.LENGTH_LONG).show();
                for(counter=0;counter<User_viewdetails_adapter.values.size();counter++)
                {
//                    Log.d("sendsms", User_viewdetails_adapter.values.get(counter));
                    createSmsThread(counter);
//                    sendMessage(User_viewdetails_adapter.values.get(counter),mess_info.getText().toString());
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }

            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mess_info.setText("");
                number.setText("");
            }
        });
    }

    public void createSmsThread(final int i){
        handler = new Handler(Looper.getMainLooper() /*UI thread*/);
        Runnable workRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d("sendsms1", User_viewdetails_adapter.values.get(i));
                sendMessage(User_viewdetails_adapter.values.get(i),mess_info.getText().toString());
            }
        };

        handler.removeCallbacks(workRunnable);
        handler.postDelayed(workRunnable, 3000);
    }

    public void sendMessage(String phoneNo , String sms)  {

        User_viewdetails_adapter.values.clear();
        phoneNo = number.getText().toString();
        sms = mess_info.getText().toString();

        Intent intent=new Intent(Send_Mess_Page.this,MainActivity.class);
        PendingIntent pi= PendingIntent.getActivity(Send_Mess_Page.this, 0, intent,0);

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, sms, pi, null);
            Toast.makeText(Send_Mess_Page.this, "SMS Sent!",
                    Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(Send_Mess_Page.this,
                    "SMS failed, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
