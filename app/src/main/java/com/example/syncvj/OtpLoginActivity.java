package com.example.syncvj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class OtpLoginActivity extends Activity {
    EditText PhEditText;
    Button Submit;

    SessionManagement session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_login);

        session = new SessionManagement(getApplicationContext());

        PhEditText = (EditText) findViewById(R.id.editTextPhone);

        Submit = (Button) findViewById(R.id.otpButton);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Ph_number = PhEditText.getText().toString();

                //----------Code to put the DB validation code;---------------------



                //-----------------------------------------------------------------

                if(Ph_number.length() == 10){
                    //The following code should be executed only after the DB validation is complete
                    if(true){//If the mobile number matches a DB value


                        Intent i = new Intent(getApplicationContext(),OTPRequestActivity.class);
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
}
