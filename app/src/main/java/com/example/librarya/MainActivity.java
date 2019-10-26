package com.example.librarya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.librarya.data.LibraryContract.Entry;
import com.example.librarya.data.LibraryCursorAdapter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    public static final int LOADER = 1;

    LibraryCursorAdapter libraryCursorAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {

            case R.id.insert:

                insertBook();
                Toast.makeText(this, "Good", Toast.LENGTH_SHORT).show();

                break;

            case R.id.editor:

                Intent intent = new Intent(MainActivity.this,Editor.class);
                startActivity(intent);
                break;

            case R.id.delete_all:

                deleteAll();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = findViewById(R.id.list);
        libraryCursorAdapter = new LibraryCursorAdapter(this,null);

        getSupportLoaderManager().initLoader(LOADER,null,this);


        list.setAdapter(libraryCursorAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Uri currentItem = ContentUris.withAppendedId(Entry.CONTENT_URI,l);

                Intent intent = new Intent(MainActivity.this,Editor.class);

                intent.setData(currentItem);

                startActivity(intent);

            }
        });

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String [] projection = {Entry._ID,Entry.PRODUCT,Entry.SUPPLIER_NAME};

        return new CursorLoader(this, Entry.CONTENT_URI,projection,null,
                null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        libraryCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        libraryCursorAdapter.swapCursor(null);

    }

    public void insertBook() {

        ContentValues values = new ContentValues();

        values.put(Entry.PRODUCT,"Book");
        values.put(Entry.PRICE,50);
        values.put(Entry.QUANTITY,30);
        values.put(Entry.SUPPLIER_NAME,"Ahmed");
        values.put(Entry.SUPPLIER_PHONE,123);

        getContentResolver().insert(Entry.CONTENT_URI,values);
    }

    public void deleteAll() {

        getContentResolver().delete(Entry.CONTENT_URI,null,null);
    }
}
