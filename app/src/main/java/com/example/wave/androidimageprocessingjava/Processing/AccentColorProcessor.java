package com.example.wave.androidimageprocessingjava.Processing;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;

import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.SaturationVariables;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.ScriptVariables;
import com.example.wave.androidimageprocessingjava.ScriptC_colorAccent;
import com.example.wave.androidimageprocessingjava.ScriptC_saturation;

/**
 * Created by Wave on 02.04.2016.
 */
public class AccentColorProcessor extends Processor {


    private ScriptC_colorAccent mScript;

    public AccentColorProcessor(Bitmap bitmap, Context context) {
        super(bitmap, context);
    }

    @Override
    public void startProcessing(){
        createScript();
        this.ready = true;
    }


    private void createScript(){
        mRS = RenderScript.create(context);

        mInAllocation = Allocation.createFromBitmap(mRS, mBitmapIn);
        mOutAllocation = Allocation.createFromBitmap(mRS, mBitmapOut);

        mScript = new ScriptC_colorAccent(mRS);
    }

    @Override
    public void processScript(ScriptVariables variables) {
        SaturationVariables vars = (SaturationVariables)variables;

//        mScript.set_saturationValue(vars.getSaturationValue());

        mScript.forEach_colorAccent(mInAllocation, mOutAllocation);

        mOutAllocation.copyTo(mBitmapOut);
    }

    @Override
    public void destroyScript() {
        mScript.destroy();
    }

    @Override
    public boolean isScriptReady() {
        return ready;
    }


}
