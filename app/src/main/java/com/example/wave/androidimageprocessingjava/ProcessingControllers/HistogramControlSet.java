package com.example.wave.androidimageprocessingjava.ProcessingControllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;;

import com.example.wave.androidimageprocessingjava.Edit.EditActivity;
import com.example.wave.androidimageprocessingjava.MenuFragment;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.HistogramVariables;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.SaturationVariables;
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
public class HistogramControlSet extends DrawerControls implements NumberPicker.OnValueChangeListener, Switch.OnCheckedChangeListener{

    private Context context;
    private Processor processor;
    private ImageView imageView;
    private View view;

    private PopupWindow popupWindow;
    private NumberPicker leftSideNumberPicker;
    private NumberPicker rightSideNumberPicker;

    private SwitchCompat redColorOn;
    private SwitchCompat greenColorOn;
    private SwitchCompat blueColorOn;

    private View popupWindowView;
    private BarGraphSeries<DataPoint> RedColorSeries;
    private BarGraphSeries<DataPoint> GreenColorSeries;
    private BarGraphSeries<DataPoint> BlueColorSeries;

    private PointsGraphSeries<DataPoint> PointerSeries;

    private float[] RedLutTable = new float[256];
    private float[] GreenLutTable = new float[256];
    private float[] BlueLutTable = new float[256];

    public float minRedVal = 255;
    public float maxRedVal = 0;
    public float minGreenVal = 255;
    public float maxGreenVal = 0;
    public float minBlueVal = 255;
    public float maxBlueVal = 0;

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
        RedColorSeries = new BarGraphSeries<>();
        GreenColorSeries = new BarGraphSeries<>();
        BlueColorSeries = new BarGraphSeries<>();

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

        redColorOn = (SwitchCompat) popupWindowView.findViewById(R.id.redColorOn);
        redColorOn.setOnCheckedChangeListener(this);
        greenColorOn = (SwitchCompat) popupWindowView.findViewById(R.id.greenColorOn);
        greenColorOn.setOnCheckedChangeListener(this);
        blueColorOn = (SwitchCompat) popupWindowView.findViewById(R.id.blueColorOn);
        blueColorOn.setOnCheckedChangeListener(this);

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

        RedColorSeries.setColor(Color.RED);
        GreenColorSeries.setColor(Color.GREEN);
        BlueColorSeries.setColor(Color.BLUE);

        histogramGraph.getLegendRenderer().setVisible(true);
        histogramGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        // ustawianie szerokości widoku histogramu
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int width = displaymetrics.widthPixels;

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

