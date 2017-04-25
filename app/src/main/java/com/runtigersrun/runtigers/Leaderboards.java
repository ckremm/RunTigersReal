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

        new backgroundtask().execute();
        leaders = new ArrayList<LeaderboardProperties>();
        la = new LeaderboardAdapter(this, R.layout.leaderboardrow);
        lv.setAdapter(la);

        //jdata = getIntent().getExtras().getString("Json_data");

        try {
            jobj = new JSONObject(jdata);
            jarray = jobj.getJSONArray("track_response");
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

    class backgroundtask extends AsyncTask<Void, Void, String> {
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
            j_string = result;
        }
    }
}
