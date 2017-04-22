package com.runtigersrun.runtigers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;


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


    public User (String fname, String lname, String uname, String pass, String mail){
        FirstName = fname;
        LastName = lname;
        Username = uname;
        Password = pass;
        Email = mail;

        updateInternal();
    }

    private void updateInternal(){
        try{

            //
            MainActivity.myDB.execSQL(
                    "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USERS + " (" +
                            COLUMN_NAME_USERID + " INTEGER PRIMARY KEY autoincrement not null," +
                            SPACE + COLUMN_NAME_FIRSTN + TEXT_TYPE + COMMA_SEP +
                            SPACE + COLUMN_NAME_LASTN + TEXT_TYPE + COMMA_SEP +
                            SPACE + COLUMN_NAME_USERN + TEXT_TYPE + COMMA_SEP +
                            SPACE + COLUMN_NAME_PASS + TEXT_TYPE + COMMA_SEP +
                            SPACE + COLUMN_NAME_EMAIL + TEXT_TYPE  +
                            ")");

            MainActivity.myDB.execSQL("INSERT INTO " + MainActivity.TABLE_NAME_USERS + SPACE +
                    "VALUES (NULL, '" + FirstName + "', '" + LastName + "', '" + Username + "', '" + Password + "', '" + Email + "')");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
