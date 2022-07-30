package com.example.wave.androidimageprocessingjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wave.androidimageprocessingjava.DBConnection.ImageDBHelper;
import com.example.wave.androidimageprocessingjava.DBConnection.SettingsDBHelper;
import com.example.wave.androidimageprocessingjava.Edit.EditActivity;
import com.example.wave.androidimageprocessingjava.Edit.Histogram.HistogramActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    private MainActivity context;
    public static final int MakeImageId = 616;
    private ImageView editedImageView;


    public MenuFragment() {
        super();
    }

    public void setArguments(MainActivity mainActivity, ImageView editedImageView) {
        this.context = mainActivity;
        this.editedImageView = editedImageView;
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

        ImageButton goToMakePhoto = (ImageButton) view.findViewById(R.id.goToMakePhoto);
        goToMakePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        ImageButton refresh = (ImageButton) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.setupGallery();
            }
        });

        final ImageButton changeMode = (ImageButton) view.findViewById(R.id.showSettings);

        changeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SettingsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MakeImageId && resultCode == RESULT_OK) {

            String fileName = MainActivity.editedImageUri.getLastPathSegment();
            String fileDir = MainActivity.editedImageUri.getPath();

            ImageDBHelper dbHelper = new ImageDBHelper(this.context);
            dbHelper.insertImage(fileName, fileDir);

            this.editedImageView.setImageURI(MainActivity.editedImageUri);
            this.context.setupGallery();

        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ignored) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                MainActivity.editedImageUri = Uri.fromFile(photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, MainActivity.editedImageUri);
                startActivityForResult(takePictureIntent, MakeImageId);

            }
        }
    }



    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.GERMANY).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }

}
