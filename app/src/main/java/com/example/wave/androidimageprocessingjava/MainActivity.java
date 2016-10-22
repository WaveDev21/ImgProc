package com.example.wave.androidimageprocessingjava;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.wave.androidimageprocessingjava.DBConnection.DBHelper;
import com.example.wave.androidimageprocessingjava.Gallery.GalleryActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    public static final int PickImageId = 666;

    private ImageView editedImageView;
    public static android.net.Uri editedImageUri;


    private ListView obj;
    // test
//    Integer[] imageIds = {
//        R.drawable.birghtnes,
//        R.drawable.ic_add_a_photo_black_24dp,
//        R.drawable.ic_edit_black_24dp,
//        R.drawable.ic_linked_camera_black_24dp,
//        R.drawable.ic_settings_black_24dp
//    };
    // test end


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editedImageView = (ImageView) findViewById(R.id.editedImageView);
        this.editedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PickImageId);

                Intent intent = new Intent(v.getContext(), GalleryActivity.class);
                startActivity(intent);
            }
        });

        this.setupGallery();

        MenuFragment menuFragment = new MenuFragment();
        menuFragment.setArguments(this, this.editedImageView);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.menuContainer, menuFragment);
        fragmentTransaction.commit();

    }

    public void setupGallery() {

        LinearLayout lastEditedImages = (LinearLayout) findViewById(R.id.lastEditedImages);
        assert lastEditedImages != null;
        lastEditedImages.removeAllViewsInLayout();
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<String> imageDirs = dbHelper.getAllImages();

        for (String imageDir : imageDirs) {

            final File file = new File(imageDir);
            Bitmap thumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imageDir),
                    225, 170);

            if (file.isFile()) {

                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams linearParams =
                        new LinearLayout.LayoutParams(230, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayout.setLayoutParams(linearParams);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.editedImageUri = Uri.fromFile(file);
                        editedImageView.setImageURI(MainActivity.editedImageUri);
                    }
                });

                RelativeLayout relativeLayout = new RelativeLayout(this);
                RelativeLayout.LayoutParams relativeParams =
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                relativeLayout.setLayoutParams(relativeParams);

                ImageView iv = new ImageView(this);
                ViewGroup.MarginLayoutParams imageParams =
                        new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                iv.setLayoutParams(imageParams);

                iv.setImageBitmap(thumbImage);
                lastEditedImages.addView(linearLayout);
                linearLayout.addView(relativeLayout);
                relativeLayout.addView(iv);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PickImageId && resultCode == RESULT_OK && data != null){
            editedImageUri = data.getData();

            String fileName = editedImageUri.getLastPathSegment();
            String fileDir = editedImageUri.getPath();

            DBHelper dbHelper = new DBHelper(this);
            dbHelper.insertImage(fileName, fileDir);

            editedImageView.setImageURI(editedImageUri);

            this.setupGallery();

        }

    }

}
