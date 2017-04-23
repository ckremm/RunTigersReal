package com.runtigersrun.runtigers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText loginU;
    private EditText loginP;
    String JSON_STRING;
    String j_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new backgroundtask().execute();
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



        Intent trackIntent = new Intent(MainActivity.this, Tracks.class);
        trackIntent.putExtra("Json_data", j_string);
        MainActivity.this.startActivity((trackIntent));
    }

    class backgroundtask extends AsyncTask<Void, Void, String>{
        String json_url;
        @Override
        protected void onPreExecute(){
            json_url = " https://people.cs.clemson.edu/~dstieby/cpsc4820/RTR/externaldb/displaytracktext.php";
        }

        @Override
        protected String doInBackground(Void... voids){
            try {
                URL url = new URL(json_url);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                InputStream is = huc.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                while((JSON_STRING = bf.readLine()) != null ){
                    sb.append(JSON_STRING+"\n");
                }

                bf.close();
                is.close();
                huc.disconnect();

                return sb.toString().trim();

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result){
            TextView tv  = (TextView)findViewById(R.id.tv);
            tv.setText(result);
            j_string = result;
        }

    }





}
