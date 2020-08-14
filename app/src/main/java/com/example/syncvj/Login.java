package com.example.syncvj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    EditText username_text,password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username_text=(EditText)findViewById(R.id.username_text);
        password_text=(EditText)findViewById(R.id.password_text);
        mAuth=FirebaseAuth.getInstance();
        findViewById(R.id.btLogin).setOnClickListener(this);
    }
    private void userLogin()
    {
        String username=username_text.getText().toString().trim();
        String pw=password_text.getText().toString().trim();
        if(username.isEmpty()){
            username_text.setError("Username Required");
            username_text.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            username_text.setError("Please Enter Valid Username");
            username_text.requestFocus();
        }
        if(pw.length()<6){
            password_text.setError("Min Length is 6");
            password_text.requestFocus();
        }
        if(pw.isEmpty()){
            password_text.setError("Password Required");
            password_text.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(username, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent (Login.this, MainActivity2.class);
                    intent.putExtra("ADMIN",1);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                //if(!task.isSuccessful())
               // {
                  //  Toast.makeText(getApplicationContext(), "Please Provide Correct Credentials", Toast.LENGTH_SHORT).show();
                //}


            }

        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btLogin:
                userLogin();
                break;
        }
    }
}