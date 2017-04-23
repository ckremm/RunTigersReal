package com.runtigersrun.runtigers.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.runtigersrun.runtigers.R;
import com.runtigersrun.runtigers.control.RouteAdapter;
import com.runtigersrun.runtigers.control.TrackAdapter;
import com.runtigersrun.runtigers.model.Estimote;
import com.runtigersrun.runtigers.model.TrackProperties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.ArrayList;

public class Route extends AppCompatActivity {

    String jdata;
    JSONObject jobj;
    JSONArray jarray;
    RouteAdapter ra;
    ListView lv;
    ArrayList<Estimote> es;
    Tracks myT;
    String start;
    String chp;
    String fin;
    TextView timerTextView;
    long startTime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int mill = (int) (millis % 1000) ;

            timerTextView.setText(String.format("%d:%02d.%03d", minutes, seconds, mill));

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);


        // Timer
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        Button b =(Button) findViewById(R.id.startButton);
        b.setText("start");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("stop")) {
                    timerHandler.removeCallbacks(timerRunnable);
                    b.setText("start");
                } else {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setText("stop");
                }
            }
        });



        myT = new Tracks();
        lv = (ListView) findViewById(R.id.routeListView);

        ra = new RouteAdapter(this, R.layout.routerow);
        lv.setAdapter(ra);
        jdata = getIntent().getExtras().getString("Json_data");
        start = getIntent().getExtras().getString("Start");
        chp = getIntent().getExtras().getString("Checkpoint");
        fin = getIntent().getExtras().getString("Finish");


        try {
            jobj = new JSONObject(jdata);
            jarray = jobj.getJSONArray("estimote_response");
            int c = 0;
            String UUID, Major, Minor, Callsign;
            while(c < jarray.length()) {
                JSONObject jo = jarray.getJSONObject(c);
                UUID = jo.getString("UUID");
                Major = jo.getString("Major");
                Minor = jo.getString("Minor");
                Callsign = jo.getString("Callsign");;
                Estimote e = new Estimote(UUID, Major, Minor, Callsign);

                es.add(e);

                c++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ra.add(start);
        ra.add(chp);
        ra.add(fin);
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button b = (Button) findViewById(R.id.startButton);
        b.setText("start");
    }
}
