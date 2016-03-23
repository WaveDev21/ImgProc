package com.example.wave.androidimageprocessingjava.Edit;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

/**
 * Created by Rafał on 22.03.2016.
 */
public class EditActionBarDrawerToggle extends ActionBarDrawerToggle {

    private Activity hostActivity;
    private int mOpenedResource;
    private int mClosedResource;

    public EditActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);

        this.hostActivity = activity;
        this.mOpenedResource = openDrawerContentDescRes;
        this.mClosedResource = closeDrawerContentDescRes;
    }


    @Override
    public void onDrawerOpened(View view){
        int drawerType = (int)view.getTag();

        if (drawerType == 0){
            super.onDrawerOpened(view);
            hostActivity.getActionBar().setTitle(mOpenedResource);
        }
    }

    @Override
    public void onDrawerClosed(View view){
        int drawerType = (int)view.getTag();

        if (drawerType == 0){
            super.onDrawerClosed(view);
            hostActivity.getActionBar().setTitle(mClosedResource);
        }
    }

    @Override
    public void onDrawerSlide(View view, float slideOffset){
        int drawerType = (int)view.getTag();

        if (drawerType == 0){
            super.onDrawerSlide(view, slideOffset);
        }
    }

}
