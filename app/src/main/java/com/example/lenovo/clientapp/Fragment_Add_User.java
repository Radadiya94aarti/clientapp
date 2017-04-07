package com.example.lenovo.clientapp;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class Fragment_Add_User extends Fragment {


    EditText mName,mEmail,mMobNo,mEmergNo,mDesc;
    Button mSave,mCancel;
    Long semergno;
    User_details_info data;
    Toolbar toolbar;
    TextView toolbar_header;

    public Fragment_Add_User() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_user, null, false);
//        Firebase.setAndroidContext(this,getActivity());


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();


        mName = (EditText)view.findViewById(R.id.name);
        mEmail = (EditText)view.findViewById(R.id.email_id);
        mMobNo = (EditText)view.findViewById(R.id.mobile_no);
        mEmergNo = (EditText)view.findViewById(R.id.emerg_no);
        mDesc = (EditText)view.findViewById(R.id.desc);
        toolbar = (Toolbar)view.findViewById(R.id.toolbar_head);
        toolbar_header = (TextView)toolbar.findViewById(R.id.title_head);


        mSave = (Button) view.findViewById(R.id.save);
        mCancel = (Button) view.findViewById(R.id.cancel);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"font/Roboto-Light.ttf");

        mName.setTypeface(typeface);
        mEmail.setTypeface(typeface);
        mMobNo.setTypeface(typeface);
        mEmergNo.setTypeface(typeface);
        mDesc.setTypeface(typeface);
        mSave.setTypeface(typeface);
        mCancel.setTypeface(typeface);

        toolbar_header.setTypeface(typeface);



        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sname = mName.getText().toString();
                String smobno = Objects.toString(mMobNo.getText(),null);
                String semail = mEmail.getText().toString();
                String semergno = Objects.toString(mEmergNo.getText(),null);
                String sdesc = mDesc.getText().toString();

                if(sname.isEmpty() || semail.isEmpty() || sdesc.isEmpty() || smobno.isEmpty())
                {
                    Toast.makeText(getActivity(), "please fill all the details", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    User_details_info data = new User_details_info(sname,smobno,semail,semergno,sdesc);
                    FirebaseDatabase.getInstance().getReference().child("Users").push().setValue(data);

                    Toast.makeText(getActivity(), "Details stored successfully", Toast.LENGTH_SHORT).show();

                    mName.setText("");
                    mMobNo.setText("");
                    mEmail.setText("");
                    mEmergNo.setText("");
                    mDesc.setText("");

                }
            }

        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mName.setText("");
                mMobNo.setText("");
                mEmail.setText("");
                mEmergNo.setText("");
                mDesc.setText("");
            }
        });


        return view;
    }

}
