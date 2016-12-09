package com.example.wave.androidimageprocessingjava.Edit;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.wave.androidimageprocessingjava.MainActivity;
import com.example.wave.androidimageprocessingjava.MenuFragment;
import com.example.wave.androidimageprocessingjava.R;
import com.example.wave.androidimageprocessingjava.SettingsActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.Sharer;
import com.facebook.share.widget.ShareDialog;
import com.wunderlist.slidinglayer.LayerTransformer;
import com.wunderlist.slidinglayer.SlidingLayer;
import com.wunderlist.slidinglayer.transformer.AlphaTransformer;
import com.wunderlist.slidinglayer.transformer.SlideJoyTransformer;

public class EditActivity extends AppCompatActivity {

    private CallbackManager callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callback = CallbackManager.Factory.create();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit);

        ImageView imageView = (ImageView) findViewById(R.id.originalImageView);

        assert imageView != null;
        imageView.setImageURI(MainActivity.editedImageUri);

        // Konfiguracja prawego slidera
        SlidingLayer rightSlidingLayer = (SlidingLayer) findViewById(R.id.rightSlidingLayer);
        assert rightSlidingLayer != null;
        rightSlidingLayer.setStickTo(SlidingLayer.STICK_TO_RIGHT);
        LayerTransformer transformer = new AlphaTransformer();
        rightSlidingLayer.setLayerTransformer(transformer);
        int offsetDistance = getResources().getDimensionPixelOffset(R.dimen.offset_distance);
        rightSlidingLayer.setOffsetDistance(offsetDistance);
//        int previewOffset = getResources().getDimensionPixelOffset(R.dimen.preview_offset_distance);
//        rightSlidingLayer.setPreviewOffsetDistance(previewOffset);
        ViewGroup.LayoutParams params = rightSlidingLayer.getLayoutParams();
        rightSlidingLayer.setLayoutParams(params);


        // Konfiguracja lefego slidera
        SlidingLayer leftSlidingLayer = (SlidingLayer) findViewById(R.id.leftSlidingLayer);
        assert leftSlidingLayer != null;
        leftSlidingLayer.setStickTo(SlidingLayer.STICK_TO_LEFT);
        params = leftSlidingLayer.getLayoutParams();

        leftSlidingLayer.setLayoutParams(params);
//        leftSlidingLayer.setLayerTransformer(transformer);

        // Konfiguracja gurnego slidera
        final SlidingLayer topSlidingLayer = (SlidingLayer) findViewById(R.id.topSlidingLayer);
        assert topSlidingLayer != null;
        topSlidingLayer.setStickTo(SlidingLayer.STICK_TO_TOP);
        transformer = new SlideJoyTransformer();
        topSlidingLayer.setLayerTransformer(transformer);
        offsetDistance = getResources().getDimensionPixelOffset(R.dimen.top_offset_distance);
        topSlidingLayer.setOffsetDistance(offsetDistance);

        RelativeLayout showTopSlider = (RelativeLayout) findViewById(R.id.showTopBarButton);
        showTopSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topSlidingLayer.openLayer(true);
            }
        });

        TextView currentMode = (TextView) findViewById(R.id.currentMode);
        currentMode.setText(SettingsActivity.currentMode + " Mode");

        RelativeLayout leftToolbox = (RelativeLayout) findViewById(R.id.leftToolBox);
        assert leftToolbox != null;

        RightDrawerFragment rightDrawer = RightDrawerFragment.newInstance(this, imageView, leftSlidingLayer, leftToolbox);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.rightDrawer, rightDrawer);
        fragmentTransaction.commit();


        CustomToolboxFragment customToolbox = CustomToolboxFragment.newInstance(this, callback);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.topSlidingLayer, customToolbox);
        fragmentTransaction.commit();

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callback.onActivityResult(requestCode, resultCode, data);

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

    @Override
    protected Dialog onCreateDialog(int id) {
        ProgressDialog dataLoadProgress = new ProgressDialog(this);
        dataLoadProgress.setMessage("Loading...");
        dataLoadProgress.setIndeterminate(true);
        dataLoadProgress.setCancelable(false);
        dataLoadProgress.setProgressStyle(android.R.attr.progressBarStyleLarge);
        return dataLoadProgress;

    }
}
