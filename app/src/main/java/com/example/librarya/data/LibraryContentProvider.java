package com.example.librarya.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.librarya.data.LibraryContract.Entry;

public class LibraryContentProvider extends ContentProvider {

    LibraryDbHelper mDbHelper;

    public static final int BOOKS = 1;
    public static final int BOOK_ID = 2;

    public static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        mUriMatcher.addURI(LibraryContract.CONTENT_AUTHORITY,LibraryContract.PATH,BOOKS);
        mUriMatcher.addURI(LibraryContract.CONTENT_AUTHORITY,LibraryContract.PATH + "/#",
                BOOK_ID);
    }

    @Override
    public boolean onCreate() {

        mDbHelper = new LibraryDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] strings1, @Nullable String s1) {

        Cursor cursor = null;
        int match = mUriMatcher.match(uri);
        SQLiteDatabase dbR = mDbHelper.getReadableDatabase();

        switch (match) {

            case BOOKS:

                cursor = dbR.query(Entry.TABLE_NAME,strings,s,strings1,null,
                        null,null);

                Log.d("A","Query had DONE!");
                break;

            case BOOK_ID:
                s = Entry._ID + "=?";

                strings1 = new String[] {String.valueOf(ContentUris.parseId(uri))};

                cursor = dbR.query(Entry.TABLE_NAME,strings,s,strings1,null,
                        null,null);
                break;

                default:
                    try {
                        throw new IllegalAccessException("No Query!");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
        }

        cursor.setNotificationUri(getContext().getContentResolver(),Entry.CONTENT_URI);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        String type = null;

        int match = mUriMatcher.match(uri);

        switch (match) {

            case BOOKS:
                type = Entry.LIST_TYPE;
                break;

            case BOOK_ID:
                type = Entry.ITEM_TYPE;
                break;

                default:
                    try {
                        throw new IllegalAccessException("No Type!");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
        }

        return type;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        SQLiteDatabase dbW = mDbHelper.getWritableDatabase();
        Uri uri1 = null;

        long ins = dbW.insert(Entry.TABLE_NAME,null,contentValues);
        uri1 = ContentUris.withAppendedId(Entry.CONTENT_URI,ins);

        Log.d("A","Insert had DONE!");

        getContext().getContentResolver().notifyChange(Entry.CONTENT_URI,null);


        return uri1;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        Integer del = null;

        SQLiteDatabase dbD = mDbHelper.getWritableDatabase();

        int match = mUriMatcher.match(uri);

        switch (match) {

            case BOOKS:

                del = dbD.delete(Entry.TABLE_NAME,s,strings);
                break;

            case BOOK_ID:

                s = Entry._ID + "=?";
                strings = new String[] {String.valueOf(ContentUris.parseId(uri))};
                del = dbD.delete(Entry.TABLE_NAME,s,strings);
                break;

                default:
                    try {
                        throw new IllegalAccessException("No Deletion");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
        }

        getContext().getContentResolver().notifyChange(Entry.CONTENT_URI,null);
        return del;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s,
                      @Nullable String[] strings) {

        Integer up = null;
        SQLiteDatabase dbU = mDbHelper.getWritableDatabase();
        int match = mUriMatcher.match(uri);

        switch (match) {

            case BOOKS:
                up = dbU.update(Entry.TABLE_NAME,contentValues,s,strings);
                break;

            case BOOK_ID:
                s = Entry._ID + "=?";
                strings = new String[] {String.valueOf(ContentUris.parseId(uri))};

                up = dbU.update(Entry.TABLE_NAME,contentValues,s,strings);
                break;
        }

        getContext().getContentResolver().notifyChange(Entry.CONTENT_URI,null);
        return up;
    }
}
