package com.example.lenovo.clientapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by lenovo on 17-03-2017.
 */

public class Login extends AppCompatActivity {


    EditText user_name, pwd;
    TextInputLayout password_field;
    Button login;
    String username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        user_name = (EditText)findViewById(R.id.txt_username);
        password_field = (TextInputLayout)findViewById(R.id.txt_pwd);
        pwd = (EditText) findViewById(R.id.login_txt_pwd);

        Typeface typeface = Typeface.createFromAsset(this.getAssets(),"font/Roboto-Light.ttf");
        user_name.setTypeface(typeface);
        pwd.setTypeface(typeface);

        login = (Button)findViewById(R.id.login_button);
        login.setTypeface(typeface);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("admin");



//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//            DataSnapshot ds_admin = dataSnapshot.child("username");
//            username = (String) ds_admin.getValue();
//
//            DataSnapshot ds_pwd = dataSnapshot.child("password");
//            password = (String) ds_pwd.getValue();
//
//                username = user_name.getText().toString();
//                password = pwd.getText().toString();
//
//                login.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//
////                        if(user_name.equals(username) && pwd.equals(password))
////                        {
////
//////                            Toast.makeText(Login.this, "Login Successfully ", Toast.LENGTH_SHORT).show();
//////
////                        }
////                        else
////                        {
////                            Toast.makeText(Login.this, "Please Enter Correct Username and Password", Toast.LENGTH_SHORT).show();
////                        }
//                    }
//                });
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
