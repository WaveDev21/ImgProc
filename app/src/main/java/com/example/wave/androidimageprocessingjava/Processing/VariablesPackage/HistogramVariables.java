package com.example.wave.androidimageprocessingjava.Processing.VariablesPackage;

/**
 * Created by Wave on 25.04.2016.
 */
public final class HistogramVariables extends ScriptVariables {

    private float[] redLut;
    private float[] greenLut;
    private float[] blueLut;

    public HistogramVariables(float[] redLut, float[] greenLut, float[] blueLut) {
        this.redLut = redLut;
        this.greenLut = greenLut;
        this.blueLut = blueLut;
    }


    public float[] getRedLut() {
        return redLut;
    }

    public float[] getGreenLut() {
        return greenLut;
    }

    public float[] getBlueLut() {
        return blueLut;
    }

}
