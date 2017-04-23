package com.runtigersrun.runtigers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjkremm on 4/22/17.
 */

public class TrackAdapter extends ArrayAdapter{
    List list = new ArrayList();
    public TrackAdapter(Context context, int resources){
        super(context, resources);
    }


    public void add(TrackProperties tp){
        super.add(tp);
        list.add(tp);
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        TrackHolder th;
        if(row == null){
            LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = li.inflate(R.layout.trackrow, parent, false);
            th = new TrackHolder();
            th.tname = (TextView) row.findViewById(R.id.tname);
            th.startp = (TextView) row.findViewById(R.id.startp);
            row.setTag(th);
        }else{
            th = (TrackHolder) row.getTag();
        }

        TrackProperties tracks = (TrackProperties) this.getItem(position);
        th.tname.setText(tracks.getName());
        th.startp.setText(tracks.getStart());
        return row;
    }

    static class TrackHolder{
        TextView tname, startp;

    }
}
