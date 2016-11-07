package com.example.wave.androidimageprocessingjava.ProcessingControllers;

/**
 * Created by Wave on 21.04.2016.
 */
public abstract class DrawerControls {

    public static String containerState = "sharpen";
    public static String previousContainerState = "sharpen";

    public abstract void setControlSet();
    public abstract void clearToolbox();
    public abstract void hideContainer();
    public abstract void openContainer();
}
