package com.example.lenovo.clientapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by lenovo on 31-03-2017.
 */

public class ClassMessageSend extends AsyncTask<URL,Integer,Long>
{

    String authkey,mobiles,senderId,message,route;


    ClassMessageSend(String authkey,String mobiles,String senderId,String message,String route)
    {
        this.authkey = authkey;
        this.mobiles = mobiles;
        this.senderId = senderId;
        this.message = message;
        this.route = route;
    }

    @Override
    protected Long doInBackground(URL... params) {

        //Your authentication key
        authkey = "147083A60bY8xkdxdm58ddf818";
        //Multiple mobiles numbers separated by comma
        mobiles = "9999999";
        //Sender ID,While using route4 sender id should be 6 characters long.
        senderId = "102234";
        //Your message to send, Add URL encoding here.
        message = "Test message";
        //define route
        route="default";

        URLConnection myURLConnection=null;
        URL myURL=null;
        BufferedReader reader=null;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<User_details_info> values = new ArrayList<User_details_info>();
                String text = values.toString();
                String new1 = "";


                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    DataSnapshot ds_mob = dataSnapshot1.child("mobileno");
                    mobiles = (String) ds_mob.getValue();
                    new1 += mobiles;
                    new1 += ",";

//                    mobiles.split(",");
//                    Log.e(TAG, mobiles + ",");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //encoding message
        String encoded_message= URLEncoder.encode(message);

        //Send SMS API
        String mainUrl="http://api.msg91.com/api/sendhttp.php?";

        //Prepare parameter string
        StringBuilder sbPostData= new StringBuilder(mainUrl);
        sbPostData.append("authkey="+authkey);
        sbPostData.append("&mobiles="+mobiles);
        sbPostData.append("&message="+encoded_message);
        sbPostData.append("&route="+route);
        sbPostData.append("&sender="+senderId);

        //final string
        mainUrl = sbPostData.toString();
        try
        {
            //prepare connection
            myURL = new URL(mainUrl);
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

            //reading response
            String response;
            while ((response = reader.readLine()) != null)
                //print response
                Log.d("RESPONSE", ""+response);

            //finally close connection
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Long aLong) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();


        super.onPostExecute(aLong);
    }
}
