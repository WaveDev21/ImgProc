package com.example.wave.androidimageprocessingjava.Edit;

import android.content.Context;
import android.os.Bundle;
import android.renderscript.ScriptC;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import com.example.wave.androidimageprocessingjava.R;
import com.example.wave.androidimageprocessingjava.ScriptC_saturation;

import android.support.v8.renderscript.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RightDrawerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RightDrawerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RightDrawerFragment extends Fragment {

    private Context context;

    private ScriptC_saturation mScript;

    // TODO: Rename and change types and number of parameters
    public static RightDrawerFragment newInstance(Context context) {
        RightDrawerFragment fragment = new RightDrawerFragment();
        Bundle args = new Bundle();

        fragment.context = context;

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_drawer, container, false);

        RadioButton button = (RadioButton) view.findViewById(R.id.highLight);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return inflater.inflate(R.layout.fragment_right_drawer, container, false);
    }



}
