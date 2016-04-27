package com.example.wave.androidimageprocessingjava.Factories;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.ProcessingControllers.OperationButton;

/**
 * Created by Wave on 22.04.2016.
 */
public interface IButtonFactory {
    public OperationButton constructButton(Context context, Bitmap bitmap, ImageView imageView, RelativeLayout leftDrawer);
}
