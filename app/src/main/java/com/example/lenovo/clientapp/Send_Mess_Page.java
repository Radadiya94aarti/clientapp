package com.example.lenovo.clientapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.Map.Entry;

import co.lujun.androidtagview.ColorFactory;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by lenovo on 03-04-2017.
 */

public class Send_Mess_Page extends AppCompatActivity {


    EditText mess_info;
    String numbers;
    TagContainerLayout mTagContainerLayout;
    HashMap<String,User_details_info> user_list=new HashMap<String,User_details_info>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mess_form);
        this.user_list=User_viewdetails_adapter.user_list;
        mess_info = (EditText) findViewById(R.id.textMess);
        Button mSend = (Button) findViewById(R.id.send_btn);
        Button mCancel = (Button) findViewById(R.id.cancel_btn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_send);
        TextView message = (TextView) toolbar.findViewById(R.id.title_send);
        TextView to = (TextView) findViewById(R.id.to_text);
        TextView here = (TextView) findViewById(R.id.here_text);


        final ArrayList<String> key_list=new ArrayList<String>();
        mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagcontainerlayout);
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "font/Roboto-Light.ttf");

        mess_info.setTypeface(typeface);
        mTagContainerLayout.setTagTypeface(typeface);
        mSend.setTypeface(typeface);
        mCancel.setTypeface(typeface);
        message.setTypeface(typeface);
        to.setTypeface(typeface);
        here.setTypeface(typeface);

        mTagContainerLayout.setTheme(ColorFactory.NONE);
        mTagContainerLayout.setTagBackgroundColor(Color.TRANSPARENT);

        final Bundle os = getIntent().getExtras();
        if(os!=null)
        {
            mTagContainerLayout.setEnableCross(false);
        }
        numbers = "";

        for(Entry entry: user_list.entrySet()){
            User_details_info user_details_info=(User_details_info) entry.getValue();
            key_list.add((String) entry.getKey());
            mTagContainerLayout.addTag(user_details_info.getUsername());
        }

        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {

            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {

                String key = mTagContainerLayout.getTagText(position);
                if (User_viewdetails_adapter.map.containsKey(key)) {

                }
                User_viewdetails_adapter.counter--;
                User_viewdetails_adapter.map.remove(key);
                User_viewdetails_adapter.user_list.remove(key_list.get(position));
                key_list.remove(position);

                Intent intent = new Intent(Send_Mess_Page.this, User_details_home.class);
                setResult(RESULT_OK, intent);

                mTagContainerLayout.removeTag(position);

            }
        });

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(Entry entry: user_list.entrySet()){
                    User_details_info user_details_info=(User_details_info) entry.getValue();
                    String message = mess_info.getText().toString() + "\n\nFrom <Company Name>, <Address/Contact No.>";
                    String tempMobile = user_details_info.getMobileno();
                    sendMessage(tempMobile, message);
                }

                mess_info.setText("");
                finish();

            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (os != null) {
                    finish();
                }
                else{
                    mess_info.setText("");
                    mTagContainerLayout.removeAllTags();
                }
            }
        });
    }

    public void sendMessage(String phoneNo, String sms) {

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        Intent intent = new Intent(SENT);
        PendingIntent pi = PendingIntent.getActivity(Send_Mess_Page.this, 0, intent, 0);

        Intent deliveryIntent = new Intent(DELIVERED);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                deliveryIntent, 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        ContentValues values = new ContentValues();
                        for (int i = 0; i < User_viewdetails_adapter.map.size() - 1; i++) {
                            values.put("address", User_viewdetails_adapter.map.get(i).toString());

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
        }, new IntentFilter(SENT));

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
        }, new IntentFilter(DELIVERED));

        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, sms, pi, deliveredPI);
            Toast.makeText(Send_Mess_Page.this, "SMS is Sent!",
                    Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Toast.makeText(Send_Mess_Page.this,
                    "SMS failed, please try again later!",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
