package com.example.lenovo.clientapp;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.lenovo.clientapp.User_viewdetails_adapter.counter;
import static com.example.lenovo.clientapp.User_viewdetails_adapter.map;

public class User_details_home extends Fragment implements User_viewdetails_adapter.onShow,SearchView.OnQueryTextListener {

    public User_details_home(){

    }

    List<User_details_info> info;
    RecyclerView recyclerView;
   static User_viewdetails_adapter adapter;

    ArrayList<String> user_list=new ArrayList<String>();

    Toolbar toolbar;
    ImageView mess, closeBtn,delBtn;
    TextView itemselected, home_title;

    String keydata,email,emergno,mobile,uname,text;

    ArrayList<String > q_key=User_viewdetails_adapter.q_key;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");

    public static int REQUEST_CODE = 0;

    int MESSAGE_SEND_PERMISSION = 0;


    //permission for sending text message

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
    //



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
   View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        setHasOptionsMenu(true);


        AskPermission();

        info = new ArrayList<User_details_info>();
        recyclerView = (RecyclerView) view.findViewById(R.id.user_details);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_home);
        home_title = (TextView) toolbar.findViewById(R.id.title_home);
        mess = (ImageView) toolbar.findViewById(R.id.mail);
        itemselected = (TextView) toolbar.findViewById(R.id.num_item);
        closeBtn = (ImageView) toolbar.findViewById(R.id.close);
        delBtn = (ImageView)toolbar.findViewById(R.id.del);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);


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

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){

        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.search_item);
        SearchView s_view = (SearchView)MenuItemCompat.getActionView(item);
        s_view.setLayoutParams(new ActionBar.LayoutParams(Gravity.LEFT));
        s_view.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu,inflater);
    }


    // for search data

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText=newText.toLowerCase();
        ArrayList<User_details_info> user_details_infos=new ArrayList<User_details_info>();
        for(User_details_info details_info:info){
            String user=details_info.getUsername().toLowerCase();
            if(user.contains(newText)){
                user_details_infos.add(details_info);
            }
        }
        adapter.setFilter(user_details_infos);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.search_item:
                return true;
        }
        return false;
    }

    //

    //fetch data from database

    private void InitRecyclerView() {

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                info.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    DataSnapshot ds_key = dataSnapshot1.child("key");
                    keydata = (String)ds_key.getValue();

                    DataSnapshot ds_email = dataSnapshot1.child("emailid");
                    email = (String) ds_email.getValue();

                    DataSnapshot ds_emergno = dataSnapshot1.child("emergencyno");
                    emergno = (String) ds_emergno.getValue();

                    DataSnapshot ds_mobileno = dataSnapshot1.child("mobileno");
                    mobile = (String) ds_mobileno.getValue();

                    DataSnapshot ds_username = dataSnapshot1.child("username");
                    uname = (String) ds_username.getValue();
                    user_list.add(uname);

                    DataSnapshot ds_txt = dataSnapshot1.child("text");
                    text = (String) ds_txt.getValue();

                    User_details_info infoUser = new User_details_info(keydata, uname, mobile, email, emergno, text);
                    info.add(infoUser);
                    user_list.add(uname);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //action bar item code

    @Override
    public void onCardSelected(final boolean IsSelected, final int count) {

        if (IsSelected) {
            mess.setVisibility(View.VISIBLE);
            closeBtn.setVisibility(View.VISIBLE);
            itemselected.setVisibility(View.VISIBLE);
            itemselected.setText(String.valueOf(counter) + " items selected");
            home_title.setVisibility(View.GONE);
            delBtn.setVisibility(View.VISIBLE);

            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    map.clear();
                    User_viewdetails_adapter.user_list.clear();
                    mess.setVisibility(View.GONE);
                    closeBtn.setVisibility(View.GONE);
                    itemselected.setVisibility(View.GONE);
                    home_title.setVisibility(View.VISIBLE);
                    delBtn.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();

                }
            });

            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for(int i=0;i<q_key.size();i++) {

                        Query query=myRef.orderByChild("key").equalTo(q_key.get(i));
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                                    Log.d("key : ", snapshot.getKey());
//                                    Log.d("snapshot",snapshot.getValue().toString());
//                                    if(keydata.equals(snapshot.getKey())) {

                                        snapshot.getRef().removeValue();
                                        break;
                                    }
                                Toast.makeText(getActivity(),counter+ " Items removed successfully", Toast.LENGTH_SHORT).show();

                                map.clear();
                                User_viewdetails_adapter.user_list.clear();
                                mess.setVisibility(View.GONE);
                                closeBtn.setVisibility(View.GONE);
                                itemselected.setVisibility(View.GONE);
                                home_title.setVisibility(View.VISIBLE);
                                delBtn.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                }
            });

            mess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), Send_Mess_Page.class);
                    startActivityForResult(intent,REQUEST_CODE);
                }
            });


        } else {
            mess.setVisibility(View.GONE);
            closeBtn.setVisibility(View.GONE);
            itemselected.setVisibility(View.GONE);
            home_title.setVisibility(View.VISIBLE);
            delBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            if(counter==0)
            {
                map.clear();
                User_viewdetails_adapter.user_list.clear();
                mess.setVisibility(View.GONE);
                closeBtn.setVisibility(View.GONE);
                itemselected.setVisibility(View.GONE);
                delBtn.setVisibility(View.GONE);
                home_title.setVisibility(View.VISIBLE);
            }
            else
            {
                itemselected.setText(String.valueOf(counter) + " items selected");
            }
            adapter.notifyDataSetChanged();
        }
    }

}
