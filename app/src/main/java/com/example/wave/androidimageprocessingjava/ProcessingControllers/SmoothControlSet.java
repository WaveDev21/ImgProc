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
 * Created by Wave on 27.04.2016.
 */
public class SmoothControlSet extends DrawerControls{

    public final float[] FL1 = {1f, 1f, 1f, 1f , 1f, 1f, 1f, 1f, 1f};
    public final float[] FL2 = {1f, 1f, 1f, 1f , 2f, 1f, 1f, 1f, 1f};
    public final float[] FL3 = {1f, 1f, 1f, 1f , 4f, 1f, 1f, 1f, 1f};
    public final float[] Gauss = {1f, 2f, 1f, 2f , 4f, 2f, 1f, 2f, 1f};

    public SmoothControlSet(Context context, Processor processor, ImageView imageView, RelativeLayout leftToolbox) {
        super(context, processor, imageView, leftToolbox);
    }

    @Override
    public void setControlSet(){

        if(SettingsActivity.currentMode.equals("PRO")){

            RadioGroup group = new RadioGroup(this.context);
            setRadioGroupLayout(group);

            RadioButton buttonFH1 = new RadioButton(this.context);
            setButton(buttonFH1, "FL1");
            setListener(buttonFH1, FL1);

            RadioButton buttonFH2 = new RadioButton(this.context);
            setButton(buttonFH2, buttonFH1, "FL2");
            setListener(buttonFH2, FL2);

            RadioButton buttonFH3 = new RadioButton(this.context);
            setButton(buttonFH3, buttonFH2, "FL3");
            setListener(buttonFH3, FL3);

            RadioButton buttonGauss = new RadioButton(this.context);
            setButton(buttonGauss, buttonFH3, "Gauss");
            setListener(buttonGauss, Gauss);

            group.addView(buttonFH1);
            group.addView(buttonFH2);
            group.addView(buttonFH3);
            group.addView(buttonGauss);

            ((RelativeLayout)toolbox).addView(group);
            DrawerControls.setContainerStates("smooth");
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

        if(slider.isOpened() && DrawerControls.containerState.equals("") && !SettingsActivity.currentMode.equals("AUTO")){
            slider.closeLayer(true);
            ((RelativeLayout)toolbox).removeAllViews();
        }
    }

    @Override
    public void openContainer() {
        SlidingLayer slider = (SlidingLayer) ((EditActivity)context).findViewById(R.id.leftSlidingLayer);

        if(SettingsActivity.currentMode.equals("AUTO")){
            processor.processScript(new SharpenVariables(FL1));
            imageView.setImageBitmap(processor.getmBitmapOut());
            imageView.invalidate();
        }else if (slider.isClosed() && DrawerControls.containerState.equals("smooth")){
            slider.openLayer(true);
        }
    }

}

