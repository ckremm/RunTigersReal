package com.runtigersrun.runtigers;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.runtigersrun.runtigers.activity.MainActivity;
import com.runtigersrun.runtigers.activity.Tracks;
import com.runtigersrun.runtigers.control.LeaderboardAdapter;
import com.runtigersrun.runtigers.control.TrackAdapter;
import com.runtigersrun.runtigers.model.LeaderboardProperties;
import com.runtigersrun.runtigers.model.TrackProperties;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Leaderboards extends AppCompatActivity {

    String JSON_STRING;
    String j_string;
    String jdata;
    String currentTrack;
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
        currentTrack = getIntent().getExtras().getString("trackID");


        try {
            jobj = new JSONObject(jdata);
            jarray = jobj.getJSONArray("times_response");
            int c = 0;
            String Time, userID, TrackID;
            while(c < jarray.length()) {
                JSONObject jo = jarray.getJSONObject(c);
                TrackID = jo.getString("trackID");
                Time = jo.getString("Time");
                userID = jo.getString("userID");
                LeaderboardProperties lp = new LeaderboardProperties(formatTime(Time), TrackID,  getUsername(userID));

                if (TrackID.equals(currentTrack)){
                    leaders.add(lp);
                    la.add(lp);
                }
                //
                c++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getUsername(String userID){
        for (User u : MainActivity.users){
            if (u.getUserID().equals(userID)){
                return u.getUname();
            }
        }
        return "";
    }

    private String formatTime(String time){
        int timeseconds = Integer.parseInt(time);
        int millis = timeseconds;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String result = df.format(millis);
        return result;
    }

}
