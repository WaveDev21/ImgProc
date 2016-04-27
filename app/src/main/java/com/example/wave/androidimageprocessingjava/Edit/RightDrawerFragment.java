package com.example.wave.androidimageprocessingjava.Edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wave.androidimageprocessingjava.Factories.IButtonFactory;
import com.example.wave.androidimageprocessingjava.Factories.SaturationButtonFactory;
import com.example.wave.androidimageprocessingjava.Factories.SharpenButtonFactory;
import com.example.wave.androidimageprocessingjava.Factories.SmoothButtonFactory;
import com.example.wave.androidimageprocessingjava.MainActivity;
import com.example.wave.androidimageprocessingjava.ProcessingControllers.OperationButton;
import com.example.wave.androidimageprocessingjava.Processing.SaturationProcessor;
//import com.example.wave.androidimageprocessingjava.Processing.Filter3x3Processor;
import com.example.wave.androidimageprocessingjava.R;
import com.example.wave.androidimageprocessingjava.ScriptC_saturation;

import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RightDrawerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RightDrawerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RightDrawerFragment extends Fragment{

    private Context context;
    private ImageView imageView;
    private RelativeLayout leftDrawer;

    private ScriptC_saturation mScript;


    // TODO: Rename and change types and number of parameters
    public static RightDrawerFragment newInstance(Context context, ImageView imageView, RelativeLayout leftDrawer) {
        RightDrawerFragment fragment = new RightDrawerFragment();

        Bundle args = new Bundle();

        fragment.imageView = imageView;
        fragment.context = context;
        fragment.leftDrawer = leftDrawer;

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_drawer, container, false);

        addButtons((RadioGroup) view.findViewById(R.id.toolbox));

        return view;
    }

    private void addButtons(RadioGroup toolbox) {
        IButtonFactory buttonFactory = new SaturationButtonFactory();
        toolbox.addView(buttonFactory.constructButton(context, getBitmap(), this.imageView, this.leftDrawer));
        IButtonFactory buttonFactory1 = new SharpenButtonFactory();
        toolbox.addView(buttonFactory1.constructButton(context, getBitmap(), this.imageView, this.leftDrawer));
        IButtonFactory buttonFactory2 = new SmoothButtonFactory();
        toolbox.addView(buttonFactory2.constructButton(context, getBitmap(), this.imageView, this.leftDrawer));
     //   toolbox.addView(new OperationButton(context, "Ripple", new RippleProcessor(getBitmap(), this.context), this.imageView, this.leftDrawer));
        //toolbox.addView(new OperationButton(context, "Sharpen", new Filter3x3Processor(getBitmap(), this.context), this.imageView, this.leftDrawer));
    }


    private Bitmap getBitmap() {
        Bitmap btm = null;
        try {
            btm = android.provider.MediaStore.Images.Media.getBitmap(context.getContentResolver(), MainActivity.editedImageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return btm;
    }
}
