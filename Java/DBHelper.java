package com.example.demoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "MedicineData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Medicinedetails(name TEXT primary key, time TEXT, date TEXt)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Medicinedetails");
    }

    public Boolean insertmedicinedata(String name, String time, String date) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("time", time);
        contentValues.put("date", date);
        long result = DB.insert("Medicinedetails", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updatemedicinedata(String name, String time, String date) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", time);
        contentValues.put("date", date);
        Cursor cursor = DB.rawQuery("Select * from Medicinedetails where name= ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.update("Medicinedetails", contentValues, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deletedata(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Medicinedetails where name= ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Medicinedetails", "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Medicinedetails", null);
        return cursor;
    }

}
