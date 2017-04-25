package com.runtigersrun.runtigers;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.runtigersrun.runtigers.activity.Tracks;
import com.runtigersrun.runtigers.control.LeaderboardAdapter;
import com.runtigersrun.runtigers.control.TrackAdapter;
import com.runtigersrun.runtigers.model.LeaderboardProperties;
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

public class Leaderboards extends AppCompatActivity {

    String JSON_STRING;
    String j_string;
    String jdata;
    JSONObject jobj;
    JSONArray jarray;
    LeaderboardAdapter la;
    ArrayList<LeaderboardProperties> leaders;
    ListView lv;
    LeaderboardProperties p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        lv = (ListView) findViewById(R.id.leaderListView);

        leaders = new ArrayList<LeaderboardProperties>();
        la = new LeaderboardAdapter(this, R.layout.leaderboardrow);
        lv.setAdapter(la);

        jdata = getIntent().getExtras().getString("Json_data");

        try {
            jobj = new JSONObject(jdata);
            jarray = jobj.getJSONArray("times_response");
            int c = 0;
            String TrackID, Time, userID;
            while(c < jarray.length()) {
                JSONObject jo = jarray.getJSONObject(c);
                TrackID = jo.getString("trackID");
                Time = jo.getString("Time");
                userID = jo.getString("userID");
                LeaderboardProperties lp = new LeaderboardProperties(TrackID, Time, userID);

                leaders.add(lp);
                la.add(lp);

                c++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
