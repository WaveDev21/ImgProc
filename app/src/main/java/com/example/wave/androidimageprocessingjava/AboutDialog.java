package com.example.wave.androidimageprocessingjava;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by root on 09.12.16.
 */

public class AboutDialog extends Dialog {

    private static Context context;

    public AboutDialog(Context context) {
        super(context);
        AboutDialog.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_about);

        TextView legalTextView = (TextView) findViewById(R.id.legal_text);
        legalTextView.setText(readRawTextFromFile(R.raw.legal));

        TextView infoTextView = (TextView) findViewById(R.id.info_text);
        infoTextView.setText(Html.fromHtml(readRawTextFromFile(R.raw.info)));
        infoTextView.setLinkTextColor(AboutDialog.context.getResources().getColor(R.color.colorPrimary));
        Linkify.addLinks(infoTextView, Linkify.ALL);

        TextView additionalInfoTextView = (TextView) findViewById(R.id.additional_info_text);
        additionalInfoTextView.setText(Html.fromHtml(readRawTextFromFile(R.raw.additional_info)));
        additionalInfoTextView.setLinkTextColor(AboutDialog.context.getResources().getColor(R.color.colorPrimary));
        Linkify.addLinks(additionalInfoTextView, Linkify.ALL);

    }

    public static String readRawTextFromFile(int id){
        InputStream inputStream = AboutDialog.context.getResources().openRawResource(id);
        InputStreamReader in = new InputStreamReader(inputStream);
        BufferedReader buffer = new BufferedReader(in);
        String line;
        StringBuilder text = new StringBuilder();
        try{
            while ((line = buffer.readLine()) != null){
                text.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }
}
