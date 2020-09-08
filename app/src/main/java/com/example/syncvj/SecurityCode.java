package com.example.syncvj;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecurityCode extends AppCompatActivity implements View.OnClickListener {
    EditText code;
    int RC = 0; //This should be retrieved from the database and initial value should be null, NOTE : Datatype is made as String inorder to compare easily with EditText..

    //Once the email otp is completed, remove the value assigned to RC in the above line.
    //Also remove the comment on the RC value assign statement below(line 30's).

    SessionManagement session;
    String Name, Ph_Number;
    private static ImageView imgview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_code);
        code=findViewById(R.id.password_text);
        findViewById(R.id.btLogin).setOnClickListener(this);
        session = new SessionManagement(getApplicationContext());
        Name = getIntent().getStringExtra("Name");
        Ph_Number = getIntent().getStringExtra("Ph_Number");
        RC = getIntent().getIntExtra("security_code",0);
        OnclickButtonListener();
    }
    private void Login() {
        String cd = code.getText().toString().trim();
        if (cd.length() < 4) {
            code.setError("Min Length is 5");
            code.requestFocus();
        }
        if (cd.isEmpty()) {
            code.setError("Code Required");
            code.requestFocus();
            return;
        }
        if(cd.equals(String.valueOf(RC)))//Please Verify The Datatype of both variables
        {
            session.createLoginSession(Name,Ph_Number);
            Intent intent = new Intent (SecurityCode.this, MainActivity2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            //Log.i("hi", "Login: ");
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
    public void OnclickButtonListener() {

        imgview =  findViewById(R.id.imageView4);

        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(SecurityCode.this, "Click Ckick", Toast.LENGTH_SHORT).show();
                final Dialog epicDialog;
                Button aboutPopupBtn;
                TextView title, message;
                ImageView closePopupAbout;
                epicDialog = new Dialog(SecurityCode.this);
                epicDialog.setContentView(R.layout.fragment_codeinfo);
                closePopupAbout = (ImageView) epicDialog.findViewById(R.id.closePopupAbout);

                closePopupAbout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        epicDialog.dismiss();
                    }
                });
                //epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                epicDialog.show();
            }
        });

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