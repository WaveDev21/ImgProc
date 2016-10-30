package com.example.wave.androidimageprocessingjava.Gallery;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.DBConnection.ImageDBHelper;
import com.example.wave.androidimageprocessingjava.R;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery);

        Bundle extras = getIntent().getExtras();
        String type = extras.getString("type");

        setupGallery(type);


    }

    public void setupGallery(String type) {

        LinearLayout gallery = (LinearLayout) findViewById(R.id.galleryContainer);
        assert gallery != null;
        gallery.removeAllViewsInLayout();
        ArrayList<String> arrPath = new ArrayList<String>();

        switch (type) {
            case "shared":
                final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
                final String orderBy = MediaStore.Images.Media._ID;
                Cursor cursor;
                int count;

                cursor = getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                        null, orderBy);
                assert cursor != null;
                count = cursor.getCount();

                for (int i = 0; i < count; i++) {
                    cursor.moveToPosition(i);
                    int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    arrPath.add(cursor.getString(dataColumnIndex));
                }
                break;
            case "app":
                ImageDBHelper dbHelper = new ImageDBHelper(this);
                arrPath = dbHelper.getAllImages();
                break;
        }


        int counter = 0;
        LinearLayout linearLayout = this.initGalleryLine();

        Display display = getWindowManager().getDefaultDisplay();


        int width = display.getWidth();
        int delimiter = 4;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            width = (width / 4) - 13;
        } else {
            width = (width / 3) - 13;
            delimiter = 3;
        }
        Double height = width / 1.35;

        for (final String imageDir : arrPath) {

            final File file = new File(imageDir);
            Bitmap thumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imageDir),
                    width, height.intValue());

            if (file.isFile()) {

                if (counter % delimiter == 0) {
                    linearLayout = this.initGalleryLine();
                    gallery.addView(linearLayout);
                }

                RelativeLayout relativeLayout = new RelativeLayout(this);
                RelativeLayout.LayoutParams relativeParams =
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                relativeLayout.setLayoutParams(relativeParams);
                relativeLayout.setPadding(5, 0, 5, 0);
                relativeLayout.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setData(Uri.fromFile(file));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

                ImageView iv = new ImageView(this);
                ViewGroup.MarginLayoutParams imageParams =
                        new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                iv.setLayoutParams(imageParams);

                iv.setImageBitmap(thumbImage);
                linearLayout.addView(relativeLayout);
                relativeLayout.addView(iv);
            }
            counter++;
        }

    }

    private LinearLayout initGalleryLine() {
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams linearParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 190);
        linearLayout.setLayoutParams(linearParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        return linearLayout;
    }

}
