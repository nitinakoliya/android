package com.example.nitinakoliya.advancedfeatures3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivityGuessCeleb extends AppCompatActivity {

    ArrayList<String> celebURLs = new ArrayList<>();
    ArrayList<String> celebNames = new ArrayList<>();
    int chosenCeleb;
    ImageView imageView;
    Button[] buttons = new Button[4];

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class WebDownloader extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            URL url;
            HttpURLConnection urlConnection;

            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                String result = "";
                int data = reader.read();

                while (data != -1) {
                    char curr = (char) data;
                    result += curr;
                    data = reader.read();
                }
                return result;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void showToast(String value) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }

    public void chosenCeleb(View view) {
        Button btn = (Button) view;
        if (btn.getText().equals(celebNames.get(chosenCeleb))) {
            showToast("Correct!");
        }
        else {
            showToast("Wrong! Correct was " + celebNames.get(chosenCeleb));
        }
        setNewCeleb();
    }

    public void setNewCeleb() {
        Random random = new Random();
        chosenCeleb = random.nextInt(celebURLs.size());

        ImageDownloader imageDownloader = new ImageDownloader();
        Bitmap bitmap = null;
        try {
            bitmap = imageDownloader.execute(celebURLs.get(chosenCeleb)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);

        int correctAnswerPosition = random.nextInt(4);

        for (int i = 0; i < 4; i++) {
            if (i == correctAnswerPosition) {
                buttons[i].setText(celebNames.get(chosenCeleb));
            }
            else {
                int incorrectAnswerPosition = random.nextInt(celebURLs.size());
                while (incorrectAnswerPosition == chosenCeleb) {
                    incorrectAnswerPosition = random.nextInt(celebURLs.size());
                }
                buttons[i].setText(celebNames.get(incorrectAnswerPosition));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_guess_celeb);

        imageView = (ImageView) findViewById(R.id.imageView);
        for(int i = 0; i < 4; i++) {
            //Log.i("Resource", Integer.toString(this.getResources().getIdentifier("button"+i, "id", getPackageName())));
            buttons[i] = (Button) findViewById(this.getResources().getIdentifier("button"+i, "id", getPackageName()));
        }

        WebDownloader task = new WebDownloader();
        try {
            String result = task.execute("http://www.posh24.se/kandisar").get();

            String[] data = result.split("<div class=\"sidebarContainer\">");

            Pattern p = Pattern.compile("<img src=\"(.*?)\"");
            Matcher m = p.matcher(data[0]);

            while (m.find()) {
                celebURLs.add(m.group(1));
            }

            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(data[0]);

            while (m.find()) {
                celebNames.add(m.group(1));
            }

            setNewCeleb();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
