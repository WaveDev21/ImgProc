package com.example.wave.androidimageprocessingjava.ProcessingControllers;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.Processing.VariablesPackage.SharpenVariables;
import com.example.wave.androidimageprocessingjava.R;

/**
 * Created by Wave on 26.04.2016.
 */
public class SharpenControlSet implements IDrawerControls{

    public final float[] FH1 = {0f, -1f, 0f, -1f , 5f, -1f, 0f, -1f, 0f};
    public final float[] FH2 = {1f, -2f, 1f, -2f , 5f, -2f, 1f, -2f, 1f};
    public final float[] FH3 = {-1f, -1f, -1f, -1f , 9f, -1f, -1f, -1f, -1f};

    private Context context;
    private Processor processor;
    private ImageView imageView;
    private RelativeLayout leftDrawer;

    public SharpenControlSet(Context context, Processor processor, ImageView imageView, RelativeLayout leftDrawer) {
        this.context = context;
        this.processor = processor;
        this.imageView = imageView;
        this.leftDrawer = leftDrawer;
    }

    @Override
    public void setControlSet(){
        RadioGroup group = new RadioGroup(this.context);
        setRadioGroupLayout(group);

        RadioButton buttonFH1 = new RadioButton(this.context);
        setButton(buttonFH1, "FH1");
        setListener(buttonFH1, FH1);

        RadioButton buttonFH2 = new RadioButton(this.context);
        setButton(buttonFH2, buttonFH1, "FH2");
        setListener(buttonFH2, FH2);

        RadioButton buttonFH3 = new RadioButton(this.context);
        setButton(buttonFH3, buttonFH2, "FH3");
        setListener(buttonFH3, FH3);

        group.addView(buttonFH1);
        group.addView(buttonFH2);
        group.addView(buttonFH3);

        leftDrawer.addView(group);
    }

    private void setListener(RadioButton button, final float[] fh) {
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    processor.processScript(new SharpenVariables(fh));
                    imageView.setImageBitmap(processor.getmBitmapOut());
                    imageView.invalidate();
                }
            }
        });
    }

    private void setButton(RadioButton button, String name) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        button.setLayoutParams(params);
        this.setButtonApperiance(button);
        button.setText(name);
    }

    private void setButton(RadioButton button, RadioButton previous, String name) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW, previous.getId());
        button.setLayoutParams(params);
        button.setText(name);
        this.setButtonApperiance(button);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setButtonApperiance(RadioButton button){
        button.setId(button.generateViewId());
        button.setTextSize(20);
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        button.setPadding( 0, 20, 0, 20 );

        button.setBackgroundResource(R.drawable.radaiobuttonbackground);
        button.setButtonDrawable(android.R.color.transparent);
    }

    private void setRadioGroupLayout(RadioGroup group){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        group.setLayoutParams(params);
        group.setGravity(Gravity.CENTER);
    }

    @Override
    public void clearLeftDrawer() {
        leftDrawer.removeAllViews();
    }

}
