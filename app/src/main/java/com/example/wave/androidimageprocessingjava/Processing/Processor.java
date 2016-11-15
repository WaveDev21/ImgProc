package com.example.wave.androidimageprocessingjava.Processing;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.SaturationVariables;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.ScriptVariables;

/**
 * Created by Wave on 30.03.2016.
 */
public abstract class Processor {

    protected Bitmap mBitmapIn = null;
    protected Bitmap mBitmapOut = null;

    protected RenderScript mRS;
    protected Allocation mInAllocation;
    protected Allocation mOutAllocation;

    protected Context context;

    protected Processor(Bitmap bitmap, Context context){
        this.mBitmapIn = bitmap;
        this.context = context;

        mBitmapOut = Bitmap.createBitmap(mBitmapIn.getWidth(),
                mBitmapIn.getHeight(), mBitmapIn.getConfig());

    }

    public abstract void startProcessing();
    public abstract void processScript(ScriptVariables variables);
    public abstract void destroyScript();

    public Bitmap getmBitmapOut(){
        return mBitmapOut;
    }

    public Bitmap getmBitmapIn(){
        return mBitmapIn;
    }

}