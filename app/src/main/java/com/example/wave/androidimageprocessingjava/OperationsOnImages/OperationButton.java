package com.example.wave.androidimageprocessingjava.OperationsOnImages;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.R;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;

/**
 * Created by Wave on 06.04.2016.
 */
public class OperationButton extends RadioButton{



    public OperationButton(Context context, String name, final Processor processor, final View imageView, final RelativeLayout leftDrawer) {
        super(context);

        this.setLayoutParams(new RadioGroup.LayoutParams(100, 100));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        }
        this.setGravity(Gravity.BOTTOM);
        this.setText(name);

        this.setBackground(R.drawable.radaiobuttonbackground);
        this.setButtonDrawable(android.R.color.transparent);

        /*this.setCompoundDrawables(null, new Drawable() {
            @Override
            public void draw(Canvas canvas) {

                //Bitmap bitmapLittle = Bitmap.createBitmap(bitmap , 0, 0, 100, (int) (100 * bitmap.getHeight()) / bitmap.getWidth());

                canvas.drawBitmap();
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
        */

        processor.setmLeftDrawer(leftDrawer);

        processor.setmImageView(this);
        processor.startProcessing();

        this.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    processor.setmImageView(imageView);
                    processor.startProcessing();
                }else{
                    leftDrawer.removeAllViews();
                }

            }
        });

    }

    private void setBackground(int radaiobuttonbackground) {

    }
}
