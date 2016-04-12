package com.example.wave.androidimageprocessingjava.Processing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.example.wave.androidimageprocessingjava.OperationsOnImages.OperationButton;

/**
 * Created by Wave on 10.04.2016.
 */
public abstract class RenderScriptTask extends AsyncTask<Float, Integer, Integer> {

    Boolean issued = false;

    void updateView(View mImageView, final Bitmap bmp){

            if (mImageView instanceof OperationButton){

                ((OperationButton) mImageView).setCompoundDrawables(null, new Drawable() {
                    @Override
                    public void draw(Canvas canvas) {

                        canvas.drawBitmap(
                                Bitmap.createScaledBitmap(bmp , 100, (int) (100 * bmp.getHeight()) / bmp.getWidth(), false),
                                -50, 0,
                                null);
                    }

                    @Override
                    public void setAlpha(int alpha) {

                    }

                    @Override
                    public void setColorFilter(ColorFilter colorFilter) {

                    }

                    @Override
                    public int getOpacity() {
                        return 0;
                    }
                }, null, null);
            } else if (mImageView instanceof ImageView){
                ((ImageView) mImageView).setImageBitmap(bmp);
                mImageView.invalidate();
            }
    }




}
