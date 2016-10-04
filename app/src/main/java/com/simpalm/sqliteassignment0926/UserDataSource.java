package com.simpalm.sqliteassignment0926;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Simpalm on 9/30/16.
 */

public class UserDataSource {
    SqliteOpenHelper sqliteHelper;
    SQLiteDatabase sqLiteDatabase;
    public SharedPreferences mSharedPreferences;

    String[] columnNames = {SqliteOpenHelper.COLUMN_NAME_ID, SqliteOpenHelper.USER_USERNAME, SqliteOpenHelper.USER_PASSWORD};
    String[] contactcolumnNames = {SqliteOpenHelper.COLUMN_NAME_ID, SqliteOpenHelper.USER_USERNAME, SqliteOpenHelper.USER_CONTACT_NAME, SqliteOpenHelper.USER_CONTACT_PHONE, SqliteOpenHelper.USER_CONTACT_DOB, SqliteOpenHelper.USER_CONTACT_ADDRESS};


    public UserDataSource(Context context) {

        sqliteHelper = new SqliteOpenHelper(context);


    }

    public void open() {

        sqLiteDatabase = sqliteHelper.getWritableDatabase();


    }

    public void insertNewUser(String userName, String password) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(SqliteOpenHelper.USER_USERNAME, userName);
        contentValues.put(SqliteOpenHelper.USER_PASSWORD, password);


        sqLiteDatabase.insert(SqliteOpenHelper.USER_TABLE, null, contentValues);
        Log.d("Sign Up Data", "insert new user ");


    }

    public void addContact(String userName, String Name, String Phone, String DOB, String Address) {


        ContentValues contentValues = new ContentValues();
        contentValues.put(SqliteOpenHelper.USER_USERNAME, userName);
        contentValues.put(SqliteOpenHelper.USER_CONTACT_NAME, Name);
        contentValues.put(SqliteOpenHelper.USER_CONTACT_PHONE, Phone);
        contentValues.put(SqliteOpenHelper.USER_CONTACT_DOB, DOB);
        contentValues.put(SqliteOpenHelper.USER_CONTACT_ADDRESS, Address);

        sqLiteDatabase.insert(SqliteOpenHelper.USER_CONTACT_TABLE, null, contentValues);
        Log.d("Add a new contact", "contact");

    }


   /* public String getSingleEntry(String userName, String password) {

        String[] column = new String[]{
                SqliteOpenHelper.USER_USERNAME
        };
        Cursor cursor = sqLiteDatabase.query(SqliteOpenHelper.USER_TABLE, columnNames, SqliteOpenHelper.USER_USERNAME + " = " + "'" + userName + "'",
                null, null, null, null);
        if (cursor.getCount() < 1) {
            cursor.close();
            return "User does not exist";
        }
        cursor.moveToFirst();
        password = cursor.getString(cursor.getColumnIndex(SqliteOpenHelper.USER_PASSWORD));
        cursor.close();
        return password;
    }*/


    public boolean checkUserExist(String userName) {

        String[] column = new String[]{
                SqliteOpenHelper.USER_USERNAME
        };

        Cursor cursor = sqLiteDatabase.query(SqliteOpenHelper.USER_TABLE, column, SqliteOpenHelper.USER_USERNAME + " = " + "'" + userName + "'"
                , null, null, null, null);

        Integer count = cursor.getCount();
        if (count > 0) {

            return true;
        }
        return false;
    }

    public boolean checkUserNotExist(String userName) {


        Cursor cursor = sqLiteDatabase.query(SqliteOpenHelper.USER_TABLE, columnNames, SqliteOpenHelper.USER_USERNAME + " = " + "'" + userName + "'"
                , null, null, null, null);

        Integer count = cursor.getCount();
        if (count < 1) {

            return true;
        }
        return false;
    }

    public String checkUserPassword(String userName) {


        Cursor cursor = sqLiteDatabase.query(SqliteOpenHelper.USER_TABLE, columnNames, SqliteOpenHelper.USER_USERNAME + " = " + "'" + userName + "'"
                , null, null, null, null);

        Integer count = cursor.getCount();
        cursor.moveToFirst();
        if (count > 0) {


            String password = cursor.getString(2);

            Log.d("Checkuserpassword", password);

            return password;


        }
        return null;
    }

    public void closeDatabase() {

        sqLiteDatabase.close();
    }
}


// select table where user_name =