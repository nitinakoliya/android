package com.nitin.advancedfeatures2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivityDownloadImage extends AppCompatActivity {

    ImageView downloadedImg;

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                Bitmap reader = BitmapFactory.decodeStream(in);

                return reader;
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void downloadImage(View view) {
        //https://vignette.wikia.nocookie.net/thebige/images/8/86/Bart.png/revision/latest?cb=20120630220250
        Log.i("Info", "Button Pressed...");
        DownloadImageTask task = new DownloadImageTask();
        Bitmap result = null;
        try {
            result = task.execute("https://vignette.wikia.nocookie.net/thebige/images/8/86/Bart.png/revision/latest?cb=20120630220250").get();
            downloadedImg.setImageBitmap(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_download_image);

        downloadedImg = (ImageView) findViewById(R.id.imageView);

    }
}
