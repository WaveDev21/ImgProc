package com.example.wave.androidimageprocessingjava.ProcessingControllers;

import android.content.Context;
import android.os.Build;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.wave.androidimageprocessingjava.CustomElements.CustomSeekBar;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.SaturationVariables;

/**
 * Created by Wave on 19.04.2016.
 */
public class SaturationControlSet implements SeekBar.OnSeekBarChangeListener, IDrawerControls{

    private Context context;
    private Processor processor;
    private ImageView imageView;
    private RelativeLayout leftDrawer;

    public SaturationControlSet(Context context, Processor processor, ImageView imageView, RelativeLayout leftDrawer) {
        this.context = context;
        this.processor = processor;
        this.imageView = imageView;
        this.leftDrawer = leftDrawer;
    }

    @Override
    public void setControlSet(){
        CustomSeekBar seekBar = new CustomSeekBar(context);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                30,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.setMargins(30, 0, 0, 0);

        seekBar.setLayoutParams(layoutParams);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            seekBar.setSplitTrack(false);
        }

        seekBar.setProgress(50);

        seekBar.setOnSeekBarChangeListener(this);

        leftDrawer.addView(seekBar);
    }

    @Override
    public void clearLeftDrawer() {
        leftDrawer.removeAllViews();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float max = 2.0f;
        float min = 0.0f;
        float f = (float) ((max-min) * (progress / 100.0) + min);

        processor.processScript(new SaturationVariables(f));
        imageView.setImageBitmap(processor.getmBitmapOut());
        imageView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
