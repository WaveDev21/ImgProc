package com.example.wave.androidimageprocessingjava.Edit;

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
import android.widget.TextView;

import com.example.wave.androidimageprocessingjava.MainActivity;
import com.example.wave.androidimageprocessingjava.MenuFragment;
import com.example.wave.androidimageprocessingjava.R;
import com.wunderlist.slidinglayer.LayerTransformer;
import com.wunderlist.slidinglayer.SlidingLayer;
import com.wunderlist.slidinglayer.transformer.SlideJoyTransformer;

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

    private SlidingLayer mSlidingLayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit);

        imageView = (ImageView) findViewById(R.id.originalImageView);

        assert imageView != null;
        imageView.setImageURI(MainActivity.editedImageUri);



        mSlidingLayer = (SlidingLayer) findViewById(R.id.rightSlidingLayer);

//        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) mSlidingLayer.getLayoutParams();

        mSlidingLayer.setStickTo(SlidingLayer.STICK_TO_RIGHT);

        LayerTransformer transformer = new SlideJoyTransformer();
        mSlidingLayer.setLayerTransformer(transformer);

        mSlidingLayer.setShadowSize(0);
        mSlidingLayer.setShadowDrawable(null);

        int offsetDistance = true ? getResources().getDimensionPixelOffset(R.dimen.offset_distance) : 0;
        mSlidingLayer.setOffsetDistance(offsetDistance);

        int previewOffset = true ? getResources().getDimensionPixelOffset(R.dimen.preview_offset_distance) : -1;
        mSlidingLayer.setPreviewOffsetDistance(previewOffset);




        mSlidingLayer = (SlidingLayer) findViewById(R.id.topSlidingLayer);

//        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) mSlidingLayer.getLayoutParams();

        mSlidingLayer.setStickTo(SlidingLayer.STICK_TO_TOP);
//        rlp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
//        rlp.height = getResources().getDimensionPixelSize(R.dimen.layer_size);
//        mSlidingLayer.setLayoutParams(rlp);

        transformer = new SlideJoyTransformer();
        mSlidingLayer.setLayerTransformer(transformer);

        offsetDistance = getResources().getDimensionPixelOffset(R.dimen.top_offset_distance);
        mSlidingLayer.setOffsetDistance(offsetDistance);


        RightDrawerFragment rightDrawer = RightDrawerFragment.newInstance(this, imageView, mLeftDrawer);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.rightDrawer, rightDrawer);
        fragmentTransaction.commit();


        CustomToolboxFragment customToolbox = new CustomToolboxFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.topSlidingLayer, customToolbox);
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

    public void buttonClicked(View view){

    }
}
