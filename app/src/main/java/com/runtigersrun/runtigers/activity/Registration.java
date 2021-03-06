package com.runtigersrun.runtigers.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.runtigersrun.runtigers.R;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Registration extends AppCompatActivity {

    EditText first, last, username, password;
    String fi, la, un, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        first = (EditText)findViewById(R.id.regEditFName);
        last = (EditText)findViewById(R.id.regEditLName);
        username = (EditText)findViewById(R.id.regEditUser);
        password = (EditText)findViewById(R.id.regEditPass);
    }


    public void clickRegister(View view){
        fi = first.getText().toString();
        la = last.getText().toString();
        un = username.getText().toString();
        pass = password.getText().toString();


        if(fi.isEmpty() || la.isEmpty() || un.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_LONG).show();
        }
        else{

            BackgroundAddUser addUser = new BackgroundAddUser();
            addUser.execute(fi,la,un,pass);
            finish();

            Intent maIntent = new Intent(Registration.this, MainActivity.class);
            Registration.this.startActivity((maIntent));
        }
    }

    public class BackgroundAddUser extends AsyncTask<String,Void, String>{

        String add_user_url;
        @Override
        protected void onPreExecute() {
            add_user_url = "https://people.cs.clemson.edu/~dstieby/cpsc4820/RTR/externaldb/handleinput.php";
        }

        @Override
        protected String doInBackground(String... args) {
            String fn,ln,un,pass;
            fn=args[0];
            ln=args[1];
            un=args[2];
            pass=args[3];
            try{
                URL url = new URL(add_user_url);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("POST");
                huc.setDoOutput(true);
                OutputStream os = huc.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data_string = URLEncoder.encode("FirstName","UTF-8")+"="+URLEncoder.encode(fn,"UTF-8") + "&" +
                        URLEncoder.encode("LastName","UTF-8")+"="+URLEncoder.encode(ln,"UTF-8") + "&" +
                        URLEncoder.encode("Username","UTF-8")+"="+URLEncoder.encode(un,"UTF-8") + "&" +
                        URLEncoder.encode("Password","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");

                bw.write(data_string);
                bw.flush();
                bw.close();
                os.close();
                InputStream is = huc.getInputStream();
                is.close();
                huc.disconnect();
                return "User Added";
            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }


    }

}
