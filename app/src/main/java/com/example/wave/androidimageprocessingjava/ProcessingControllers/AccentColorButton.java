package com.example.wave.androidimageprocessingjava.ProcessingControllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CompoundButton;

import com.example.wave.androidimageprocessingjava.MenuFragment;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.R;

/**
 * Created by Wave on 21.04.2016.
 */
public class AccentColorButton extends OperationButton implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    public AccentColorButton(final Context context, Processor processor, DrawerControls controlSet) {
        super(context, processor, controlSet);
    }

    @Override
    protected void setNameAndImage() {
        this.setText("Accent Color");

        this.setCompoundDrawables(null, new Drawable() {
            @Override
            public void draw(Canvas canvas) {

                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.accent_color);
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
        this.setOnClickListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            processor.startProcessing();
            controlSet.hideContainer();
            controlSet.setControlSet();
            controlSet.openContainer();
        }else{
            processor.destroyScript();
            controlSet.hideContainer();
        }
    }

    @Override
    public void onClick(View v) {
        if(MenuFragment.currentMode.equals("AUTO")){
//            processor.startProcessing();
            controlSet.openContainer();
        }
    }
}
