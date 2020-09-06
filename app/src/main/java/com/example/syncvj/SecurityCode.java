package com.example.syncvj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SecurityCode extends AppCompatActivity implements View.OnClickListener {
    EditText code;
    String RC = "1234"; //This should be retrieved from the database and initial value should be null, NOTE : Datatype is made as String inorder to compare easily with EditText..

    //Once the email otp is completed, remove the value assigned to RC in the above line.
    //Also remove the comment on the RC value assign statement below(line 30's).

    SessionManagement session;
    String Name, Ph_Number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_code);
        code=findViewById(R.id.password_text);
        findViewById(R.id.btLogin).setOnClickListener(this);
        session = new SessionManagement(getApplicationContext());
        Name = getIntent().getStringExtra("Name");
        Ph_Number = getIntent().getStringExtra("Ph_Number");
        //RC = getIntent().getStringExtra("security_code");
    }
    private void Login() {
        String cd = code.getText().toString().trim();
        if (cd.length() < 4) {
            code.setError("Min Length is 4");
            code.requestFocus();
        }
        if (cd.isEmpty()) {
            code.setError("Code Required");
            code.requestFocus();
            return;
        }
        if(cd.equals(RC))//Please Verify The Datatype of both variables
        {
            session.createLoginSession(Name,Ph_Number);
            Intent intent = new Intent (SecurityCode.this, MainActivity2.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(SecurityCode.this,"Wrong Code, Try Again !!", Toast.LENGTH_SHORT).show();
            code.requestFocus();
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btLogin) {
            Login();
        }
    }
/*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(),OtpLoginActivity.class);
        startActivity(i);
    }

 */
}