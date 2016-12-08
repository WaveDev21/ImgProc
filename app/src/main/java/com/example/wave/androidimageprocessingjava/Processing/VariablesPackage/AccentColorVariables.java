package com.example.wave.androidimageprocessingjava.Processing.VariablesPackage;

/**
 * Created by Wave on 25.04.2016.
 */
public final class AccentColorVariables extends ScriptVariables {

    private int range;

    private float R;
    private float G;
    private float B;

    public AccentColorVariables(int range, float R, float G, float B) {
        this.range = range;
        this.R = R;
        this.G = G;
        this.B = B;
    }

    public int getRangeValue() {
        return range;
    }

    public float getRed() {
        return R;
    }

    public float getGreen() {
        return G;
    }

    public float getBlue() {
        return B;
    }
}
