package com.example.wave.androidimageprocessingjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.wave.androidimageprocessingjava.Edit.EditActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    Context context;

    public MenuFragment(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        ImageButton goToEdit = (ImageButton) view.findViewById(R.id.goToEdit);
        goToEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.editedImageUri != null){
                    Intent intent = new Intent(context, EditActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(context, "First choose image", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }


}
