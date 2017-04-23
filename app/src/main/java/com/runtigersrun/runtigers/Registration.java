package com.runtigersrun.runtigers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.runtigersrun.runtigers.activity.MainActivity;
import com.runtigersrun.runtigers.app.User;

public class Registration extends AppCompatActivity {

    EditText first;
    EditText last;
    EditText username;
    EditText password;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }


    public void clickRegister(View view){

        EditText first = (EditText)findViewById(R.id.regEditFName);
        EditText last = (EditText)findViewById(R.id.regEditLName);
        EditText username = (EditText)findViewById(R.id.regEditUser);
        EditText password = (EditText)findViewById(R.id.regEditPass);
        EditText email = (EditText)findViewById(R.id.regEditEmail);

        String fi = first.getText().toString();
        String la = last.getText().toString();
        String un = username.getText().toString();
        String pass = password.getText().toString();
        String em = email.getText().toString();

        if(fi.isEmpty() || la.isEmpty() || un.isEmpty() || pass.isEmpty() || em.isEmpty()){
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_LONG).show();
        }
        else{
            User u = new User(fi,la,un,pass);
            Toast.makeText(this, "User added!", Toast.LENGTH_LONG).show();

            Intent maIntent = new Intent(Registration.this, MainActivity.class);
            Registration.this.startActivity((maIntent));

        }
//

    }
}
