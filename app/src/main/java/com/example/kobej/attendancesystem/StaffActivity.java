package com.example.kobej.attendancesystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;


public class StaffActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        /*
        Getting the information passed from the previous intent
         */
        Intent intent = getIntent();
        ArrayList<String> carried;
        carried = intent.getStringArrayListExtra("carryList");
        String username = String.valueOf(carried.get(0));

        setText(username);
    }

    public void setText(String username){
        TextView name;
        name = findViewById(R.id.textName);
        name.setText(username);
    }
}
