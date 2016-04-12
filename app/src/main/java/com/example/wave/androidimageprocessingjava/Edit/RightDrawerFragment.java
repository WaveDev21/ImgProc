package com.example.wave.androidimageprocessingjava.Edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.ScriptC;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.wave.androidimageprocessingjava.MainActivity;
import com.example.wave.androidimageprocessingjava.OperationsOnImages.OperationButton;
import com.example.wave.androidimageprocessingjava.Processing.SaturationProcessor;
import com.example.wave.androidimageprocessingjava.R;
import com.example.wave.androidimageprocessingjava.ScriptC_saturation;

import android.support.v8.renderscript.*;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.io.FileNotFoundException;
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

        toolbox.addView(new OperationButton(context, "Saturation", new SaturationProcessor(getBitmap(), this.context), this.imageView, this.leftDrawer));

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
