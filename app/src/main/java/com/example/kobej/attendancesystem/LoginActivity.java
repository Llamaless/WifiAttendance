package com.example.kobej.attendancesystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Kobe Davis - 6905105
 * 07/04/18
 */

public class LoginActivity extends Activity {

    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
        Setting up Database hanler
         */
        dbHandler = new DBHandler(this);

        /*
        creating a wifi manager
        Essential to accessing all of the wifi
        */
        WifiManager wifiManager = (WifiManager)getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);

        forceWifi(wifiManager);
        //testData();
        //dummyData();
        onButtonPress();


    }
    public void testData(){
        dbHandler.insertStudent(005, "Kobe Davis");
        dbHandler.insertStaff(006, "Laman Temple");
        dbHandler.insertDetails(005, "test1", "test1");
        dbHandler.insertDetailsS(006, "staff1", "staff1");
        dbHandler.insertClass2("101SM", "Test Class", "8:00 AM", "6:00 PM", "Wednesday");
        dbHandler.insertSC(005, "101SM");
        dbHandler.insertLC(006, "101SM");
        dbHandler.insertClassI("102SM", "Wednesday", "02:00:00:00:00:00");
    }

    public void dummyData(){
        dbHandler.insertStudent(6, "Jamel King");
        dbHandler.insertStudent(7, "Fred Dope");
        dbHandler.insertStudent(8, "Kyron Killew");
        dbHandler.insertStudent(9, "Lina Kyu");
        dbHandler.insertStudent(10, "Michelle Rhodes");
        dbHandler.insertStudent(11, "Kira Phantom");
        dbHandler.insertDetails(6, "jamel", "jamel");
        dbHandler.insertDetails(7, "fred", "fred");
        dbHandler.insertDetails(8, "kyron", "kyron");
        dbHandler.insertDetails(9, "lina", "lina");
        dbHandler.insertDetails(10, "michelle", "michelle");
        dbHandler.insertDetails(11, "kira", "kira");
        dbHandler.insertSC(6, "101SM");
        dbHandler.insertSC(7, "101SM");
        dbHandler.insertSC(8, "101SM");
        dbHandler.insertSC(9, "101SM");
        dbHandler.insertSC(10, "101SM");
        dbHandler.insertSC(11, "101SM");
        dbHandler.insertSC(6, "107SM");
        dbHandler.insertSC(7, "107SM");
        dbHandler.insertSC(8, "107SM");
        dbHandler.insertClass2("107SM", "History", "8:00 AM", "6:00 PM", "Wednesday");
        dbHandler.insertStaff(7, "Tishe Kingro");
        dbHandler.insertDetailsS(7, "staff2", "staff2");
        dbHandler.insertLC(7, "101SM");
        dbHandler.insertLC(6, "107SM");
    }

    public void forceWifi(WifiManager wifiManager){
        //Forces the wifi to be on allowing the app to work
        wifiManager.setWifiEnabled(true);
    }

    public boolean switchState(){
        Switch position;
        position = findViewById(R.id.positionSwitch);
        boolean checked;
        if(position.isChecked()){
            checked = true;
        }else{
            checked = false;
        }
        return checked;
    }

    public void validStudent(){
        /*
        checks that username and password are valid for a student
        */

        //getting username and password from there respective fields
        EditText username, password;
        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        String user = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());
        ArrayList actualPass = dbHandler.getPasswordStudent(user);
        String actualPassString = String.valueOf(actualPass.get(0));
        //comparing gathered values to known values
        if(pass.equals(actualPassString)){
            Intent carry = new Intent(LoginActivity.this, ConnectActivity.class);
            ArrayList<String> carried;
            carried = carryOver();
            carry.putStringArrayListExtra("carryList", carried);
            startActivity(carry);
            finish();
        }else{
            //error message
            Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_SHORT).show();
        }
    }

    public void validStaff(){
        /*
        checks that username and password are valid for a staff
        */

        //getting username and password from there respective fields
        EditText username, password;
        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        String user = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());
        ArrayList actualPass = dbHandler.getPasswordStaff(user);
        String actualPassString = String.valueOf(actualPass.get(0));
        //comparing gathered values to known values
        if(pass.equals(actualPassString)){
            Intent carry = new Intent(LoginActivity.this, StaffActivity.class);
            ArrayList<String> carried;
            carried = carryOver();
            carry.putStringArrayListExtra("carryList", carried);
            startActivity(carry);
            finish();
        }else{
            //error message
            Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList carryOver(){
        /*
        Adds username to an array to be carried
        over to the next activity
         */
        EditText username;
        username = findViewById(R.id.inputUsername);
        String user = String.valueOf(username.getText());
        ArrayList<String> carried = new ArrayList<>();
        carried.add(user);
        return carried;
    }

    public void onButtonPress(){
        /*
        activates when button is pressed
         */
        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = switchState();
                if(checked == true){
                    validStaff();
                }
                if(checked == false){
                    validStudent();
                }
            }
        });
    }

}
