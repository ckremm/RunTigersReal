package com.runtigersrun.runtigers.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
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
    String id;
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
    String prevBeacon = "Empty";
    static long timeExport = 0;
    int count = 0;
    int count2 = 0;
    int count3 = 0;

    boolean isRunning = false;


    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                isRunning = true;
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                int mill = (int) (millis % 1000);
                timeExport = millis;

                timerTextView.setText(String.format("%d:%02d.%03d", minutes, seconds, mill));

                timerHandler.postDelayed(this, 500);
            }
            catch (Exception e) {
                isRunning = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        es = new ArrayList<>();
        //region = new Region("ranged region",
                //UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d"), null, null);

        Button b =(Button) findViewById(R.id.startButton);

        // keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        // Timer
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        b.setText("start");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monitor();
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
        id = getIntent().getExtras().getString("TrackID");
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

        region = new Region("ranged region",
                UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d"), null, null);


        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                @Override
                public void onServiceReady() {
                    beaconManager.startMonitoring(region);

                }
            });
            beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
                @Override
                public void onEnteredRegion(Region region, List<Beacon> list) {
                    beaconManager.startRanging(region);

                }
                @Override
                public void onExitedRegion(Region region) {
                    Toast.makeText(Route.this, "Exiting...", Toast.LENGTH_LONG).show();
                    tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS) {
                                int result = tts.setLanguage(Locale.US);
                                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_LONG).show();
                                }
                                tts.speak("Exiting", TextToSpeech.QUEUE_FLUSH, null);

                            }
                        }
                    });
                }
            });
            beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    String val = String.valueOf(list.get(0).getMajor());
                    Estimote st = null;
                    Estimote ch = null;
                    Estimote f = null;
                    String eval = String.valueOf(es.size());


                    for (Estimote E : es) {
                        if (E.getCallsign().equals(start)) {
                            e1 = E;
                        } else if (E.getCallsign().equals(chp)) {
                            e2 = E;
                        } else if (E.getCallsign().equals(fin)) {
                            e3 = E;
                        }
                    }

                    if (val.equals(e1.getMajor()) && count == 0) {

                        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                            @Override
                            public void onInit(int status) {
                                if (status == TextToSpeech.SUCCESS) {
                                    if(prevBeacon.equals(e1.getMajor())){

                                    }else {
                                        Toast.makeText(Route.this, "Found " + e1.getCallsign(), Toast.LENGTH_LONG).show();
                                        count++;
                                        int result = tts.setLanguage(Locale.US);
                                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                            Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_LONG).show();
                                        }
                                        tts.speak("Found " + e1.getCallsign(), TextToSpeech.QUEUE_FLUSH, null);
                                        prevBeacon = e1.getMajor();
                                    }
                                }
                            }
                        });

                        //tts.speak(sayText + "Blueberry",TextToSpeech.QUEUE_FLUSH, null);
                    }else if (val.equals(e2.getMajor()) && count2 == 0) {

                        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                            @Override
                            public void onInit(int status) {
                                if (status == TextToSpeech.SUCCESS) {
                                    if(prevBeacon.equals(e2.getMajor())){

                                    }else {
                                        Toast.makeText(Route.this, "Found " + e2.getCallsign(), Toast.LENGTH_LONG).show();
                                        count2++;
                                        int result = tts.setLanguage(Locale.US);
                                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                            Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_LONG).show();
                                        }
                                        tts.speak("Found " + e2.getCallsign(), TextToSpeech.QUEUE_FLUSH, null);
                                        prevBeacon = e2.getMajor();
                                    }
                                }
                            }
                        });

                    } else if(val.equals(e3.getMajor()) && count3 == 0){

                        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                            @Override
                            public void onInit(int status) {
                                if (status == TextToSpeech.SUCCESS) {
                                    if(prevBeacon.equals(e3.getMajor())){

                                    }else {
                                        Toast.makeText(Route.this, "Found " + e3.getCallsign(), Toast.LENGTH_LONG).show();
                                        count3++;
                                        int result = tts.setLanguage(Locale.US);
                                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                            Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_LONG).show();
                                        }
                                        tts.speak("Found " + e3.getCallsign(), TextToSpeech.QUEUE_FLUSH, null);

                                        timerHandler.removeCallbacks(timerRunnable);
                                        // uhhhhh something something put in database
                                        prevBeacon = e3.getMajor();
                                        pushTime();
                                    }
                                }
                            }
                        });

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

    public void pushTime(){
        long time = timeExport;
        String trackID = ""; // THIS NEEDS TO CHANGE. HOW DO I GET THE CURRENT TRACKID?

        BackgroundAddTime addUser = new BackgroundAddTime();
        addUser.execute(MainActivity.currentUser.getUserID(), trackID, Long.toString(time));
        finish();

    }

    public class BackgroundAddTime extends AsyncTask<String,Void, String> {

        String add_time_url;
        @Override
        protected void onPreExecute() {
            add_time_url = "https://people.cs.clemson.edu/~dstieby/cpsc4820/RTR/externaldb/addTime.php";
        }

        @Override
        protected String doInBackground(String... args) {
            String userID, trackID, Time;
            userID=args[0];
            trackID=args[1];
            Time=args[2];
            try{
                URL url = new URL(add_time_url);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("POST");
                huc.setDoOutput(true);
                OutputStream os = huc.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data_string = URLEncoder.encode("userID","UTF-8")+"="+URLEncoder.encode(userID,"UTF-8") + "&" +
                        URLEncoder.encode("trackID","UTF-8")+"="+URLEncoder.encode(trackID,"UTF-8") + "&" +
                        URLEncoder.encode("Time","UTF-8")+"="+URLEncoder.encode(Time,"UTF-8");

                bw.write(data_string);
                bw.flush();
                bw.close();
                os.close();
                InputStream is = huc.getInputStream();
                is.close();
                huc.disconnect();
                return "Time Added";
            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
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
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }


    }

}
