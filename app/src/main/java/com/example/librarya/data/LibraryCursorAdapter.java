package com.example.librarya.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.librarya.data.LibraryContract.Entry;

import com.example.librarya.R;

public class LibraryCursorAdapter extends CursorAdapter {

    public LibraryCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

//        return LayoutInflater.from(context).inflate(R.layout.item_list,null);

        View view = LayoutInflater.from(context).inflate(R.layout.item_list,null);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

//        TextView namT = view.findViewById(R.id.name);
//        TextView sumT = view.findViewById(R.id.summary);
//
//        int namColumnIndex = cursor.getColumnIndex(Entry.PRODUCT);
//        int sumColumnIndex = cursor.getColumnIndex(Entry.SUPPLIER_NAME);
//
//        String nam = cursor.getString(namColumnIndex);
//        String sum = cursor.getString(sumColumnIndex);
//
//        namT.setText(nam);
//        sumT.setText(sum);

        TextView nameT = view.findViewById(R.id.name);
        TextView summT = view.findViewById(R.id.summary);

        int nameColumnIndex = cursor.getColumnIndex(Entry.PRODUCT);
        int summColumnIndex = cursor.getColumnIndex(Entry.SUPPLIER_NAME);

        String name = cursor.getString(nameColumnIndex);
        String summ = cursor.getString(summColumnIndex);

        nameT.setText(name);
        summT.setText(summ);

    }
}
