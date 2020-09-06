package com.example.syncvj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class OtpLoginActivity extends Activity {
    EditText username_text;
    ImageButton btLogin;
    int flag=0;

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
                final String Ph_number = username_text.getText().toString();
                final String[] name = new String[1];


                //----------Code to put the DB validation code;---------------------
                if (Ph_number.length() != 10) {
                    Toast.makeText(OtpLoginActivity.this, "Enter a valid Mobile Number!", Toast.LENGTH_SHORT).show();
                } else {


                StringRequest stringRequest = new StringRequest(Request.Method.POST, DBsync.SERVER_URL_GETONE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jarray;
                            JSONObject jsonObject;
                            jsonObject = new JSONObject(response);
                            jarray = jsonObject.getJSONArray("server_response");
                            int count = 0;
                            if (jarray.isNull(0)) {
                                Toast.makeText(OtpLoginActivity.this, "Server Down", Toast.LENGTH_SHORT).show();
                                flag = 0;
                            } else {
                                while (count < jarray.length()) {
                                    JSONObject jo = jarray.getJSONObject(count);
                                    name[0] = jo.getString("Name");
                                    flag = 1;
                                    count = count + 1;
                                    //The following code should be executed only after the DB validation is complete
                                    if (flag == 1) {//If the mobile number matches a DB value


                                        Intent i = new Intent(getApplicationContext(), SecurityCode.class);
                                        //Just add the name of the person into this field-------------------
                                        i.putExtra("Name", name[0]);
                                        i.putExtra("Ph_Number", Ph_number);

                                        startActivity(i);
                                        finish();
                                    } else {//If that mobile number does not exist in the DB
                                        Toast.makeText(OtpLoginActivity.this, "Mobile Number not found!", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("Ph_Number", Ph_number);
                        return params;
                    }
                };
                MySingleton.getInstance(getApplicationContext()).adddtoRequestQueue(stringRequest);

            }
                //-----------------------------------------------------------------




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
