package com.runtigersrun.runtigers.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.runtigersrun.runtigers.R;
import com.runtigersrun.runtigers.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText loginU;
    private EditText loginP;
    String JSON_STRING;
    String JSON_STRINGUSER;
    String j_string;
    String j_stringuser;
    String jdata;
    JSONObject jobj;
    JSONArray jarray;
    ArrayList<User> users;
    String un;
    String pa;
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new backgroundtask().execute();
        new backgroundtask2().execute();
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni==null || !ni.isConnected()){
            Toast.makeText(this, "Network Unavailable!", Toast.LENGTH_LONG);
        }

        ImageButton info = (ImageButton) findViewById(R.id.infoButton);

        // Info panel
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

    public void toRegister(View view) {
        Intent registerIntent = new Intent(MainActivity.this, Registration.class);
        MainActivity.this.startActivity((registerIntent));
    }

    public void toTracks(View view) {

        users = new ArrayList<User>();
        final EditText loginU = (EditText) findViewById(R.id.loginUser);
        final EditText loginP = (EditText) findViewById(R.id.loginPass);
        Button loginB = (Button) findViewById(R.id.loginButton);
        TextView register = (TextView) findViewById(R.id.registerHereText);
        ImageButton info = (ImageButton) findViewById(R.id.infoButton);

        jdata = j_stringuser;

        //Toast.makeText()

        try {
            jobj = new JSONObject(jdata);
            jarray = jobj.getJSONArray("user_response");
            int c = 0;
            String ID, FName, LName, Username, Pass;
            while(c < jarray.length()) {
                JSONObject jo = jarray.getJSONObject(c);
                ID = jo.getString("userID");
                FName = jo.getString("FirstName");
                LName = jo.getString("LastName");
                Username = jo.getString("Username");
                Pass = jo.getString("Password");
                User u = new User(FName, LName, Username, Pass);

                users.add(u);
                c++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        un = loginU.getText().toString();
        pa = loginP.getText().toString();

        for(User use : users){
            if(use.getUname().equals(un)){
                if(use.getPass().equals(pa)){
                    currentUser = use;
                    Intent trackIntent = new Intent(MainActivity.this, Tracks.class);
                    trackIntent.putExtra("Json_data", j_string);
                    MainActivity.this.startActivity((trackIntent));
                }
            }
        }
        Toast.makeText(this, "Wrong Username or Password.", Toast.LENGTH_LONG);

    }

    class backgroundtask extends AsyncTask<Void, Void, String> {
        String json_url;
        String json_urlUser;

        @Override
        protected void onPreExecute() {
            json_url = " https://people.cs.clemson.edu/~dstieby/cpsc4820/RTR/externaldb/displaytracktext.php";
            //json_urlUser = " https://people.cs.clemson.edu/~dstieby/cpsc4820/RTR/externaldb/displaytext.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                InputStream is = huc.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                while ((JSON_STRING = bf.readLine()) != null) {
                    sb.append(JSON_STRING + "\n");
                }

                bf.close();
                is.close();
                huc.disconnect();

                return sb.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
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
            j_string = result;
        }
    }
    class backgroundtask2 extends AsyncTask<Void, Void, String> {
        String json_url;
        String json_urlUser;

        @Override
        protected void onPreExecute() {
            //json_url = " https://people.cs.clemson.edu/~dstieby/cpsc4820/RTR/externaldb/displaytracktext.php";
            json_urlUser = " https://people.cs.clemson.edu/~dstieby/cpsc4820/RTR/externaldb/displaytext.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_urlUser);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                InputStream is = huc.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                while ((JSON_STRINGUSER = bf.readLine()) != null) {
                    sb.append(JSON_STRINGUSER + "\n");
                }

                bf.close();
                is.close();
                huc.disconnect();

                return sb.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
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
            j_stringuser = result;
        }

    }




}
