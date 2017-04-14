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

/**
 * Created by lenovo on 28-03-2017.
 */

public class Activity_User_show extends AppCompatActivity{

    TextView t_name,t_mobno,t_email,t_emergno,t_text,header;
    Toolbar toolbar;
    FloatingActionButton fab1,fab2;


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

        Typeface typeface = Typeface.createFromAsset(this.getAssets(),"font/Roboto-Light.ttf");
        t_name.setTypeface(typeface);
        t_mobno.setTypeface(typeface);
        t_email.setTypeface(typeface);
        t_emergno.setTypeface(typeface);
        t_text.setTypeface(typeface);



        toolbar = (Toolbar)findViewById(R.id.toolbar_details);
        header = (TextView)toolbar.findViewById(R.id.heading_data);
        header.setTypeface(typeface);


        final String name = getIntent().getStringExtra("data1").toString();
        final String mobnum = getIntent().getStringExtra("data2").toString();
        final String email = getIntent().getStringExtra("data3").toString();
        final String emergno = getIntent().getStringExtra("data4").toString();
        final String desc = getIntent().getStringExtra("data5").toString();

        t_name.setText(name);
        t_mobno.setText(String.valueOf(mobnum));
        t_email.setText(email);
        t_emergno.setText(String.valueOf(emergno));
        t_text.setText(desc);

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
                User_viewdetails_adapter.map.put(name, mobnum);
                startActivity(intent);


            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Activity_User_show.this,Add_user_activity.class);
                intent.putExtra("t_name",name);
                intent.putExtra("t_mobno",mobnum);
                intent.putExtra("t_email",email);
                intent.putExtra("t_emergno",emergno);
                intent.putExtra("t_text",desc);
                intent.putExtra("edit", 1);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        User_viewdetails_adapter.map.clear();
    }
}
