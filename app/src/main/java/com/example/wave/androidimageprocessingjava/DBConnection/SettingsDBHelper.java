package com.example.wave.androidimageprocessingjava.DBConnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.wave.androidimageprocessingjava.DBConnection.ImageDBHelper.IMAGE_COLUMN_DIR;

/**
 * Created by root on 16.10.16.
 */

public class SettingsDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ImgProc.db";
    public static final String DATABASE_CREATE_SETTINGS = "CREATE TABLE IF NOT EXISTS t_settings "+
            "(id integer primary key, name varchar(100), date timestamp, value varchar(100))";
    public static final String SETTINGS_TABLE_NAME = "t_settings";
    public static final String SETTINGS_COLUMN_ID = "id";
    public static final String SETTINGS_COLUMN_NAME = "name";
    public static final String SETTINGS_COLUMN_VALUE = "value";
    private HashMap hp;

    public SettingsDBHelper(Context context){
        super(context, DATABASE_NAME, null, 3);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_SETTINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS t_settings");
        onCreate(sqLiteDatabase);
    }

    public boolean insertSetting(String name, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("value", value);
        db.insert("t_settings", null, contentValues);
        return true;
    }

    public String getSetting(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs = db.rawQuery("SELECT * FROM t_settings WHERE name='"+name+"'", null);
        if(rs.getCount() == 0){
            return "";
        }
        rs.moveToFirst();

        return rs.getString(rs.getColumnIndex(SettingsDBHelper.SETTINGS_COLUMN_VALUE));
    }

    public boolean updateSetting(String name, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("value", value);
        db.update("t_settings", contentValues, "name = ?", new String[]{ name });
        return true;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SETTINGS_TABLE_NAME);
        return numRows;
    }

    public Integer deleteSetting(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("t_settings", "name = ?", new String[]{ name });
    }

    public ArrayList<ArrayList<String>> getAllSettings()
    {
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT name, value FROM t_settings", null);
        res.moveToFirst();

        while (!res.isAfterLast()){
            ArrayList<String> list = new ArrayList<>();
            list.add(res.getString(res.getColumnIndex(SETTINGS_COLUMN_NAME)));
            list.add(res.getString(res.getColumnIndex(SETTINGS_COLUMN_VALUE)));
            arrayList.add(list);
            res.moveToNext();
        }
        return arrayList;
    }
}
