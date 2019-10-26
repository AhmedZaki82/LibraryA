package com.example.librarya.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.librarya.data.LibraryContract.Entry;

import androidx.annotation.Nullable;

public class LibraryDbHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "books.db";
    public final static int DATABASE_VERSION = 1;

    public LibraryDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

//        String SQL = " CREATE TABLE " + Entry.TABLE_NAME + " ("
//                + Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + Entry.PRODUCT + " TEXT NOT NULL, "
//                + Entry.PRICE + " INTEGER NOT NULL, "
//                + Entry.QUANTITY + " INTEGER NOT NULL, "
//                + Entry.SUPPLIER_NAME + " TEXT NOT NULL, "
//                + Entry.SUPPLIER_PHONE + " INTEGER NOT NULL);";
//
//        sqLiteDatabase.execSQL(SQL);

        String CREATE_SQL = " CREATE TABLE " + Entry.TABLE_NAME + " ("
                + Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Entry.PRODUCT + " TEXT NOT NULL, "
                + Entry.PRICE + " INTEGER NOT NULL, "
                + Entry.QUANTITY + " INTEGER NOT NULL, "
                + Entry.SUPPLIER_NAME + " TEXT NOT NULL, "
                + Entry.SUPPLIER_PHONE + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
