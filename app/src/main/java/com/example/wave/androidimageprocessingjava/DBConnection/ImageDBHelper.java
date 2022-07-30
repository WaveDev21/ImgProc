package com.example.wave.androidimageprocessingjava.DBConnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 16.10.16.
 */

public class ImageDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ImgProc.db";
    public static final String IMAGE_TABLE_NAME = "images";
    public static final String IMAGE_COLUMN_ID = "id";
    public static final String IMAGE_COLUMN_NAME = "name";
    public static final String IMAGE_COLUMN_DIR = "dir";
    private HashMap hp;

    public ImageDBHelper(Context context){
        super(context, DATABASE_NAME, null, 3);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS images "+
                        "(id integer primary key, name text, date timestamp, dir varchar(100))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i2, int i3) {
        switch(i2) {
            case 2:
                sqLiteDatabase.execSQL(SettingsDBHelper.DATABASE_CREATE_SETTINGS);
                // we want both updates, so no break statement here...
        }
        onCreate(sqLiteDatabase);
    }

    public boolean insertImage(String name, String dir){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("dir", dir);
        db.insert("images", null, contentValues);
        return true;
    }

    public Cursor getImage(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM images WHERE id="+id+"", null);
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, IMAGE_TABLE_NAME);
        return numRows;
    }

    public Integer deleteImage(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("images", "id = ?", new String[]{ Integer.toString(id)});
    }

    public ArrayList<String> getAllImages()
    {
        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM images ORDER BY id DESC", null);
        res.moveToFirst();

        while (!res.isAfterLast()){
            arrayList.add(res.getString(res.getColumnIndex(IMAGE_COLUMN_DIR)));
            res.moveToNext();
        }
        return arrayList;
    }
}