            if(oldVal == 0 && newVal == 255){
                picker.setValue(0);
            }else if(rightSideNumberPicker.getValue() <= leftSideNumberPicker.getValue()){
                picker.setValue(rightSideNumberPicker.getValue() - 1);
            }
        }

        // Prawy NumberPicker
        if(picker.getId() == rightSideNumberPicker.getId()){

            if(oldVal == 255 && newVal == 0){
                picker.setValue(255);
            }else if(leftSideNumberPicker.getValue() >= rightSideNumberPicker.getValue()){
                picker.setValue(leftSideNumberPicker.getValue() + 1);
            }
        }
        computeLut();

        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>();
        series.setTitle("PT");
        series.setColor(Color.YELLOW);
        series.setShape(PointsGraphSeries.Shape.TRIANGLE);
        series.setSize((float) 4);

        series.appendData(new DataPoint(leftSideNumberPicker.getValue(), 0), true, 256);
        series.appendData(new DataPoint(rightSideNumberPicker.getValue(), 0), true, 256);

        setupPointerSeries(series);

        processor.processScript(new HistogramVariables(RedLutTable, GreenLutTable, BlueLutTable));
        imageView.setImageBitmap(processor.getmBitmapOut());
        imageView.invalidate();

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

    public void computeLut(){
        if(redColorOn.isChecked()){
            for (int i = 0; i < 256; i++){
                RedLutTable[i] = (float) ((255.0/rightSideNumberPicker.getValue() - leftSideNumberPicker.getValue()) * (i - leftSideNumberPicker.getValue()));
            }
        }else{
            for (int i = 0; i < 256; i++){
                RedLutTable[i] = (float) ((255.0/255.0 - 0) * (i));
            }
        }
        if(greenColorOn.isChecked()){
            for (int i = 0; i < 256; i++){
                GreenLutTable[i] = (float) ((255.0/rightSideNumberPicker.getValue() - leftSideNumberPicker.getValue()) * (i - leftSideNumberPicker.getValue()));
            }
        }else{
            for (int i = 0; i < 256; i++){
                GreenLutTable[i] = (float) ((255.0/255.0 - 0) * (i));
            }
        }
        if(blueColorOn.isChecked()){
            for (int i = 0; i < 256; i++){
                BlueLutTable[i] = (float) ((255.0/rightSideNumberPicker.getValue() - leftSideNumberPicker.getValue()) * (i - leftSideNumberPicker.getValue()));
            }
        }else{
            for (int i = 0; i < 256; i++){
                BlueLutTable[i] = (float) ((255.0/255.0 - 0) * (i));
            }
        }
    }

    public void computeLutOnAuto(){
        for (int i = 0; i < 256; i++){
            RedLutTable[i] = (float) ((255.0/maxRedVal - minRedVal) * (i - minRedVal));
            GreenLutTable[i] = (float) ((255.0/maxGreenVal - minGreenVal) * (i - minGreenVal));
            BlueLutTable[i] = (float) ((255.0/maxBlueVal - minBlueVal) * (i - minBlueVal));
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == redColorOn.getId()){
            if(isChecked){
                addSeriesToGraph(RedColorSeries);
            }else{
                removeSeriesFromGraph(RedColorSeries);
            }
        }

        if (buttonView.getId() == greenColorOn.getId()){
            if(isChecked){
                addSeriesToGraph(GreenColorSeries);
            }else{
                removeSeriesFromGraph(GreenColorSeries);
            }
        }

        if (buttonView.getId() == blueColorOn.getId()){
            if(isChecked){
                addSeriesToGraph(BlueColorSeries);
            }else{
                removeSeriesFromGraph(BlueColorSeries);
            }
        }
        computeLut();

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
            if(MenuFragment.currentMode.equals("AUTO")){

                computeLutOnAuto();

                processor.processScript(new HistogramVariables(RedLutTable, GreenLutTable, BlueLutTable));
                imageView.setImageBitmap(processor.getmBitmapOut());
                imageView.invalidate();
            }else{
                setupSeries(new ArrayList<BarGraphSeries>(){{
                    add(RedColorSeries);
                    add(GreenColorSeries);
                    add(BlueColorSeries);
                }});
                computeLut();

                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                int width = displaymetrics.widthPixels;
                int height = displaymetrics.heightPixels;

                int layer_size = (int) context.getResources().getDimension(R.dimen.layer_size);

                popupWindow.update(0, 0, width - (2 *  layer_size), height/2);
            }
        }
    }

    public void setupSeries(ArrayList<BarGraphSeries> seriesList){
        GraphView graph = (GraphView) popupWindowView.findViewById(R.id.histogramGraph);
        for (BarGraphSeries series: seriesList) {
            graph.addSeries(series);
        }
    }

    public void addSeriesToGraph(BarGraphSeries series){
        GraphView graph = (GraphView) popupWindowView.findViewById(R.id.histogramGraph);
        graph.addSeries(series);
    }

    public void removeSeriesFromGraph(BarGraphSeries series){
        GraphView graph = (GraphView) popupWindowView.findViewById(R.id.histogramGraph);
        graph.removeSeries(series);
    }


    public void setupPointerSeries(PointsGraphSeries<DataPoint> series){

        GraphView graph = (GraphView) popupWindowView.findViewById(R.id.histogramGraph);
        graph.removeSeries(PointerSeries);
        graph.addSeries(series);
        PointerSeries = series;
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

                    minRedVal = Color.red(pixel) < minRedVal ? Color.red(pixel) : minRedVal;
                    maxRedVal = Color.red(pixel) > maxRedVal ? Color.red(pixel) : maxRedVal;
                    minGreenVal = Color.green(pixel) < minGreenVal ? Color.green(pixel) : minGreenVal;
                    maxGreenVal = Color.green(pixel) > maxGreenVal ? Color.green(pixel) : maxGreenVal;
                    minBlueVal = Color.blue(pixel) < minBlueVal ? Color.blue(pixel) : minBlueVal;
                    maxBlueVal = Color.blue(pixel) > maxBlueVal ? Color.blue(pixel) : maxBlueVal;
                }
            }


            for (int i = 0; i < SIZE; i++) {
                RedColorSeries.appendData(new DataPoint(i, colourBins[RED][i]), true, 256);
            }

            for (int i = 0; i < SIZE; i++) {
                GreenColorSeries.appendData(new DataPoint(i, colourBins[GREEN][i]), true, 256);
            }

            for (int i = 0; i < SIZE; i++) {
                BlueColorSeries.appendData(new DataPoint(i, colourBins[BLUE][i]), true, 256);
            }


            loaded = true;
        } else {
            loaded = false;
        }
    }

}
