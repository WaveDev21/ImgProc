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
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SmoothButton;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.SmoothControlSet;

/**
 * Created by Wave on 27.04.2016.
 */
public class SmoothButtonFactory implements IButtonFactory {
    @Override
    public OperationButton constructButton(Context context, Bitmap bitmap, ImageView imageView, RelativeLayout leftDrawer) {
        Processor processor = new Filter3x3Processor(bitmap, context);
        IDrawerControls controls = new SmoothControlSet(context, processor, imageView, leftDrawer);
        return new SmoothButton(context, processor, controls);
    }
}
