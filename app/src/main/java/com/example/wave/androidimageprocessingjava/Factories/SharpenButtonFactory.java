package com.example.wave.androidimageprocessingjava.Factories;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.Processing.Filter3x3Processor;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.IDrawerControls;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.OperationButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SharpenButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SharpenControlSet;

/**
 * Created by Wave on 26.04.2016.
 */
public class SharpenButtonFactory implements IButtonFactory{

    @Override
    public OperationButton constructButton(Context context, Bitmap bitmap, ImageView imageView, RelativeLayout leftDrawer) {
        Processor processor = new Filter3x3Processor(bitmap, context);
        IDrawerControls controls = new SharpenControlSet(context, processor, imageView, leftDrawer);
        return new SharpenButton(context, processor, controls);
    }
}