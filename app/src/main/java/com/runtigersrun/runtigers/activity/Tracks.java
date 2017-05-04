package com.runtigersrun.runtigers.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.runtigersrun.runtigers.R;
import com.runtigersrun.runtigers.control.TrackAdapter;
import com.runtigersrun.runtigers.TrackEditor;
import com.runtigersrun.runtigers.model.TrackProperties;

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

public class Tracks extends AppCompatActivity {

    String JSON_STRING;
    String j_string;
    String j_stringLB;
    String jdata;
    JSONObject jobj;
    JSONArray jarray;
    TrackAdapter ta;
    ArrayList<TrackProperties> tracks;
    ListView lv;
    TrackProperties t;

    //blank
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        lv = (ListView) findViewById(R.id.trackListView);

        new backgroundtaskLB().execute();
        new backgroundtask().execute();
        tracks = new ArrayList<TrackProperties>();
        ta = new TrackAdapter(this, R.layout.trackrow);
        lv.setAdapter(ta);

        jdata = getIntent().getExtras().getString("Json_data");

        //Toast.makeText()


        try {
            jobj = new JSONObject(jdata);
            jarray = jobj.getJSONArray("track_response");
            int c = 0;
            String TrackName, typeID, Start, Checkpoint, Finish, TrackID;
            while(c < jarray.length()) {
                JSONObject jo = jarray.getJSONObject(c);
                TrackID = jo.getString("trackID");
                TrackName = jo.getString("TrackName");
                typeID = jo.getString("typeID");
                Start = jo.getString("StartEstimote");
                Checkpoint = jo.getString("CheckpointEstimote");
                Finish = jo.getString("FinishEstimote");

                TrackProperties tp = new TrackProperties(TrackName, Start, Checkpoint, Finish, TrackID);

                tracks.add(tp);
                ta.add(tp);

                c++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final TrackProperties t = tracks.get(position);

                final String[] option = new String[] { "View Route", "Leader Board"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Tracks.this,
                        android.R.layout.select_dialog_item, option);
                AlertDialog.Builder builder = new AlertDialog.Builder(Tracks.this);

                builder.setTitle(t.getName());
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(Tracks.this, "%i"+ which, Toast.LENGTH_LONG).show();
                        if(which == 0){
                            Intent trackIntent = new Intent(Tracks.this, Route.class);
                            trackIntent.putExtra("TrackID", t.getTrackID());
                            trackIntent.putExtra("Start", t.getStart());
                            trackIntent.putExtra("Checkpoint", t.getCheckpoint());
                            trackIntent.putExtra("Finish", t.getFinish());
                            trackIntent.putExtra("Json_data", j_string);
                            trackIntent.putExtra("TrackName", t.getName());
                            Tracks.this.startActivity((trackIntent));
                        }
                        else if (which == 1){
                            Intent leaderboarintent = new Intent(Tracks.this, Leaderboards.class);
                            leaderboarintent.putExtra("trackID",t.getTrackID());
                            leaderboarintent.putExtra("Json_data", j_stringLB);
                            Tracks.this.startActivity((leaderboarintent));
                        }
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        Button trackEditor = (Button) findViewById(R.id.buttonEditTrack);
        Button editUser = (Button) findViewById(R.id.buttonEditUser);

        trackEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editorIntent = new Intent(Tracks.this, TrackEditor.class);
                editorIntent.putExtra("Json_data", j_string);
                Tracks.this.startActivity(editorIntent);
            }
        });

    }


    public void toEditUser(View view){
        Intent editUserintent = new Intent(Tracks.this, EditUser.class);
        this.startActivity((editUserintent));
    }

    class backgroundtask extends AsyncTask<Void, Void, String> {
        String json_url;
        String json_urlUser;

        @Override
        protected void onPreExecute() {
            json_url = " https://people.cs.clemson.edu/~dstieby/cpsc4820/RTR/externaldb/displayEstimotes.php";
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
    class backgroundtaskLB extends AsyncTask<Void, Void, String> {
        String json_url;
        String json_urlUser;

        @Override
        protected void onPreExecute() {
            json_url = " https://people.cs.clemson.edu/~dstieby/cpsc4820/RTR/externaldb/displayTimes.php";
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
            j_stringLB = result;
        }
    }
}
