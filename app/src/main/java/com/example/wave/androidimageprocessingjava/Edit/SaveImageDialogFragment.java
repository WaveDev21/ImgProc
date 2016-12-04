package com.example.wave.androidimageprocessingjava.Edit;

import android.app.Dialog;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wave.androidimageprocessingjava.DBConnection.ImageDBHelper;
import com.example.wave.androidimageprocessingjava.MainActivity;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by root on 01.12.16.
 */

public class SaveImageDialogFragment extends DialogFragment {

    Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.save_question, null);

        final EditText editText = (EditText) view.findViewById(R.id.fileName);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String text = editText.getText().toString();
                        try {
                            saveToExternalStorage(Processor.mBitmapIn, text);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    public void setContext(Context context){
        this.context = context;
    }

    private void saveToExternalStorage(Bitmap bitmapImage, String name) throws IOException {
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File storageDir = cw.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // Create imageDir
        File image= File.createTempFile(name, ".jpg", storageDir);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(image);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ImageDBHelper dbHelper = new ImageDBHelper(this.context);
        dbHelper.insertImage(name, Uri.fromFile(image).getPath());

    }
}
