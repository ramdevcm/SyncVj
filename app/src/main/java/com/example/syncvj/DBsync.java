package com.example.syncvj;

public class DBsync {

    public static final int SYNC_STATUS_OK = 0;
    public static final int SYNC_STATUS_FAILED =1;
    //table staffdetails
    public static final String DATABASE_NAME = "vjinfo";
    public static final String SERVER_URL_SYNC = "http://syncvj-com.stackstaging.com/sync_staffdetails.php";
    public static final String SERVER_URL_ISACTIVE = "http://syncvj-com.stackstaging.com/isactive.php";
    public static final String SERVER_URL_DEL = "http://syncvj-com.stackstaging.com/delete_staffdetails.php";
    public static final String SERVER_URL_GET = "http://syncvj-com.stackstaging.com/getAll.php";
    public static final String SERVER_URL_DELONE = "http://syncvj-com.stackstaging.com/delone_staffdetails.php";
    public static final String SERVER_URL_UPDATEONE = "http://syncvj-com.stackstaging.com/updateone_staffdetails.php";
    public static final String TABLE_NAME = "staffdetails";
    public static final String NAME = "NAME";
    public static final String POST = "POST";
    public static final String NUMBER = "NUMBER";
    public static final String EMAIL = "EMAIL";
    public static final String DEPARTMENT = "DEPARTMRNT";
    public static final String SYNC_STATUS = "SYNC_STATUS";
    //table Intercom
    public static final String SERVER_URL_SYNC_INTERCOM = "http://syncvj-com.stackstaging.com/sync_Intercom.php";
    public static final String SERVER_URL_GET_INTERCOM = "http://syncvj-com.stackstaging.com/getAll_Intercom.php";
    public static final String SERVER_URL_DELONE_INTERCOM = "http://syncvj-com.stackstaging.com/delone_Intercom.php";
    public static final String SERVER_URL_UPDATEONE_INTERCOM = "http://syncvj-com.stackstaging.com/updateone_Intercom.php";
    public static final String TABLE_NAME_INTERCOM = "intercomm";
    public static final String INT_COMM = "INT_COMM";

}
