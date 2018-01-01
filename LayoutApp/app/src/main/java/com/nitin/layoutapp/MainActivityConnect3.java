package com.nitin.layoutapp;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivityConnect3 extends AppCompatActivity {

    private int player;

    private int[] currentState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    private int[][] successStates = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void setMark(View view) {

        ImageView mark = (ImageView) view;

        if (currentState[Integer.parseInt(mark.getTag().toString())] == 2) {

            currentState[Integer.parseInt(mark.getTag().toString())] = player;

            if (player == 0) {
                mark.setImageResource(R.drawable.yellow);
                player = 1;
            }
            else {
                mark.setImageResource(R.drawable.red);
                player = 0;
            }

            int i, j=0;
            for (i=0; i<successStates.length; i++) {
                if (currentState[successStates[i][0]] == currentState[successStates[i][1]]
                        && currentState[successStates[i][1]] == currentState[successStates[i][2]]
                        && currentState[successStates[i][0]] != 2) {
                    //Log.i("Result", "Winner: " + Integer.toString(currentState[successStates[i][0]]));
                    LinearLayout layout = (LinearLayout) findViewById(R.id.winnerLayout);
                    layout.setVisibility(View.VISIBLE);
                    TextView textView = (TextView) findViewById(R.id.textView1);
                    textView.setText("Winner: " + Integer.toString(currentState[successStates[i][0]]));
                    break;
                }
            }

            if (i >= successStates.length) {
                for (j=0; j<currentState.length; j++) {
                    if (currentState[j] == 2) {
                        break;
                    }
                }
            }

            if (j >= currentState.length) {
                //Log.i("Result", "Match Draw");
                LinearLayout layout = (LinearLayout) findViewById(R.id.winnerLayout);
                layout.setVisibility(View.VISIBLE);
                TextView textView = (TextView) findViewById(R.id.textView1);
                textView.setText("Match Draw");
            }

        }
    }

    public void playAgain(View view) {
        for (int i=0; i<currentState.length; i++) {
            currentState[i] = 2;
            String imageView = "imageView"+i;
            int imageViewId = getResources().getIdentifier(imageView, "id", getPackageName());
            ImageView v = (ImageView) findViewById(imageViewId);
            v.setImageResource(R.drawable.transparent);
        }
        LinearLayout layout = (LinearLayout) findViewById(R.id.winnerLayout);
        layout.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_connect3);
        player = 0;
    }
}
