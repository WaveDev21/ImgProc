package com.example.wave.androidimageprocessingjava.Processing;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;

import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.ScriptVariables;

/**
 * Created by Wave on 30.03.2016.
 */
public abstract class Processor {

    public static Bitmap mBitmapIn = null;
    protected Bitmap mBitmapOut = null;

    protected RenderScript mRS;
    protected Allocation mInAllocation;
    protected Allocation mOutAllocation;

    protected Context context;

    protected boolean ready = false;

    protected Processor(Bitmap bitmap, Context context){
        mBitmapIn = bitmap;
        this.context = context;

        mBitmapOut = Bitmap.createBitmap(mBitmapIn.getWidth(),
                mBitmapIn.getHeight(), mBitmapIn.getConfig());

    }

    public abstract void startProcessing();
    public abstract void processScript(ScriptVariables variables);
    public abstract void destroyScript();
    public abstract boolean isScriptReady();

    public Bitmap getmBitmapOut(){
        return mBitmapOut;
    }

    public Bitmap getmBitmapIn(){
        return mBitmapIn;
    }

    public void overwriteBitmapIn(){
        mBitmapIn.recycle();
        mBitmapIn = Bitmap.createBitmap(mBitmapOut);
    }
}