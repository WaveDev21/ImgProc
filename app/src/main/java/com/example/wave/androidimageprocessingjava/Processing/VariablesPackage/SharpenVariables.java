package com.example.wave.androidimageprocessingjava.Processing.VariablesPackage;

/**
 * Created by Wave on 25.04.2016.
 */
public final class SharpenVariables extends ScriptVariables {
    private float[] filter;

    public SharpenVariables(float[] filter) {
        this.filter = filter;
    }

    public float[] getFilter() {

        return filter;
    }
}
