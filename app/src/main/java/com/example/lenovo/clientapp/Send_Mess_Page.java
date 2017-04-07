package com.example.lenovo.clientapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
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

        final ArrayList<String> abc = getIntent().getStringArrayListExtra("array_list");


        numbers="";

        for (int i=0;i<User_viewdetails_adapter.values.size();i++)
        {
            numbers+=User_viewdetails_adapter.values.get(i)+",";
        }

        Toast.makeText(this, "" +User_viewdetails_adapter.values, Toast.LENGTH_SHORT).show();
        number.setText(numbers);


        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < User_viewdetails_adapter.values.size(); i++)
                {
                    String message = mess_info.getText().toString();
                    String tempMobileNumber = User_viewdetails_adapter.values.get(i).toString();
                    sendMessage(tempMobileNumber, message);
                }
//                Toast.makeText(getApplicationContext(), User_viewdetails_adapter.values.size()+"", Toast.LENGTH_LONG).show();
//                for(counter=0;counter<User_viewdetails_adapter.values.size();counter++)
//                {
////                    Log.d("sendsms", User_viewdetails_adapter.values.get(counter));
//                    createSmsThread(counter);
////                    sendMessage(User_viewdetails_adapter.values.get(counter),mess_info.getText().toString());
////                    try {
////                        Thread.sleep(3000);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
//                }

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

//    public void createSmsThread(final int i){
//        handler = new Handler(Looper.getMainLooper() /*UI thread*/);
//        Runnable workRunnable = new Runnable() {
//            @Override
//            public void run() {
//                Log.d("sendsms1", User_viewdetails_adapter.values.get(i));
//                sendMessage(User_viewdetails_adapter.values.get(i),mess_info.getText().toString());
//            }
//        };
//
//        handler.removeCallbacks(workRunnable);
//        handler.postDelayed(workRunnable, 3000);
//    }

    public void sendMessage(String phoneNo , String sms)  {

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        User_viewdetails_adapter.values.clear();

        Intent intent=new Intent(SENT);
        PendingIntent pi= PendingIntent.getActivity(Send_Mess_Page.this, 0, intent,0);

        Intent deliveryIntent = new Intent(DELIVERED);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                deliveryIntent, 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        ContentValues values = new ContentValues();
                        for (int i = 0; i < User_viewdetails_adapter.values.size() - 1; i++) {
                            values.put("address", User_viewdetails_adapter.values.get(i).toString());
                            // txtPhoneNo.getText().toString());
                            values.put("body", mess_info.getText().toString());
                        }
                        getContentResolver().insert(
                                Uri.parse("content://sms/sent"), values);
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;

                    //
                }
            }
        },new IntentFilter(SENT));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        },new IntentFilter(DELIVERED));


        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, sms, pi, deliveredPI);
        Toast.makeText(Send_Mess_Page.this, "SMS Sent!",
                    Toast.LENGTH_LONG).show();
//
//        } catch (Exception e) {
//            Toast.makeText(Send_Mess_Page.this,
//                    "SMS failed, please try again later!",
//                    Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
    }
}
