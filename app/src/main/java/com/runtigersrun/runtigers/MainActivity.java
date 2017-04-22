package com.runtigersrun.runtigers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static final String COLUMN_NAME_USERID = "userID";
    public static final String COLUMN_NAME_FIRSTN = "FirstName";
    public static final String COLUMN_NAME_LASTN = "LastName";
    public static final String COLUMN_NAME_USERN = "Username";
    public static final String COLUMN_NAME_PASS = "Password";
    public static final String COLUMN_NAME_EMAIL = "Email";
    private static final String TEXT_TYPE = " VARCHAR";
    private static final String COMMA_SEP = ",";
    private static final String SPACE = " ";

    public static final String DATABASE_NAME = "RTR";
    public static final String TABLE_NAME_USERS = "User";
    public static SQLiteDatabase myDB;
    private EditText loginU;
    private EditText loginP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            myDB = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            MainActivity.myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS " + MainActivity.TABLE_NAME_USERS + " (" +
                        COLUMN_NAME_USERID + " INTEGER PRIMARY KEY autoincrement not null," +
                        COLUMN_NAME_FIRSTN + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_LASTN + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_USERN + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_PASS + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_EMAIL + TEXT_TYPE  +
                        " )");
            myDB.execSQL("DELETE FROM "+ TABLE_NAME_USERS);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void toInfo(View view){
        AlertDialog.Builder infoBuilder = new AlertDialog.Builder(MainActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            infoBuilder.setView(R.layout.activity_info);
        }

        infoBuilder.create();
        infoBuilder.show();
    }

    public void toRegister(View view){
        Intent registerIntent = new Intent(MainActivity.this, Registration.class);
        MainActivity.this.startActivity((registerIntent));
    }

    public void toTracks(View view) {

        final EditText loginU = (EditText) findViewById(R.id.loginUser);
        final EditText loginP = (EditText) findViewById(R.id.loginPass);
        Button loginB = (Button) findViewById(R.id.loginButton);
        TextView register = (TextView) findViewById(R.id.registerHereText);
        ImageButton info = (ImageButton) findViewById(R.id.infoButton);


        Cursor c = MainActivity.myDB.rawQuery("SELECT * FROM "+ TABLE_NAME_USERS
               + " WHERE TRIM(Username) = '" + loginU.getText().toString() + "'", null);


        int passwordindex = c.getColumnIndex("Password");
        c.moveToFirst();
        String password = c.getString(passwordindex);

        if(loginP.getText().toString() == password){
            Intent loginIntent = new Intent(MainActivity.this, Tracks.class);
            MainActivity.this.startActivity(loginIntent);
        }else{
            Toast.makeText(MainActivity.this, "Username or Password is incorrect", Toast.LENGTH_LONG).show();
        }


    }



}
