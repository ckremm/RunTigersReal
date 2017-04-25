package com.runtigersrun.runtigers.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
    Estimote e1;
    Estimote e2;
    Estimote e3;
    TextView timerTextView;
    long startTime = 0;
    private BeaconManager beaconManager;
    int marker = 0;
    Region region;
    TextToSpeech tts;
    Beacon nearestBeacon;
    int prevBeacon = -1;



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
        es = new ArrayList<>();

        region = new Region("ranged region",
                UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d"), null, null);


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
                    monitor();
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
                Callsign = jo.getString("CallSign");
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

    public void monitor(){

        //Toast.makeText(this, "Inside monitor", Toast.LENGTH_LONG).show();
        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(region);

            }
        });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {

            int count = 0;

            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                String val = String.valueOf(list.get(0).getMajor());
                Estimote st = null;
                Estimote ch = null;
                Estimote f = null;
                String eval = String.valueOf(es.size());

                for(Estimote E: es){
                    if(E.getCallsign().equals(start)){
                        st = E;
                    }else if(E.getCallsign().equals(chp)){
                        ch = E;
                    }else if(E.getCallsign().equals(fin)){
                        f = E;
                    }
                }

                //Toast.makeText(Route.this, val, Toast.LENGTH_LONG).show();
                //Toast.makeText(Route.this, "Break", Toast.LENGTH_LONG).show();
                //Toast.makeText(Route.this, start, Toast.LENGTH_LONG).show();
                //beaconManager.startRanging(region);

                if(val.equals(st.getMajor()) && count == 0){
                    Toast.makeText(Route.this, "Found Blueberry", Toast.LENGTH_LONG).show();
                    tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        //@Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                tts.setLanguage(Locale.US);
                            }
                        }
                    });
                    tts.speak("Found Blueberry",TextToSpeech.QUEUE_FLUSH, null, "Test");
                }
                if(val.equals(ch.getMajor()) && count == 1){
                    Toast.makeText(Route.this, "Found Mint", Toast.LENGTH_LONG).show();
                    tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        //@Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                tts.setLanguage(Locale.US);
                            }

                        }
                    });
                    tts.speak("Found Mint",TextToSpeech.QUEUE_FLUSH, null, "Test2");
                }
                if(val.equals(f.getMajor()) && count == 2){
                    Toast.makeText(Route.this, "Found Ice" +
                            "", Toast.LENGTH_LONG).show();
                    tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        //@Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                tts.setLanguage(Locale.US);
                            }

                        }
                    });
                    tts.speak("Found Ice",TextToSpeech.QUEUE_FLUSH, null, "Test3");
                }

            }

            @Override
            public void onExitedRegion(Region region) {
                //Toast.makeText(Route.this, "here again", Toast.LENGTH_LONG).show();
                count++;
            }
        });

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                Toast.makeText(Route.this, String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
                if (!list.isEmpty()) {
                    nearestBeacon = list.get(0);
                    Toast.makeText(Route.this, String.valueOf(nearestBeacon.getMajor()), Toast.LENGTH_LONG).show();
                    if (nearestBeacon.getMajor() == Integer.parseInt(es.get(0).getMajor())) {
                        if (marker == 0) {
                            Toast.makeText(Route.this, "What the Fuck", Toast.LENGTH_SHORT).show();
                            tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                //@Override
                                public void onInit(int status) {
                                    if (status != TextToSpeech.ERROR) {
                                        tts.setLanguage(Locale.US);

                                    }

                                }
                            });

                            //tts.speak("Test Test",TextToSpeech.QUEUE_FLUSH, null);
                            marker++;
                        }
                    }
                }
            }
        });
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
