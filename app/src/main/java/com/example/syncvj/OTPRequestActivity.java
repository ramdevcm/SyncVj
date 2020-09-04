package com.example.syncvj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class OTPRequestActivity extends Activity {

    SessionManagement session;

    Button verify;

    TextView otp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_request);
        //Remove the below comment once Akshay makes his side of the code(DB side)
        String Name =  getIntent().getStringExtra("Name");
        String Ph_Number = getIntent().getStringExtra("Ph_Number");
        session = new SessionManagement(getApplicationContext());

        otp = findViewById(R.id.editTextNumberOtp);

        //session.checkLogin(); //Can use this to check if he is logged in;

        //OTP request code here------------------------------------------


        //---------------------------------------------------------------
        //Once the otp is verified, below line should execute
        if(true){
            session.createLoginSession(Name,Ph_Number);
            Toast.makeText(this, ""+Name+" "+Ph_Number, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(),SecurityCode.class);
            startActivity(i);
            finish();
        }

    }
}
