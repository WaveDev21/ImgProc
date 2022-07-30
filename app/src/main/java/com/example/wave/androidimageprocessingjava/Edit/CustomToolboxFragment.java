package com.example.wave.androidimageprocessingjava.Edit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.example.wave.androidimageprocessingjava.MenuFragment;
import com.example.wave.androidimageprocessingjava.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.widget.ShareDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CustomToolboxFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CustomToolboxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomToolboxFragment extends Fragment{

    private Context context;
    private CallbackManager callback;

    View view;

    public CustomToolboxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CustomToolboxFragment.
     */
    public static CustomToolboxFragment newInstance(Context context, CallbackManager callback) {
        CustomToolboxFragment fragment = new CustomToolboxFragment();

        fragment.context = context;
        fragment.callback = callback;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_custom_toolbox, container, false);

        ImageButton saveButton = (ImageButton) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveImageDialogFragment dialog = new SaveImageDialogFragment();
                dialog.setContext(context);
                dialog.show(((EditActivity)context).getSupportFragmentManager(), "SaveImageDialogFragmet");

            }
        });


        final ShareDialog shareFacebookDialog = new ShareDialog(this);
//        shareFacebookDialog.registerCallback(callback, new FacebookCallback<Sharer.Result>() {
//            @Override
//            public void onSuccess(Sharer.Result result) {
////                Log.wtf("test", "success");
//            }
//
//            @Override
//            public void onCancel() {
////                Log.wtf("test", "cancel");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
////                Log.wtf("test", "error");
////                Log.wtf("test", error.toString());
//
//            }
//        });



        ImageButton shareButton = (ImageButton) view.findViewById(R.id.shareButton);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShareDialogFragment dialogFragment = new ShareDialogFragment();
                dialogFragment.setContext(context);
                dialogFragment.setupDialogs(shareFacebookDialog);
                dialogFragment.show(((EditActivity)context).getSupportFragmentManager(), "ShareDialogFragment");

            }
        });


        return view;

    }

}
