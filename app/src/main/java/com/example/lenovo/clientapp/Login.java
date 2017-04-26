package com.example.lenovo.clientapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by lenovo on 17-03-2017.
 */

public class Login extends AppCompatActivity {


    EditText u_name_field, pwd_field;
    TextInputLayout password_field;
    Button login;
    String username1="",password1="";
    String user_name="",pass_word="";
    SharedPreferences login_pref;
    SharedPreferences.Editor login_editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_pref=getSharedPreferences("Login",MODE_PRIVATE);
        login_editor=login_pref.edit();

        String login_user=login_pref.getString("UserName","");

        if(login_user.length()>0){
            startActivity(new Intent(Login.this,MainActivity.class));
            finish();
        }


        u_name_field = (EditText)findViewById(R.id.txt_username);
        password_field = (TextInputLayout)findViewById(R.id.txt_pwd);
        pwd_field = (EditText) findViewById(R.id.login_txt_pwd);

        Typeface typeface = Typeface.createFromAsset(this.getAssets(),"font/Roboto-Light.ttf");
        u_name_field.setTypeface(typeface);
        pwd_field.setTypeface(typeface);

        login = (Button)findViewById(R.id.login_button);
        login.setTypeface(typeface);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("admin");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot ds_admin = dataSnapshot.child("username");
                user_name = (String) ds_admin.getValue();


                DataSnapshot ds_pwd = dataSnapshot.child("password");
                pass_word = (String) ds_pwd.getValue();


                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        username1 = u_name_field.getText().toString();
                        password1 = pwd_field.getText().toString();


                        if(username1.equals(user_name) && password1.equals(pass_word))
                        {

                            Toast.makeText(Login.this, "Login Successfully ", Toast.LENGTH_SHORT).show();
                            login_editor.putString("UserName",user_name);
                            login_editor.commit();
                            Intent intent = new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        else
                        {
                            Toast.makeText(Login.this, "Please Enter Correct Username and Password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
