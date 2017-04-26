package com.example.lenovo.clientapp;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class Fragment_Add_User extends Fragment {

    EditText mName,mEmail,mMobNo,mEmergNo,mDesc;
    Button mSave,mCancel;
    User_details_info data;
    Toolbar toolbar;
    boolean flag=false;
    String q_email;
    TextView toolbar_header;
    String sname,smobno,semail,semergno,sdesc;
    String keyvalue;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_add_user, null, false);


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


        final Bundle os = getActivity().getIntent().getExtras();
        if(os!=null)
        {
            keyvalue = getArguments().getString("m_key");
            q_email=getArguments().getString("m_email");
            sname = getArguments().getString("m_name");
            smobno = getArguments().getString("m_mobilenum");
            semail = getArguments().getString("m_email");
            semergno = getArguments().getString("m_emergno");
            sdesc = getArguments().getString("m_desc");

            toolbar.setNavigationIcon(R.drawable.back_arrow);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            mName.setText(sname);
            mMobNo.setText(smobno);
            mEmail.setText(semail);
            mEmergNo.setText(semergno);
            mDesc.setText(sdesc);
        }

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sname = mName.getText().toString();
                smobno = Objects.toString(mMobNo.getText(),null);
                semail = mEmail.getText().toString();
                semergno = Objects.toString(mEmergNo.getText(),null);
                sdesc = mDesc.getText().toString();

                if(sname.isEmpty() || semail.isEmpty() || sdesc.isEmpty() || smobno.isEmpty())
                {
                    Toast.makeText(getActivity(), "please fill all the details", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(os!=null)
                    {
                       Query query=myRef.child("Users").orderByChild("key").equalTo(keyvalue);

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    Log.d("key : ", snapshot.getKey());
                                    Log.d("snapshot",snapshot.getValue().toString());

                                    DataSnapshot ds_key = snapshot.child("key");
                                    String keydata = (String)ds_key.getValue();

                                    if(keyvalue.equals(keydata)){
                                        User_details_info data = new User_details_info(keyvalue, sname,smobno,semail,semergno,sdesc);
                                        snapshot.getRef().setValue(data);
                                        flag=true;
                                        Intent i=new Intent(getActivity(),Activity_User_show.class);
                                        i.putExtra("key",keyvalue);
                                        getActivity().startActivity(i);
                                        break;
                                    }
                                }
                                Toast.makeText(getActivity(), "Details changed Successfully " , Toast.LENGTH_SHORT).show();
                                getActivity().finish();

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                    else {

                        String keydata = String.valueOf(System.currentTimeMillis());
                        String GenerateKey=sname + keydata;

                        User_details_info data = new User_details_info(GenerateKey,sname,smobno,semail,semergno,sdesc);
                        FirebaseDatabase.getInstance().getReference().child("Users").push().setValue(data);
                        Toast.makeText(getActivity(), "Details stored successfully", Toast.LENGTH_SHORT).show();
                    }

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

                if(os!=null)
                {
                    getActivity().finish();
                }
                else {

                    mName.setText("");
                    mMobNo.setText("");
                    mEmail.setText("");
                    mEmergNo.setText("");
                    mDesc.setText("");
                }
            }
        });
        return view;
    }

}
