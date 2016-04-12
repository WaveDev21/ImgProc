package com.example.wave.androidimageprocessingjava.Processing;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Wave on 30.03.2016.
 */
public abstract class Processor {

    protected final int NUM_BITMAPS = 3;
    protected int mCurrentBitmap = 0;
    protected Bitmap mBitmapIn;
    protected Bitmap mBitmapOut;
    protected Bitmap[] mBitmapsOutArray;
    protected View mImageView = null;
    protected RelativeLayout mLeftDrawer = null;

    protected RenderScript mRS;
    protected Allocation mInAllocation;
    protected Allocation[] mOutAllocations;

    protected Context context;

    protected Processor(Bitmap bitmap, Context context){
        this.mBitmapIn = bitmap;
        this.context = context;

        mBitmapsOutArray = new Bitmap[NUM_BITMAPS];
        for (int i = 0; i < NUM_BITMAPS; i++){
            mBitmapsOutArray[i] = Bitmap.createBitmap(mBitmapIn.getWidth(),
                    mBitmapIn.getHeight(), mBitmapIn.getConfig());
        }

        mCurrentBitmap += (mCurrentBitmap + 1) % NUM_BITMAPS;

    }

    public abstract void startProcessing();

    public void setmImageView(View view){
        this.mImageView = view;
    }

    public void setmLeftDrawer(RelativeLayout leftDrawer) { this.mLeftDrawer = leftDrawer; }
}