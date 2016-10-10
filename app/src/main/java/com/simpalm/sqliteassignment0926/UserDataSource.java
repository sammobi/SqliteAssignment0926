package com.simpalm.sqliteassignment0926;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Simpalm on 9/30/16.
 */

public class UserDataSource {
    SqliteOpenHelper sqliteHelper;
    SQLiteDatabase sqLiteDatabase;
    public SharedPreferences mSharedPreferences;
    Context mContext;

    String[] columnNames = {SqliteOpenHelper.COLUMN_NAME_ID, SqliteOpenHelper.USER_USERNAME, SqliteOpenHelper.USER_PASSWORD};

    String[] contactColumnNames = {SqliteOpenHelper.COLUMN_NAME_ID, SqliteOpenHelper.USER_USERNAME, SqliteOpenHelper.USER_CONTACT_NAME, SqliteOpenHelper.USER_CONTACT_DOB, SqliteOpenHelper.USER_CONTACT_PHONE, SqliteOpenHelper.USER_CONTACT_ADDRESS};


    public UserDataSource(Context context) {

        sqliteHelper = new SqliteOpenHelper(context);
        mContext = context;


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

    public void updateContact(int id, String userName, String Name, String Phone, String DOB, String Address) {


        ContentValues contentValues = new ContentValues();
        contentValues.put(SqliteOpenHelper.COLUMN_NAME_ID, id);
        contentValues.put(SqliteOpenHelper.USER_USERNAME, userName);
        contentValues.put(SqliteOpenHelper.USER_CONTACT_NAME, Name);
        contentValues.put(SqliteOpenHelper.USER_CONTACT_PHONE, Phone);
        contentValues.put(SqliteOpenHelper.USER_CONTACT_DOB, DOB);
        contentValues.put(SqliteOpenHelper.USER_CONTACT_ADDRESS, Address);

        sqLiteDatabase.update(SqliteOpenHelper.USER_CONTACT_TABLE, contentValues, SqliteOpenHelper.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)});
        Log.d("Update a contact ", "contact");

    }


    public ArrayList<User> getUsers() {

        String username = "";


        SharedPreferences mSharedPreferences = mContext.getSharedPreferences("Logged User", MODE_PRIVATE);
        username = mSharedPreferences.getString("user_name", "");
        ArrayList<User> userInformationArrayList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(SqliteOpenHelper.USER_CONTACT_TABLE, contactColumnNames, SqliteOpenHelper.USER_USERNAME + " = ?", new String[]{(username)}, null, null, null);
        if (cursor.getCount() == 0) {

            return userInformationArrayList;
        }
        cursor.moveToFirst();
        Log.d("ArrayList", "size of cursor" + cursor.getCount());

        do {

            User userInformation = new User();
            userInformation.setUsername(cursor.getString(1));

            userInformation.setId(cursor.getInt(0));
            userInformation.setName(cursor.getString(2));
            userInformation.setDob(cursor.getString(3));
            userInformation.setNumber(cursor.getString(4));
            userInformation.setAddress(cursor.getString(5));

           /* Log.d("ArrayList", " Name" + cursor.getString(1));
            Log.d("ArrayList", "Last Name" + cursor.getString(2));
            Log.d("ArrayList", "Email" + cursor.getString(4));
*/
            userInformationArrayList.add(userInformation);

        }

        // cursor.movetoNext will move to next row until finished
        while (cursor.moveToNext());


        cursor.close();
        return userInformationArrayList;
    }

    public void removeContact(int id) {

        open();

        int i = sqLiteDatabase.delete(SqliteOpenHelper.USER_CONTACT_TABLE, SqliteOpenHelper.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)});

        Log.d("Delete value", "value:" + i);

        closeDatabase();


    }

    public void updateContact(String username, String Name, String Phone, String DOB, String Address) {


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