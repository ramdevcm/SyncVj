package com.example.syncvj;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    Button vjecbutton, vjimbutton, buttonSync;
    FloatingActionButton fab_main, fab_op1, fab_op2, fab_op3, logout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    int ADMIN;
    int flag = 1;
    ActionBarDrawerToggle toggle;
    String JSONString;
    String JSONString_intercomm;
    SessionManagement session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManagement(getApplicationContext());
        session.checkLogin();
        setContentView(R.layout.activity_main2);
        initFabMenu();
        fab_op1.setVisibility(View.GONE);
        fab_op2.setVisibility(View.GONE);
        fab_op3.setVisibility(View.GONE);
        ADMIN = getIntent().getIntExtra("ADMIN", 0);
        if (ADMIN == 0) {
            fab_main.setVisibility(View.GONE);
            //fab_op1.setVisibility(View.GONE);
            //fab_op2.setVisibility(View.GONE);
           // fab_op3.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ADMIN = 0;
                fab_main.setVisibility(View.GONE);
                fab_op1.setVisibility(View.GONE);
                fab_op2.setVisibility(View.GONE);
                fab_op3.setVisibility(View.GONE);
                logout.setVisibility(View.GONE);
                Toast.makeText(MainActivity2.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        //......................HOOKS.......................

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        //....................TOOL bAR.........................
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //....................NAVIGATION DRAWER MENU................
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // Handle navigation view item clicks here.

                switch (item.getItemId()) {

                    case R.id.nav_admin:
                        Intent intent = new Intent(MainActivity2.this, Login.class);
                        startActivity(intent);
                        intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        break;
                    case R.id.nav_share:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://drive.google.com/file/d/1kYR66Eaf6pa-hKJZSXkBKgBKKtK3Z-Hf/view?usp=sharing"); //GIVE URL OF APP
                        sendIntent.setType("text/plain");
                        Intent.createChooser(sendIntent, "Share via");
                        startActivity(Intent.createChooser(sendIntent, "Share SyncVj via"));
                        break;
                    case R.id.nav_about:
                        final Dialog epicDialog;
                        Button aboutPopupBtn;
                        TextView title, message;
                        ImageView closePopupAbout;
                        epicDialog = new Dialog(MainActivity2.this);
                        epicDialog.setContentView(R.layout.fragment_about);
                        closePopupAbout = (ImageView) epicDialog.findViewById(R.id.closePopupAbout);

                        closePopupAbout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                epicDialog.dismiss();
                            }
                        });
                        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        epicDialog.show();
                        break;
                    case R.id.nav_exit:
                        moveTaskToBack(true);
                        android.os.Process.killProcess((android.os.Process.myPid()));
                        System.exit(1);
                        break;
                }

                return true;
            }

        });


        //................................................................
        vjecbutton = findViewById(R.id.vjecButton);
        vjimbutton = findViewById(R.id.vjimButton);
        buttonSync = findViewById(R.id.buttonSync);

        vjecbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, VjecActivity1.class);
                intent.putExtra("ADMIN", ADMIN);
                startActivity(intent);
            }
        });

        vjimbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, VjimActivity1.class);
                intent.putExtra("ADMIN", ADMIN);
                startActivity(intent);

            }
        });

        buttonSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNetworkConnection()) {
                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                    SQLiteDatabase database = dbHelper.getReadableDatabase();
                    dbHelper.onUpgrade(database, 1, 1);
                    dbHelper.close();
                    Toast.makeText(MainActivity2.this, "Please wait...SYNCING", Toast.LENGTH_SHORT).show();
                    //new backgroundTask().execute();
                    //new backgroundTask_intercom().execute();
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, DBsync.SERVER_URL_GET,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jarray;
                                        JSONObject jsonObject;
                                        jsonObject = new JSONObject(response);
                                        jarray = jsonObject.getJSONArray("server_response");
                                        int count = 0;
                                        String name, post, email, department,designation;
                                        Long number;
                                        if (jarray.isNull(0)) {
                                            Toast.makeText(MainActivity2.this, "Server Down", Toast.LENGTH_SHORT).show();
                                            flag = 0;
                                        } else {
                                            while (count < jarray.length()) {
                                                JSONObject jo = jarray.getJSONObject(count);
                                                designation = jo.getString("Designation");
                                                name = jo.getString("Name");
                                                post = jo.getString("Post");
                                                number = Long.parseLong(jo.getString("Ph_Number"));
                                                email = jo.getString("Email");
                                                department = jo.getString("Department");

                                                saveToLocalDatabase(designation,name, post, number, email, department, DBsync.SYNC_STATUS_OK);
                                                count = count + 1;


                                            }
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //displaying the error in toast if occurrs
                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    StringRequest stringRequest_intercomm = new StringRequest(Request.Method.GET, DBsync.SERVER_URL_GET_INTERCOM,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {


                                    try {
                                        JSONArray jarray_intercomm;
                                        JSONObject jsonObject_intercomm;
                                        jsonObject_intercomm = new JSONObject(response);
                                        jarray_intercomm = jsonObject_intercomm.getJSONArray("server_response");
                                        int count = 0;
                                        String name, post, department;
                                        long int_comm;
                                        if (jarray_intercomm.isNull(0)) {
                                            Toast.makeText(MainActivity2.this, "Server Down", Toast.LENGTH_SHORT).show();
                                            flag = 0;
                                        } else {
                                            while (count < jarray_intercomm.length()) {
                                                JSONObject jo = jarray_intercomm.getJSONObject(count);
                                                name = jo.getString("Name");
                                                post = jo.getString("Post");
                                                int_comm = Long.parseLong(jo.getString("Int_comm"));
                                                department = jo.getString("Department");

                                                saveToLocalDatabase_intercom(name, post, int_comm, department);
                                                count = count + 1;


                                            }
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //displaying the error in toast if occurrs
                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    MySingleton.getInstance(getApplicationContext()).adddtoRequestQueue(stringRequest);
                    MySingleton.getInstance(getApplicationContext()).adddtoRequestQueue(stringRequest_intercomm);
                    if(flag == 1) {
                        Toast.makeText(MainActivity2.this, "Sync Succesful", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Turn on internet", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    /************************* FOR FLOATING BUTTON IN ADMIN SESSION ******************/


    float translationY = 100f;

    private void initFabMenu() {

        fab_main = findViewById(R.id.fab_main);
        fab_op1 = findViewById(R.id.fab_op1);
        fab_op2 = findViewById(R.id.fab_op2);
        fab_op3 = findViewById(R.id.fab_op3);
        logout = findViewById(R.id.logout);


        fab_op1.setAlpha(0f);
        fab_op2.setAlpha(0f);
        fab_op3.setAlpha(0f);
        logout.setAlpha(0f);

        fab_op1.setTranslationY(translationY);
        fab_op2.setTranslationY(translationY);
        fab_op3.setTranslationY(translationY);
        logout.setTranslationY(translationY);

        fab_main.setOnClickListener(this);
        fab_op1.setOnClickListener(this);
        fab_op2.setOnClickListener(this);
        fab_op3.setOnClickListener(this);
        logout.setOnClickListener(this);


    }

    OvershootInterpolator interpolator = new OvershootInterpolator();
    Boolean isMenuOpen = false;

    private void openMenu() {
        isMenuOpen = !isMenuOpen;

        fab_main.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();
        fab_op1.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fab_op2.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fab_op3.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        logout.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();

    }

    private void closeMenu() {
        isMenuOpen = !isMenuOpen;

        fab_main.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();
        fab_op1.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fab_op2.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fab_op3.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        logout.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fab_main:
            case R.id.fab_op1:
            case R.id.fab_op2:
            case R.id.fab_op3:

                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.logout:


        }


    }

    /*********************************** FLOATING BUTTON END *******************************************/



    public void saveToLocalDatabase(String designation,String name, String post, Long number, String email, String department, int status) {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToLocalDatabase(designation,name, post, number, email, department, status, database);
        dbHelper.close();
    }

    public void saveToLocalDatabase_intercom(String name, String post, Long int_comm, String department) {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToLocalDatabase_intercomm(name, post, int_comm, department, database);
        dbHelper.close();
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    /***********************back press exit**************/
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess((android.os.Process.myPid()));
        System.exit(1);
    }
}
