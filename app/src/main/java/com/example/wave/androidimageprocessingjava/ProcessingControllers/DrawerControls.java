package com.example.wave.androidimageprocessingjava.ProcessingControllers;

/**
 * Created by Wave on 21.04.2016.
 */
public abstract class DrawerControls {

    public static String containerState = "empty";
    public static String previousContainerState = "empty";

    public abstract void setControlSet();
    public abstract void clearToolbox();
    public abstract void hideContainer();
    public abstract void openContainer();
}
