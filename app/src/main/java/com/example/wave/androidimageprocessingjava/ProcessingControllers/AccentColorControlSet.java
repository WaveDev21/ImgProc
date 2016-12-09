package com.example.wave.androidimageprocessingjava.ProcessingControllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.wave.androidimageprocessingjava.Edit.EditActivity;
import com.example.wave.androidimageprocessingjava.MenuFragment;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.AccentColorVariables;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.SaturationVariables;
import com.example.wave.androidimageprocessingjava.R;
import com.example.wave.androidimageprocessingjava.SettingsActivity;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import java.util.Random;

/**
 * Created by Wave on 19.04.2016.
 */
public class AccentColorControlSet extends DrawerControls implements SeekBar.OnSeekBarChangeListener{

    private PopupWindow popupWindow;

    private boolean isClicked = false;
    private float selectedR = 0;
    private float selectedG = 0;
    private float selectedB = 0;

    public AccentColorControlSet(Context context, Processor processor, ImageView imageView, View view) {
        super(context, processor, imageView, view);
    }

    @Override
    public void setControlSet(){

        if(SettingsActivity.currentMode.equals("PRO")){

            final ColorPicker colorPicker = new ColorPicker((EditActivity)context, 150, 150, 150);

            LinearLayout containerLayout = new LinearLayout(context);
            popupWindow = new PopupWindow(context);

            LayoutInflater inflater = ((EditActivity) context).getLayoutInflater();
            View popupWindowView = inflater.inflate(R.layout.accent_color_toolbox, containerLayout);

            final AppCompatSeekBar seekBar = (AppCompatSeekBar) popupWindowView.findViewById(R.id.leftSeekBar);
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

            final Button showColorPickerBtn = (Button) popupWindowView.findViewById(R.id.chooseColorButton);
            showColorPickerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    colorPicker.show();
                    Button okColor = (Button)colorPicker.findViewById(R.id.okColorButton);
                    okColor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            selectedR = (float)colorPicker.getRed();
                            selectedG = (float)colorPicker.getGreen();
                            selectedB = (float)colorPicker.getBlue();

                            showColorPickerBtn.setBackgroundColor(Color.rgb((int)selectedR, (int)selectedG, (int)selectedB));

                            processor.processScript(new AccentColorVariables(seekBar.getProgress(), selectedR, selectedG, selectedB));
                            imageView.setImageBitmap(processor.getmBitmapOut());
                            imageView.invalidate();

                            colorPicker.dismiss();
                        }
                    });
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
                float minX = 0.5f;
                float maxX = 1.5f;
                Random rand = new Random();
                float f = rand.nextFloat() * (maxX - minX) + minX;

                processor.processScript(new SaturationVariables(f));
                imageView.setImageBitmap(processor.getmBitmapOut());
                imageView.invalidate();
            }else{
                isClicked = true;

                popupWindow.showAtLocation(toolbox, Gravity.BOTTOM, 0, 0);

                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                int width = displaymetrics.widthPixels;

                int layer_size = (int) context.getResources().getDimension(R.dimen.layer_size);

                popupWindow.update(0, 0, width - (2 *  layer_size), 130);
            }
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        processor.processScript(new AccentColorVariables(progress, selectedR, selectedG, selectedB));
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
