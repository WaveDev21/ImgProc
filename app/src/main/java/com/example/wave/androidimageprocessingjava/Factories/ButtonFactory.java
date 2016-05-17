package com.example.wave.androidimageprocessingjava.Factories;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.Processing.Filter3x3Processor;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.Processing.SaturationProcessor;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.IDrawerControls;
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


    public ButtonFactory(Context context, Bitmap bitmap, ImageView imageView, RelativeLayout leftDrawer) {
        super(context, bitmap, imageView, leftDrawer);
    }

    @Override
    public OperationButton produceButton(ButtonType type) {

        OperationButton button = null;
        Processor processor;
        IDrawerControls controls;

        switch (type){
            case Saturation:
                processor = new SaturationProcessor(bitmap, context);
                controls = new SaturationControlSet(context, processor, imageView, leftDrawer);
                button = new SaturationButton(context, processor, controls);
                break;
            case Sharpen:
                processor = new Filter3x3Processor(bitmap, context);
                controls = new SharpenControlSet(context, processor, imageView, leftDrawer);
                button = new SharpenButton(context, processor, controls);
                break;
            case Smooth:
                processor = new Filter3x3Processor(bitmap, context);
                controls = new SmoothControlSet(context, processor, imageView, leftDrawer);
                button = new SmoothButton(context, processor, controls);
                break;
        }

        return button;

    }
}
