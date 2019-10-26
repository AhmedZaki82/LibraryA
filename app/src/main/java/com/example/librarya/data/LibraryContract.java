package com.example.librarya.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

public class LibraryContract {

    public LibraryContract() {}

    public static String CONTENT_AUTHORITY = "com.example.librarya";
    public static Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static String PATH = "Books";


    public static class Entry implements BaseColumns {

        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI,PATH);

        public final static String ITEM_TYPE = ContentResolver.ANY_CURSOR_ITEM_TYPE + "/" +
                CONTENT_AUTHORITY + PATH;

        public final static String LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + PATH;

        public final static String _ID = BaseColumns._ID;
        public final static String TABLE_NAME = "Books";
        public final static String PRODUCT = "Product";
        public final static String PRICE = "Price";
        public final static String QUANTITY = "Quantity";
        public final static String SUPPLIER_NAME = "Name";
        public final static String SUPPLIER_PHONE = "Phone";
    }
}
