package com.example.syncvj;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class NetworkMonitor  {
    public void deloneonline(final String name, final Long number, Context context){
        if(checkNetworkConnection(context)){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBsync.SERVER_URL_DELONE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("response");
                    if(Response.equals("OK")){
                        Log.i(TAG, "onResponse: deleted from server");
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
                params.put("Name", name);
                params.put("Number", String.valueOf(number));
                return params;
            }
        };
        MySingleton.getInstance(context).adddtoRequestQueue(stringRequest);

        }

    }

    public void updateoneonline(final String name, final Long number, final String namenew, final String postnew, final Long numbernew, final String emailnew, final String departmentnew, Context context){
        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBsync.SERVER_URL_UPDATEONE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String Response = jsonObject.getString("response");
                        if(Response.equals("OK")){
                            Log.i(TAG, "onResponse: updated server");
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
                    params.put("Name", name);
                    params.put("Number", String.valueOf(number));
                    params.put("Namenew", namenew);
                    params.put("Postnew", postnew);
                    params.put("Numbernew", String.valueOf(numbernew));
                    params.put("Emailnew", emailnew);
                    params.put("Departmentnew", departmentnew);
                    return params;
                }
            };
            MySingleton.getInstance(context).adddtoRequestQueue(stringRequest);

        }


    }
    /*public void syncAll(final Context context) {
        if(checkNetworkConnection(context)){

            StringRequest stringRequest_del = new StringRequest(Request.Method.DELETE, DBsync.SERVER_URL_DEL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if(Response.equals("OK")){
                                    Log.i(TAG, "deleted all ");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            MySingleton.getInstance(context).adddtoRequestQueue(stringRequest_del);

            final DBHelper dbHelper = new DBHelper(context);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();
            Cursor cursor = dbHelper.readFromLocalDatabase(database);

            while(cursor.moveToNext()){
                final int sync_status = cursor.getInt(cursor.getColumnIndex(DBsync.SYNC_STATUS));
                final String name = cursor.getString(cursor.getColumnIndex(DBsync.NAME));
                final String post = cursor.getString(cursor.getColumnIndex(DBsync.POST));
                final Long number = cursor.getLong(cursor.getColumnIndex(DBsync.NUMBER));
                final String email = cursor.getString(cursor.getColumnIndex(DBsync.EMAIL));
                final String department = cursor.getString(cursor.getColumnIndex(DBsync.DEPARTMENT));



                StringRequest stringRequest = new StringRequest(Request.Method.POST, DBsync.SERVER_URL_SYNC,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String Response = jsonObject.getString("response");
                                        if(Response.equals("OK")){
                                            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                                            //dbHelper.updateLocalDatabase(name,DBsync.SYNC_STATUS_OK,database);
                                        }
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<>();
                            params.put("Name",name);
                            params.put("Post",post);
                            params.put("Number", String.valueOf(number));
                            params.put("Email",email);
                            params.put("Department",department);

                            return params;

                        }
                    };
                    MySingleton.getInstance(context).adddtoRequestQueue(stringRequest);
                }

            dbHelper.close();
        }

    }*/

    public boolean checkNetworkConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
}
