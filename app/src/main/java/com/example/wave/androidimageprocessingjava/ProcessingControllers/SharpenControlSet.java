package com.example.wave.androidimageprocessingjava.ProcessingControllers;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.Edit.EditActivity;
import com.example.wave.androidimageprocessingjava.MenuFragment;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.SharpenVariables;
import com.example.wave.androidimageprocessingjava.R;
import com.example.wave.androidimageprocessingjava.SettingsActivity;
import com.wunderlist.slidinglayer.SlidingLayer;

/**
 * Created by Wave on 26.04.2016.
 */
public class SharpenControlSet extends DrawerControls{


    public final float[] HP0 = {-1f, -1f, -1f, -1f , 9f, -1f, -1f, -1f, -1f};
    public final float[] HP1 = {0f, -1f, 0f, -1f , 5f, -1f, 0f, -1f, 0f};
    public final float[] HP2 = {1f, -2f, 1f, -2f , 5f, -2f, 1f, -2f, 1f};
    public final float[] HP3 = {0f, -1f, 0f, -1f , 20f, -1f, 0f, -1f, 0f};

    public SharpenControlSet(Context context, Processor processor, ImageView imageView, RelativeLayout leftToolbox) {
        super(context, processor, imageView, leftToolbox);
    }

    @Override
    public void setControlSet(){

        if(SettingsActivity.currentMode.equals(this.proModeString)){

            RadioGroup group = new RadioGroup(this.context);
            setRadioGroupLayout(group);

            RadioButton buttonHP0 = new RadioButton(this.context);
            setButton(buttonHP0, "HP0");
            setListener(buttonHP0, HP0);

            RadioButton buttonHP1 = new RadioButton(this.context);
            setButton(buttonHP1, buttonHP0, "HP1");
            setListener(buttonHP1, HP1);

            RadioButton buttonHP2 = new RadioButton(this.context);
            setButton(buttonHP2, buttonHP1, "HP2");
            setListener(buttonHP2, HP2);

            RadioButton buttonHP3 = new RadioButton(this.context);
            setButton(buttonHP3, buttonHP2, "HP3");
            setListener(buttonHP3, HP3);

            group.addView(buttonHP0);
            group.addView(buttonHP1);
            group.addView(buttonHP2);
            group.addView(buttonHP3);

            ((RelativeLayout)toolbox).addView(group);

            DrawerControls.setContainerStates("sharpen");
        }

        setOkExitListeners();

        imageView.setImageBitmap(processor.getmBitmapIn());
        imageView.invalidate();
    }

    private void setListener(RadioButton button, final float[] fh) {
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    processor.processScript(new SharpenVariables(fh));
                    imageView.setImageBitmap(processor.getmBitmapOut());
                    imageView.invalidate();
                }
            }
        });
    }

    private void setButton(RadioButton button, String name) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        button.setLayoutParams(params);
        this.setButtonApperiance(button);
        button.setText(name);
    }

    private void setButton(RadioButton button, RadioButton previous, String name) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW, previous.getId());
        button.setLayoutParams(params);
        button.setText(name);
        this.setButtonApperiance(button);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setButtonApperiance(RadioButton button){
        button.setId(RadioButton.generateViewId());
        button.setTextSize(20);
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        button.setPadding( 0, 20, 0, 20 );

        button.setBackgroundResource(R.drawable.radaiobuttonbackground);
        button.setButtonDrawable(android.R.color.transparent);
    }

    private void setRadioGroupLayout(RadioGroup group){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        group.setLayoutParams(params);
        group.setGravity(Gravity.CENTER);
    }

    @Override
    public void clearToolbox() {
        ((RelativeLayout)toolbox).removeAllViews();
    }

    @Override
    public void hideContainer() {
        SlidingLayer slider = (SlidingLayer) ((EditActivity)context).findViewById(R.id.leftSlidingLayer);

        if(slider.isOpened() && DrawerControls.containerState.equals("") && !SettingsActivity.currentMode.equals(this.autoModeString)){
            slider.closeLayer(true);
            ((RelativeLayout)toolbox).removeAllViews();
        }
    }

    @Override
    public void openContainer() {
        SlidingLayer slider = (SlidingLayer) ((EditActivity)context).findViewById(R.id.leftSlidingLayer);

        if(SettingsActivity.currentMode.equals(this.autoModeString)){
            processor.processScript(new SharpenVariables(HP0));
            imageView.setImageBitmap(processor.getmBitmapOut());
            imageView.invalidate();
        } else if (slider.isClosed() && DrawerControls.containerState.equals("sharpen")){
            slider.openLayer(true);
        }
    }

}
