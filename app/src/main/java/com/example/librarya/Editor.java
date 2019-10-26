package com.example.librarya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import com.example.librarya.data.LibraryContract.Entry;

public class Editor extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public boolean touched = false;

    View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            touched = true;

            return false;
        }
    };

    public static final int LOADER = 2;

    Uri currentItem;

    EditText productT;
    EditText priceT;
    EditText quantityT;
    EditText nameT;
    EditText phoneT;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.editor,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.save:

                if (currentItem != null) {

                    update();
                    finish();
                } else {

                    saveBook();
                    finish();

                }



                break;

            case R.id.delete:

                deleteBook();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        productT = findViewById(R.id.product);
        priceT = findViewById(R.id.price);
        quantityT = findViewById(R.id.quantity);
        nameT = findViewById(R.id.name);
        phoneT = findViewById(R.id.phone);

        productT.setOnTouchListener(mTouchListener);
        priceT.setOnTouchListener(mTouchListener);
        quantityT.setOnTouchListener(mTouchListener);
        nameT.setOnTouchListener(mTouchListener);
        phoneT.setOnTouchListener(mTouchListener);

        Intent intent = getIntent();
        currentItem = intent.getData();

        if (currentItem != null) {

            setTitle("Edit Book!");

            getSupportLoaderManager().initLoader(LOADER,null,this);

        } else {

            setTitle("Insert a Book!");
        }
    }

    public void saveBook() {

        String product = productT.getText().toString();
        String price = priceT.getText().toString();
        String quantity = quantityT.getText().toString();
        String name = nameT.getText().toString();
        String phone = phoneT.getText().toString();

        ContentValues values = new ContentValues();

        values.put(Entry.PRODUCT,product);
        values.put(Entry.PRICE,price);
        values.put(Entry.QUANTITY,quantity);
        values.put(Entry.SUPPLIER_NAME,name);
        values.put(Entry.SUPPLIER_PHONE,phone);

        getContentResolver().insert(Entry.CONTENT_URI,values);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {Entry._ID,Entry.PRODUCT,Entry.PRICE,Entry.QUANTITY,
                Entry.SUPPLIER_NAME,Entry.SUPPLIER_PHONE};

        return new CursorLoader(this,currentItem,projection,null,null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        if (data.moveToFirst()) {

            int productColumnIndex = data.getColumnIndex(Entry.PRODUCT);
            int priceColumnIndex = data.getColumnIndex(Entry.PRICE);
            int quantityColumnIndex = data.getColumnIndex(Entry.QUANTITY);
            int nameColumnIndex = data.getColumnIndex(Entry.SUPPLIER_NAME);
            int phoneColumnIndex = data.getColumnIndex(Entry.SUPPLIER_PHONE);

            String product = data.getString(productColumnIndex);
            String price = data.getString(priceColumnIndex);
            String quantity = data.getString(quantityColumnIndex);
            String name = data.getString(nameColumnIndex);
            String phone = data.getString(phoneColumnIndex);

            productT.setText(product);
            priceT.setText(price);
            quantityT.setText(quantity);
            nameT.setText(name);
            phoneT.setText(phone);
        }



    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        productT.setText("");
        priceT.setText("");
        quantityT.setText("");
        nameT.setText("");
        phoneT.setText("");

    }

    public void deleteBook() {

        getContentResolver().delete(currentItem,null,null);
    }

    public void update() {

        ContentValues values = new ContentValues();

        String product = productT.getText().toString();
        String price = priceT.getText().toString();
        String quantity = quantityT.getText().toString();
        String name = nameT.getText().toString();
        String phone = phoneT.getText().toString();

        values.put(Entry.PRODUCT,product);
        values.put(Entry.PRICE,price);
        values.put(Entry.QUANTITY,quantity);
        values.put(Entry.SUPPLIER_NAME,name);
        values.put(Entry.SUPPLIER_PHONE,phone);

        getContentResolver().update(currentItem,values,null,null);
    }

    @Override
    public void onBackPressed() {

        if (!touched) {

            super.onBackPressed();

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to Discard the changes?");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    return;
                }
            });

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            AlertDialog create = builder.create();
            create.show();
        }
    }
}
