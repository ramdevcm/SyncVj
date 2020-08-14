package com.example.syncvj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "create table "+ DBsync.TABLE_NAME+"( "+ DBsync.NAME+" text,"+ DBsync.POST+" text,"+ DBsync.NUMBER+" , "+ DBsync.EMAIL+" text,"+ DBsync.DEPARTMENT+" text , "+ DBsync.SYNC_STATUS+" integer);";
    private static final String DROP_TABLE = "drop table if exists "+ DBsync.TABLE_NAME;
    private static final String CREATE_TABLE_INTERCOMM = "create table "+ DBsync.TABLE_NAME_INTERCOM+"( "+ DBsync.NAME+" text,"+ DBsync.POST+" text,"+ DBsync.INT_COMM+" integer, "+ DBsync.DEPARTMENT+" text );";
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
    public void saveToLocalDatabase_intercomm(String name, String post, Long int_comm,String department, SQLiteDatabase database)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBsync.NAME,name);
        contentValues.put(DBsync.POST,post);
        contentValues.put(DBsync.INT_COMM,int_comm);
        contentValues.put(DBsync.DEPARTMENT,department);
        database.insert(DBsync.TABLE_NAME_INTERCOM,null,contentValues);
    }

    public Cursor readFromLocalDatabase_intercomm(SQLiteDatabase database){
        String[] projection = {DBsync.NAME,DBsync.POST, DBsync.INT_COMM,DBsync.DEPARTMENT};
        return (database.query(DBsync.TABLE_NAME_INTERCOM,projection,null,null,null,null,DBsync.DEPARTMENT));
    }

    public void updateoneLocalDatabase_intercomm(String nameold, Long int_commold, String namenew, String postnew, Long int_commnew, String departmentnew, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        String whereClause = DBsync.NAME+"='"+nameold+"' AND "+ DBsync.NUMBER+"="+int_commold;
        database.delete(DBsync.TABLE_NAME_INTERCOM, whereClause, null);
        contentValues.put(DBsync.NAME,namenew);
        contentValues.put(DBsync.POST,postnew);
        contentValues.put(DBsync.INT_COMM,int_commnew);
        contentValues.put(DBsync.DEPARTMENT,departmentnew);
        database.insert(DBsync.TABLE_NAME_INTERCOM,null,contentValues);

    }
    public void deleteoneLocalDatabase_intercomm(String nameold, Long int_comm, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        String whereClause = DBsync.NAME+"='"+nameold+"' AND "+ DBsync.NUMBER+"="+int_comm;
        database.delete(DBsync.TABLE_NAME_INTERCOM, whereClause, null);

    }
    //

    public void saveToLocalDatabase(String name, String post, Long number, String email, String department, int sync_status, SQLiteDatabase database)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBsync.NAME,name);
        contentValues.put(DBsync.POST,post);
        contentValues.put(DBsync.NUMBER,number);
        contentValues.put(DBsync.EMAIL,email);
        contentValues.put(DBsync.DEPARTMENT,department);
        contentValues.put(DBsync.SYNC_STATUS,sync_status);
        database.insert(DBsync.TABLE_NAME,null,contentValues);
    }

    public Cursor readFromLocalDatabase(SQLiteDatabase database,String department_select){
        String[] projection = {DBsync.NAME,DBsync.POST, DBsync.NUMBER,DBsync.EMAIL,DBsync.DEPARTMENT,DBsync.SYNC_STATUS};
        String whereClause = DBsync.DEPARTMENT+"=?";
        String whereArgs[] = {department_select};
        return (database.query(DBsync.TABLE_NAME,projection,whereClause,whereArgs,null,null,DBsync.NAME));
    }

    public void updateoneLocalDatabase(String nameold, Long numberold, String namenew, String postnew, Long numbernew, String emailnew, String departmentnew, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        String whereClause = DBsync.NAME+"='"+nameold+"' AND "+ DBsync.NUMBER+"="+numberold;
        database.delete(DBsync.TABLE_NAME, whereClause, null);
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
