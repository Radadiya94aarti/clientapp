package com.example.lenovo.clientapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lenovo on 14-04-2017.
 */

public class Add_user_activity extends AppCompatActivity {

    String m_name,m_mobilenum,m_email,m_emergno,m_desc;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_activity);

        m_name = getIntent().getExtras().getString("t_name");
        m_mobilenum = getIntent().getExtras().getString("t_mobno");
        m_email = getIntent().getExtras().getString("t_email");
        m_emergno = getIntent().getExtras().getString("t_emergno");
        m_desc = getIntent().getExtras().getString("t_text");


        Fragment_Add_User add_user = new Fragment_Add_User();
        Bundle bundle = new Bundle();

        bundle.putString("m_name",m_name);
        bundle.putString("m_mobilenum",m_mobilenum);
        bundle.putString("m_email",m_email);
        bundle.putString("m_emergno",m_emergno);
        bundle.putString("m_desc",m_desc);

        add_user.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,add_user).commit();


    }
}
