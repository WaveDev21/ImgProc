package com.example.wave.androidimageprocessingjava.ProcessingControllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.widget.CompoundButton;

import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.R;

/**
 * Created by Wave on 26.04.2016.
 */
public class SharpenButton extends OperationButton implements CompoundButton.OnCheckedChangeListener{
    public SharpenButton(final Context context, Processor processor, IDrawerControls controlSet) {
        super(context, processor, controlSet);
    }

    @Override
    protected void setNameAndImage() {
        this.setText("Sharpen");

        this.setCompoundDrawables(null, new Drawable() {
            @Override
            public void draw(Canvas canvas) {

                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.birghtnes);
                canvas.drawBitmap(
                        Bitmap.createScaledBitmap(bmp , 100, (int) (100 * bmp.getHeight()) / bmp.getWidth(), false),
                        -50, 0,
                        null);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        }, null, null);
    }

    @Override
    protected void setListener() {
        this.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            controlSet.clearLeftDrawer();
            controlSet.setControlSet();
            processor.startProcessing();
        }else{
            processor.destroyScript();

        }
    }
}
