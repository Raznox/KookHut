package com.example.jonathan.kookhut.db;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.example.jonathan.kookhut.CategoryFragment;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jonat on 3/09/2015.
 */
public class ImageLoader extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewWeakReference;
    public ImageLoader(ImageView imageView){
        imageViewWeakReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap=null;
        if (!isCancelled()){

            try {
                URL newurl = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) newurl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();

                bitmap  = (Bitmap) BitmapFactory.decodeStream(input);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap){
        if (isCancelled()){
            bitmap = null;
        }

        if(imageViewWeakReference != null){
            ImageView imageView = imageViewWeakReference.get();
            if (imageView != null){
                if (bitmap != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        }

    }
}

