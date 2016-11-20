package com.example.wave.androidimageprocessingjava.Processing;

import android.content.Context;
import android.graphics.Bitmap;

import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.util.Log;

import com.androidrecipes.imageprocessing.ScriptC_convolve3x3;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.ScriptVariables;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.SharpenVariables;

/**
 * Created by Wave on 18.04.2016.
 */

public class Filter3x3Processor extends Processor {

    private ScriptC_convolve3x3 mScript;

    public Filter3x3Processor(Bitmap bitmap, Context context) {
        super(bitmap, context);
    }

    @Override
    public void startProcessing() {
        createScript();
    }

    @Override
    public void processScript(ScriptVariables variables) {

        SharpenVariables vars = (SharpenVariables)variables;

        mScript.set_filter(vars.getFilter());

        mScript.set_input(mInAllocation);

        mScript.forEach_root(mInAllocation, mOutAllocation);

        mOutAllocation.copyTo(mBitmapOut);

    }

    @Override
    public void destroyScript() {
        mScript.destroy();
    }


    private void createScript(){
        mRS = RenderScript.create(context);

        mInAllocation = Allocation.createFromBitmap(mRS, mBitmapIn);
        mOutAllocation = Allocation.createFromBitmap(mRS, mBitmapOut);

        mScript = new ScriptC_convolve3x3(mRS);

    }
}
