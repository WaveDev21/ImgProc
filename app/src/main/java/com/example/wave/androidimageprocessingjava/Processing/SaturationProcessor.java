package com.example.wave.androidimageprocessingjava.Processing;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.wave.androidimageprocessingjava.CustomElements.CustomSeekBar;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.SaturationVariables;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.ScriptVariables;
import com.example.wave.androidimageprocessingjava.ScriptC_saturation;

/**
 * Created by Wave on 02.04.2016.
 */
public class SaturationProcessor extends Processor {


    private ScriptC_saturation mScript;

    public SaturationProcessor(Bitmap bitmap, Context context) {
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

        mScript = new ScriptC_saturation(mRS);
    }

    @Override
    public void processScript(ScriptVariables variables) {
        SaturationVariables vars = (SaturationVariables)variables;

        mScript.set_saturationValue(vars.getSaturationValue());

        mScript.forEach_saturation(mInAllocation, mOutAllocation);

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
