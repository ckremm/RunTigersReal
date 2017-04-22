package com.runtigersrun.runtigers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tracks extends AppCompatActivity {

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        Button trackEditor = (Button) findViewById(R.id.buttonEditTrack);
        Button editUser = (Button) findViewById(R.id.buttonEditUser);

        trackEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editorIntent = new Intent(Tracks.this, TrackEditor.class);
                Tracks.this.startActivity(editorIntent);
            }
        });

        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userIntent = new Intent(Tracks.this, UserInfo.class);
                Tracks.this.startActivity(userIntent);
            }
        });
    }
}
