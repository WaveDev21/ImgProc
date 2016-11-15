package com.example.wave.androidimageprocessingjava.ProcessingControllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;;

import com.example.wave.androidimageprocessingjava.Edit.EditActivity;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

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
    private View popupWindowView;
    private BarGraphSeries<DataPoint> RedColorSeries = new BarGraphSeries<>();
    private BarGraphSeries<DataPoint> GreenColorSeries = new BarGraphSeries<>();
    private BarGraphSeries<DataPoint> BlueColorSeries = new BarGraphSeries<>();

    private int SIZE = 256;
    // Red, Green, Blue
    private int NUMBER_OF_COLOURS = 3;

    private int[][] colourBins;
    private volatile boolean loaded = false;
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
        popupWindowView = inflater.inflate(R.layout.histogram_toolbox, containerLayout);

        leftSideNumberPicker = (NumberPicker) popupWindowView.findViewById(R.id.leftSideNumberPicker);
        leftSideNumberPicker.setMinValue(0);
        leftSideNumberPicker.setMaxValue(255);
        leftSideNumberPicker.setOnValueChangedListener(this);

        rightSideNumberPicker = (NumberPicker) popupWindowView.findViewById(R.id.rightSideNumberPicker);
        rightSideNumberPicker.setMinValue(0);
        rightSideNumberPicker.setMaxValue(255);
        rightSideNumberPicker.setValue(255);
        rightSideNumberPicker.setOnValueChangedListener(this);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int width = displaymetrics.widthPixels;

        // Ustawianie graph view
        GraphView histogramGraph = (GraphView) popupWindowView.findViewById(R.id.histogramGraph);

        Viewport viewport = histogramGraph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(15000);

        viewport.setXAxisBoundsManual(true);
        viewport.setMinX(0);
        viewport.setMaxX(255);
        viewport.setScrollable(true);

        histogramGraph.getViewport().setScalable(true);
        histogramGraph.getViewport().setScalableY(true);

        // ustawianie legendy
        RedColorSeries.setTitle("RED");
        GreenColorSeries.setTitle("GREEN");
        BlueColorSeries.setTitle("BLUE");
        histogramGraph.getLegendRenderer().setVisible(true);
        histogramGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        // ustawianie szerokości widoku histogramu
        ViewGroup.LayoutParams params = histogramGraph.getLayoutParams();
        params.width = width/2 ;
        histogramGraph.setLayoutParams(params);

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

    private class MyAsync extends AsyncTask
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
                load(processor.getmBitmapIn());
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

            GraphView graph = (GraphView) popupWindowView.findViewById(R.id.histogramGraph);
            RedColorSeries.setColor(Color.RED);
            graph.addSeries(RedColorSeries);
            GreenColorSeries.setColor(Color.GREEN);
            graph.addSeries(GreenColorSeries);
            BlueColorSeries.setColor(Color.BLUE);
            graph.addSeries(BlueColorSeries);

            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            int width = displaymetrics.widthPixels;
            int height = displaymetrics.heightPixels;

            int layer_size = (int) context.getResources().getDimension(R.dimen.layer_size);

            popupWindow.update(0, 0, width - (2 *  layer_size), height/2);

        }

    }

    public void load(Bitmap bi) throws IOException {

        int RED = 0;
        int GREEN = 1;
        int BLUE = 2;

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

            int maxY = 0;

            for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (maxY < colourBins[i][j]) {
                        maxY = colourBins[i][j];
                    }
                }
            }

            for (int i = 0; i < SIZE; i++) {
                RedColorSeries.appendData(new DataPoint(i, colourBins[RED][i]), true, 300);
            }

            for (int i = 0; i < SIZE; i++) {
                GreenColorSeries.appendData(new DataPoint(i, colourBins[GREEN][i]), true, 300);
            }

            for (int i = 0; i < SIZE; i++) {
                BlueColorSeries.appendData(new DataPoint(i, colourBins[BLUE][i]), true, 300);
            }


            loaded = true;
        } else {
            loaded = false;
        }
    }

}
