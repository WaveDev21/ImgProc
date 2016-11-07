package com.example.wave.androidimageprocessingjava.ProcessingControllers;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.R;

/**
 * Created by Wave on 06.04.2016.
 */
public abstract class OperationButton extends RadioButton{

    protected Context context;
    protected Processor processor;
    protected DrawerControls controlSet;

    public OperationButton(Context context, Processor processor, DrawerControls contolSet) {
        super(context);

        this.context = context;
        this.processor = processor;
        this.controlSet = contolSet;

        this.setStandardButtonLayout();
        this.setNameAndImage();
        this.setListener();
    }

    private void setStandardButtonLayout() {
        this.setLayoutParams(new RadioGroup.LayoutParams(100, 100));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        }
        this.setGravity(Gravity.BOTTOM);

        this.setBackgroundResource(R.drawable.radaiobuttonbackground);
        this.setButtonDrawable(android.R.color.transparent);
    }

    protected abstract void setNameAndImage();
    protected abstract void setListener();


}
