package com.example.lenovo.clientapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Company_details_page extends Fragment {

    SharedPreferences pref_login;
    SharedPreferences.Editor editor_login;

    TextView t_header,t_title,t_desc;
    Button btn_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pref_login=getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor_login=pref_login.edit();
        View view =  inflater.inflate(R.layout.fragment_compny_details, null, false);

        t_header = (TextView)view.findViewById(R.id.logo_title);
        t_title = (TextView)view.findViewById(R.id.desc_header);
        t_desc = (TextView)view.findViewById(R.id.desc_details);
        btn_logout = (Button)view.findViewById(R.id.logout_app);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"font/Roboto-Light.ttf");

        t_header.setTypeface(typeface);
        t_title.setTypeface(typeface);
        t_desc.setTypeface(typeface);
        btn_logout.setTypeface(typeface);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor_login.clear();
                editor_login.commit();
                startActivity(new Intent(getActivity(),Login.class));
            }
        });

        return view;
    }

}
