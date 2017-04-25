package com.runtigersrun.runtigers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.runtigersrun.runtigers.control.RouteAdapter;
import com.runtigersrun.runtigers.model.Estimote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrackEditor extends AppCompatActivity {

    ArrayList<String> spinnerArray;
    String jdata;
    JSONObject jobj;
    JSONArray jarray;
    RouteAdapter ra;
    ListView lv;
    ArrayList<Estimote> es;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_editor);

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

        Spinner spinner = new Spinner(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        Button finished = (Button) findViewById(R.id.buttonTrackFinish);

        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
