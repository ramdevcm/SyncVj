package com.example.syncvj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "create table "+ DBsync.TABLE_NAME+"( "+ DBsync.DESIGNATION+" tinytext,"+ DBsync.NAME+" tinytext,"+ DBsync.POST+" tinytext,"+ DBsync.NUMBER+" bigint, "+ DBsync.EMAIL+" tinytext,"+ DBsync.DEPARTMENT+" tinytext , "+ DBsync.SYNC_STATUS+" integer);";
    private static final String DROP_TABLE = "drop table if exists "+ DBsync.TABLE_NAME;
    private static final String CREATE_TABLE_INTERCOMM = "create table "+ DBsync.TABLE_NAME_INTERCOM+"( "+ DBsync.NAME+" tinytext,"+ DBsync.POST+" tinytext,"+ DBsync.INT_COMM+" integer, "+ DBsync.DEPARTMENT+" tinytext );";
    private static final String DROP_TABLE_INTERCOMM = "drop table if exists "+ DBsync.TABLE_NAME_INTERCOM;

    public DBHelper(Context context){
        super(context, DBsync.DATABASE_NAME,null,DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_INTERCOMM);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL(DROP_TABLE);
        db.execSQL(DROP_TABLE_INTERCOMM);
        onCreate(db);
    }

    //
    public void saveToLocalDatabase_intercomm(String name, String post, Long int_comm,String department, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBsync.NAME,name);
        contentValues.put(DBsync.POST,post);
        contentValues.put(DBsync.INT_COMM,int_comm);
        contentValues.put(DBsync.DEPARTMENT,department);
        database.insert(DBsync.TABLE_NAME_INTERCOM,null,contentValues);
    }

    public Cursor readFromLocalDatabase_intercomm(SQLiteDatabase database,String department_select){
        String[] projection = {DBsync.NAME,DBsync.POST, DBsync.INT_COMM,DBsync.DEPARTMENT};
        if(department_select.equals("Link") || department_select.equals("VJIM")){
            String whereClause = DBsync.DEPARTMENT+"=?";
            String whereArgs[] = {department_select};
            return (database.query(DBsync.TABLE_NAME_INTERCOM,projection,whereClause,whereArgs,null,null,DBsync.DEPARTMENT));
        }
        else{
            String whereClause = DBsync.DEPARTMENT+" NOT IN ('Link','VJIM')";
            //String whereArgs[] = {department_select};
            return (database.query(DBsync.TABLE_NAME_INTERCOM,projection,whereClause,null,null,null,DBsync.DEPARTMENT));
        }
    }

    public void updateoneLocalDatabase_intercomm(String nameold, Long int_commold, String namenew, String postnew, Long int_commnew, String departmentnew, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        if(departmentnew.equals("Link")){
            String whereClause = DBsync.NAME+"='"+nameold+"' AND "+ DBsync.DEPARTMENT+"="+departmentnew;
            database.delete(DBsync.TABLE_NAME_INTERCOM, whereClause, null);
        }
        else{
        String whereClause = DBsync.NAME+"='"+nameold+"' AND "+ DBsync.NUMBER+"="+int_commold;
        database.delete(DBsync.TABLE_NAME_INTERCOM, whereClause, null);
        }
        contentValues.put(DBsync.NAME,namenew);
        contentValues.put(DBsync.POST,postnew);
        contentValues.put(DBsync.INT_COMM,int_commnew);
        contentValues.put(DBsync.DEPARTMENT,departmentnew);
        database.insert(DBsync.TABLE_NAME_INTERCOM,null,contentValues);

    }
    public void deleteoneLocalDatabase_intercomm(String nameold, Long int_comm,String department, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        if(department.equals("Link")){
            String whereClause = DBsync.NAME+"='"+nameold+"' AND "+ DBsync.DEPARTMENT+"="+department;
            database.delete(DBsync.TABLE_NAME_INTERCOM, whereClause, null);
        }
        else{
        String whereClause = DBsync.NAME+"='"+nameold+"' AND "+ DBsync.NUMBER+"="+int_comm;
        database.delete(DBsync.TABLE_NAME_INTERCOM, whereClause, null);
        }

    }
    //
    public Cursor readFromLocalDatabaseadmn(SQLiteDatabase database,String department_select,String department_select1){
        String[] projection = {DBsync.DESIGNATION,DBsync.NAME,DBsync.POST, DBsync.NUMBER,DBsync.EMAIL,DBsync.DEPARTMENT,DBsync.SYNC_STATUS};
        String whereClause = DBsync.DEPARTMENT+" IN (?,?)";
        String whereArgs[] = {department_select,department_select1};
        return (database.query(DBsync.TABLE_NAME,projection,whereClause,whereArgs,null,null,DBsync.NAME));
    }

    //
    public Cursor readFromLocalDatabasegeneral(SQLiteDatabase database,String department_select,String department_select1,String department_select2,String department_select3,String department_select4) {
        String[] projection = {DBsync.DESIGNATION, DBsync.NAME, DBsync.POST, DBsync.NUMBER, DBsync.EMAIL, DBsync.DEPARTMENT, DBsync.SYNC_STATUS};
        String whereClause = DBsync.DEPARTMENT + " IN (?,?,?,?,?)";
        String whereArgs[] = {department_select, department_select1, department_select2, department_select3, department_select4};
        return (database.query(DBsync.TABLE_NAME, projection, whereClause, whereArgs, null, null, DBsync.NAME));
    }

    //
    public void saveToLocalDatabase(String designation,String name, String post, Long number, String email, String department, int sync_status, SQLiteDatabase database)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBsync.DESIGNATION,designation);
        contentValues.put(DBsync.NAME,name);
        contentValues.put(DBsync.POST,post);
        contentValues.put(DBsync.NUMBER,number);
        contentValues.put(DBsync.EMAIL,email);
        contentValues.put(DBsync.DEPARTMENT,department);
        contentValues.put(DBsync.SYNC_STATUS,sync_status);
        database.insert(DBsync.TABLE_NAME,null,contentValues);
    }

    public Cursor readFromLocalDatabase(SQLiteDatabase database,String department_select){
        String[] projection = {DBsync.DESIGNATION,DBsync.NAME,DBsync.POST, DBsync.NUMBER,DBsync.EMAIL,DBsync.DEPARTMENT,DBsync.SYNC_STATUS};
        String whereClause = DBsync.DEPARTMENT+"=?";
        String whereArgs[] = {department_select};
        return (database.query(DBsync.TABLE_NAME,projection,whereClause,whereArgs,null,null,DBsync.NAME));
    }

    public void updateoneLocalDatabase(String nameold, Long numberold,String designationnew, String namenew, String postnew, Long numbernew, String emailnew, String departmentnew, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        String whereClause = DBsync.NAME+"='"+nameold+"' AND "+ DBsync.NUMBER+"="+numberold;
        database.delete(DBsync.TABLE_NAME, whereClause, null);
        contentValues.put(DBsync.DESIGNATION,designationnew);
        contentValues.put(DBsync.NAME,namenew);
        contentValues.put(DBsync.POST,postnew);
        contentValues.put(DBsync.NUMBER,numbernew);
        contentValues.put(DBsync.EMAIL,emailnew);
        contentValues.put(DBsync.DEPARTMENT,departmentnew);
        database.insert(DBsync.TABLE_NAME,null,contentValues);

    }
    public void deleteoneLocalDatabase(String nameold, Long numberold, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        String whereClause = DBsync.NAME+"='"+nameold+"' AND "+ DBsync.NUMBER+"="+numberold;
        database.delete(DBsync.TABLE_NAME, whereClause,null);

    }
}
