/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivitySignUp extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

      ParseUser.logOut();

      if (ParseUser.getCurrentUser() != null) {
          Log.i("Current user", ParseUser.getCurrentUser().getUsername());
      }
      else {
          Log.i("Current user", "None");
      }

      /*ParseUser.logInInBackground("nitin", "test", new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
              if (e == null && user != null) {
                  Log.i("Login", "Success");
              }
              else {
                  Log.i("Login", "Failed, " + e.toString());
              }
          }
      });

      ParseUser user = new ParseUser();
      user.setUsername("nitin");
      user.setPassword("test");

      user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
              if (e == null) {
                  Log.i("Sign Up", "Success");
              }
              else {
                  Log.i("Sign Up", "Failed");
              }
          }
      });*/

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}