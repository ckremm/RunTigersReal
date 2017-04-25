package com.runtigersrun.runtigers.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.runtigersrun.runtigers.R;
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
            String Time, userID, TrackID, username;
            while(c < jarray.length()) {
                JSONObject jo = jarray.getJSONObject(c);
                TrackID = jo.getString("trackID");
                Time = jo.getString("Time");
                userID = jo.getString("userID");
                username = getUsername(userID);
                LeaderboardProperties lp;
                if (MainActivity.currentUser.getFriend1().equals(username) ||
                        MainActivity.currentUser.getFriend2().equals(username)||
                        MainActivity.currentUser.getFriend3().equals(username)){
                    lp = new LeaderboardProperties(formatTime(Time), TrackID,  username+"***");
                }
                else
                {
                    lp = new LeaderboardProperties(formatTime(Time), TrackID,  username);
                }





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
            if (u.getUserID().trim().equals(userID.trim())){
                return u.getUname();
            }
        }
        return "";
    }

    private String formatTime(String time){
        int timeseconds = Integer.parseInt(time);
        int hours = timeseconds / 3600;
        int minutes = (timeseconds % 3600) / 60;
        int seconds = timeseconds % 60;

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return timeString;
    }

}
