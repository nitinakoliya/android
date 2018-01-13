package com.example.nitinakoliya.offlinenewsapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> arrayList = new ArrayList<>();
    static ArrayList<String> htmlList = new ArrayList<>();

    public class DownloadTask extends AsyncTask<String, Void, String> {

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

    private boolean checkIfDataBaseExists(String dbName) {

        String[] dbList = this.databaseList();
        for (String db: dbList) {
            if (dbName.equals(db)) {
                return true;
            }
        }
        return false;
    }

    private String parseStringQuote(String str) {
        str = str.replaceAll("'", "");
        return str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.newsList);

        SQLiteDatabase newsDB = null;
        /*if (checkIfDataBaseExists("NewsDB")) {
            Log.i("Database", "Exists");
            newsDB = this.openOrCreateDatabase("NewsDB", MODE_PRIVATE, null);

            //  newsDB.execSQL("CREATE TABLE IF NOT EXISTS news (title VARCHAR, html VARCHAR)");
            //newsDB.execSQL("INSERT INTO news (title, html) VALUES ('Signal >> Blog', 'https://www.google.com')");

            newsDB.execSQL("DELETE FROM news");
        }
        else {*/

            newsDB = this.openOrCreateDatabase("NewsDB", MODE_PRIVATE, null);

            newsDB.execSQL("DELETE FROM news");
            Log.i("Database", "Deleted all data");

            newsDB.execSQL("CREATE TABLE IF NOT EXISTS news (title VARCHAR, html VARCHAR)");

            try {
                DownloadTask task = new DownloadTask();
                String result = task.execute("https://hacker-news.firebaseio.com/v0/beststories.json").get();

                JSONArray jsonArray = new JSONArray(result);
                int totalNews = (jsonArray.length() < 3) ? jsonArray.length() : 3;

                for (int i = 0; i < totalNews; i++) {

                    DownloadTask task1 = new DownloadTask();
                    String result1 = task1.execute("https://hacker-news.firebaseio.com/v0/item/" + jsonArray.get(i) + ".json").get();
                    JSONObject jsonObject = new JSONObject(result1);
                    if (!jsonObject.isNull("title") && !jsonObject.isNull("url")) {
                        String title = jsonObject.getString("title");
                        String url = jsonObject.optString("url");
                        DownloadTask task2 = new DownloadTask();
                        String result2 = task2.execute(url).get();
                        //newsDB.execSQL("INSERT INTO news (title, html) VALUES ('" + parseStringQuote(title) + "', '" + url + "')");
                        String sql = "INSERT INTO news (title, html) VALUES (?, ?)";
                        SQLiteStatement statement = newsDB.compileStatement(sql);
                        statement.bindString(1, title);
                        statement.bindString(2, result2);
                        statement.execute();

                        Log.i("Database", "Inserted data for " + title);
                    }

                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }



        Cursor c = newsDB.rawQuery("SELECT * FROM news", null);

        int titleIndex = c.getColumnIndex("title");
        int htmlIndex = c.getColumnIndex("html");

        Log.i("Row Count", Integer.toString(c.getCount()));
        int rowCount = c.getCount();
        c.moveToFirst();
        arrayList.clear();
        htmlList.clear();
        for (int i=0; i<rowCount; i++) {
            arrayList.add(c.getString(titleIndex));
            htmlList.add(c.getString(htmlIndex));
            c.moveToNext();
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivityWebView.class);
                intent.putExtra("newsIndex", position);
                startActivity(intent);
            }
        });


    }
}
