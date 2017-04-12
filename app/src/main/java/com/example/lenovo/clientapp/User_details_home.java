package com.example.lenovo.clientapp;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.lenovo.clientapp.User_viewdetails_adapter.counter;
import static com.example.lenovo.clientapp.User_viewdetails_adapter.map;

public class User_details_home extends Fragment implements User_viewdetails_adapter.onShow {


    ActionMode mActionMode;
    List<User_details_info> info;
    RecyclerView recyclerView;
    User_viewdetails_adapter adapter;
    int numbers_post = 0;
    boolean isMultiSelect = false;
    boolean isShow = false;
    Menu context_menu;
    Toolbar toolbar;
    ImageView mess, closeBtn, searchBtn,delBtn;
    TextView itemselected, home_title;

    public static int REQUEST_CODE = 0;


    int MESSAGE_SEND_PERMISSION = 0;

    private void AskPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{android.Manifest.permission.SEND_SMS}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    MESSAGE_SEND_PERMISSION = 1;
                } else {

                    Toast.makeText(getActivity(), "Permission Denied to Sending SMS", Toast.LENGTH_LONG).show();

                }
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_user_details, container, false);

        AskPermission();

        info = new ArrayList<User_details_info>();
        recyclerView = (RecyclerView) view.findViewById(R.id.user_details);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_home);
        home_title = (TextView) toolbar.findViewById(R.id.title_home);
        mess = (ImageView) toolbar.findViewById(R.id.mail);
        itemselected = (TextView) toolbar.findViewById(R.id.num_item);
        closeBtn = (ImageView) toolbar.findViewById(R.id.close);
        searchBtn = (ImageView) toolbar.findViewById(R.id.item_search);
        delBtn = (ImageView)toolbar.findViewById(R.id.del);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/Roboto-Light.ttf");
        home_title.setTypeface(typeface);
        itemselected.setTypeface(typeface);


        recyclerView.setHasFixedSize(true);
        adapter = new User_viewdetails_adapter(getActivity(), info, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        InitRecyclerView();


        return view;
    }

    private void InitRecyclerView() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");


        myRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                info.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    DataSnapshot ds_email = dataSnapshot1.child("emailid");
                    String email = (String) ds_email.getValue();

                    DataSnapshot ds_emergno = dataSnapshot1.child("emergencyno");
                    String emergno = (String) ds_emergno.getValue();

                    DataSnapshot ds_mobileno = dataSnapshot1.child("mobileno");
                    String mobile = (String) ds_mobileno.getValue();

                    DataSnapshot ds_username = dataSnapshot1.child("username");
                    String uname = (String) ds_username.getValue();

                    DataSnapshot ds_txt = dataSnapshot1.child("text");
                    String text = (String) ds_txt.getValue();


                    User_details_info infoUser = new User_details_info(uname, mobile, email, emergno, text);
                    info.add(infoUser);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCardSelected(final boolean IsSelected, final int count) {

        if (IsSelected) {
            mess.setVisibility(View.VISIBLE);
            closeBtn.setVisibility(View.VISIBLE);
            itemselected.setVisibility(View.VISIBLE);
            itemselected.setText(String.valueOf(counter) + " items selected");
            home_title.setVisibility(View.GONE);
            searchBtn.setVisibility(View.GONE);


            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    User_viewdetails_adapter.map.remove(IsSelected);

                    Toast.makeText(getActivity(), "" +map, Toast.LENGTH_SHORT).show();

                    adapter.notifyDataSetChanged();

                }
            });


            mess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), Send_Mess_Page.class);
//                    intent.putStringArrayListExtra("array_list", User_viewdetails_adapter.values);
                    startActivityForResult(intent,REQUEST_CODE);

                }
            });
        } else {
            mess.setVisibility(View.GONE);
            closeBtn.setVisibility(View.GONE);
            itemselected.setVisibility(View.GONE);
            home_title.setVisibility(View.VISIBLE);
            searchBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            itemselected.setText(String.valueOf(counter) + " items selected");
            adapter.notifyDataSetChanged();
        }
    }
}
