package com.example.wave.androidimageprocessingjava.Edit;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.wave.androidimageprocessingjava.DBConnection.ImageDBHelper;
import com.example.wave.androidimageprocessingjava.MainActivity;
import com.example.wave.androidimageprocessingjava.Processing.Processor;
import com.example.wave.androidimageprocessingjava.R;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 05.12.16.
 */

public class ShareDialogFragment extends DialogFragment {

    Context context;
    ShareDialog shareFacebookDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.share_choose, null);

        LinearLayout chooseContainer = (LinearLayout) view.findViewById(R.id.chooseContainer);

        final Intent shareIntent = new Intent(
                android.content.Intent.ACTION_SEND);

        shareIntent.setType("image/*");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "Content to share");

        PackageManager pm = context.getPackageManager();

        List<ResolveInfo> activityList = pm.queryIntentActivities(
                shareIntent, 0);
        ArrayList<String> alreadyDisplayed = new ArrayList<>(10);
        for (final ResolveInfo app : activityList) {

            if (app.activityInfo.name.contains("facebook") && !alreadyDisplayed.contains("facebook")) {

                ImageButton share = createImageButton(R.drawable.facebook);
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postPictureOnFacebook();
                    }
                });
                chooseContainer.addView(share);
                alreadyDisplayed.add("facebook");
            }

            if (app.activityInfo.name.contains("twitter") && !alreadyDisplayed.contains("twitter")) {

                ImageButton share = createImageButton(R.drawable.twitter);
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postPictureOnTwitter(shareIntent, app);
                    }
                });
                chooseContainer.addView(share);
                alreadyDisplayed.add("twitter");

            }
        }

        return builder.setView(view).create();
    }


    public void setContext(Context context){
        this.context = context;
    }

    public void setupDialogs(ShareDialog facebookDialog){
        this.shareFacebookDialog = facebookDialog;
    }

    private void postPictureOnFacebook() {

        if (ShareDialog.canShow(ShareLinkContent.class)) {


            Bitmap image = Processor.mBitmapIn;
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(image)
                    .build();
            SharePhotoContent sharedContent = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();

            shareFacebookDialog.show(sharedContent, ShareDialog.Mode.AUTOMATIC);

        }
    }

    private void postPictureOnTwitter(Intent shareIntent, ResolveInfo app){

        final ActivityInfo activity = app.activityInfo;
        final ComponentName name = new ComponentName(
                activity.applicationInfo.packageName,
                activity.name);
        shareIntent.setClassName("com.twitter.android",
                "com.twitter.android.PostActivity");

        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        shareIntent.setComponent(name);

        shareIntent.putExtra(Intent.EXTRA_TEXT, "");

        if(SaveImageDialogFragment.savedImageUri != null){
            shareIntent.putExtra(Intent.EXTRA_STREAM,
                    SaveImageDialogFragment.savedImageUri);
        }else{
            shareIntent.putExtra(Intent.EXTRA_STREAM,
                    MainActivity.editedImageUri);
        }

        startActivity(shareIntent);
    }

    private ImageButton createImageButton(int resource){
        ImageButton button = new ImageButton(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(15, 0, 15, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params.setMarginEnd(15);
            params.setMarginStart(15);
        }
        button.setLayoutParams(params);

        button.setImageResource(resource);
        button.setBackgroundResource(R.color.tw__transparent);
        return button;
    }


}
