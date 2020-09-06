package com.example.syncvj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class OtpLoginActivity extends Activity {
    EditText username_text;
    ImageButton btLogin;

    SessionManagement session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_login);

        session = new SessionManagement(getApplicationContext());

        username_text = (EditText) findViewById(R.id.username_text);

        btLogin = (ImageButton) findViewById(R.id.btLogin);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Ph_number = username_text.getText().toString();

                //----------Code to put the DB validation code;---------------------



                //-----------------------------------------------------------------

                if(Ph_number.length() == 10){
                    //The following code should be executed only after the DB validation is complete
                    if(true){//If the mobile number matches a DB value


                        Intent i = new Intent(getApplicationContext(),SecurityCode.class);
                        //Just add the name of the person into this field-------------------
                        i.putExtra("Name","Agin");
                        i.putExtra("Ph_Number",Ph_number);

                        startActivity(i);
                        finish();
                    }
                    else{//If that mobile number does not exist in the DB
                        Toast.makeText(OtpLoginActivity.this, "Mobile Number not found!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(OtpLoginActivity.this, "Enter a valid Mobile Number!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess((android.os.Process.myPid()));
        System.exit(1);
    }

     */
}
