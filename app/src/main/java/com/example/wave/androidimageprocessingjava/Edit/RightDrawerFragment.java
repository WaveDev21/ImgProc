package com.example.wave.androidimageprocessingjava.Edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wave.androidimageprocessingjava.Factories.AbstractButtonFactory;
import com.example.wave.androidimageprocessingjava.Factories.ButtonFactory;
import com.example.wave.androidimageprocessingjava.Factories.ButtonType;
import com.example.wave.androidimageprocessingjava.MainActivity;
import com.example.wave.androidimageprocessingjava.MenuFragment;
import com.example.wave.androidimageprocessingjava.R;
import com.example.wave.androidimageprocessingjava.SettingsActivity;
import com.wunderlist.slidinglayer.SlidingLayer;

import android.widget.RadioButton;
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

    public static Context context;
    private ImageView imageView;
    private SlidingLayer leftSlider;
    private RelativeLayout leftToolbox;


    // TODO: Rename and change types and number of parameters
    public static RightDrawerFragment newInstance(Context context, ImageView imageView, SlidingLayer leftDrawer, RelativeLayout leftToolbox) {
        RightDrawerFragment fragment = new RightDrawerFragment();

        fragment.imageView = imageView;
        RightDrawerFragment.context = context;
        fragment.leftSlider = leftDrawer;
        fragment.leftToolbox = leftToolbox;

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

        toolbox.removeAllViews();
        // Inicjalizacja fabryki
        AbstractButtonFactory buttonFactory = new ButtonFactory(context, getBitmap(), this.imageView, this.leftToolbox);

        toolbox.addView(buttonFactory.produceButton(ButtonType.Saturation));
        toolbox.addView(buttonFactory.produceButton(ButtonType.Contrast));
        if(SettingsActivity.currentMode.equals(context.getResources().getString(R.string.mode_pro))){
            toolbox.addView(buttonFactory.produceButton(ButtonType.AccentColor));
        }
        toolbox.addView(buttonFactory.produceButton(ButtonType.Exposure));
        toolbox.addView(buttonFactory.produceButton(ButtonType.Gamma));
        toolbox.addView(buttonFactory.produceButton(ButtonType.Sharpen));
        toolbox.addView(buttonFactory.produceButton(ButtonType.Smooth));
        toolbox.addView(buttonFactory.produceButton(ButtonType.HistogramExt));
        if(SettingsActivity.currentMode.equals(context.getResources().getString(R.string.mode_pro))){
            toolbox.addView(buttonFactory.produceButton(ButtonType.HistogramAlign));
        }

    }


    private Bitmap getBitmap() {
        Bitmap btm = null;
        try {
            btm = android.provider.MediaStore.Images.Media.getBitmap(context.getApplicationContext().getContentResolver(), MainActivity.editedImageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return btm;
    }
}
