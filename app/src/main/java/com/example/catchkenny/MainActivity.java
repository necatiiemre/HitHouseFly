package com.example.catchkenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView score, highScoreText;
    TextView timeText;

    int before = -1;
    Handler handler;
    Runnable runnable;
    int totalScore , highScore ;

    int time;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView imageView10;
    ImageView imageView11;
    ImageView imageView12;

    ImageView imageArray[] ;

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = findViewById(R.id.scorer);
        timeText = findViewById(R.id.timer);
        highScoreText = findViewById(R.id.highScoreText);
        totalScore = 0;
        time = 60;
        imageView1= findViewById(R.id.imageView1);
        imageView2= findViewById(R.id.imageView2);
        imageView3= findViewById(R.id.imageView3);
        imageView4= findViewById(R.id.imageView4);
        imageView5= findViewById(R.id.imageView5);
        imageView6= findViewById(R.id.imageView6);
        imageView7= findViewById(R.id.imageView7);
        imageView8= findViewById(R.id.imageView8);
        imageView9= findViewById(R.id.imageView9);
        imageView10= findViewById(R.id.imageView10);
        imageView11= findViewById(R.id.imageView11);
        imageView12= findViewById(R.id.imageView12);

        imageArray = new ImageView[] {imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,imageView10,imageView11,imageView12};

        sp = this.getSharedPreferences("com.example.catchkenny", Context.MODE_PRIVATE);

        highScore = sp.getInt("HighScore", 0);

        if (highScore == 0){
            highScoreText.setText("High Score : 0");
        }
        else{
            highScoreText.setText("High Score : " + highScore);
        }


        hideImage();

        new CountDownTimer(60000,1000){


            @Override
            public void onTick(long l) {
                timeText.setText("Time : " + (l /1000));
            }

            @Override
            public void onFinish() {
                timeText.setText("Time Over ");

                if(totalScore > highScore){
                    sp.edit().putInt("HighScore", totalScore).apply();
                }
                handler.removeCallbacks(runnable);

                for (ImageView image:imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart ?");
                alert.setMessage("Are you sure to restart game ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "End Game", Toast.LENGTH_LONG).show();
                        System.exit(0);
                    }
                });
                alert.show();
            }
        }.start();


    }

    public void getPoint(View view){
        totalScore++;
        score.setText("Score : " + totalScore);
    }

    public void hideImage(){


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image:imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }
                Random random = new Random();
                int i = random.nextInt(12);
                while(before == i){
                    i = random.nextInt(12);
                }
                before = i;
                imageArray[i].setVisibility(View.VISIBLE);

                handler.postDelayed(runnable,500);
            }
        };

        handler.post(runnable);
    }

}