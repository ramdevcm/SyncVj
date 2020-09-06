package com.example.syncvj;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.se.omapi.Session;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Timer timer;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    // private TextView textView;
    SessionManagement session;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManagement(getApplicationContext());
        progressBar = findViewById(R.id.progressBar);
        //textView = (TextView) findViewById(R.id.textView);
        // Start long running operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus <= 100) {

                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            //textView.setText(progressStatus+"%");
                        }
                    });
                    try {
                        // Sleep for 20 milliseconds.
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressStatus += 1;
                }
            }
        }).start();
        // FOR SCHEDULING ACTIVITY 2
        HashMap<String,String> user = session.getUserDetails();
        final String name = user.get(SessionManagement.PH_NUMBER);
        Log.i("session value",""+name);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(name == null){
                        Intent i = new Intent(getApplicationContext(), OtpLoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            },2000);
        }


    }


