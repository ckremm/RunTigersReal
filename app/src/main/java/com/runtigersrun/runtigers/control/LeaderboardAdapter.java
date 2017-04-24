package com.runtigersrun.runtigers.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.runtigersrun.runtigers.R;
import com.runtigersrun.runtigers.model.LeaderboardProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dstieby on 4/24/2017.
 */

public class LeaderboardAdapter extends ArrayAdapter{
    List list = new ArrayList();
    public LeaderboardAdapter(Context context, int resources){
        super(context, resources);
    }


    public void add(LeaderboardProperties lp){
        super.add(lp);
        list.add(lp);
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
        LeaderboardHolder lh;
        if(row == null){
            LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = li.inflate(R.layout.leaderboardrow, parent, false);
            lh = new LeaderboardHolder();
            lh.time = (TextView) row.findViewById(R.id.Time);
            lh.userID = (TextView) row.findViewById(R.id.user);
            row.setTag(lh);
        }else{
            lh = (LeaderboardHolder) row.getTag();
        }

        LeaderboardProperties leaderboards = (LeaderboardProperties) this.getItem(position);
        lh.userID.setText(leaderboards.getUserID());
        lh.time.setText(leaderboards.getTime());
        return row;
    }

    static class LeaderboardHolder{
        TextView userID, time;

    }
}
