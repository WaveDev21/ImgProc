package com.example.wave.androidimageprocessingjava.Processing.VariablesPackage;

/**
 * Created by Wave on 25.04.2016.
 */
public final class SaturationVariables extends ScriptVariables {

    private float saturationValue;

    public SaturationVariables(float saturationValue) {
        this.saturationValue = saturationValue;
    }

    public float getSaturationValue() {

        return saturationValue;
    }
}
