package com.example.barakiva.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //Getting ID's
    GridLayout sumLayout;
    //Buttons
    Button goBtn;
    Button retryBtn;
    //TextViews
    TextView exerciseTV;
    TextView secondsLeftTV;
    TextView scoreTV;
    TextView resultTV;

    CountDownTimer myCount;
    Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sumLayout = (GridLayout) findViewById(R.id.sumLayout);
        goBtn = (Button) findViewById(R.id.goBtn);
        exerciseTV = (TextView) findViewById(R.id.exerciseTV);
        resultTV = (TextView) findViewById(R.id.resultTV);
        retryBtn = (Button) findViewById(R.id.retryBtn);

    }
    public void setUpUI() {
        secondsLeftTV = (TextView) findViewById(R.id.secondsLeftTV);
        scoreTV = (TextView) findViewById(R.id.scoreTV);
        goBtn.setVisibility(View.INVISIBLE);
        secondsLeftTV.setVisibility(View.VISIBLE);
        exerciseTV.setVisibility(View.VISIBLE);
        scoreTV.setVisibility(View.VISIBLE);
        sumLayout.setVisibility(View.VISIBLE);
    }
    public void updateTimer(long secondsLeft) {
        int seconds = (int) secondsLeft/1000;
        secondsLeftTV.setText(Integer.toString(seconds) + "'s");
    }

    public void setUpCountDown() {
        myCount = new CountDownTimer(30000,1000) {

           @Override
           public void onTick(long l) {
                updateTimer(l);
           }

           @Override
           public void onFinish() {
                secondsLeftTV.setText("Done!");
                setUpEndGameUI();
                gameEnabled = false;
           }
       }.start();
    }
    int totalTries = 0;
    int correctAnswers = 0;
    int sumResult;
    boolean gameEnabled = true;
    public void goBtn(View view) {
        setUpUI();
        setUpCountDown();
        createExercise();
    }
    public void createExercise() {
        TextView sum1 = (TextView) findViewById(R.id.sum1);
        TextView sum2 = (TextView) findViewById(R.id.sum2);
        TextView sum3 = (TextView) findViewById(R.id.sum3);
        TextView sum4 = (TextView) findViewById(R.id.sum4);

        TextView [] sumArray = {sum1, sum2, sum3, sum4};
        int firstNum = random.nextInt(10);
        int secondNum = random.nextInt(10);
        sumResult = firstNum + secondNum;
        totalTries++;
        exerciseTV.setText(Integer.toString(firstNum) + " + " + Integer.toString(secondNum));
        int ran = random.nextInt(sumArray.length);
        for (int j = 0; j < sumArray.length;j++) {
            sumArray[j].setText(Integer.toString(random.nextInt(10 + sumResult)));
        }
        sumArray[ran].setText(Integer.toString(firstNum + secondNum));
    }
    public void sumClick(View view) {
        if (gameEnabled) {
            TextView txt = (TextView) findViewById(view.getId());
            Log.i("button clicked is: ", txt.getText().toString());
            if (sumResult == Integer.parseInt(txt.getText().toString())){
                correctAnswers++;
            }
            if (totalTries <= 15) {
                createExercise();
            }
            if (totalTries > 15) {
                gameEnabled = false;
            }
            updateScore();
        } else {
            Log.i("Game state", "Game Over!");
            setUpEndGameUI();
        }
    }
    public void updateScore () {
        scoreTV.setText(correctAnswers + " / " + totalTries);
    }
    public void setUpEndGameUI() {
        resultTV.setVisibility(View.VISIBLE);
        resultTV.setText("Your score is : " + Integer.toString(correctAnswers) + " / " + Integer.toString(totalTries));
        retryBtn.setVisibility(View.VISIBLE);
    }
    public void resetUI() {
        scoreTV.setText("0 / 0");
        totalTries = 0;
        correctAnswers = 0;
        gameEnabled = true;
        retryBtn.setVisibility(View.INVISIBLE);
        myCount.cancel();
    }
    public void retryBtn(View view) {
        resetUI();
        setUpCountDown();
        createExercise();
    }
}
