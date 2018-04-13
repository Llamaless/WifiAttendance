package com.example.kobej.attendancesystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Kobe Davis - 6905105
 * 07/04/18
 */

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*
        creating a wifi manager
        Essential to accessing all of the wifi
        */
        WifiManager wifiManager = (WifiManager)getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);

        forceWifi(wifiManager);
        onButtonPress();
    }

    public void forceWifi(WifiManager wifiManager){
        //Forces the wifi to be on allowing the app to work
        wifiManager.setWifiEnabled(true);
    }

    public void valid(){
        /*
        checks that username and password are valid
        */

        //getting username and password from there respective fields
        EditText username, password;
        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        String user = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());
        //comparing gathered values to known values
        if(pass.equals("pass")){
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
                valid();
            }
        });
    }

}
