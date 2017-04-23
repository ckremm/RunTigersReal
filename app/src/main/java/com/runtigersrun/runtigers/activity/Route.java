package com.runtigersrun.runtigers.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.runtigersrun.runtigers.R;
import com.runtigersrun.runtigers.control.RouteAdapter;
import com.runtigersrun.runtigers.control.TrackAdapter;
import com.runtigersrun.runtigers.model.Estimote;
import com.runtigersrun.runtigers.model.TrackProperties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Route extends AppCompatActivity {

    String jdata;
    JSONObject jobj;
    JSONArray jarray;
    RouteAdapter ra;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        lv = (ListView) findViewById(R.id.routeListView);

        ra = new RouteAdapter(this, R.layout.routerow);
        lv.setAdapter(ra);
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
                Callsign = jo.getString("Callsign");;
                Estimote e = new Estimote(UUID, Major, Minor, Callsign);

                ra.add(e);

                c++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
