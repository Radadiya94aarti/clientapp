//package com.example.lenovo.clientapp;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by lenovo on 17-03-2017.
// */
//
//public class User_viewdetails_adapter extends RecyclerView.Adapter<User_viewdetails_adapter.MyViewHolder>{
//
//
//    private Context mContext;
//    private onShow show;
//    int counter = 0;
//
//  static   ArrayList<String> values;
//
//    public List<User_details_info> infoList;
//    public List<User_details_info> selected_usersList;
//    boolean IsSelected=false;
//
//    boolean isChecked = false;
//
//    public User_viewdetails_adapter(Context mContext , List<User_details_info> list,onShow show)
//    {
//        this.mContext = mContext;
//        this.infoList = list;
//        this.show = show;
//
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder
//    {
//        TextView username,emailid,mobileno,emergncyno,text;
//        LinearLayout linearLayout;
//
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            username = (TextView)itemView.findViewById(R.id.username);
//            mobileno = (TextView)itemView.findViewById(R.id.mob_num);
//            emailid = (TextView)itemView.findViewById(R.id.email);
//            linearLayout = (LinearLayout)itemView.findViewById(R.id.layout_details);
//        }
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.custom_user_details, parent, false);
//        values=new ArrayList<String >();
//        return new MyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//
//        final User_details_info details = infoList.get(position);
//        holder.username.setText(details.getUsername());
//        holder.mobileno.setText(String.valueOf(details.getMobileno()));
//        holder.emailid.setText(details.getEmailid());
//
//        holder.itemView.setBackgroundColor(details.isSelected()? Color.GRAY : Color.WHITE);
//
//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                    if (IsSelected)
//                    {
//
//                        values.add(details.getMobileno());
//
//
//                        if (details.isSelected()) {
//                        counter--;
//                        show.onCardSelected(v, true, counter,values);
//                        holder.itemView.setBackgroundColor(details.isSelected() ? Color.GREEN : Color.WHITE);
//                        notifyDataSetChanged();
//
//                    } else {
//                        counter++;
//                        show.onCardSelected(v, true, counter,values);
//                    }
//
//                    if (counter == 0) {
//                        show.onCardSelected(v, false, counter,values);
//                    }
//
//                    details.setSelected(!details.isSelected());
//                    holder.itemView.setBackgroundColor(details.isSelected() ? Color.GREEN : Color.WHITE);
//
//                }
//                else
//                {
//                    Intent intent = new Intent(mContext, Activity_User_show.class);
//                    intent.putExtra("data1", details.getUsername());
//                    intent.putExtra("data2", details.getMobileno());
//                    intent.putExtra("data3", details.getEmailid());
//                    intent.putExtra("data4", details.getEmergencyno());
//                    intent.putExtra("data5", details.getText());
//                    mContext.startActivity(intent);
//                }
//
//            }
////            else {
////
////                }
//
//
//
//        });
//
//        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                counter++;
//                show.onCardSelected(v,true,counter,values);
//
//                Intent intent = new Intent(mContext,Send_Mess_Page.class);
//  //              intent.putExtra("name",details.getUsername());
//                intent.putExtra("mob",details.getMobileno());
//                values.add(details.getMobileno());
//
//
//                details.setSelected(!details.isSelected());
//                holder.itemView.setBackgroundColor(details.isSelected()? Color.GREEN : Color.WHITE);
//                IsSelected=true;
//                notifyDataSetChanged();
//
//                return details.isSelected()? true : false;
//            }
//        });
//    }
//
//
//    public interface onShow{
//
//        void onCardSelected(View view ,boolean IsSelected ,int count,ArrayList<String> data);
//
//    }
//
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return infoList.size();
//    }
//
//
//}


