package com.example.wave.androidimageprocessingjava.ProcessingControllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.wave.androidimageprocessingjava.Edit.EditActivity;
import com.example.wave.androidimageprocessingjava.Edit.Histogram.HistogramActivity;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.SaturationVariables;
import com.example.wave.androidimageprocessingjava.R;

import java.io.IOException;

/**
 * Created by Wave on 19.04.2016.
 */
public class HistogramControlSet extends DrawerControls implements NumberPicker.OnValueChangeListener{

    private Context context;
    private Processor processor;
    private ImageView imageView;
    private View view;

    private PopupWindow popupWindow;
    private NumberPicker leftSideNumberPicker;
    private NumberPicker rightSideNumberPicker;

    private int SIZE = 256;
    // Red, Green, Blue
    private int NUMBER_OF_COLOURS = 3;

    private final int RED = 0;
    private final int GREEN = 1;
    private final int BLUE = 2;

    private int[][] colourBins;
    private volatile boolean loaded = false;
    private int maxY;
    private boolean isColored;
    private float offset = 1;
    private LinearLayout histogramPlacement;

    private boolean isClicked = false;

    public HistogramControlSet(Context context, Processor processor, ImageView imageView, View view) {
        this.context = context;
        this.processor = processor;
        this.imageView = imageView;
        this.view = view;

        colourBins = new int[NUMBER_OF_COLOURS][];

        for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
            colourBins[i] = new int[SIZE];
        }

        loaded = false;
    }

    @Override
    public void setControlSet(){

        RelativeLayout containerLayout = new RelativeLayout(context);
        popupWindow = new PopupWindow(context);

        LayoutInflater inflater = ((EditActivity) context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.histogram_toolbox, containerLayout);

        leftSideNumberPicker = (NumberPicker) layout.findViewById(R.id.leftSideNumberPicker);
        leftSideNumberPicker.setMinValue(0);
        leftSideNumberPicker.setMaxValue(255);
        leftSideNumberPicker.setOnValueChangedListener(this);

        rightSideNumberPicker = (NumberPicker) layout.findViewById(R.id.rightSideNumberPicker);
        rightSideNumberPicker.setMinValue(0);
        rightSideNumberPicker.setMaxValue(255);
        rightSideNumberPicker.setValue(255);
        rightSideNumberPicker.setOnValueChangedListener(this);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int width = displaymetrics.widthPixels;
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
//                width/2,
//                ViewGroup.LayoutParams.MATCH_PARENT
//        );


//        layoutParams.setMargins(30, 0, 0, 0);


        LinearLayout histogramPlacement = (LinearLayout) layout.findViewById(R.id.histogramPlacement);
        ViewGroup.LayoutParams params = histogramPlacement.getLayoutParams();
        params.width = width/2;
        histogramPlacement.setLayoutParams(params);
//        histogramPlacement.setLayoutParams(layoutParams);

        popupWindow.setContentView(layout);
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

            startComputingHistogram();
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        // Lewy NumberPicker
        if(picker.getId() == leftSideNumberPicker.getId()){
            if(rightSideNumberPicker.getValue() <= leftSideNumberPicker.getValue()){
                picker.setValue(rightSideNumberPicker.getValue() - 1);
            }
        }

        // Prawy NumberPicker
        if(picker.getId() == rightSideNumberPicker.getId()){
            if(leftSideNumberPicker.getValue() >= rightSideNumberPicker.getValue()){
                picker.setValue(leftSideNumberPicker.getValue() + 1);
            }
        }
    }

    public void startComputingHistogram(){
        if(processor.getmBitmapOut()!=null)
        {
            try {
                new MyAsync().execute();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // ============================================================================================
    // ------------------------------------ Sekcja klas ------------------------------------------
    // ============================================================================================

    class MyAsync extends AsyncTask
    {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            ((EditActivity)context).showDialog(0);
        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub

            try {
                load(processor.getmBitmapOut());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            ((EditActivity)context).dismissDialog(0);
            Log.wtf("wtf", "asdw");
            histogramPlacement = (LinearLayout) ((EditActivity)context).findViewById(R.id.histogramPlacement);
            Log.wtf("wtf", "asdw2");
            histogramPlacement.addView(new MyHistogram(context));
            Log.wtf("wtf", "asdw3");
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            Log.wtf("wtf", "asd4");
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            Log.wtf("wtf", "asdw5");
            int width = displaymetrics.widthPixels;
            int height = displaymetrics.heightPixels;

            int layer_size = (int) context.getResources().getDimension(R.dimen.layer_size);
            Log.wtf("wtf", "asd6");
            popupWindow.update(0, 0, width - (2 *  layer_size), height/2);
            Log.wtf("wtf", "asdw7");
        }

    }

    public void load(Bitmap bi) throws IOException {

        if (bi != null) {
            // Reset all the bins
            for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
                for (int j = 0; j < SIZE; j++) {
                    colourBins[i][j] = 0;
                }
            }

            for (int x = 0; x < bi.getWidth(); x++) {
                for (int y = 0; y < bi.getHeight(); y++) {

                    int pixel = bi.getPixel(x, y);

                    colourBins[RED][Color.red(pixel)]++;
                    colourBins[GREEN][Color.green(pixel)]++;
                    colourBins[BLUE][Color.blue(pixel)]++;
                }
            }
            maxY = 0;

            for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (maxY < colourBins[i][j]) {
                        maxY = colourBins[i][j];
                    }
                }
            }

            loaded = true;
        } else {
            loaded = false;
        }
    }

    class MyHistogram extends View {

        public MyHistogram(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            if (loaded) {
                canvas.drawColor(Color.GRAY);

                Log.e("NIRAV", "Height : " + getHeight() + ", Width : "
                        + getWidth());

                int xInterval = (int) ((double) getWidth() / ((double) SIZE + 1));

                for (int i = 0; i < NUMBER_OF_COLOURS; i++) {

                    Paint wallpaint;

                    wallpaint = new Paint();
                    if (isColored) {
                        if (i == RED) {
                            wallpaint.setColor(Color.RED);
                        } else if (i == GREEN) {
                            wallpaint.setColor(Color.GREEN);
                        } else if (i == BLUE) {
                            wallpaint.setColor(Color.BLUE);
                        }
                    } else {
                        wallpaint.setColor(Color.WHITE);
                    }

                    wallpaint.setStyle(Paint.Style.FILL);

                    Path wallpath = new Path();
                    wallpath.reset();
                    wallpath.moveTo(0, getHeight());
                    for (int j = 0; j < SIZE - 1; j++) {
                        int value = (int) (((double) colourBins[i][j] / (double) maxY) * (getHeight()+100));


                        //if(j==0) {
                        //   wallpath.moveTo(j * xInterval* offset, getHeight() - value);
                        //}
                        // else {
                        wallpath.lineTo(j * xInterval * offset, getHeight() - value);
                        // }
                    }
                    wallpath.lineTo(SIZE * offset, getHeight());
                    canvas.drawPath(wallpath, wallpaint);
                }

            }

        }
    }

}
