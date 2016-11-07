package com.example.wave.androidimageprocessingjava.ProcessingControllers;

/**
 * Created by Wave on 21.04.2016.
 */
public abstract class DrawerControls {

    public static String containerState = "";
    public static String previousContainerState = "";

    public abstract void setControlSet();
    public abstract void clearToolbox();
    public abstract void hideContainer();
    public abstract void openContainer();

    public static void setContainerStates(String currentState){
        if(containerState.equals("")){
            previousContainerState = currentState;
            containerState = currentState;
        }else{
            previousContainerState = containerState;
            containerState = currentState;
        }
    }
}
