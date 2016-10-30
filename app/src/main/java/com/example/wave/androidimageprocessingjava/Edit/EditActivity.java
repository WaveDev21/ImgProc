package com.example.wave.androidimageprocessingjava.Edit;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.wave.androidimageprocessingjava.MainActivity;
import com.example.wave.androidimageprocessingjava.R;

public class EditActivity extends AppCompatActivity {

    //private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerListener;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout mLeftDrawer;
    private FrameLayout mRightDrawer;
    private ArrayAdapter mLeftAdapter;
    private ArrayAdapter mRightAdapter;
    private String[] mLeftDataSet;
    private String[] mRightDataSet;
    private RelativeLayout CorrentImageLayout;

    private ImageView imageView;

    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit);

        imageView = (ImageView) findViewById(R.id.originalImageView);

        assert imageView != null;
        imageView.setImageURI(MainActivity.editedImageUri);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mLeftDrawer = (RelativeLayout) findViewById(R.id.leftDrawer);
        mRightDrawer = (FrameLayout) findViewById(R.id.rightDrawer);

        mLeftDrawer.setTag(0);
        mRightDrawer.setTag(1);

        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerLayout.openDrawer(mRightDrawer);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, mRightDrawer);

        RightDrawerFragment rightDrawer = RightDrawerFragment.newInstance(this, imageView, mLeftDrawer);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.rightDrawer, rightDrawer);
        fragmentTransaction.commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        mDrawerListener.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//       if(mDrawerListener.onOptionsItemSelected(item))
//           return true;

       return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
//        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
//            outState.putString("DrawerState", "Opened");
//        }else{
//            outState.putString("DrawerState", "Closed");
//        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
//        mDrawerListener.onConfigurationChanged(newConfig);
    }
}
