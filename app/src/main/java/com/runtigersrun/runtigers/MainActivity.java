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

public class MainActivity extends AppCompatActivity {

    public static final String DATABASE_NAME = "RTR";
    public static final String TABLE_NAME_USERS = "User";
    public static SQLiteDatabase myDB;
    private EditText loginU;
    private EditText loginP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

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
                + " WHERE Username = " + loginU.getText().toString(), null);
        if(loginP.getText().toString() == c.getString(c.getColumnIndex("Password"))){
            Intent loginIntent = new Intent(MainActivity.this, Tracks.class);
            MainActivity.this.startActivity(loginIntent);
        }else{
            Toast.makeText(MainActivity.this, "Username or Password is incorrect", Toast.LENGTH_LONG).show();
        }


    }
}
