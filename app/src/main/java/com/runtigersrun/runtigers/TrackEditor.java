package com.runtigersrun.runtigers;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.runtigersrun.runtigers.activity.MainActivity;
import com.runtigersrun.runtigers.activity.Route;
import com.runtigersrun.runtigers.control.RouteAdapter;
import com.runtigersrun.runtigers.model.Estimote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class TrackEditor extends AppCompatActivity {

    ArrayList<String> spinnerArray;
    String jdata;
    JSONObject jobj;
    JSONArray jarray;
    RouteAdapter ra;
    ListView lv;
    ArrayList<Estimote> es;
    Spinner first;
    Spinner second;
    Spinner last;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_editor);

        es = new ArrayList<>();
        spinnerArray = new ArrayList<>();
        jdata = getIntent().getExtras().getString("Json_data");


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

                spinnerArray.add(Callsign);
                es.add(e);

                c++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        first = (Spinner) findViewById(R.id.fisrtWaypoint);
        second = (Spinner) findViewById(R.id.secondWaypoint);
        last = (Spinner) findViewById(R.id.lastWaypoint);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        first.setAdapter(spinnerArrayAdapter);
        second.setAdapter(spinnerArrayAdapter);
        last.setAdapter(spinnerArrayAdapter);


        Button finished = (Button) findViewById(R.id.buttonTrackFinish);

        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushTrack();
                onBackPressed();
            }
        });
    }

    public void pushTrack(){
        String text = first.getSelectedItem().toString();
        String text2 = second.getSelectedItem().toString();
        String text3 = last.getSelectedItem().toString();

        EditText TrackName = (EditText)findViewById(R.id.trackNameInput);
        String tn = TrackName.getText().toString();


        BackgroundAddTrack addTrack = new BackgroundAddTrack();
        addTrack.execute(tn,text, text2, text3);
        finish();
    }

    public class BackgroundAddTrack extends AsyncTask<String,Void, String> {

        String add_time_url;
        @Override
        protected void onPreExecute() {
            add_time_url = "https://people.cs.clemson.edu/~dstieby/cpsc4820/RTR/externaldb/inputTrack.php";
        }

        @Override
        protected String doInBackground(String... args) {
            String TrackName, StartEstimote, CheckpointEstimote, FinishEstimote;
            TrackName=args[0];
            StartEstimote=args[1];
            CheckpointEstimote=args[2];
            FinishEstimote=args[3];
            try{
                URL url = new URL(add_time_url);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("POST");
                huc.setDoOutput(true);
                OutputStream os = huc.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data_string = URLEncoder.encode("TrackName","UTF-8")+"="+URLEncoder.encode(TrackName,"UTF-8") + "&" +
                        URLEncoder.encode("StartEstimote","UTF-8")+"="+URLEncoder.encode(StartEstimote,"UTF-8") + "&" +
                        URLEncoder.encode("CheckpointEstimote","UTF-8")+"="+URLEncoder.encode(CheckpointEstimote,"UTF-8") + "&" +
                        URLEncoder.encode("FinishEstimote","UTF-8")+"="+URLEncoder.encode(FinishEstimote,"UTF-8");

                bw.write(data_string);
                bw.flush();
                bw.close();
                os.close();
                InputStream is = huc.getInputStream();
                is.close();
                huc.disconnect();
                return "Track Added";
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
