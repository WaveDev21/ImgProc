package com.example.wave.androidimageprocessingjava.Factories;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.Processing.SaturationProcessor;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.IDrawerControls;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.OperationButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SaturationButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SaturationControlSet;

/**
 * Created by Wave on 22.04.2016.
 */
public class SaturationButtonFactory implements IButtonFactory{

    @Override
    public OperationButton constructButton(Context context, Bitmap bitmap, ImageView imageView, RelativeLayout leftDrawer) {
        Processor processor = new SaturationProcessor(bitmap, context);
        IDrawerControls controls = new SaturationControlSet(context, processor, imageView, leftDrawer);
        return new SaturationButton(context, processor, controls);
    }
}
