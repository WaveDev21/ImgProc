package com.example.wave.androidimageprocessingjava.ProcessingControllers;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.wave.androidimageprocessingjava.Edit.EditActivity;
import com.example.wave.androidimageprocessingjava.MenuFragment;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.SingleLutVariables;
import com.example.wave.androidimageprocessingjava.R;
import com.example.wave.androidimageprocessingjava.SettingsActivity;

import java.util.Random;


/**
 * Created by Wave on 19.04.2016.
 */
public class ContrastControlSet extends DrawerControls implements AppCompatSeekBar.OnSeekBarChangeListener{

    private PopupWindow popupWindow;

    private boolean isClicked = false;

    public ContrastControlSet(Context context, Processor processor, ImageView imageView, View view) {
        super(context, processor, imageView, view);
    }

    @Override
    public void setControlSet(){

        if(SettingsActivity.currentMode.equals("AUTO")){
            setOkExitListeners();
        }else{
            LinearLayout containerLayout = new LinearLayout(context);
            popupWindow = new PopupWindow(context);

            LayoutInflater inflater = ((EditActivity) context).getLayoutInflater();
            View popupWindowView = inflater.inflate(R.layout.seekbar_toolbox, containerLayout);

            AppCompatSeekBar seekBar = (AppCompatSeekBar) popupWindowView.findViewById(R.id.leftSeekBar);
            seekBar.setOnSeekBarChangeListener(this);

            final Animation alpha = AnimationUtils.loadAnimation(context, R.anim.anim_alpha);
            ImageButton okButton = (ImageButton) popupWindowView.findViewById(R.id.okButton);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(alpha);
                    processor.overwriteBitmapIn();
                }
            });


            ImageButton exitButton = (ImageButton) popupWindowView.findViewById(R.id.exitButton);
            exitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideContainer();
                    imageView.setImageBitmap(processor.getmBitmapIn());
                    imageView.invalidate();
                }
            });


            popupWindow.setContentView(popupWindowView);

            setContainerStates("");
        }

        imageView.setImageBitmap(processor.getmBitmapIn());
        imageView.invalidate();

    }

    @Override
    public void clearToolbox() {
        ((RelativeLayout)toolbox).removeAllViews();
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

            if(SettingsActivity.currentMode.equals("AUTO")){

                Random rand = new Random();
                int i = rand.nextInt(60) + 20;

                processor.processScript(new SingleLutVariables(computeLut(i)));

                imageView.setImageBitmap(processor.getmBitmapOut());
                imageView.invalidate();
            }else {
                isClicked = true;

                popupWindow.showAtLocation(toolbox, Gravity.BOTTOM, 0, 0);

                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                int width = displaymetrics.widthPixels;

                int layer_size = (int) context.getResources().getDimension(R.dimen.layer_size);

                popupWindow.update(0, 0, width - (2 * layer_size), 100);
            }
        }
    }

    public float[] computeLut(int progress){
        float[] lutTable = new float[256];
        float var;
        float max = 2.0f;
        float a = (float) (max * (progress / 100.0));
        for (int i = 0; i < 256; i++){
            var = (float) (a*(i - (255.0/2.0)) + (255.0/2.0));

            if(var < 0) lutTable[i] = 0;
            else if (var > 255) lutTable[i] = 255;
            else lutTable[i] = var;
        }

        return lutTable;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        processor.processScript(new SingleLutVariables(computeLut(progress)));
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
