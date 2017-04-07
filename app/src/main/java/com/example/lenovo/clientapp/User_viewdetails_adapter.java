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

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;


public class User_viewdetails_adapter extends RecyclerView.Adapter<User_viewdetails_adapter.MyViewHolder> {

    private boolean isDoubleSelected = false;
    private Context mContext;
    private onShow show;
    TextDrawable drawable;


    String xyz;
    int counter = 0;

    public static ArrayList<String> values = new ArrayList<>();

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
                        counter--;
                        values.remove(details.getMobileno());
                        show.onCardSelected(true, counter);
                        details.setSelected(false);
                        holder.itemView.setBackgroundColor(Color.GREEN);

                    } else {
                        counter++;
                        values.add(details.getMobileno());
                        details.setSelected(true);
                        holder.itemView.setBackgroundColor(Color.CYAN);
                        show.onCardSelected(true, counter);
                    }
                    if (counter == 0) {
                        values.clear();
                        isDoubleSelected = false;
                        show.onCardSelected(false, counter);
                    }

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
                    counter = 1;
                    isDoubleSelected = true;
                    show.onCardSelected(true, counter);
                    values.add(details.getMobileno());
                    details.setSelected(true);
                    holder.itemView.setBackgroundColor(Color.RED);
                    notifyDataSetChanged();

                    return details.isSelected();
                }
                return true;
            }
        });

    }


    public interface onShow {

        void onCardSelected(boolean IsSelected, int count);

        //
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        return infoList.size();
    }


}
