package com.runtigersrun.runtigers.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.runtigersrun.runtigers.R;
import com.runtigersrun.runtigers.model.Estimote;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjkremm on 4/23/17.
 * work
 */

public class RouteAdapter extends ArrayAdapter{

    List list = new ArrayList();
    public RouteAdapter(Context context, int resources){
        super(context, resources);
    }


    public void add(Estimote e){
        super.add(e);
        list.add(e);
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
        RouteAdapter.RouteHolder rh;
        if(row == null){
            LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = li.inflate(R.layout.routerow, parent, false);
            rh = new RouteAdapter.RouteHolder();
            rh.call = (TextView) row.findViewById(R.id.call);
            rh.num = (TextView) row.findViewById(R.id.num);
            row.setTag(rh);
        }else{
            rh = (RouteAdapter.RouteHolder) row.getTag();
        }

        Estimote est = (Estimote) this.getItem(position);
        rh.call.setText(est.getCallsign());

        return row;
    }

    static class RouteHolder{
        TextView num, call;

    }
}
