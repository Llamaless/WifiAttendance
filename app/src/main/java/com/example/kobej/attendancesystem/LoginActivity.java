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
       // dbHandler.insertClassI("101SM", "Thursday", "00:81:c4:e7:39:0e");
        forceWifi(wifiManager);
        testData();
        onButtonPress();


    }
    public void testData(){
        //Test data
        dbHandler.insertStudent(005, "Kobe Davis");
        dbHandler.insertStaff(006, "Laman Temple");
        dbHandler.insertDetails(005, "test1", "test1");
        dbHandler.insertDetailsS(006, "staff1", "staff1");
        dbHandler.insertClass2("101SM", "Test Class", "8:00 AM", "11:00 PM", "Thursday"); //Need to change the day to whichever day it is when testing
        dbHandler.insertSC(005, "101SM");
        dbHandler.insertLC(006, "101SM");
        dbHandler.insertClassI("102SM", "Wednesday", "02:00:00:00:00:00"); //need to change the BSSID to the BSSID of the router your working on when testing
    }

    public void forceWifi(WifiManager wifiManager){
        //Forces the wifi to be on allowing the app to work
        wifiManager.setWifiEnabled(true);
    
    public boolean switchState(){
        /*
        Code to control the switch
         */
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
        Integer act = pass.size();
        String actualPassString = String.valueOf(actualPass.get(0));
        //comparing gathered values to known values
        if(act.equals(0)){
            Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
        }else {
            if (pass.equals(actualPassString)) {
                Intent carry = new Intent(LoginActivity.this, ConnectActivity.class);
                ArrayList<String> carried;
                carried = carryOver();
                carry.putStringArrayListExtra("carryList", carried);
                startActivity(carry);
                finish();
            } else {
                //error message
                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
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
