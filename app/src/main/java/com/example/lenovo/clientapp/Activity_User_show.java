package com.example.lenovo.clientapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.lenovo.clientapp.User_viewdetails_adapter.user_list;

/**
 * Created by lenovo on 28-03-2017.
 */

public class Activity_User_show extends AppCompatActivity{

    TextView t_name,t_mobno,t_email,t_emergno,t_text,header;
    Toolbar toolbar;
    FloatingActionButton fab1,fab2,fab3;


    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Users");

    String key;
    String name="";
    String mobnum="";
    String emergno="";
    String email="";
    String desc="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_show);


        t_name = (TextView)findViewById(R.id.show_name);
        t_mobno = (TextView)findViewById(R.id.show_mobnum);
        t_email = (TextView)findViewById(R.id.show_email);
        t_emergno = (TextView)findViewById(R.id.show_emegno);
        t_text = (TextView)findViewById(R.id.show_desc);
        fab1 = (FloatingActionButton)findViewById(R.id.addFloating);
        fab2 = (FloatingActionButton)findViewById(R.id.edit_data);
        fab3 = (FloatingActionButton)findViewById(R.id.delFloating);

        Typeface typeface = Typeface.createFromAsset(this.getAssets(),"font/Roboto-Light.ttf");
        t_name.setTypeface(typeface);
        t_mobno.setTypeface(typeface);
        t_email.setTypeface(typeface);
        t_emergno.setTypeface(typeface);
        t_text.setTypeface(typeface);

        toolbar = (Toolbar)findViewById(R.id.toolbar_details);
        header = (TextView)toolbar.findViewById(R.id.heading_data);
        header.setTypeface(typeface);


        key = getIntent().getStringExtra("key").toString();

        getData();

        toolbar.setNavigationIcon(R.drawable.back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_User_show.this,Send_Mess_Page.class);
                User_viewdetails_adapter.map.clear();
                user_list.clear();
                User_details_info user_details_info=new User_details_info(name,mobnum);
                user_list.put(key,user_details_info);
                startActivity(intent);

            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_User_show.this,Add_user_activity.class);
                intent.putExtra("key", key);
                intent.putExtra("t_name",name);
                intent.putExtra("t_mobno",mobnum);
                intent.putExtra("t_email",email);
                intent.putExtra("t_emergno",emergno);
                intent.putExtra("t_text",desc);
                startActivity(intent);
                finish();
            }
        });


        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query query = myRef.orderByChild("key").equalTo(key);
                   query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                           {
                               dataSnapshot1.getRef().removeValue();

                            }
                            finish();
                            Toast.makeText(Activity_User_show.this,name + " details deleted", Toast.LENGTH_SHORT).show();
                       }
                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                     }
                   });

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        User_viewdetails_adapter.map.clear();
        User_viewdetails_adapter.user_list.clear();
    }

    public  void getData(){
        Query query=myRef.orderByChild("key").equalTo(key);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    DataSnapshot ds_name=snapshot.child("username");
                    name=(String)ds_name.getValue();

                    DataSnapshot ds_email = snapshot.child("emailid");
                    email = (String) ds_email.getValue();

                    DataSnapshot ds_emergno = snapshot.child("emergencyno");
                    emergno = (String) ds_emergno.getValue();

                    DataSnapshot ds_mobileno = snapshot.child("mobileno");
                    mobnum = (String) ds_mobileno.getValue();

                    DataSnapshot ds_txt = snapshot.child("text");
                    desc = (String) ds_txt.getValue();

                    t_name.setText(name);
                    t_mobno.setText(String.valueOf(mobnum));
                    t_email.setText(email);
                    t_emergno.setText(String.valueOf(emergno));
                    t_text.setText(desc);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {

    }
}
