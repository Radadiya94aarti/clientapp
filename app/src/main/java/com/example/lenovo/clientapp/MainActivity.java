package com.example.lenovo.clientapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private View tab1,tab2,tab3;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);


        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.BottomTabView);

        mViewPager.setOffscreenPageLimit(2);

        tabLayout.addTab(tabLayout.newTab().setText("View"));
        tabLayout.addTab(tabLayout.newTab().setText("Add"));
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));



        tab1 = LayoutInflater.from(this).inflate(R.layout.custom_bottombar_tabs, null);
        (tab1.findViewById(R.id.tabIcon)).setBackgroundResource(R.drawable.viewdetails);
        TabLayout.Tab customTab1 = tabLayout.getTabAt(0);
        customTab1.setCustomView(tab1);



        tab2 = LayoutInflater.from(this).inflate(R.layout.custom_bottombar_tabs, null);
        (tab2.findViewById(R.id.tabIcon)).setBackgroundResource(R.drawable.adduser);
        TabLayout.Tab customTab2 = tabLayout.getTabAt(1);
        customTab2.setCustomView(tab2);


        tab3 = LayoutInflater.from(this).inflate(R.layout.custom_bottombar_tabs, null);
        (tab3.findViewById(R.id.tabIcon)).setBackgroundResource(R.drawable.userprofile);
        TabLayout.Tab customTab3 = tabLayout.getTabAt(2);
        customTab3.setCustomView(tab3);


        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        int numOfTabs;

        public SectionsPagerAdapter(FragmentManager fm , int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){

                case 0 :
                    User_details_home first = new User_details_home();
                    return first;

                case 1 :
                    Fragment_Add_User second = new Fragment_Add_User();
                    return second;

                case 2 :
                    ThirdTabFragment third = new ThirdTabFragment();
                    return third;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {

            return numOfTabs;
        }
    }


}
