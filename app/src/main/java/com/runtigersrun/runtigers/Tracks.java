package com.runtigersrun.runtigers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Tracks extends AppCompatActivity {

    String jdata;
    JSONObject jobj;
    JSONArray jarray;
    TrackAdapter ta;
    ListView lv;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        lv = (ListView) findViewById(R.id.trackListView);

        ta = new TrackAdapter(this, R.layout.trackrow);
        lv.setAdapter(ta);
        jdata = getIntent().getExtras().getString("Json_data");

        //Toast.makeText()

        try {
            jobj = new JSONObject(jdata);
            jarray = jobj.getJSONArray("track_response");
            int c = 0;
            String TrackID, TrackName, typeID, Start, Checkpoint, Finish;
            while(c < jarray.length()) {
                JSONObject jo = jarray.getJSONObject(c);
                TrackID = jo.getString("trackID");
                TrackName = jo.getString("TrackName");
                typeID = jo.getString("typeID");
                Start = jo.getString("StartEstimote");
                Checkpoint = jo.getString("CheckpointEstimote");
                Finish = jo.getString("FinishEstimote");
                TrackProperties tp = new TrackProperties(TrackName, Start, Checkpoint, Finish);

                ta.add(tp);

                c++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button trackEditor = (Button) findViewById(R.id.buttonEditTrack);
        Button editUser = (Button) findViewById(R.id.buttonEditUser);

        trackEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editorIntent = new Intent(Tracks.this, TrackEditor.class);
                Tracks.this.startActivity(editorIntent);
            }
        });

        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userIntent = new Intent(Tracks.this, UserInfo.class);
                Tracks.this.startActivity(userIntent);
            }
        });
    }
}
