package com.example.lenovo.clientapp;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ThirdTabFragment extends Fragment {


    TextView t_header,t_title,t_desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_third_tab, null, false);

        t_header = (TextView)view.findViewById(R.id.logo_title);
        t_title = (TextView)view.findViewById(R.id.desc_header);
        t_desc = (TextView)view.findViewById(R.id.desc_details);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"font/Roboto-Light.ttf");

        t_header.setTypeface(typeface);
        t_title.setTypeface(typeface);
        t_desc.setTypeface(typeface);

        return view;
    }

}
