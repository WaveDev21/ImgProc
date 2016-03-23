package com.example.wave.androidimageprocessingjava.Edit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.wave.androidimageprocessingjava.MainActivity;
import com.example.wave.androidimageprocessingjava.R;

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

    public RightDrawerFragment(Context context){
        super();
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        RadioButton button = (RadioButton) view.findViewById(R.id.highLight);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return inflater.inflate(R.layout.fragment_right_drawer, container, false);
    }



}
