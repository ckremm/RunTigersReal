package com.runtigersrun.runtigers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Groups extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        Button addGroup = (Button) findViewById(R.id.buttonAddGroup);

        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addGroup = new Intent(Groups.this, GroupEditor.class);
                Groups.this.startActivity(addGroup);
            }
        });
    }
}
