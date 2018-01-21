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
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivityInstagram extends AppCompatActivity {

    TextView textViewSignIn;
    boolean signingUp = true;
    Button signUpButton;
    EditText usernameEditText;
    EditText passwordEditText;

  public void signUp(View view) {

      String username = usernameEditText.getText().toString();
      String password = passwordEditText.getText().toString();

      if (username.equals("") || password.equals("")) {
          Toast.makeText(getApplicationContext(), "Username or Password is empty.", Toast.LENGTH_SHORT).show();
      }
      else {

          if (signingUp) {
              ParseUser user = new ParseUser();
              user.setUsername(username);
              user.setPassword(password);

              user.signUpInBackground(new SignUpCallback() {
                  @Override
                  public void done(ParseException e) {
                      if (e == null) {
                          Log.i("Sign Up", "Success");
                          showUsers();
                      } else {
                          Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                      }
                  }
              });
          }
          else {
              ParseUser.logInInBackground(username, password, new LogInCallback() {
                  @Override
                  public void done(ParseUser user, ParseException e) {
                      if (e == null && user != null) {
                          Log.i("Login", "Success");
                          showUsers();
                      }
                      else {
                          Toast.makeText(getApplicationContext(), "Username/Password is incorrect", Toast.LENGTH_SHORT).show();
                      }
                  }
              });
          }
      }
  }

  public void showUsers() {
      Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
      startActivity(intent) ;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

      setTitle("Instagram");

      textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);
      signUpButton = (Button) findViewById(R.id.signupButton);
      usernameEditText = (EditText) findViewById(R.id.usernameEditText);
      passwordEditText = (EditText) findViewById(R.id.passwordEditText);
      RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
      ImageView imageView = (ImageView) findViewById(R.id.imageView);

      if (ParseUser.getCurrentUser() != null) {
          showUsers();
      }

      relativeLayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
              inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
          }
      });

      imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
              inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
          }
      });

      textViewSignIn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (signingUp) {
                  signingUp = false;
                  signUpButton.setText("Sign In");
                  textViewSignIn.setText("Or, Sign Up");
              }
              else {
                  signingUp = true;
                  signUpButton.setText("Sign Up");
                  textViewSignIn.setText("Or, Sign In");
              }
          }
      });

      passwordEditText.setOnKeyListener(new View.OnKeyListener() {
          @Override
          public boolean onKey(View v, int keyCode, KeyEvent event) {
              if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                  signUp(v);
              }
              return false;
          }
      });


    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}