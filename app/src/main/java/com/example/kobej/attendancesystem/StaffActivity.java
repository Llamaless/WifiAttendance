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
    private DBHandler dbHandler;

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

         /*
        Setting up Database hanler
         */
        dbHandler = new DBHandler(this);


        setText(username);
        listViewSetUp(username);
    }

    public void listViewSetUp(String username){
        final ListView classes;
        classes = findViewById(R.id.listClasses);
        //String[] lectures = new String[] {"Class 1", "Class 2", "Class 3"};
        ArrayList id, classy;
        id = dbHandler.getLClasses1(username);
        classy = dbHandler.getLClasses2(String.valueOf(id.get(0)));
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.listview, classy);
        classes.setAdapter(adapter);
        classes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)classes.getItemAtPosition(position);
                resetListView(item);

            }
        });
    }

    public void resetListView(String code){
        //String[] test = new String[] {"Josh Grobin", "Homer Simpson", "Leela"};
        ArrayList students;
        students = dbHandler.getAllStudents(code);
        final ListView classes;
        classes = findViewById(R.id.listClasses);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.listview, students);
        classes.setAdapter(adapter);
    }

    public void setText(String username){
        TextView name;
        name = findViewById(R.id.textName);
        name.setText(username);
    }

    @Override
    public void onBackPressed(){
        Intent intent = getIntent();
        ArrayList<String> carried;
        carried = intent.getStringArrayListExtra("carryList");
        String username = String.valueOf(carried.get(0));
        /*
        Allows the user to traverse back and forth
         */
        listViewSetUp(username);
    }
}
