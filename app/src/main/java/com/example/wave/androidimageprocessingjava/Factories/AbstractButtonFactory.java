package com.example.wave.androidimageprocessingjava.Factories;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.ProcessingControllers.OperationButton;

/**
 * Created by Wave on 04.05.2016.
 */
public abstract class AbstractButtonFactory {

    protected Context context;
    protected Bitmap bitmap;
    protected ImageView imageView;
    protected RelativeLayout leftDrawer;

    public AbstractButtonFactory(Context context, Bitmap bitmap, ImageView imageView, RelativeLayout leftDrawer){
        this.context = context;
        this.bitmap = bitmap;
        this.imageView = imageView;
        this.leftDrawer = leftDrawer;
    }

    public abstract OperationButton produceButton(ButtonType type);
}
