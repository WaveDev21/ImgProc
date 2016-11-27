package com.example.wave.androidimageprocessingjava.ProcessingControllers;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.Edit.EditActivity;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.R;
import com.wunderlist.slidinglayer.SlidingLayer;

/**
 * Created by Wave on 21.04.2016.
 */
public abstract class DrawerControls{

    public static String containerState = "";
    public static String previousContainerState = "";


    protected Context context;
    protected Processor processor;
    protected ImageView imageView;
    protected View toolbox;


    public abstract void setControlSet();
    public abstract void clearToolbox();
    public abstract void hideContainer();
    public abstract void openContainer();

    public static void setContainerStates(String currentState){
        if(containerState.equals("")){
            previousContainerState = currentState;
            containerState = currentState;
        }else{
            previousContainerState = containerState;
            containerState = currentState;
        }
    }

    public DrawerControls(Context context, Processor processor, ImageView imageView, View view){
        this.context = context;
        this.processor = processor;
        this.imageView = imageView;
        this.toolbox = view;
    }

    public void setLeftToolboxListeners(){

        final Animation alpha = AnimationUtils.loadAnimation(context, R.anim.anim_alpha);
        final SlidingLayer slider = (SlidingLayer) ((EditActivity)context).findViewById(R.id.leftSlidingLayer);


        View leftToolboxContainer = (View)toolbox.getParent();
        ImageButton okButton = (ImageButton) leftToolboxContainer.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(alpha);
                processor.overwriteBitmapIn();

                assert slider != null;
                slider.closeLayer(true);
            }
        });


        ImageButton exitButton = (ImageButton) leftToolboxContainer.findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert slider != null;
                slider.closeLayer(true);
            }
        });
    }

}
