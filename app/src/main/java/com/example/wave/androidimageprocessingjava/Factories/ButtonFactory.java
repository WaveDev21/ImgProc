package com.example.wave.androidimageprocessingjava.Factories;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.Processing.ContrastProcesor;
import com.example.wave.androidimageprocessingjava.Processing.ExtHistogramProcessor;
import com.example.wave.androidimageprocessingjava.Processing.Filter3x3Processor;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.Processing.SaturationProcessor;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.ContastButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.ContrastControlSet;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.DrawerControls;

import com.example.wave.androidimageprocessingjava.ProcessingControllers.HistogramButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.HistogramControlSet;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.OperationButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SaturationButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SaturationControlSet;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SharpenButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SharpenControlSet;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SmoothButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SmoothControlSet;
import com.wunderlist.slidinglayer.SlidingLayer;

/**
 * Created by Wave on 04.05.2016.
 */
public class ButtonFactory extends AbstractButtonFactory {


    public ButtonFactory(Context context, Bitmap bitmap, ImageView imageView, RelativeLayout toolbox) {
        super(context, bitmap, imageView, toolbox);
    }

    @Override
    public OperationButton produceButton(ButtonType type) {

        OperationButton button = null;
        Processor processor;
        DrawerControls controls;

        switch (type){
            case Saturation:
                processor = new SaturationProcessor(bitmap, context);
                controls = new SaturationControlSet(context, processor, imageView, toolbox);
                button = new SaturationButton(context, processor, controls);
                break;
            case Sharpen:
                processor = new Filter3x3Processor(bitmap, context);
                controls = new SharpenControlSet(context, processor, imageView, toolbox);
                button = new SharpenButton(context, processor, controls);
                break;
            case Smooth:
                processor = new Filter3x3Processor(bitmap, context);
                controls = new SmoothControlSet(context, processor, imageView, toolbox);
                button = new SmoothButton(context, processor, controls);
                break;
            case Histogram:
                processor = new ExtHistogramProcessor(bitmap, context);
                controls = new HistogramControlSet(context, processor, imageView, toolbox);
                button = new HistogramButton(context, processor, controls);
                break;
            case Contrast:
                processor = new ContrastProcesor(bitmap, context);
                controls = new ContrastControlSet(context, processor, imageView, toolbox);
                button = new ContastButton(context, processor, controls);
        }

        return button;

    }
}
