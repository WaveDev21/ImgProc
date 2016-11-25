package com.example.wave.androidimageprocessingjava.ProcessingControllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;

import com.example.wave.androidimageprocessingjava.CustomElements.CustomSeekBar;
import com.example.wave.androidimageprocessingjava.Edit.EditActivity;
import com.example.wave.androidimageprocessingjava.MenuFragment;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.ContrastVariables;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.HistogramVariables;
import com.example.wave.androidimageprocessingjava.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Wave on 19.04.2016.
 */
public class ContrastControlSet extends DrawerControls implements AppCompatSeekBar.OnSeekBarChangeListener{

    private Context context;
    private Processor processor;
    private ImageView imageView;
    private View view;

    private PopupWindow popupWindow;

    private View popupWindowView;

    private boolean isClicked = false;

    public ContrastControlSet(Context context, Processor processor, ImageView imageView, View view) {
        this.context = context;
        this.processor = processor;
        this.imageView = imageView;
        this.view = view;

    }

    @Override
    public void setControlSet(){

//        CustomSeekBar seekBar = new CustomSeekBar(context);
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//                30,
//                RelativeLayout.LayoutParams.MATCH_PARENT
//        );
//        layoutParams.setMargins(30, 0, 0, 0);
//
//        seekBar.setLayoutParams(layoutParams);
//        seekBar.setBackgroundColor(Color.LTGRAY);
//        seekBar.getProgressDrawable().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_IN);
//        seekBar.setMax(0);
//        seekBar.setMax(100);
//        seekBar.setProgress(50);
//        seekBar.refreshDrawableState();
//
//        seekBar.setOnSeekBarChangeListener(this);
//
//        RelativeLayout containerLayout = new RelativeLayout(context);
//        popupWindow = new PopupWindow(context);
//
//        containerLayout.addView(seekBar);
//        popupWindow.setContentView(containerLayout);
//
//        setContainerStates("");


        LinearLayout containerLayout = new LinearLayout(context);
        popupWindow = new PopupWindow(context);

        LayoutInflater inflater = ((EditActivity) context).getLayoutInflater();
        popupWindowView = inflater.inflate(R.layout.seekbar_toolbox, containerLayout);

        AppCompatSeekBar seekBar = (AppCompatSeekBar) popupWindowView.findViewById(R.id.leftSeekBar);
        seekBar.setOnSeekBarChangeListener(this);

        popupWindow.setContentView(popupWindowView);


        setContainerStates("");



    }

    @Override
    public void clearToolbox() {
    }

    @Override
    public void hideContainer() {
        if(isClicked){
            isClicked = false;
            popupWindow.dismiss();
        }
    }

    @Override
    public void openContainer() {
        if (!isClicked){
            isClicked = true;

//            popupWindow.showAtLocation(view, Gravity.START, 0, 0);
//            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
//            int height = display.getHeight();
//            popupWindow.update(0, 0, 100, height);

            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            int width = displaymetrics.widthPixels;
            int height = displaymetrics.heightPixels;

            int layer_size = (int) context.getResources().getDimension(R.dimen.layer_size);

            popupWindow.update(0, 0, width - (2 *  layer_size), 200);
        }
    }

    public float[] computeLut(int progress){
        float[] lutTable = new float[256];
        float var;
        float max = 2.0f;
        float a = (float) (max * (progress / 100.0));
        for (int i = 0; i < 256; i++){
            var = (float) (a*(i - (255.0/2.0)) + (255.0/2.0));

            if(var < 0) lutTable[i] = 0;
            else if (var > 255) lutTable[i] = 255;
            else lutTable[i] = var;
        }

        return lutTable;
    }

    public void computeLutOnAuto(){ // na razie zbędne

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        processor.processScript(new ContrastVariables(computeLut(progress)));
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
