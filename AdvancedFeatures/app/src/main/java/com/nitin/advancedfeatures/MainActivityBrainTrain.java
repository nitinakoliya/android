package com.nitin.advancedfeatures;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivityBrainTrain extends AppCompatActivity {

    Random rand = new Random();

    int rand1, rand2;
    int[] opt = new int[4];
    int correctAns;
    int totalAttempt;
    int timer;
    CountDownTimer countDownTimer;
    TextView timerText, sumText, resultText, answerText;
    Button button0, button1, button2, button3, playAgainButton, goButton;
    boolean playing = false;

    public void generateRandomNumber() {
        rand1 = rand.nextInt(50) + 1;
        rand2 = rand.nextInt(49) + 1;
        int n = rand.nextInt(4);
        opt[0] = rand.nextInt(99) + 1;
        opt[1] = rand.nextInt(99) + 1;
        opt[2] = rand.nextInt(99) + 1;
        opt[3] = rand.nextInt(99) + 1;
        opt[n] = rand1 + rand2;

        sumText.setText(rand1 + " + " + rand2);
        resultText.setText(correctAns + "/" + totalAttempt);
        button0.setText(Integer.toString(opt[0]));
        button1.setText(Integer.toString(opt[1]));
        button2.setText(Integer.toString(opt[2]));
        button3.setText(Integer.toString(opt[3]));
    }

    public void startCountDown() {
        playing = true;
        correctAns = 0;
        totalAttempt = 0;
        timer = 31;
        timerText.setText(timer + "s");
        answerText.setText("-");
        playAgainButton.setVisibility(View.INVISIBLE);
        countDownTimer = new CountDownTimer(timer * 1000, 1000) {
            public void onTick(long milisecondsUntillDone) {
                timer--;
                timerText.setText(timer + "s");
            }
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                playing = false;
                answerText.setText("Your Score: " + correctAns + "/" + totalAttempt);
            }
        }.start();
    }

    public void evaluateAnswer(View view) {
        if (playing) {
            Button pressedButton = (Button) view;
            int index = Integer.parseInt(pressedButton.getTag().toString());

            if (opt[index] == rand1 + rand2) {
                answerText.setText("Correct!");
                correctAns++;
            } else {
                answerText.setText("Wrong!");
            }
            totalAttempt++;
            generateRandomNumber();
        }
    }

    public void playAgain(View view) {
        startCountDown();
        generateRandomNumber();
    }

    public void startPlaying(View view) {
        timerText.setVisibility(View.VISIBLE);
        sumText.setVisibility(View.VISIBLE);
        resultText.setVisibility(View.VISIBLE);
        answerText.setVisibility(View.VISIBLE);
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        goButton.setVisibility(View.INVISIBLE);
        startCountDown();
        generateRandomNumber();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_brain_train);
        timerText = (TextView) findViewById(R.id.timerText);
        sumText = (TextView) findViewById(R.id.sumText);
        resultText = (TextView) findViewById(R.id.resultText);
        answerText = (TextView) findViewById(R.id.answerText);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        goButton = (Button) findViewById(R.id.goButton);



    }
}
