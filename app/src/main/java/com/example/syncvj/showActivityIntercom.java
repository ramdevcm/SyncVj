package com.example.syncvj;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class showActivityIntercom extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ListView listUser;
    EditText Name;
    EditText Post;
    EditText Int_comm;
    EditText Department;
    FloatingActionButton addIntercommbt;
    SearchView searchView;
    String department_select;
    int ADMIN;
    ListAdapter_intercomm adapter;
    ListAdapter_link adapter1;
    ArrayList<DBcontrol_intercom> arrayList;
    ArrayList<DBcontrol_intercom> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userview_intercomm);
        ADMIN = getIntent().getIntExtra("ADMIN",0);

        department_select = getIntent().getStringExtra("DEPT");
        addIntercommbt = findViewById(R.id.addNewIntercomm);
        searchView = findViewById(R.id.searchview);
        searchView.setFocusable(false);
        if(ADMIN == 0){
            addIntercommbt.setVisibility(View.GONE);
        }
        listUser = findViewById(R.id.listUser_intercomm);
        arrayList = new ArrayList<DBcontrol_intercom>();
        if(department_select.equals("Link")){
            adapter1 = new ListAdapter_link(getApplicationContext(),R.layout.staff_view_link,arrayList);
            listUser.setAdapter(adapter1);
            //Comment the below line to show the search button in "Frequents"
            searchView.setVisibility(View.GONE);
        }
        else{
            adapter = new ListAdapter_intercomm(getApplicationContext(),R.layout.staff_view_intercomm,arrayList);
            listUser.setAdapter(adapter);
        }

        listUser.setTextFilterEnabled(false);
        setupSearchView();

        readFromLocalStorage();
        for(DBcontrol_intercom x: arrayList)
            results.add(x);
        addIntercommbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.admin_departmentactivity_intercomm);
                Name = (EditText) findViewById(R.id.textView1_intercomm);
                Post = (EditText) findViewById(R.id.textView2_intercomm);
                Int_comm = (EditText) findViewById(R.id.textView3_intercomm);
                Department = (EditText) findViewById(R.id.textView4_intercomm);
                if(department_select.equals("Link")){
                    Post.setHint("Link");
                    Int_comm.setVisibility(View.GONE);
                    Department.setVisibility(View.GONE);
                }
            }
        });

        listUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(department_select.equals("Link")){
                    final String name = results.get(i).getName();
                    String post = results.get(i).getPost();
                    final Long int_comm = results.get(i).getInt_comm();
                    String department = results.get(i).getDepartment();
                    Intent intent = new Intent(showActivityIntercom.this, lookcloseIntercomm.class);
                    intent.putExtra("Name", name);
                    intent.putExtra("Post", post);
                    intent.putExtra("Int_comm", int_comm);
                    intent.putExtra("Department", department);
                    intent.putExtra("ADMIN", ADMIN);
                    intent.putExtra("DEPT", department_select);
                    startActivity(intent);
                }
                return false;
            }
        });

        listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(department_select.equals("Link")){
                        String post = results.get(i).getPost();
                        if (!post.startsWith("http://") && !post.startsWith("https://"))
                            post = "http://" + post;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(post));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setPackage("com.android.chrome");
                        startActivity(intent);
                    }

                    else {
                        final String name = results.get(i).getName();
                        String post = results.get(i).getPost();
                        final Long int_comm = results.get(i).getInt_comm();
                        String department = results.get(i).getDepartment();
                        Intent intent = new Intent(showActivityIntercom.this, lookcloseIntercomm.class);
                        intent.putExtra("Name", name);
                        intent.putExtra("Post", post);
                        intent.putExtra("Int_comm", int_comm);
                        intent.putExtra("Department", department);
                        intent.putExtra("ADMIN", ADMIN);
                        intent.putExtra("DEPT", department_select);
                        startActivity(intent);
                    }



            }
        });
    }

    private void setupSearchView(){
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search");

    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(department_select.equals("Link")){
            ListAdapter_link adapter = (ListAdapter_link) listUser.getAdapter();
            Filter filter = adapter.getFilter();
            filter.filter(s);
            results.clear();
            for(DBcontrol_intercom x : arrayList)
                if(x.getName().toLowerCase().contains(s))
                    results.add(x);
        }
        else{
            ListAdapter_intercomm adapter = (ListAdapter_intercomm) listUser.getAdapter();
            Filter filter = adapter.getFilter();
            filter.filter(s);
            results.clear();
            for(DBcontrol_intercom x : arrayList)
                if(x.getPost().toLowerCase().contains(s))
                    results.add(x);
        }

        if(TextUtils.isEmpty(s)){
            listUser.clearTextFilter();
            //adapter.notifyDataSetChanged();
        }
        else{
            listUser.setFilterText(s);
        }
        return false;
    }

    public void addNewDeptStaff_intercomm(View view){

        String name = Name.getText().toString();
        String post = Post.getText().toString();
        String int_comm = (Int_comm.getText().toString());
        String department = Department.getText().toString();
        if(department_select.equals("Link")){
            if(name.isEmpty() || post.isEmpty()){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }
            else{
                department=department_select;
                try {
                    long num = Long.parseLong("0");
                    saveToAppServer(name,post,num,department);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Name.setText("");
                    Post.setText("");
                    Int_comm.setText("");
                    Department.setText("");
                    Intent intent = new Intent(showActivityIntercom.this,showActivityIntercom.class);
                    intent.putExtra("ADMIN",ADMIN);
                    intent.putExtra("DEPT",department_select);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } catch (NumberFormatException nfe) {
                    System.out.println("Please enter a valid number");
                }
            }
        }
        else if(name.isEmpty() || post.isEmpty() || int_comm.isEmpty() || department.isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();

        }
        else{
            try {
                long num = Long.parseLong(int_comm);
                saveToAppServer(name,post, num,department);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Name.setText("");
                Post.setText("");
                Int_comm.setText("");
                Department.setText("");
                Intent intent = new Intent(showActivityIntercom.this,showActivityIntercom.class);
                intent.putExtra("ADMIN",ADMIN);
                intent.putExtra("DEPT","Intercom");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a valid number");
            }
        }


    }

    private void readFromLocalStorage(){
        arrayList.clear();
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database =  dbHelper.getReadableDatabase();
        Cursor cursor;
        if(department_select.equals("Link") || department_select.equals("MBA")){
            Log.i("hi", "readFromLocalStorage: "+department_select);
        }
        cursor = dbHelper.readFromLocalDatabase_intercomm(database,department_select);

        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(DBsync.NAME));
            String post = cursor.getString(cursor.getColumnIndex(DBsync.POST));
            Long int_comm = cursor.getLong(cursor.getColumnIndex(DBsync.INT_COMM));
            String department = cursor.getString(cursor.getColumnIndex(DBsync.DEPARTMENT));
            arrayList.add(new DBcontrol_intercom(name,post,int_comm,department));
        }



        if(department_select.equals("Link")){
            adapter1.notifyDataSetChanged();
        }
        else{
            adapter.notifyDataSetChanged();
        }
        cursor.close();
        dbHelper.close();

    }

    private void saveToAppServer(final String name, final String post, final Long int_comm, final String department){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBsync.SERVER_URL_SYNC_INTERCOM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    String Response = jsonObject.getString("response");
                    Log.i("MESSAGE","Within try of saveToAppServer");
                    saveToLocalDatabase(name,post,int_comm,department);
                }catch (JSONException e){
                    Log.i("MESSAGE","Inside catch of saveToAppServer");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("MESSAGE","Inside errorResponse of saveToAppServer");
                saveToLocalDatabase(name,post,int_comm,department);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Name",name);
                params.put("Post",post);
                params.put("Int_comm",String.valueOf(int_comm));
                params.put("Department",department);

                return params;
            }
        };
        MySingleton.getInstance(showActivityIntercom.this).adddtoRequestQueue(stringRequest);
        readFromLocalStorage();


    }

    public boolean checkNetworkConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }

    public void saveToLocalDatabase(String name,String post,Long int_comm,String department){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToLocalDatabase_intercomm(name,post,int_comm,department,database);
        readFromLocalStorage();
        dbHelper.close();
    }

}
