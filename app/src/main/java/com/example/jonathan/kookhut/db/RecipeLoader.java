package com.example.jonathan.kookhut.db;

import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.media.Image;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jonathan.kookhut.Contract;
import com.example.jonathan.kookhut.R;
import com.example.jonathan.kookhut.db.Models.Category;
import com.example.jonathan.kookhut.db.Models.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

/**
 * Created by jonat on 2/09/2015.
 */
public class RecipeLoader extends AsyncTask<String, Void, Recipe> {
    private final String url = "http://kookhutrestservice.azurewebsites.net/Recipe/GetById/";
    private final WeakReference<Activity> activityWeakReference;
    private Integer RecipeID;

    public RecipeLoader(Activity activity, Integer RecipeID) {
        this.activityWeakReference = new WeakReference<Activity>(activity);
        this.RecipeID = RecipeID;
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
    protected Recipe doInBackground(String... params) {
        Recipe recipe = null;
        try {
            recipe = new Gson().fromJson(getJsonFromServer(url + RecipeID), Recipe.class);
            Activity activity = activityWeakReference.get();
        } catch (IOException e) {
        }
        return recipe;
    }

    @Override
    protected void onPostExecute(Recipe recipe){
        if (isCancelled()){
            recipe = null;
        }

        if(activityWeakReference != null){
            Activity activity = activityWeakReference.get();

            ((TextView)activity.findViewById(R.id.textViewPreparation)).setText(recipe.preparation);
            ((TextView)activity.findViewById(R.id.textViewPreparation)).setMovementMethod(new ScrollingMovementMethod());
            ((TextView)activity.findViewById(R.id.textViewPeopleTime)).setText("Voor " + recipe.people + " personen.\n" + recipe.time +" bereidingstijd.");
            ((TextView)activity.findViewById(R.id.textViewIngredients)).setText(recipe.GetIngredients());
            ((TextView)activity.findViewById(R.id.textViewIngredients)).setMovementMethod(new ScrollingMovementMethod());
        }

    }
}
