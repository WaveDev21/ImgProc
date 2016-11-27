package com.example.wave.androidimageprocessingjava.Processing;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;

import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.HistogramVariables;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.ScriptVariables;
import com.example.wave.androidimageprocessingjava.ScriptC_useLutTable;

/**
 * Created by Wave on 02.04.2016.
 */
public class ExtHistogramProcessor extends Processor {


    private ScriptC_useLutTable mScript;

    public ExtHistogramProcessor(Bitmap bitmap, Context context) {
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

        mScript = new ScriptC_useLutTable(mRS);
    }

    @Override
    public void processScript(ScriptVariables variables) {
        HistogramVariables vars = (HistogramVariables)variables;

        mScript.set_redLut(vars.getRedLut());
        mScript.set_greenLut(vars.getGreenLut());
        mScript.set_blueLut(vars.getBlueLut());

        mScript.forEach_useLutTable(mInAllocation, mOutAllocation);

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
