package com.runtigersrun.runtigers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Button addFriend = (Button) findViewById(R.id.buttonAddFriend);
        Button editUser = (Button) findViewById(R.id.buttonEditUser);
        final Button manageGroup = (Button) findViewById(R.id.buttonEditGroups);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder add = new AlertDialog.Builder(UserInfo.this);
                add.setTitle("Add Friend");
                add.setMessage("Input friend username");

                final EditText friendUser = new EditText(getApplicationContext());
                friendUser.setTextColor(Color.BLACK);

                LinearLayout l = new LinearLayout(getApplication());
                l.setOrientation(LinearLayout.VERTICAL);
                l.setGravity(Gravity.CENTER_VERTICAL);
                l.addView(friendUser);

                add.setView(l);

                add.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                add.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                add.create().show();
            }
        });

        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editUserIntent = new Intent(UserInfo.this, EditUser.class);
                UserInfo.this.startActivity(editUserIntent);
            }
        });

        manageGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manageGroupIntent = new Intent(UserInfo.this, Groups.class);
                UserInfo.this.startActivity(manageGroupIntent);
            }
        });
    }
}
