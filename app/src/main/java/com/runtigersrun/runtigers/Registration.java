package com.runtigersrun.runtigers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button register = (Button) findViewById(R.id.regButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    EditText first = (EditText)findViewById(R.id.regEditFName);
    EditText last = (EditText)findViewById(R.id.regEditLName);
    EditText username = (EditText)findViewById(R.id.regEditUser);
    EditText password = (EditText)findViewById(R.id.regEditPass);
    EditText email = (EditText)findViewById(R.id.regEditEmail);

    public void clickRegister(View view){


        String fi = first.getText().toString();
        String la = last.getText().toString();
        String un = username.getText().toString();
        String pass = password.getText().toString();
        String em = email.getText().toString();

        if(fi.isEmpty() || la.isEmpty() || un.isEmpty() || pass.isEmpty() || em.isEmpty()){
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_LONG).show();
        }
        else{
            User u = new User(fi,la,un,pass,em);
            Toast.makeText(this, "User added!", Toast.LENGTH_LONG).show();
        }


    }
}