package com.example.lenovo.clientapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class User_viewdetails_adapter extends RecyclerView.Adapter<User_viewdetails_adapter.MyViewHolder> {

    boolean isDoubleSelected = false;
    private Context mContext;
    private onShow show;
    TextDrawable drawable;


    String xyz;
    static int counter = 0;


    public static ArrayList<String> values = new ArrayList<>();


    public static Map<String, String> map = new HashMap<>();


    public List<User_details_info> infoList;

    public User_viewdetails_adapter(Context mContext, List<User_details_info> list, onShow show) {
        this.mContext = mContext;
        this.infoList = list;
        this.show = show;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username, emailid, mobileno, emergncyno, text;
        ImageView image1, profile;
        RelativeLayout relativeLayout;
        LinearLayout linearLayout;


        public MyViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            mobileno = (TextView) itemView.findViewById(R.id.mob_num);
            emailid = (TextView) itemView.findViewById(R.id.email);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear1);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.card);
            image1 = (ImageView) itemView.findViewById(R.id.checked);
            profile = (ImageView) itemView.findViewById(R.id.img);

            Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "font/Roboto-Light.ttf");
            username.setTypeface(typeface);
            mobileno.setTypeface(typeface);
            emailid.setTypeface(typeface);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_user_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final User_details_info details = infoList.get(position);
        holder.username.setText(details.getUsername());
        holder.mobileno.setText(String.valueOf(details.getMobileno()));
        holder.emailid.setText(details.getEmailid());

        xyz = String.valueOf(details.getUsername().charAt(0));

        preSelectContacts(details);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(details.getUsername());
        drawable = TextDrawable.builder().beginConfig().toUpperCase().endConfig().buildRoundRect(xyz, color, 150);
        holder.profile.setImageDrawable(drawable);
        holder.itemView.setBackgroundColor(details.isSelected() ? Color.CYAN : Color.WHITE);


        if (details.isSelected()) {
            holder.image1.setVisibility(View.VISIBLE);
        } else {
            holder.image1.setVisibility(View.GONE);
        }


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDoubleSelected) {
                    if (details.isSelected()) {

//                        counter--;
                        map.remove((details.getUsername()+counter));
                        counter--;
                        details.setSelected(false);
                        show.onCardSelected(true, counter);
                    } else {
                        counter++;
                        map.put((details.getUsername()+counter), details.getMobileno());
                        details.setSelected(true);
                        show.onCardSelected(true, counter);
                    }
                    if (counter == 0) {
                        map.clear();
                        isDoubleSelected = false;
                        show.onCardSelected(false, counter);

                    }
                    Toast.makeText(mContext, details.getUsername()+counter, Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();

                } else {
                    Intent intent = new Intent(mContext, Activity_User_show.class);
                    intent.putExtra("data1", details.getUsername());
                    intent.putExtra("data2", details.getMobileno());
                    intent.putExtra("data3", details.getEmailid());
                    intent.putExtra("data4", details.getEmergencyno());
                    intent.putExtra("data5", details.getText());
                    mContext.startActivity(intent);
                }
            }
        });

        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!isDoubleSelected) {
                    map.clear();
                    counter = 1;
                    isDoubleSelected = true;
                    map.put(details.getUsername()+counter, details.getMobileno());
                    details.setSelected(true);
                    show.onCardSelected(true, counter);
                    notifyDataSetChanged();

                    return details.isSelected();
                }
                return true;
            }
        });
    }

    public interface onShow {

        void onCardSelected(boolean IsSelected, int count);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }



    public boolean preSelectContacts(User_details_info userDetails) {
        if (!map.isEmpty()) {
            if (map.containsKey(userDetails.getUsername()+counter)) {
//                Toast.makeText(mContext, map.get(userDetails.getUsername()), Toast.LENGTH_LONG).show();
                if(map.get(userDetails.getUsername()+counter) == userDetails.getMobileno()){
                    userDetails.setSelected(true);
                }
            }
            return true;
        } else {
            isDoubleSelected = false;
            userDetails.setSelected(false);
        }
        return false;
    }
}
