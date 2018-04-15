package com.example.kobej.attendancesystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
        listViewSetUp();
    }

    public void listViewSetUp(){
        final ListView classes;
        classes = findViewById(R.id.listClasses);
        String[] lectures = new String[] {"Class 1", "Class 2", "Class 3"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.listview, lectures);
        classes.setAdapter(adapter);
        classes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)classes.getItemAtPosition(position);
                if(item.equals("Class 1")){
                    resetListView();
                }
                if(item.equals("Class 2")){
                    resetListView();
                }
                if(item.equals("Class 3")){
                    resetListView();
                }
            }
        });
    }

    public void resetListView(){
        String[] test = new String[] {"Josh Grobin", "Homer Simpson", "Leela"};
        final ListView classes;
        classes = findViewById(R.id.listClasses);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.listview, test);
        classes.setAdapter(adapter);
    }

    public void setText(String username){
        TextView name;
        name = findViewById(R.id.textName);
        name.setText(username);
    }

    @Override
    public void onBackPressed(){
        /*
        Allows the user to traverse back and forth
         */
        listViewSetUp();
    }
}
