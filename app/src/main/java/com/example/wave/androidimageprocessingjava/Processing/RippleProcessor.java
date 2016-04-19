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

import com.androidrecipes.imageprocessing.ScriptC_ripple;
import com.example.wave.androidimageprocessingjava.CustomElements.CustomSeekBar;

/**
 * Created by Wave on 18.04.2016.
 */
public class RippleProcessor extends Processor{


    private ScriptC_ripple mScript;

    private Task currentTask = null;


    public RippleProcessor(Bitmap bitmap, Context context) {
        super(bitmap, context);
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

        mScript = new ScriptC_ripple(mRS);





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

                mScript.set_centerX(mBitmapIn.getWidth() / 2);
                mScript.set_centerY(mBitmapIn.getHeight() / 2);
                mScript.set_minRadius(0f);

                float amplitude = Math.max(0.01f, 20f/100f);
                mScript.set_scalar(amplitude);

                float dampening = Math.max(0.0001f, 40f/10000f);
                mScript.set_dampler(dampening);

                float frequency = Math.max(0.01f,40f/100f);
                mScript.set_frequency(frequency);

                mScript.forEach_root(mInAllocation, mOutAllocations[index]);

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
