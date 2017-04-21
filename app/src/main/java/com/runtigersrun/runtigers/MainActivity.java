package com.runtigersrun.runtigers;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Do you see this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText loginU = (EditText) findViewById(R.id.loginUser);
        EditText loginP = (EditText) findViewById(R.id.loginPass);
        Button loginB = (Button) findViewById(R.id.loginButton);
        TextView register = (TextView) findViewById(R.id.registerHereText);
        ImageButton info = (ImageButton) findViewById(R.id.infoButton);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, Tracks.class);
                MainActivity.this.startActivity(loginIntent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, Registration.class);
                MainActivity.this.startActivity((registerIntent));
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(MainActivity.this);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    infoBuilder.setView(R.layout.activity_info);
                }

                infoBuilder.create();
                infoBuilder.show();
            }
        });


    }
}
