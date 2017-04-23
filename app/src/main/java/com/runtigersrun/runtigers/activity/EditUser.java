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

public class EditUser extends AppCompatActivity {

    EditText first, last, username, password;
    String oldun, fi, la, un, pass;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        first = (EditText)findViewById(R.id.regEditFName);
        last = (EditText)findViewById(R.id.regEditLName);
        username = (EditText)findViewById(R.id.regEditUser);
        password = (EditText)findViewById(R.id.regEditPass);

        first.setText(MainActivity.currentUser.getFname());
        last.setText(MainActivity.currentUser.getLname());
        username.setText(MainActivity.currentUser.getUname());
        password.setText(MainActivity.currentUser.getPass());

    }

    public void clickEdit(View view){
        fi = first.getText().toString();
        la = last.getText().toString();
        un = username.getText().toString();
        pass = password.getText().toString();

        BackgroundEditUser editUser = new BackgroundEditUser();
        editUser.execute(MainActivity.currentUser.getUname(), fi,la,un,pass);
        finish();

        Intent maIntent = new Intent(this, MainActivity.class);
        this.startActivity((maIntent));
    }

    public class BackgroundEditUser extends AsyncTask<String,Void, String> {

        String add_user_url;
        @Override
        protected void onPreExecute() {
            add_user_url = "https://people.cs.clemson.edu/~dstieby/cpsc4820/RTR/externaldb/edituser.php";
        }

        @Override
        protected String doInBackground(String... args) {
            String oldun, fn,ln,un,pass;
            oldun=args[0];
            fn=args[1];
            ln=args[2];
            un=args[3];
            pass = args[4];
            try{
                URL url = new URL(add_user_url);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("POST");
                huc.setDoOutput(true);
                OutputStream os = huc.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data_string =
                        URLEncoder.encode("OldUsername","UTF-8")+"="+URLEncoder.encode(oldun,"UTF-8") + "&" +
                        URLEncoder.encode("FirstName","UTF-8")+"="+URLEncoder.encode(fn,"UTF-8") + "&" +
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
                return "User Updated!";
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
