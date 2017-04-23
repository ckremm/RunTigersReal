package com.runtigersrun.runtigers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;
import static android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE;


/**
 * Created by cjkremm on 4/20/17.
 */

public class User {


    public static final String TABLE_NAME_USERS = "Users";
    public static final String COLUMN_NAME_USERID = "userID";
    public static final String COLUMN_NAME_FIRSTN = "FirstName";
    public static final String COLUMN_NAME_LASTN = "LastName";
    public static final String COLUMN_NAME_USERN = "Username";
    public static final String COLUMN_NAME_PASS = "Password";
    public static final String COLUMN_NAME_EMAIL = "Email";
    private static final String TEXT_TYPE = " VARCHAR";
    private static final String COMMA_SEP = ",";
    private static final String SPACE = " ";


    private String FirstName;
    private String LastName;
    private String Username;
    private String Password;
    private String Email;


    public User(String fname, String lname, String uname, String pass, String mail) {
        FirstName = fname;
        LastName = lname;
        Username = uname;
        Password = pass;
        Email = mail;

//        updateInternal();
    }
/*
    private void updateInternal(){
        try{

            //SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MainActivity.COLUMN_NAME_FIRSTN, FirstName);
            contentValues.put(MainActivity.COLUMN_NAME_LASTN, LastName);
            contentValues.put(MainActivity.COLUMN_NAME_USERN, Username);
            contentValues.put(MainActivity.COLUMN_NAME_PASS, Password);
            contentValues.put(MainActivity.COLUMN_NAME_EMAIL, Email);
            MainActivity.myDB.insert(MainActivity.TABLE_NAME_USERS, null, contentValues);
                 }
        catch (Exception e){
            e.printStackTrace();
        }
    }
*/
}