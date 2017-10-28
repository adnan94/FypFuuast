package com.example.ali.myapplication.Activities.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;


import com.example.ali.myapplication.Activities.Adaptor.Navigations_ItemsAdapter;
import com.example.ali.myapplication.Activities.User_Ui.UserHomeFragment;
import com.example.ali.myapplication.R;

public class UserHome extends AppCompatActivity {

    Button form_btn_list, form_btn_details;
    public DrawerLayout drawer_layout;
    public ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public FrameLayout customer_container;
    public String[] menuName = {"Change Password", "Terms & Conditions", "Send Messages", "Add Customer", "Pay Bill Online"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.maincontainer, new UserHomeFragment()).commit();

        customer_container = (FrameLayout) findViewById(R.id.maincontainer);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        View viewinflate = UserHome.this.getLayoutInflater().inflate(R.layout.nav_header_main, null);
        Navigations_ItemsAdapter navigations_itemsAdapter = new Navigations_ItemsAdapter(UserHome.this, menuName, null);
        mDrawerList.setAdapter(navigations_itemsAdapter);
        mDrawerList.addHeaderView(viewinflate);

        mDrawerToggle = new ActionBarDrawerToggle(UserHome.this, drawer_layout, null, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                customer_container.setTranslationX(slideOffset * drawerView.getWidth());
                drawer_layout.bringChildToFront(drawerView);
                drawer_layout.requestLayout();
            }
        };
        drawer_layout.setDrawerListener(mDrawerToggle);



    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


