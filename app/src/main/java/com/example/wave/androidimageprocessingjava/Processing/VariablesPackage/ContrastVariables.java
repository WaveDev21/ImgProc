package com.example.wave.androidimageprocessingjava.Processing.VariablesPackage;

/**
 * Created by Wave on 25.04.2016.
 */
public final class ContrastVariables extends ScriptVariables {

    private float[] lut;


    public ContrastVariables(float[] lut) {
        this.lut = lut;

    }


    public float[] getLut() {
        return lut;
    }

}
