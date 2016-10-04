package com.simpalm.sqliteassignment0926;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Simpalm on 9/30/16.
 */

public class SqliteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Userdatabase.db";
    private static final int DATABASE_VERSION = 1;
    public static final String USER_TABLE = "UserTable";
    public static final String USER_CONTACT_TABLE = "UserContactTable";
    public static final String USER_CONTACT_NAME = "UserContactName";
    public static final String USER_CONTACT_PHONE = "UserContactPhone";
    public static final String USER_CONTACT_DOB = "UserContactDob";
    public static final String USER_CONTACT_ADDRESS = "UserContactAddress";

    public static final String USER_USERNAME = "Username";
    public static final String USER_PASSWORD = "Password";
    public static final String COLUMN_NAME_ID = "_id";

    private static final String DATABASE_SIGNUP_USER = "create table " + USER_TABLE + " (" + COLUMN_NAME_ID + " integer primary key autoincrement," +
            USER_USERNAME + " text not null, " + USER_PASSWORD + " text not null);";

    private static final String DATABASE_LOGGED_IN_USER = "create table " + USER_CONTACT_TABLE + " (" + COLUMN_NAME_ID + " integer primary key autoincrement, " + USER_USERNAME + " text not null, "+  USER_CONTACT_NAME + " text not null, " + USER_CONTACT_PHONE + " text not null, " + USER_CONTACT_DOB + " text not null, " + USER_CONTACT_ADDRESS + " text not null);";

    private static final String DATABASE_DELETE_QUERY = "drop table if exists " + USER_TABLE;
    private static final String USERCONTACT_DATABASE_DELETE = "drop table if exists " + USER_CONTACT_TABLE;


    public SqliteOpenHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_SIGNUP_USER);
        sqLiteDatabase.execSQL(DATABASE_LOGGED_IN_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DATABASE_DELETE_QUERY);
        sqLiteDatabase.execSQL(USERCONTACT_DATABASE_DELETE);
        onCreate(sqLiteDatabase);
    }

}
