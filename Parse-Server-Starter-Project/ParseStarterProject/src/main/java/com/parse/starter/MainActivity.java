/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    /*ParseObject parseObject = new ParseObject("Score");
    parseObject.put("username", "rahul");
    parseObject.put("score", 84);
      parseObject.put("grade", "A");
    parseObject.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null) {
          Log.i("Parse", "Success");
        }
        else {
          Log.i("Parse", "Fail");
        }
      }
    });

    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
    query.getInBackground("QLZ5lzlUya", new GetCallback<ParseObject>() {
        @Override
        public void done(ParseObject object, ParseException e) {
            if (e == null && object != null) {
                object.put("score", 114);
                object.saveInBackground();

                Log.i("Object", object.getString("username"));
                Log.i("Object", Integer.toString(object.getInt("score")));
            }
        }
    });*/

      /*ParseObject parseObject = new ParseObject("Tweet");
      parseObject.put("username", "nitin");
      parseObject.put("tweet", "Hey There!");
      parseObject.saveInBackground(new SaveCallback() {
          @Override
          public void done(ParseException e) {
              if (e == null) {
                  Log.i("Tweet", "Success");
              }
              else {
                  Log.i("Tweet", "Fail");
              }
          }
      });*/

      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
      query.whereGreaterThan("score", 100);
      query.findInBackground(new FindCallback<ParseObject>() {
          @Override
          public void done(List<ParseObject> objects, ParseException e) {
              if (e == null) {
                  Log.i("Object count", Integer.toString(objects.size()));
                  if (objects.size() > 0) {
                      for (ParseObject obj: objects) {
                          Log.i("Object Value", obj.getString("username"));
                          Log.i("Object Value", Integer.toString(obj.getInt("score")));
                          obj.put("score", obj.getInt("score") + 50);
                          obj.saveInBackground();
                          Log.i("Object Value", Integer.toString(obj.getInt("score")));
                      }
                  }
              }
          }
      });

    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}