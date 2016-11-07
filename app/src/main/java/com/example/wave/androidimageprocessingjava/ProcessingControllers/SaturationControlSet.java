package com.example.wave.androidimageprocessingjava.ProcessingControllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.wave.androidimageprocessingjava.CustomElements.CustomSeekBar;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.SaturationVariables;
import com.example.wave.androidimageprocessingjava.R;
import com.wunderlist.slidinglayer.SlidingLayer;

/**
 * Created by Wave on 19.04.2016.
 */
public class SaturationControlSet extends DrawerControls implements SeekBar.OnSeekBarChangeListener{

    private Context context;
    private Processor processor;
    private ImageView imageView;
    private View view;
    private PopupWindow popupWindow;

    private boolean isClicked = false;

    public SaturationControlSet(Context context, Processor processor, ImageView imageView, View view) {
        this.context = context;
        this.processor = processor;
        this.imageView = imageView;
        this.view = view;
    }

    @Override
    public void setControlSet(){
        final CustomSeekBar seekBar = new CustomSeekBar(context);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                30,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.setMargins(30, 0, 0, 0);

        seekBar.setLayoutParams(layoutParams);
        seekBar.setBackgroundColor(Color.BLACK);
        seekBar.getProgressDrawable().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_IN);


        seekBar.post(new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(50);
            }
        });

        seekBar.setOnSeekBarChangeListener(this);

        RelativeLayout containerLayout = new RelativeLayout(context);
        popupWindow = new PopupWindow(context);

        containerLayout.addView(seekBar);
        popupWindow.setContentView(containerLayout);

    }

    @Override
    public void clearToolbox() {
    }

    @Override
    public void hideContainer() {
        if(isClicked){
            isClicked = false;
            popupWindow.dismiss();
        }
    }

    @Override
    public void openContainer() {
        if (!isClicked){
            isClicked = true;

            popupWindow.showAtLocation(view, Gravity.START, 0, 0);
            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            int height = display.getHeight();
            popupWindow.update(0, 0, 100, height);
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float max = 2.0f;
        float min = 0.0f;
        float f = (float) ((max-min) * (progress / 100.0) + min);

        processor.processScript(new SaturationVariables(f));
        imageView.setImageBitmap(processor.getmBitmapOut());
        imageView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
