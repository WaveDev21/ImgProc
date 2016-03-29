package com.example.wave.androidimageprocessingjava.Edit;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.wave.androidimageprocessingjava.MainActivity;
import com.example.wave.androidimageprocessingjava.R;

import java.util.List;

public class EditActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mLeftDrawer;
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

        imageView.setImageURI(MainActivity.editedImageUri);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mLeftDrawer = (ListView) findViewById(R.id.leftDrawer);
        mRightDrawer = (FrameLayout) findViewById(R.id.rightDrawer);

        mLeftDrawer.setTag(0);
        mRightDrawer.setTag(1);

        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mLeftDrawer.setBackgroundColor(Color.TRANSPARENT);
        mRightDrawer.setBackgroundColor(Color.TRANSPARENT);


        mLeftDataSet = new String[]{
                "Left item 1",
                "Left item 2",
                "Left item 3"
        };

        mLeftAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mLeftDataSet);
        mLeftDrawer.setAdapter(mLeftAdapter);

        RightDrawerFragment rightDrawer = RightDrawerFragment.newInstance(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.rightDrawer, rightDrawer);
        fragmentTransaction.commit();
/*
        mDrawerToggle = new EditActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.openDrawer,
                R.string.closeDrawer
        );

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mDrawerToggle.syncState();

        if (savedInstanceState != null){
            if(savedInstanceState.getString("DrawerState") == "Opened"){
                getSupportActionBar().setTitle(R.string.openDrawer);
            }
        }else {
            getSupportActionBar().setTitle(R.string.closeDrawer);
        }
*/

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            outState.putString("DrawerState", "Opened");
        }else{
            outState.putString("DrawerState", "Closed");
        }
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
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
