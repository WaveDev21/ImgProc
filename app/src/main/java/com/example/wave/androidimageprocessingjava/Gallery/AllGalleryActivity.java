package com.example.wave.androidimageprocessingjava.Gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.wave.androidimageprocessingjava.R;


public class AllGalleryActivity extends AppCompatActivity {

    private final int PickImageId = 667;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery_all);


        ImageButton shared = (ImageButton) findViewById(R.id.sharedImages);
        assert shared != null;
        shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GalleryActivity.class);
                intent.putExtra("type", "shared");
                startActivityForResult(intent, PickImageId);
            }
        });

        ImageButton app = (ImageButton) findViewById(R.id.appImages);
        assert app != null;
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GalleryActivity.class);
                intent.putExtra("type", "app");
                startActivityForResult(intent, PickImageId);
            }
        });

//        Intent intent = new Intent();
//        intent.putExtra("position", "test");
//        setResult(RESULT_OK, intent);
//        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PickImageId && resultCode == RESULT_OK && data != null){

            Intent intent = new Intent();
            intent.setData(data.getData());
            setResult(RESULT_OK, intent);
            finish();
        }

    }

}
