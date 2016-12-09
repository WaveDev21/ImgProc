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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.wave.androidimageprocessingjava.Edit.EditActivity;
import com.example.wave.androidimageprocessingjava.MenuFragment;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
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

;
;


/**
 * Created by Wave on 19.04.2016.
 */
public class HistogramAlignControlSet extends DrawerControls{

    private int[] histBinSums;


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
    private double[][] colourDist;
    private volatile boolean loaded = false;
    private boolean isColored;
    private float offset = 1;
    private LinearLayout histogramPlacement;

    private boolean isClicked = false;

    public HistogramAlignControlSet(Context context, Processor processor, ImageView imageView, View view) {
        super(context, processor, imageView, view);

        colourBins = new int[NUMBER_OF_COLOURS][];
        colourDist = new double[NUMBER_OF_COLOURS][];
        histBinSums = new int[NUMBER_OF_COLOURS];
        for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
            colourBins[i] = new int[SIZE];
            colourDist[i] = new double[SIZE];
        }

        loaded = false;
    }

    @Override
    public void setControlSet(){

        setOkExitListeners();

        imageView.setImageBitmap(processor.getmBitmapIn());
        imageView.invalidate();

    }

    @Override
    public void clearToolbox() {
        ((RelativeLayout)toolbox).removeAllViews();
    }

    @Override
    public void hideContainer() {
        if(isClicked){
            isClicked = false;
        }
    }

    @Override
    public void openContainer() {
        if (!isClicked){
            isClicked = true;
            startComputingHistogram();
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

    public void computeLut(){
        for (int i = 0; i < 256; i++){
            RedLutTable[i] = (float) ((colourDist[0][i] - colourDist[0][0])/(1.0 - colourDist[0][0])) * 255.0f;
            GreenLutTable[i] =  (float)((colourDist[1][i] - colourDist[1][0])/(1.0 - colourDist[1][0])) * 255.0f;
            BlueLutTable[i] = (float)((colourDist[2][i] - colourDist[2][0])/(1.0 - colourDist[2][0])) * 255.0f;

        }
    }

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

                computeLut();

                processor.processScript(new HistogramVariables(RedLutTable, GreenLutTable, BlueLutTable));
                imageView.setImageBitmap(processor.getmBitmapOut());
                imageView.invalidate();

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
                    colourDist[i][j] = 0.0;
                }
                histBinSums[i] = 0;
            }


            for (int x = 0; x < bi.getWidth(); x++) {
                for (int y = 0; y < bi.getHeight(); y++) {

                    int pixel = bi.getPixel(x, y);

                    colourBins[RED][Color.red(pixel)]++;
                    colourBins[GREEN][Color.green(pixel)]++;
                    colourBins[BLUE][Color.blue(pixel)]++;

                }
            }

            double pointSum = bi.getWidth() * bi.getHeight();

            double sum;

            for (int x = 0; x < 3; x++) {
                sum = 0.0;
                for (int y = 0; y < SIZE; y++) {
                    sum += colourBins[x][y];
                    colourDist[x][y] =  (sum / pointSum);
                }


            }


            loaded = true;
        } else {
            loaded = false;
        }
    }

}
