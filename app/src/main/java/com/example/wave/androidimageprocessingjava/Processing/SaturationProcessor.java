package com.example.wave.androidimageprocessingjava.Processing;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.wave.androidimageprocessingjava.CustomElements.CustomSeekBar;
import com.example.wave.androidimageprocessingjava.OperationsOnImages.OperationButton;
import com.example.wave.androidimageprocessingjava.ScriptC_saturation;

/**
 * Created by Wave on 02.04.2016.
 */
public class SaturationProcessor extends Processor {


    private ScriptC_saturation mScript;

    private Task currentTask = null;


    public SaturationProcessor(Bitmap bitmap, Context context) {
        super(bitmap, context);
    }

    @Override
    public void startProcessing(){

        if (mImageView != null){

            if (mImageView instanceof ImageView){
                setSeekBar();
            }

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

        mScript = new ScriptC_saturation(mRS);

    }

    private void updateImage(final float f) {
        if (currentTask != null){
            currentTask.cancel(false);
        }

        currentTask = new Task();
        currentTask.execute(f);
    }

    private void setSeekBar(){
        CustomSeekBar seekBar = new CustomSeekBar(context);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                30,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.setMargins(30, 0, 0, 0);

        seekBar.setLayoutParams(layoutParams);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            seekBar.setSplitTrack(false);
        }

        seekBar.setProgress(50);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float max = 2.0f;
                float min = 0.0f;
                float f = (float) ((max-min) * (progress / 100.0) + min);

                updateImage(f);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        mLeftDrawer.addView(seekBar);
    }

    private class Task extends RenderScriptTask{

        Boolean issued = false;

        @Override
        protected Integer doInBackground(Float... values) {
            int index = -1;

            if (isCancelled() == false){
                issued = true;
                index = mCurrentBitmap;

                mScript.set_saturationValue(values[0]);

                mScript.forEach_saturation(mInAllocation, mOutAllocations[index]);

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
