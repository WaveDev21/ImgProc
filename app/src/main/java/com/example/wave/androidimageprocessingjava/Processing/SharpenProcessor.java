package com.example.wave.androidimageprocessingjava.Processing;

import android.content.Context;
import android.graphics.Bitmap;

import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.util.Log;

import com.androidrecipes.imageprocessing.ScriptC_convolve3x3;

/**
 * Created by Wave on 18.04.2016.
 */
public class SharpenProcessor extends Processor {

    private ScriptC_convolve3x3 mScript;

    private Task currentTask = null;

    public SharpenProcessor(Bitmap bitmap, Context context) {
        super(bitmap, context);
        Log.wtf("wtf", "wtf");
    }

    @Override
    public void startProcessing() {
        if (mImageView != null){

            createScript();
            updateImage(1.0f);

        }else{
            Log.i("error", "mImageView must be set");
        }
    }

    private void createScript(){
        mRS = RenderScript.create(context);

        mInAllocation = Allocation.createFromBitmap(mRS, mBitmapIn);
        mOutAllocations = new Allocation[NUM_BITMAPS];
        for(int i = 0; i< NUM_BITMAPS; ++i){
            mOutAllocations[i] = Allocation.createFromBitmap(mRS, mBitmapsOutArray[i]);
        }

        mScript = new ScriptC_convolve3x3(mRS);

    }

    private void updateImage(final float f) {
        if (currentTask != null){
            currentTask.cancel(false);
        }

        currentTask = new Task();
        currentTask.execute(f);
    }


    private class Task extends RenderScriptTask{

        Boolean issued = false;

        @Override
        protected Integer doInBackground(Float... values) {
            int index = -1;

            if (isCancelled() == false){
                issued = true;
                index = mCurrentBitmap;

                float filter[] = {0f, -1f, 0f, -1f , 5f, -1f, 0f, -1f, 0f};
                Log.wtf("wtf", "1");
                mScript.set_filter(filter);

                mScript.set_input(mInAllocation);

                mScript.forEach_root(mInAllocation, mOutAllocations[index]);
                Log.wtf("wtf", "2");
                mOutAllocations[index].copyTo(mBitmapsOutArray[index]);
                mCurrentBitmap = (mCurrentBitmap + 1) % NUM_BITMAPS;
            }
            return index;
        }

        void updateView(final Integer result){
            if (result != -1){
                super.updateView(mImageView, mBitmapsOutArray[result]);
            }
        }

        protected void onPostExecute(Integer result){ updateView(result);}

        protected void onCancelled(Integer result){
            if (issued){
                updateView(result);
            }
        }


    }
}
