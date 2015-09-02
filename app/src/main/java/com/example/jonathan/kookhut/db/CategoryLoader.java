package com.example.jonathan.kookhut.db;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;

import com.example.jonathan.kookhut.Contract;
import com.example.jonathan.kookhut.db.Models.Category;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;


/**
 * Created by jonat on 2/09/2015.
 */
public class CategoryLoader extends AsyncTaskLoader<Cursor> {
    private final String url = "http://kookhutrestservice.azurewebsites.net/Category/GetList";

    private MatrixCursor mCursor;

    public CategoryLoader(Context context) {
        super(context);
    }

    public static String getJsonFromServer(String url) throws IOException {

        BufferedReader inputStream = null;

        URL jsonUrl = new URL(url);
        URLConnection dc = jsonUrl.openConnection();

        dc.setConnectTimeout(5000);
        dc.setReadTimeout(5000);

        inputStream = new BufferedReader(new InputStreamReader(
                dc.getInputStream()));

        // read the JSON results into a string
        return inputStream.readLine();
    }

    @Override
    public Cursor loadInBackground() {
        MatrixCursor mCursor = new MatrixCursor(new String[]{Contract.CategoryColumns._ID,Contract.CategoryColumns.name,Contract.CategoryColumns.imgUrl});
        try {
            Type categoriesType = new TypeToken<Collection<Category>>() {
            }.getType();

            Collection<Category> categories = new Gson().fromJson(getJsonFromServer(url), categoriesType);

            for (Category category : categories){
                MatrixCursor.RowBuilder row = mCursor.newRow();
                row.add(category.id);
                row.add(category.name);
                row.add(category.imgUrl);
            }
        } catch (IOException e) {
        }
        return (Cursor)mCursor;
    }

    @Override
    public void deliverResult(Cursor cursor) {
        if (isReset()) {
            if (cursor != null) {
                cursor.close();
            }
            return;
        }

        Cursor oldCursor = mCursor;
        mCursor = (MatrixCursor)cursor;

        if (isStarted()) {
            super.deliverResult(cursor);
        }

        if (oldCursor != null && oldCursor != cursor && !oldCursor.isClosed()) {
            oldCursor.close();
        }
    }

    @Override
    protected void onStartLoading() {
        if(mCursor != null) {
            deliverResult(mCursor);
        }

        if(takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }

        mCursor = null;
    }
}