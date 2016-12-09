package com.example.wave.androidimageprocessingjava.Factories;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.MenuFragment;
import com.example.wave.androidimageprocessingjava.Processing.AccentColorProcessor;
import com.example.wave.androidimageprocessingjava.Processing.SingleLutProcesor;
import com.example.wave.androidimageprocessingjava.Processing.Filter3x3Processor;
import com.example.wave.androidimageprocessingjava.Processing.LutProcessor;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.Processing.SaturationProcessor;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.AccentColorButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.AccentColorControlSet;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.ContastButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.ContrastControlSet;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.DrawerControls;

import com.example.wave.androidimageprocessingjava.ProcessingControllers.ExposureButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.ExposureControlSet;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.GammaButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.GammaControlSet;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.HistogramAlignButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.HistogramAlignControlSet;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.HistogramExtButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.HistogramExtControlSet;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.OperationButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SaturationButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SaturationControlSet;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SharpenButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SharpenControlSet;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SmoothButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SmoothControlSet;

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
            case HistogramExt:
                processor = new LutProcessor(bitmap, context);
                controls = new HistogramExtControlSet(context, processor, imageView, toolbox);
                button = new HistogramExtButton(context, processor, controls);
                break;
            case HistogramAlign:
                processor = new LutProcessor(bitmap, context);
                controls = new HistogramAlignControlSet(context, processor, imageView, toolbox);
                button = new HistogramAlignButton(context, processor, controls);
                break;
            case Contrast:
                processor = new SingleLutProcesor(bitmap, context);
                controls = new ContrastControlSet(context, processor, imageView, toolbox);
                button = new ContastButton(context, processor, controls);
                break;
            case Exposure:
                processor = new SingleLutProcesor(bitmap, context);
                controls = new ExposureControlSet(context, processor, imageView, toolbox);
                button = new ExposureButton(context, processor, controls);
                break;
            case Gamma:
                processor = new SingleLutProcesor(bitmap, context);
                controls = new GammaControlSet(context, processor, imageView, toolbox);
                button = new GammaButton(context, processor, controls);
                break;
            case AccentColor:
                processor = new AccentColorProcessor(bitmap, context);
                controls = new AccentColorControlSet(context, processor, imageView, toolbox);
                button = new AccentColorButton(context, processor, controls);
                break;
        }

        return button;

    }
}
